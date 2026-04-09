package com.dinno.pocket.infrastructure.port.out.persistence.repository;

import com.dinno.pocket.infrastructure.port.out.persistence.entity.TransactionEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.math.BigDecimal;

@Repository
public interface TransactionReactiveRepository extends ReactiveCrudRepository<TransactionEntity, UUID> {

    Flux<TransactionEntity> findAllByWalletIdOrderByCreatedAtDesc(UUID walletId);

    @Query("SELECT * FROM transactions WHERE wallet_id = :walletId ORDER BY create_at DESC LIMIT :limit")
    Flux<TransactionEntity> findRecentByWalletId(UUID walletId, int limit);

    @Query("SELECT category, SUM(amount) as total FROM transactions " +
            "WHERE wallet_id = :walletId AND type = 'EXPENSE' " +
            "AND EXTRACT(MONTH FROM create_at) = :month " +
            "AND EXTRACT(YEAR FROM create_at) = :year " +
            "GROUP BY category")
    Flux<CategorySum> sumAmountByCategory(UUID walletId, int month, int year);
    
    @Query("SELECT SUM(amount) FROM transactions WHERE wallet_id = :walletId AND type = 'EXPENSE' AND is_fixed = true AND EXTRACT(MONTH FROM create_at) = :month AND EXTRACT(YEAR FROM create_at) = :year")
    Mono<BigDecimal> sumFixedExpenses(UUID walletId, int month, int year);
    
    @Query("SELECT SUM(amount) FROM transactions WHERE wallet_id = :walletId AND type = 'EXPENSE' AND is_fixed = false AND EXTRACT(MONTH FROM create_at) = :month AND EXTRACT(YEAR FROM create_at) = :year")
    Mono<BigDecimal> sumVariableExpenses(UUID walletId, int month, int year);
}

