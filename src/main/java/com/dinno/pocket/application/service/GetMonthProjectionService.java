package com.dinno.pocket.application.service;

import com.dinno.pocket.application.exception.WalletNotFoundException;
import com.dinno.pocket.domain.model.Projection;
import com.dinno.pocket.domain.port.in.GetMounthProjectionUseCase;
import com.dinno.pocket.domain.port.out.ProjectionRepositoryPort;
import com.dinno.pocket.domain.port.out.TransactionRepositoryPort;
import com.dinno.pocket.domain.port.out.WalletRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
public class GetMonthProjectionService implements GetMounthProjectionUseCase {

    private final WalletRepositoryPort walletRepositoryPort;
    private final ProjectionRepositoryPort projectionRepositoryPort;

    public GetMonthProjectionService(WalletRepositoryPort walletRepositoryPort, ProjectionRepositoryPort projectionRepositoryPort) {
        this.walletRepositoryPort = walletRepositoryPort;
        this.projectionRepositoryPort = projectionRepositoryPort;
    }

    @Override
    public Mono<Projection> execute(UUID userId) {
        log.info("Generando proyeccion financiera para el usuario: {}", userId);

        LocalDate now = LocalDate.now();

        return walletRepositoryPort.findById(userId)
                .switchIfEmpty(Mono.error(new WalletNotFoundException("Billetera no encontrada")))
                .flatMap(wallet -> projectionRepositoryPort.findByWalletIdAndMonth(wallet.getId(), now)
                        .map(projection -> {

                            projection.setBalanceAtMoment(wallet.getBalance());
                            return projection;
                                })

                        );
    }
}
