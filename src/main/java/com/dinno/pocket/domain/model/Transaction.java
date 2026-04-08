package com.dinno.pocket.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private UUID walletId;
    private String title;
    private String description;
    private BigDecimal amount;
    private String category;
    private TransactionType type;
    private LocalDateTime createAt;

    public Transaction() {}

    public Transaction(UUID id, UUID walletId, String title, String description, BigDecimal amount, String category, TransactionType type, LocalDateTime createAt) {
        this.id = id;
        this.walletId = walletId;
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.createAt = createAt;
    }

    public boolean isValidCategory() {
        if (type == TransactionType.INCOME) {
            try { IncomeCategory.valueOf(category); return true; }
            catch (Exception e) { return false; }
        } else {
            try { ExpenseCategory.valueOf(category); return true; }
            catch (Exception e) { return false; }
        }
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getWalletId() { return walletId; }
    public void setWalletId(UUID walletId) { this.walletId = walletId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }
    public LocalDateTime getCreateAt() { return createAt; }
    public void setCreateAt(LocalDateTime createAt) { this.createAt = createAt; }

    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }

    public static class TransactionBuilder {
        private UUID id;
        private UUID walletId;
        private String title;
        private String description;
        private BigDecimal amount;
        private String category;
        private TransactionType type;
        private LocalDateTime createAt;

        public TransactionBuilder id(UUID id) { this.id = id; return this; }
        public TransactionBuilder walletId(UUID walletId) { this.walletId = walletId; return this; }
        public TransactionBuilder title(String title) { this.title = title; return this; }
        public TransactionBuilder description(String description) { this.description = description; return this; }
        public TransactionBuilder amount(BigDecimal amount) { this.amount = amount; return this; }
        public TransactionBuilder category(String category) { this.category = category; return this; }
        public TransactionBuilder type(TransactionType type) { this.type = type; return this; }
        public TransactionBuilder createAt(LocalDateTime createAt) { this.createAt = createAt; return this; }
        public Transaction build() {
            return new Transaction(id, walletId, title, description, amount, category, type, createAt);
        }
    }
}
