package com.dinno.pocket.application.service;

import com.dinno.pocket.domain.model.Wallet;
import com.dinno.pocket.domain.port.in.GetWalletSummaryUseCase;
import com.dinno.pocket.domain.port.out.WalletRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class GetWalletSummaryService implements GetWalletSummaryUseCase {

    private final WalletRepositoryPort walletRepositoryPort;

    public GetWalletSummaryService(WalletRepositoryPort walletRepositoryPort) {
        this.walletRepositoryPort = walletRepositoryPort;
    }

    @Override
    public Mono<Wallet> getByUserId(UUID userId) {
        log.info("Consultando resumen de billetera para el usuario: {}", userId);

        return walletRepositoryPort.findByUserId(userId)
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("No se encontró billetera para el usuario {}. Creando nueva billetera...", userId);
                    Wallet newWallet = new Wallet();
                    newWallet.setId(UUID.randomUUID());
                    newWallet.setUserId(userId);
                    newWallet.setBalance(java.math.BigDecimal.ZERO);
                    newWallet.setTotalIncome(java.math.BigDecimal.ZERO);
                    newWallet.setTotalExpense(java.math.BigDecimal.ZERO);
                    return walletRepositoryPort.save(newWallet);
                }))
                .doOnSuccess(wallet -> log.info("Resumen de billetera obtenido exitosamente para la billetera: {}", wallet.getId()))
                .doOnError(e -> log.error("Error obteniendo resumen de billetera: {}", e.getMessage()));
    }
}
