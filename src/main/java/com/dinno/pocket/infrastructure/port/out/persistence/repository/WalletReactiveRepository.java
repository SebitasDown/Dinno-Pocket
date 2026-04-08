package com.dinno.pocket.infrastructure.port.out.persistence.repository;

import com.dinno.pocket.infrastructure.port.out.persistence.entity.WalletEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface WalletReactiveRepository extends ReactiveCrudRepository<WalletEntity, UUID> {
    
    Mono<WalletEntity> findByUserId(UUID userId);
}
