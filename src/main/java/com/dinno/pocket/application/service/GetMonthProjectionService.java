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

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.UUID;

@Slf4j
@Service
public class GetMonthProjectionService implements GetMounthProjectionUseCase {

    private final WalletRepositoryPort walletRepositoryPort;
    private final ProjectionRepositoryPort projectionRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;

    public GetMonthProjectionService(WalletRepositoryPort walletRepositoryPort, ProjectionRepositoryPort projectionRepositoryPort, TransactionRepositoryPort transactionRepositoryPort) {
        this.walletRepositoryPort = walletRepositoryPort;
        this.projectionRepositoryPort = projectionRepositoryPort;
        this.transactionRepositoryPort = transactionRepositoryPort;
    }

    @Override
    public Mono<Projection> execute(UUID userId) {
        log.info("Generando proyeccion financiera para el usuario: {}", userId);

        LocalDate now = LocalDate.now();
        LocalDate previousMonth = now.minusMonths(1);

        return walletRepositoryPort.findByUserId(userId)
                .switchIfEmpty(Mono.error(new WalletNotFoundException("Billetera no encontrada")))
                .flatMap(wallet -> {
                    Mono<Projection> currentProjectionMono = getCurrentProjection(wallet, now);
                    Mono<Projection> previousProjectionMono = projectionRepositoryPort.findByWalletIdAndMonth(wallet.getId(), previousMonth);

                    return Mono.zip(currentProjectionMono, previousProjectionMono.defaultIfEmpty(new Projection()))
                            .map(tuple -> {
                                Projection current = tuple.getT1();
                                Projection previous = tuple.getT2();

                                if (previous.getId() != null) {
                                    
                                    previous.setBalanceAtMoment(wallet.getBalance());
                                    BigDecimal variation = current.calculateManeuverMargin()
                                            .subtract(previous.calculateManeuverMargin());
                                    current.setSavingsVariation(variation);
                                } else {
                                    current.setSavingsVariation(java.math.BigDecimal.ZERO);
                                }
                                return current;
                            });
                });
    }

    private Mono<Projection> getCurrentProjection(com.dinno.pocket.domain.model.Wallet wallet, LocalDate now) {
        int month = now.getMonthValue();
        int year = now.getYear();

        Mono<BigDecimal> fixedMono = getTransactionRepositoryPort().sumFixedExpenses(wallet.getId(), month, year);
        Mono<BigDecimal> varMono = getTransactionRepositoryPort().sumVariableExpenses(wallet.getId(), month, year);

        return projectionRepositoryPort.findByWalletIdAndMonth(wallet.getId(), now)
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("No se encontró proyección para el mes actual, creando una por defecto");
                    Projection defaultProjection = new Projection();
                    defaultProjection.setId(UUID.randomUUID());
                    defaultProjection.setWalletId(wallet.getId());
                    defaultProjection.setBalanceAtMoment(wallet.getBalance());
                    defaultProjection.setTargetSavings(java.math.BigDecimal.ZERO);
                    defaultProjection.setProjectionDate(now);
                    return Mono.just(defaultProjection);
                }))
                .flatMap(projection -> {
                    projection.setBalanceAtMoment(wallet.getBalance());
                    return Mono.zip(fixedMono, varMono)
                            .map(tuple -> {
                                projection.setRemainingFixedExpenses(tuple.getT1());
                                projection.setEstimatedVariables(tuple.getT2());
                                return projection;
                            });
                });
    }

    private TransactionRepositoryPort getTransactionRepositoryPort() {
        return this.transactionRepositoryPort;
    }
}
