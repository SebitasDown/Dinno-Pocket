package com.dinno.pocket.application.service;

import com.dinno.pocket.application.exception.WalletNotFoundException;
import com.dinno.pocket.domain.model.Transaction;
import com.dinno.pocket.domain.model.Wallet;
import com.dinno.pocket.domain.port.in.RegisterTransactionUseCase;
import com.dinno.pocket.domain.port.out.TransactionRepositoryPort;
import com.dinno.pocket.domain.port.out.WalletRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class RegisterTransactionService implements RegisterTransactionUseCase {

    private final WalletRepositoryPort walletRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;

    public RegisterTransactionService(WalletRepositoryPort walletRepositoryPort, TransactionRepositoryPort transactionRepositoryPort) {
        this.walletRepositoryPort = walletRepositoryPort;
        this.transactionRepositoryPort = transactionRepositoryPort;
    }

    @Override
    @Transactional
    public Mono<Transaction> register(Transaction transaction, UUID userId) {
        log.info("Iniciando registro de transacción para el usuario: {}", userId);

        // Preparamos la transacción
        if (transaction.getId() == null) {
            transaction.setId(UUID.randomUUID());
        }
        if (transaction.getCreateAt() == null) {
            transaction.setCreateAt(LocalDateTime.now());
        }

        // Buscamos la billetera. Si no viene walletId, buscamos por userId
        Mono<Wallet> walletMono;
        if (transaction.getWalletId() == null) {
            log.info("No se proporcionó walletId, resolviendo por userId: {}", userId);
            walletMono = walletRepositoryPort.findByUserId(userId);
        } else {
            log.info("Se proporcionó walletId: {}. Buscando...", transaction.getWalletId());
            walletMono = walletRepositoryPort.findById(transaction.getWalletId());
        }

        return walletMono
                .switchIfEmpty(Mono.error(() -> {
                    String msg = transaction.getWalletId() == null ? 
                        "No se encontró billetera asociada al usuario " + userId :
                        "No se encontró la billetera con ID " + transaction.getWalletId();
                    return new WalletNotFoundException(msg);
                }))
                .flatMap(wallet -> {
                    // Aseguramos que la transacción se asocie a la billetera correcta
                    transaction.setWalletId(wallet.getId());
                    
                    wallet.updateBalance(transaction.getAmount(), transaction.getType());
                    log.info("Actualizando billetera {}. Nuevo saldo: {}", wallet.getId(), wallet.getBalance());

                    return walletRepositoryPort.save(wallet)
                            .then(transactionRepositoryPort.save(transaction));
                })
                .doOnSuccess(t -> log.info("Transacción registrada con éxito ID: {}", t.getId()))
                .doOnError(e -> log.error("Error al registrar transacción: {}", e.getMessage()));
    }
}
