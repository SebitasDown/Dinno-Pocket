package com.dinno.pocket.application.service;

import com.dinno.pocket.application.exception.WalletNotFoundException;
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

        return walletRepositoryPort.findById(userId)
                .switchIfEmpty(Mono.error(new WalletNotFoundException("El usuario aun no tiene una billetera asociada")))
                .doOnSuccess(wallet -> log.info("Resumen de billetera obtenido exitosamente para la billetera: {}", wallet.getId()))
                .doOnError(e -> log.error("Error obteniendo resumen de billetera: {}", e.getMessage()));
    }
}
