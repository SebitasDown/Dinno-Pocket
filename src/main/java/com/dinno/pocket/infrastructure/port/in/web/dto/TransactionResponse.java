package com.dinno.pocket.infrastructure.port.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detalle de una transacción registrada")
public class TransactionResponse {
    @Schema(description = "ID único de la transacción", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Monto de la transacción", example = "150.50")
    private BigDecimal amount;

    @Schema(description = "Descripción del movimiento", example = "Compra en supermercado")
    private String description;

    @Schema(description = "Categoría", example = "COMIDA")
    private String category;

    @Schema(description = "Tipo de movimiento", example = "EXPENSE")
    private String type;

    @Schema(description = "Fecha de creación", example = "2026-04-07T21:12:44")
    private LocalDateTime createdAt;
    
    @Schema(description = "Indica si es un gasto/ingreso fijo", example = "true")
    private Boolean isFixed;
}
