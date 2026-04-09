package com.dinno.pocket.infrastructure.port.out.persistence.repository;

import com.dinno.pocket.infrastructure.port.out.persistence.entity.ProjectionEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface ProjectionReactiveRepository extends ReactiveCrudRepository<ProjectionEntity, UUID> {
    @Query("SELECT * FROM projections WHERE wallet_id = :walletId " +
            "AND EXTRACT(MONTH FROM projection_date) = EXTRACT(MONTH FROM CAST(:date AS DATE)) " +
            "AND EXTRACT(YEAR FROM projection_date) = EXTRACT(YEAR FROM CAST(:date AS DATE))")
    Mono<ProjectionEntity> findByWalletIdAndMonth(UUID walletId, LocalDate date);
}
