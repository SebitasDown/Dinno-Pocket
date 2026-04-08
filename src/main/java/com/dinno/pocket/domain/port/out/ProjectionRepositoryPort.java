package com.dinno.pocket.domain.port.out;

import com.dinno.pocket.domain.model.Projection;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

public interface ProjectionRepositoryPort {

    Mono<Projection> findByWalletIdAndMonth(UUID walletId, LocalDate date);

    Mono<Projection> save(Projection projection);
}
