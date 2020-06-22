package ru.arshavin.transactions;

import java.util.UUID;

public class TransactionLinkedList implements TransactionsList {

    private int listSize;
    private Node head, tail;
    private class Node {
        private Node next;
        private Transaction transaction;

        public Node(Transaction transaction) {
            this.transaction = transaction;
        }

        public Transaction getTransaction() {
            return transaction;
        }
    }

    public void addTransaction(Transaction transaction) {
        if (head == null) {
            Node node = new Node(transaction);
            head = node;
            tail = node;
        }
        else {
            tail.next = new Node(transaction);
            tail = tail.next;
        }
        listSize++;
    }

    public void deleteTransaction(UUID id) {
        Node temporary = head;
        while (temporary != null) {
            if (temporary.transaction.getId().equals(id)) {
                if (temporary.next != null && temporary.next.next != null) {
                    temporary.transaction = temporary.next.transaction;
                    temporary.next = temporary.next.next;
                }
                else if (temporary.next == null) {
                    if (temporary == head) {
                        head = null;
                    }
                    tail = null;
                }
                else {
                    temporary.transaction = temporary.next.transaction;
                    temporary.next = null;
                }
                listSize--;
                return;
            }
            temporary = temporary.next;
        }
        throw new TransactionNotFoundException("Такого UUID не существует");
    }

    public Transaction[] toArray() {
        int i = 0;
        Node temporary = head;
        Transaction[] array = new Transaction[listSize];
        while (i < listSize) {
            array[i] = temporary.getTransaction();
            temporary = temporary.next;
            i++;
        }
        return array;
    }
}
