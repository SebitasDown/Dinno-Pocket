package com.dinno.pocket.application.service;

import com.dinno.pocket.application.exception.NotFound;
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
        }
        if (transaction.getCreateAt() == null){
            transaction.setCreateAt(LocalDateTime.now());
        }
        return walletRepositoryPort.findById(transaction.getWalletId())
                .switchIfEmpty(Mono.error(new NotFound("Bulletera no encontrada")))
                .flatMap(wallet -> {
                    wallet.updateBalance(transaction.getAmount(), transaction.getType());
                    log.info("Nuevo saldo calculado. {}", wallet.getBalance());

                    return walletRepositoryPort.save(wallet)

                            .then(transactionRepositoryPort.save(transaction));
                })
                .doOnSuccess(t -> log.info("Transaccion registrada con exito: {}", t.getId()))
                .doOnError(e -> log.error("Error registrando la transaccion: {}", e.getMessage()));
    }
}
