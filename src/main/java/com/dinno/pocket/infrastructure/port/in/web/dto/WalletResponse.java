package com.dinno.pocket.infrastructure.port.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resumen del estado financiero de una billetera")
public class WalletResponse {
    @Schema(description = "ID único de la billetera", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;
    private UUID userId;
    @Schema(description = "Saldo actual disponible", example = "1250.75")
    private BigDecimal balance;
    @Schema(description = "Total de ingresos del mes actual", example = "2500.00")
    private BigDecimal totalIncome;
    @Schema(description = "Total de egresos del mes actual", example = "1249.25")
    private BigDecimal totalExpense;
    @Schema(description = "Porcentaje de días transcurridos del mes", example = "45.0")
    private double monthProgress;
}
