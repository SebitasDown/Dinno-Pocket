package com.dinno.pocket.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Projection {
    private UUID id;
    private UUID walletId;
    private BigDecimal balanceAtMoment;
    private BigDecimal remainingFixedExpenses;
    private BigDecimal estimatedVariables;

    public Projection(UUID id, UUID walletId, BigDecimal balanceAtMoment, BigDecimal remainingFixedExpenses, BigDecimal estimatedVariables) {
        this.id = id;
        this.walletId = walletId;
        this.balanceAtMoment = balanceAtMoment;
        this.remainingFixedExpenses = remainingFixedExpenses;
        this.estimatedVariables = estimatedVariables;
    }

    // Esto es para el margen de maniobra
    public  BigDecimal calculateManeuverMargin() {
        return balanceAtMoment.subtract(remainingFixedExpenses).subtract(estimatedVariables);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getBalanceAtMoment() {
        return balanceAtMoment;
    }

    public void setBalanceAtMoment(BigDecimal balanceAtMoment) {
        this.balanceAtMoment = balanceAtMoment;
    }

    public BigDecimal getRemainingFixedExpenses() {
        return remainingFixedExpenses;
    }

    public void setRemainingFixedExpenses(BigDecimal remainingFixedExpenses) {
        this.remainingFixedExpenses = remainingFixedExpenses;
    }

    public BigDecimal getEstimatedVariables() {
        return estimatedVariables;
    }

    public void setEstimatedVariables(BigDecimal estimatedVariables) {
        this.estimatedVariables = estimatedVariables;
    }
}
