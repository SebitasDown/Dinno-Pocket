package com.dinno.pocket.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Wallet {
    private UUID id;
    private UUID userId;
    private BigDecimal balance;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;


    public Wallet(UUID id, UUID userId, BigDecimal balance, BigDecimal totalIncome, BigDecimal totalExpense) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
    }

    // calcular el porcentaje de mes restante
    public double calculateMounthProgress(int daysPassed, int totalDays) {
        return ((double) (totalDays - daysPassed) / totalDays) * 100;


    }

    // sumar o restar a balance
    public void applyTransaction(BigDecimal amount, TransactionType type){
        if (TransactionType.INCOME.equals(type)){
            this.balance = this.balance.add(amount);
            this.totalIncome = this.totalIncome.add(amount);
        }else {
            this.balance = this.balance.subtract(amount);
            this.totalExpense = this.totalExpense.add(amount);
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
    }
}