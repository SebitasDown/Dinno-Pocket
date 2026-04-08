package com.dinno.pocket.infrastructure.port.out.persistence.adapter;

import com.dinno.pocket.domain.model.Projection;
import com.dinno.pocket.domain.port.out.ProjectionRepositoryPort;
import com.dinno.pocket.infrastructure.port.out.persistence.entity.ProjectionEntity;
import com.dinno.pocket.infrastructure.port.out.persistence.mapper.ProjectionMapper;
import com.dinno.pocket.infrastructure.port.out.persistence.repository.ProjectionReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProjectionPersistenceAdapter implements ProjectionRepositoryPort {

    private final ProjectionReactiveRepository repository;
    private final ProjectionMapper mapper;

    @Override
    public Mono<Projection> findByWalletIdAndMonth(UUID walletId, LocalDate date) {
        return repository.findByWalletIdAndMonth(walletId, date)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Projection> save(Projection projection) {
        ProjectionEntity entity = mapper.toEntity(projection);
        
        return repository.findById(entity.getId())
                .map(existing -> {
                    entity.setVersion(existing.getVersion());
                    return entity;
                })
                .defaultIfEmpty(entity)
                .flatMap(repository::save)
                .map(mapper::toDomain);
    }
}
