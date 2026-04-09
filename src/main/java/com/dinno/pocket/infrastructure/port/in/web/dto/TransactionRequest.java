package com.dinno.pocket.infrastructure.port.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Petición para registrar un nuevo movimiento financiero")
public class TransactionRequest {
    @Schema(description = "Monto de la transacción", example = "150.50")
    private BigDecimal amount;

    @Schema(description = "Descripción del movimiento", example = "Compra en supermercado")
    private String description;

    @Schema(description = "Categoría del gasto/ingreso", example = "COMIDA")
    private String category;

    @Schema(description = "Tipo de movimiento", allowableValues = {"INCOME", "EXPENSE"}, example = "EXPENSE")
    private String type; 
    
    @Schema(description = "Indica si es un gasto/ingreso fijo", example = "true")
    private Boolean isFixed;
}
