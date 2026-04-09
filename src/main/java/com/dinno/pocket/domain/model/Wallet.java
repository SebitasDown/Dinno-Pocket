package com.dinno.pocket.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Wallet {
    private UUID id;
    private UUID userId;
    private BigDecimal balance;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private String currency;

    public Wallet() {}

    public Wallet(UUID id, UUID userId, BigDecimal balance, BigDecimal totalIncome, BigDecimal totalExpense, String currency) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.currency = currency;
    }

    // calcular el porcentaje de mes transcurrido
    public double calculateMonthProgress() {
        java.time.LocalDate now = java.time.LocalDate.now();
        int totalDays = now.lengthOfMonth();
        int dayOfMonth = now.getDayOfMonth();
        return ((double) dayOfMonth / totalDays) * 100;
    }

    public void updateBalance(BigDecimal amount, TransactionType type) {
        if (this.balance == null) this.balance = BigDecimal.ZERO;
        if (this.totalIncome == null) this.totalIncome = BigDecimal.ZERO;
        if (this.totalExpense == null) this.totalExpense = BigDecimal.ZERO;

        if (TransactionType.INCOME.equals(type)) {
            this.balance = this.balance.add(amount);
            this.totalIncome = this.totalIncome.add(amount);
        } else if (TransactionType.EXPENSE.equals(type)) {
            this.balance = this.balance.subtract(amount);
            this.totalExpense = this.totalExpense.add(amount);
        }
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public BigDecimal getTotalIncome() { return totalIncome; }
    public void setTotalIncome(BigDecimal totalIncome) { this.totalIncome = totalIncome; }
    public BigDecimal getTotalExpense() { return totalExpense; }
    public void setTotalExpense(BigDecimal totalExpense) { this.totalExpense = totalExpense; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public static WalletBuilder builder() {
        return new WalletBuilder();
    }

    public static class WalletBuilder {
        private UUID id;
        private UUID userId;
        private BigDecimal balance;
        private BigDecimal totalIncome;
        private BigDecimal totalExpense;
        private String currency;

        public WalletBuilder id(UUID id) { this.id = id; return this; }
        public WalletBuilder userId(UUID userId) { this.userId = userId; return this; }
        public WalletBuilder balance(BigDecimal balance) { this.balance = balance; return this; }
        public WalletBuilder totalIncome(BigDecimal totalIncome) { this.totalIncome = totalIncome; return this; }
        public WalletBuilder totalExpense(BigDecimal totalExpense) { this.totalExpense = totalExpense; return this; }
        public WalletBuilder currency(String currency) { this.currency = currency; return this; }
        public Wallet build() {
            return new Wallet(id, userId, balance, totalIncome, totalExpense, currency);
        }
    }
}