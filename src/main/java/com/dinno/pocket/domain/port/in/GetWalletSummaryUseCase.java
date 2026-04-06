package com.dinno.pocket.domain.port.in;

import com.dinno.pocket.domain.model.Wallet;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GetWalletSummaryUseCase {
    Mono<Wallet> getByUserId(UUID userId);
}
