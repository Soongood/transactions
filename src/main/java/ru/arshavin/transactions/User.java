package ru.arshavin.transactions;

public class User {
    private final int ID;
    private String name;
    private double balance;
    private TransactionsList transactions;

    public User(String name, double balance, TransactionsList transactions) {
        this.ID = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        this.transactions = transactions;
        setBalance(balance);
    }

    public int getId() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public TransactionsList getTransactions() {
        return transactions;
    }

    public double getBalance() {
        return balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(double balance) {
        this.balance = balance < 0 ? 0 : balance;
    }
}
