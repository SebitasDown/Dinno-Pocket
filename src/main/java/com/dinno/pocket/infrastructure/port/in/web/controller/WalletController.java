package com.dinno.pocket.infrastructure.port.in.web.controller;

import com.dinno.pocket.domain.port.in.GetMounthProjectionUseCase;
import com.dinno.pocket.domain.port.in.GetWalletSummaryUseCase;
import com.dinno.pocket.infrastructure.port.in.web.dto.ProjectionResponse;
import com.dinno.pocket.infrastructure.port.in.web.dto.WalletResponse;
import com.dinno.pocket.infrastructure.port.in.web.mapper.WebProjectionMapper;
import com.dinno.pocket.infrastructure.port.in.web.mapper.WebWalletMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
@Tag(name = "Wallets & Projections", description = "Endpoints para el estado de la billetera y proyecciones de ahorro")
public class WalletController {

    private final GetWalletSummaryUseCase summaryUseCase;
    private final GetMounthProjectionUseCase projectionUseCase;
    private final WebWalletMapper walletMapper;
    private final WebProjectionMapper projectionMapper;

    @GetMapping("/summary")
    @Operation(summary = "Resumen de billetera", description = "Obtiene el saldo total, ingresos/egresos del mes y el progreso del mes")
    public Mono<WalletResponse> getSummary(@RequestHeader("X-User-Id") String userId) {
        return summaryUseCase.getByUserId(UUID.fromString(userId))
                .map(walletMapper::toResponse);
    }

    @GetMapping("/projection")
    @Operation(summary = "Proyección mensual", description = "Calcula el presupuesto restante y el margen de maniobra para el mes actual")
    public Mono<ProjectionResponse> getProjection(@RequestHeader("X-User-Id") String userId) {
        return projectionUseCase.execute(UUID.fromString(userId))
                .map(projectionMapper::toResponse);
    }
}
