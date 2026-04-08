package com.dinno.pocket.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Projection {
    private UUID id;
    private UUID walletId;
    private BigDecimal balanceAtMoment;
    private BigDecimal remainingFixedExpenses;
    private BigDecimal estimatedVariables;
    private LocalDate projectionDate;
    private BigDecimal targetSavings;

    public Projection() {}

    public Projection(UUID id, UUID walletId, BigDecimal balanceAtMoment, BigDecimal remainingFixedExpenses, BigDecimal estimatedVariables, LocalDate projectionDate, BigDecimal targetSavings) {
        this.id = id;
        this.walletId = walletId;
        this.balanceAtMoment = balanceAtMoment;
        this.remainingFixedExpenses = remainingFixedExpenses;
        this.estimatedVariables = estimatedVariables;
        this.projectionDate = projectionDate;
        this.targetSavings = targetSavings;
    }

    public BigDecimal calculateManeuverMargin() {
        BigDecimal currentBalance = this.balanceAtMoment != null ? this.balanceAtMoment : BigDecimal.ZERO;
        BigDecimal fixed = this.remainingFixedExpenses != null ? this.remainingFixedExpenses : BigDecimal.ZERO;
        BigDecimal variables = this.estimatedVariables != null ? this.estimatedVariables : BigDecimal.ZERO;
        return currentBalance.subtract(fixed).subtract(variables);
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getWalletId() { return walletId; }
    public void setWalletId(UUID walletId) { this.walletId = walletId; }
    public BigDecimal getBalanceAtMoment() { return balanceAtMoment; }
    public void setBalanceAtMoment(BigDecimal balanceAtMoment) { this.balanceAtMoment = balanceAtMoment; }
    public BigDecimal getRemainingFixedExpenses() { return remainingFixedExpenses; }
    public void setRemainingFixedExpenses(BigDecimal remainingFixedExpenses) { this.remainingFixedExpenses = remainingFixedExpenses; }
    public BigDecimal getEstimatedVariables() { return estimatedVariables; }
    public void setEstimatedVariables(BigDecimal estimatedVariables) { this.estimatedVariables = estimatedVariables; }
    public LocalDate getProjectionDate() { return projectionDate; }
    public void setProjectionDate(LocalDate projectionDate) { this.projectionDate = projectionDate; }
    public BigDecimal getTargetSavings() { return targetSavings; }
    public void setTargetSavings(BigDecimal targetSavings) { this.targetSavings = targetSavings; }

    public static ProjectionBuilder builder() {
        return new ProjectionBuilder();
    }

    public static class ProjectionBuilder {
        private UUID id;
        private UUID walletId;
        private BigDecimal balanceAtMoment;
        private BigDecimal remainingFixedExpenses;
        private BigDecimal estimatedVariables;
        private LocalDate projectionDate;
        private BigDecimal targetSavings;

        public ProjectionBuilder id(UUID id) { this.id = id; return this; }
        public ProjectionBuilder walletId(UUID walletId) { this.walletId = walletId; return this; }
        public ProjectionBuilder balanceAtMoment(BigDecimal balance) { this.balanceAtMoment = balance; return this; }
        public ProjectionBuilder remainingFixedExpenses(BigDecimal fixed) { this.remainingFixedExpenses = fixed; return this; }
        public ProjectionBuilder estimatedVariables(BigDecimal variables) { this.estimatedVariables = variables; return this; }
        public ProjectionBuilder projectionDate(LocalDate date) { this.projectionDate = date; return this; }
        public ProjectionBuilder targetSavings(BigDecimal target) { this.targetSavings = target; return this; }
        public Projection build() {
            return new Projection(id, walletId, balanceAtMoment, remainingFixedExpenses, estimatedVariables, projectionDate, targetSavings);
        }
    }
}
