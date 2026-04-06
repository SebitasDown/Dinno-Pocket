package com.dinno.pocket.domain.port.in;

import com.dinno.pocket.domain.model.Transaction;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface GetrecentTransactionsUseCase {

    Flux<Transaction> execute(UUID userId, int limit);
}
