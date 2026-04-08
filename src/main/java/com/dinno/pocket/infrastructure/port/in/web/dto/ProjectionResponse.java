package com.dinno.pocket.infrastructure.port.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Proyección financiera y margen de ahorro mensual")
public class ProjectionResponse {
    @Schema(description = "Identificador único de la proyección")
    private UUID id;
    @Schema(description = "Saldo al momento de la consulta")
    private BigDecimal balanceAtMoment;
    @Schema(description = "Gastos fijos restantes")
    private BigDecimal remainingFixedExpenses;
    @Schema(description = "Gastos variables estimados")
    private BigDecimal estimatedVariables;
    @Schema(description = "Meta de ahorro mensual", example = "500.00")
    private BigDecimal targetSavings;
    @Schema(description = "Fecha de la proyección")
    private LocalDate projectionDate;
    @Schema(description = "Presupuesto restante para gastos variables", example = "300.00")
    private BigDecimal maneuverMargin;
}
