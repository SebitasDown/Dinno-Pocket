package com.dinno.pocket.application.service;

import com.dinno.pocket.application.exception.WalletNotFoundException;
import com.dinno.pocket.domain.model.ExpenseCategory;
import com.dinno.pocket.domain.port.in.GetExpensesByCategoryUseCase;
import com.dinno.pocket.domain.port.out.TransactionRepositoryPort;
import com.dinno.pocket.domain.port.out.WalletRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class GetExpensesByCategoryService implements GetExpensesByCategoryUseCase {

    private final WalletRepositoryPort walletRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;

    public GetExpensesByCategoryService(WalletRepositoryPort walletRepositoryPort, TransactionRepositoryPort transactionRepositoryPort) {
        this.walletRepositoryPort = walletRepositoryPort;
        this.transactionRepositoryPort = transactionRepositoryPort;
    }

    @Override
    public Mono<Map<ExpenseCategory, BigDecimal>> execute(UUID userId) {
        log.info("Calculando gastos por categoria para el usuario: {}", userId);

        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        return walletRepositoryPort.findByUserId(userId)
                .switchIfEmpty(Mono.error(new WalletNotFoundException("Billetera no encontrada")))

                // se suman las categorias
                .flatMap(wallet -> transactionRepositoryPort.sumExpensesByCategory(
                        wallet.getId(),
                        currentMonth,
                        currentYear
                ));
    }
}
