package com.dinno.pocket.infrastructure.port.out.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Version;

@Table("transactions")
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class TransactionEntity {
    @Id
    private UUID id;

    @Column("wallet_id")
    private UUID walletId;

    @Column("description")
    private String description;
    private BigDecimal amount;
    private String category;
    private String type;

    @Column("create_at")
    private LocalDateTime createdAt;
    
    @Column("is_fixed")
    private Boolean isFixed;

    @Version
    private Long version;
}
