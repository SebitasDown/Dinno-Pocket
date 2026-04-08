package com.dinno.pocket.domain.port.out;

import com.dinno.pocket.domain.model.ExpenseCategory;
import com.dinno.pocket.domain.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public interface TransactionRepositoryPort {

    Mono<Transaction> save (Transaction transaction);

    Flux<Transaction> findRecentByWalletId(UUID walletId, int limit);

    Mono<Map<ExpenseCategory, BigDecimal>> sumExpensesByCategory(UUID walletId, int month, int year);

}
