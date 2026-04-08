package com.dinno.pocket.domain.port.in;

import com.dinno.pocket.domain.model.ExpenseCategory;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public interface GetExpensesByCategoryUseCase {
    Mono<Map<ExpenseCategory, BigDecimal>> execute(UUID userId);
}
