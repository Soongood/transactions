package ru.arshavin.transactions;

import java.util.UUID;

public class Transaction {
    private UUID id;
    private User recipient;
    private User sender;
    private Category category;
    private double sumOfTransfer;

    public Transaction(UUID id, User recipient, User sender, Category category, double sumOfTransfer) {
        this.id = id;
        this.recipient = recipient;
        this.sender = sender;
        this.category = category;
        setSumOfTransfer(sumOfTransfer);
    }

    public UUID getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public Category getCategory() {
        return category;
    }

    public double getSumOfTransfer() {
        return sumOfTransfer;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSumOfTransfer(double sumOfTransfer) {
        if (category == category.INCOME)
            this.sumOfTransfer = sumOfTransfer > 0 ? sumOfTransfer : 0;
        else {
            this.sumOfTransfer = sumOfTransfer < 0 ? sumOfTransfer : 0;
        }
    }
}
