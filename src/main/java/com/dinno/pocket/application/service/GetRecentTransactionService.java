package com.dinno.pocket.application.service;

import com.dinno.pocket.domain.model.Transaction;
import com.dinno.pocket.domain.port.in.GetrecentTransactionsUseCase;
import com.dinno.pocket.domain.port.out.TransactionRepositoryPort;
import com.dinno.pocket.domain.port.out.WalletRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.dinno.pocket.application.exception.WalletNotFoundException;

import java.util.UUID;

@Slf4j
@Service
public class GetRecentTransactionService implements GetrecentTransactionsUseCase {

    private final WalletRepositoryPort walletRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;


    public GetRecentTransactionService(WalletRepositoryPort walletRepositoryPort, TransactionRepositoryPort transactionRepositoryPort) {
        this.walletRepositoryPort = walletRepositoryPort;
        this.transactionRepositoryPort = transactionRepositoryPort;
    }

    @Override
    public Flux<Transaction> execute(UUID userId, int limit) {
        log.info("Cargando las {} transacciones mas recientes para el usuario: {}", limit, userId);

      return walletRepositoryPort.findByUserId(userId)
              .switchIfEmpty(Mono.error(new WalletNotFoundException("No se encontró billetera asociada al consultar transacciones")))
              .flatMapMany(wallet -> {
                  log.info("Billetera encontrada con ID: {}. Obteniendo transacciones...", wallet.getId());
                  return transactionRepositoryPort.findRecentByWalletId(wallet.getId(), limit);
              })
              .doOnComplete(() -> log.info("Consulta de transacciones recientes ejecutada exitosamente para el usuario: {}", userId))
              .doOnError(e -> log.error("Error al obtener transacciones recientes: {}", e.getMessage()));
    }
}
