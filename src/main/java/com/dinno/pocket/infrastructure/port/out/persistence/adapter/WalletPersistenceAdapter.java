package com.dinno.pocket.infrastructure.port.out.persistence.adapter;

import com.dinno.pocket.domain.model.Wallet;
import com.dinno.pocket.domain.port.out.WalletRepositoryPort;
import com.dinno.pocket.infrastructure.port.out.persistence.entity.WalletEntity;
import com.dinno.pocket.infrastructure.port.out.persistence.mapper.WalletMapper;
import com.dinno.pocket.infrastructure.port.out.persistence.repository.WalletReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WalletPersistenceAdapter implements WalletRepositoryPort {

    private final WalletReactiveRepository repository;
    private final WalletMapper mapper;

    @Override
    public Mono<Wallet> findByUserId(UUID userId) {
        return repository.findByUserId(userId)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Wallet> save(Wallet wallet) {
        WalletEntity entity = mapper.toEntity(wallet);
        
        
        return repository.findById(entity.getId())
                .map(existing -> {
                    entity.setVersion(existing.getVersion());
                    return entity;
                })
                .defaultIfEmpty(entity)
                .flatMap(repository::save)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Wallet> findById(UUID walletId) {
        return repository.findById(walletId)
                .map(mapper::toDomain);
    }
}
