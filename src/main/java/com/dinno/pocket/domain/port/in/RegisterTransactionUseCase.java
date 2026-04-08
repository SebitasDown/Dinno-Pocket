package com.dinno.pocket.domain.port.in;

import com.dinno.pocket.domain.model.Transaction;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RegisterTransactionUseCase {
    Mono<Transaction> register(Transaction transaction, UUID userId);
}
