package com.dinno.pocket.domain.port.in;

import com.dinno.pocket.domain.model.Projection;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GetMounthProjectionUseCase {
    Mono<Projection> execute(UUID userId);
}
