package ru.arshavin.transactions;

import java.util.UUID;

public interface TransactionsList {
    void addTransaction(Transaction transaction);
    void deleteTransaction(UUID id);
    Transaction[] toArray();
}
