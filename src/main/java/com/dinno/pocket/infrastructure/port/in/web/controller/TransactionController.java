package com.dinno.pocket.infrastructure.port.in.web.controller;

import com.dinno.pocket.domain.port.in.GetExpensesByCategoryUseCase;
import com.dinno.pocket.domain.port.in.GetrecentTransactionsUseCase;
import com.dinno.pocket.domain.port.in.RegisterTransactionUseCase;
import com.dinno.pocket.domain.model.ExpenseCategory;
import com.dinno.pocket.infrastructure.port.in.web.dto.TransactionRequest;
import com.dinno.pocket.infrastructure.port.in.web.dto.TransactionResponse;
import com.dinno.pocket.infrastructure.port.in.web.mapper.WebTransactionMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Endpoints para el registro y consulta de movimientos financieros")
public class TransactionController {

    private final RegisterTransactionUseCase registerUseCase;
    private final GetrecentTransactionsUseCase recentTransactionsUseCase;
    private final GetExpensesByCategoryUseCase expensesByCategoryUseCase;
    private final WebTransactionMapper mapper;

    @PostMapping
    @Operation(summary = "Registrar movimiento", description = "Registra un ingreso o egreso en la billetera del usuario. Si no se envía walletId, se usará la billetera principal del usuario.")
    public Mono<TransactionResponse> register(
            @RequestBody TransactionRequest request,
            @Parameter(description = "ID del usuario obtenido desde el Gateway", example = "00000000-0000-0000-0000-000000000000")
            @RequestHeader("X-User-Id") String userId) {
        return registerUseCase.register(mapper.toDomain(request), UUID.fromString(userId))
                .map(mapper::toResponse);
    }

    @GetMapping("/recent")
    @Operation(summary = "Historial reciente", description = "Obtiene las transacciones más recientes filtradas por billetera")
    public Flux<TransactionResponse> getRecent(
            @RequestHeader("X-User-Id") String userId,
            @Parameter(description = "Número máximo de transacciones a devolver")
            @RequestParam(defaultValue = "10") int limit) {
        return recentTransactionsUseCase.execute(UUID.fromString(userId), limit)
                .map(mapper::toResponse);
    }

    @GetMapping("/categories")
    @Operation(summary = "Gastos por categoría", description = "Devuelve el total acumulado de gastos agrupados por categoría del mes actual")
    public Mono<Map<ExpenseCategory, BigDecimal>> getByCategory(@RequestHeader("X-User-Id") String userId) {
        return expensesByCategoryUseCase.execute(UUID.fromString(userId));
    }
}
