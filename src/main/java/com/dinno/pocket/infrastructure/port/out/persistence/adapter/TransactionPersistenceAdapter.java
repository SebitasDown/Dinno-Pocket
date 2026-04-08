package com.dinno.pocket.infrastructure.port.out.persistence.adapter;

import com.dinno.pocket.domain.model.ExpenseCategory;
import com.dinno.pocket.domain.model.Transaction;
import com.dinno.pocket.domain.port.out.TransactionRepositoryPort;
import com.dinno.pocket.infrastructure.port.out.persistence.mapper.TransactionMapper;
import com.dinno.pocket.infrastructure.port.out.persistence.repository.TransactionReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransactionPersistenceAdapter implements TransactionRepositoryPort {

    private final TransactionReactiveRepository repository;
    private final TransactionMapper mapper;

    @Override
    public Mono<Transaction> save(Transaction transaction) {
        com.dinno.pocket.infrastructure.port.out.persistence.entity.TransactionEntity entity = mapper.toEntity(transaction);
        
        return repository.findById(entity.getId() != null ? entity.getId() : java.util.UUID.randomUUID())
                .map(existing -> {
                    entity.setVersion(existing.getVersion());
                    return entity;
                })
                .defaultIfEmpty(entity)
                .flatMap(repository::save)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Transaction> findRecentByWalletId(UUID walletId, int limit) {
        return repository.findRecentByWalletId(walletId, limit)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Map<ExpenseCategory, BigDecimal>> sumExpensesByCategory(UUID walletId, int month, int year) {
        return repository.sumAmountByCategory(walletId, month, year)
                .collect(Collectors.toMap(
                        item -> {
                            try {
                                return ExpenseCategory.valueOf(item.getCategory().toUpperCase());
                            } catch (Exception e) {
                                return ExpenseCategory.OTROS;
                            }
                        },
                        item -> item.getTotal() != null ? item.getTotal() : BigDecimal.ZERO,
                        BigDecimal::add
                ));
    }
}
