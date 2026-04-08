package com.dinno.pocket.domain.port.out;

import com.dinno.pocket.domain.model.Wallet;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface WalletRepositoryPort {

    Mono<Wallet> findByUserId(UUID userId);

    Mono<Wallet> save(Wallet wallet);

    Mono<Wallet> findById(UUID walletId);
}
