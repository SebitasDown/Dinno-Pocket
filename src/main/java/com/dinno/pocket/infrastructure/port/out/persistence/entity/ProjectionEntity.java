package com.dinno.pocket.infrastructure.port.out.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Version;

@Table("projections")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ProjectionEntity {
    @Id
    private UUID id;

    @Column("wallet_id")
    private UUID walletId;

    @Column("fixed_expenses_remaining")
    private BigDecimal fixedExpensesRemaining;

    @Column("estimated_variable_expenses")
    private BigDecimal estimatedVariableExpenses;

    @Column("target_savings")
    private BigDecimal targetSavings;

    @Column("projection_date")
    private LocalDate projectionDate;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Version
    private Long version;
}