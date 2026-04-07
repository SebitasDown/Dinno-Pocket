package com.dinno.pocket.application.service;

import com.dinno.pocket.application.exception.WalletNotFoundException;
import com.dinno.pocket.domain.model.Transaction;
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
    public Mono<Transaction> register(Transaction transaction) {
        log.info("Iniciando registro de transación para la billetera: {}", transaction.getWalletId());


        // Si no existen se crean estos valores
        if (transaction.getId() == null){
            transaction.setId(UUID.randomUUID());
            log.debug("Se generó un nuevo ID de transacción: {}", transaction.getId());
        }
        if (transaction.getCreateAt() == null){
            transaction.setCreateAt(LocalDateTime.now());
        }
        return walletRepositoryPort.findById(transaction.getWalletId())
                .switchIfEmpty(Mono.error(new WalletNotFoundException("Billetera no encontrada")))
                .flatMap(wallet -> {
                    wallet.updateBalance(transaction.getAmount(), transaction.getType());
                    log.info("Nuevo saldo calculado para la billetera {}: {}", wallet.getId(), wallet.getBalance());

                    return walletRepositoryPort.save(wallet)
                            .doOnSuccess(w -> log.info("Billetera actualizada guardada en base de datos"))

                            .then(transactionRepositoryPort.save(transaction));
                })
                .doOnSuccess(t -> log.info("Transaccion registrada con exito: {}", t.getId()))
                .doOnError(e -> log.error("Error registrando la transaccion: {}", e.getMessage()));
    }
}
