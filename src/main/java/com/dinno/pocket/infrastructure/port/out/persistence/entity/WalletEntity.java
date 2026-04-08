package com.dinno.pocket.infrastructure.port.out.persistence.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Version;

@Table("wallets")
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class WalletEntity {
    @Id
    private UUID id;

    @Column("user_id")
    private UUID userId;

    private BigDecimal balance;

    @Column("total_income")
    private BigDecimal totalIncome;

    @Column("total_expense")
    private BigDecimal totalExpense;

    private String currency;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}