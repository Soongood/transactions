package ru.arshavin.transactions;

import java.util.UUID;

public class TransactionsService {
    private UsersList usersList;

    public TransactionsService(UsersList usersList) {
        this.usersList = usersList;
    }

    public void addUser(User user) {
        usersList.addUser(user);
    }

    public double getBalance(User user) {
        return user.getBalance();
    }

    public User getUserByID(int userID) {
        return usersList.getUserById(userID);
    }

    public void makeTransfer(int senderID, int recipientID, double sumOfTransfer) {
        if (sumOfTransfer > 0) {
            User sender = usersList.getUserById(senderID);
            if (sender.getBalance() - sumOfTransfer >= 0) {
                User recipient = usersList.getUserById(recipientID);
                UUID uuid = UUID.randomUUID();
                sender.getTransactions().addTransaction(new Transaction(uuid, recipient, sender, Category.OUTCOME, sumOfTransfer * -1));
                sender.setBalance(sender.getBalance() - sumOfTransfer);
                recipient.getTransactions().addTransaction(new Transaction(uuid, recipient, sender, Category.INCOME, sumOfTransfer));
                recipient.setBalance(recipient.getBalance() + sumOfTransfer);
                return;
            }
            throw new IllegalTransactionException("На счету недостаточно средств");
        }
    }

    public Transaction[] getArrayOfTransactions(int userID) {
        return usersList.getUserById(userID).getTransactions().toArray();
    }

    public void deleteTransaction(UUID id, int userID) {
        usersList.getUserById(userID).getTransactions().deleteTransaction(id);
    }

    public Transaction[] checkTransferPairs() {
        int length = usersList.getUserQuantity();
        int userID;
        boolean isUUID = false;
        UUID id;
        TransactionsList list = new TransactionLinkedList();
        for (int i = 0; i < length; i++) {
            userID = usersList.getUserByIndex(i).getId();
            Transaction[] transactions = getArrayOfTransactions(userID);
            for (int j = 0; j < transactions.length; j++) {
                id = transactions[j].getId();
                Transaction[] transactions2 = (userID == transactions[j].getRecipient().getId()) ?
                        transactions[j].getSender().getTransactions().toArray() :
                                transactions[j].getRecipient().getTransactions().toArray();
                for (Transaction transaction : transactions2) {
                    if (transaction.getId().compareTo(id) == 0) {
                        isUUID = true;
                        break;
                    }
                }
                if (!isUUID) {
                    for (Transaction transaction : list.toArray()) {
                        if (transactions[j].getId().compareTo(id) == 0) {
                            isUUID = true;
                            break;
                        }
                    }
                    if (!isUUID) {
                        list.addTransaction(transactions[j]);
                    }
                }
                isUUID = false;
            }
        }
        return list.toArray();
    }
}