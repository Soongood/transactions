package ru.arshavin.transactions;

import java.util.UUID;

public class Menu {
    private boolean dev;
    private TransactionsService transactionsService;

    public Menu(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    public boolean isDev() {
        return dev;
    }

    public void setDev(boolean dev) {
        this.dev = dev;
    }

    public void showMessage(int commandNumber) {
        switch (commandNumber) {
            case 1:  System.out.println("Введите имя и баланс пользователя");
                     break;
            case 3:  System.out.println("Введите id-отправителя, id-получается и сумму перевода");
                     break;
            case 5:  System.out.println("Введите id пользователя и id перевода");
                     break;
            case 7:  endProgram();
                     break;
            default: System.out.println("Введите id пользователя");
                     break;
        }
    }

    public void doCommand(String[] arguments, int commandNumber) {
        switch (commandNumber) {
            case 1:
                addUser(arguments);
                break;
            case 2:
                showBalance(arguments);
                break;
            case 3:
                makeTransfer(arguments);
                break;
            case 4:
                showUserTransactions(arguments);
                break;
            case 5:
                System.out.println("Введите id пользователя и id перевода\n");
                deleteTransfer(arguments);
                break;
        }
    }

    private void addUser(String[] arguments) throws NumberFormatException {
        if (arguments.length != 2) {
            throw new IllegalArgumentException("Некорректное число аргументов!\n");
        }
        User user = new User(arguments[0], Integer.parseInt(arguments[1]), new TransactionLinkedList());
        transactionsService.addUser(user);
        System.out.printf("Пользователь добавлен с id = %d\n", user.getId());
    }

    private void showBalance(String[] arguments) throws NumberFormatException {
        User user = transactionsService.getUserByID(Integer.parseInt(arguments[0]));
        System.out.printf("%s - %.0f\n", user.getName(), transactionsService.getBalance(user));
    }

    private void makeTransfer(String[] arguments) throws NumberFormatException, IllegalArgumentException {
        if (arguments.length != 3) {
            throw new IllegalArgumentException("Некорректное число аргументов!\n");
        }
        transactionsService.makeTransfer(Integer.parseInt(arguments[0]),
                Integer.parseInt(arguments[1]), Integer.parseInt(arguments[2]));
        System.out.println("Перевод осуществлен\n");
    }

    private void showUserTransactions(String[] arguments) throws NumberFormatException {
        if (arguments.length != 1) {
            throw new IllegalArgumentException("Некорректное число аргументов!\n");
        }
        int id = Integer.parseInt(arguments[0]);
        String name;
        for (Transaction transaction : transactionsService.getArrayOfTransactions(id)) {
            if (transaction.getRecipient().getId() == id) {
                name = transaction.getSender().getName();
                System.out.printf("From %s(id = %d) %.0f with id = %s\n",
                        name, transaction.getSender().getId(), transaction.getSumOfTransfer(),
                        transaction.getId().toString());
            }
            else {
                name = transaction.getRecipient().getName();
                System.out.printf("To %s(id = %d) %.0f with id = %s\n",
                        name, transaction.getRecipient().getId(), transaction.getSumOfTransfer(),
                        transaction.getId().toString());
            }
        }
    }

    private void deleteTransfer(String[] arguments) throws NumberFormatException, IllegalArgumentException {
        if (arguments.length != 2) {
            throw new IllegalArgumentException("Некорректное число аргументов!\n");
        }
        int id = Integer.parseInt(arguments[0]);
        UUID uuid = UUID.fromString(arguments[1]);
        String name;
        for (Transaction transaction : transactionsService.getArrayOfTransactions(id)) {
            if (transaction.getId().compareTo(uuid) == 0) {
                if (transaction.getRecipient().getId() == id) {
                    name = transaction.getSender().getName();
                    System.out.printf("Перевод From %s(id = %d) %.0f удален\n",
                            name, transaction.getSender().getId(), transaction.getSumOfTransfer());
                }
                else {
                    name = transaction.getRecipient().getName();
                    System.out.printf("Перевод To %s(id = %d) %.0f удален\n",
                            name, transaction.getRecipient().getId(), transaction.getSumOfTransfer());
                }
                transactionsService.deleteTransaction(uuid, id);
                break;
            }
        }
    }

    public void checkTransactionPairs() {
        System.out.println("Результаты проверки:\n");
        Transaction[] transactions = transactionsService.checkTransferPairs();
        for (Transaction transaction : transactions) {
            System.out.printf("%s(id = %d) имеет неподтвержденный перевод id =\n%s от %s(id = %d) на сумму %.0f\n",
                    transaction.getRecipient().getName(), transaction.getRecipient().getId(), transaction.getId().toString(),
                    transaction.getSender().getName(), transaction.getSender().getId(), transaction.getSumOfTransfer());
        }
        System.exit(1);
    }

    private void endProgram() {
        System.exit(1);
    }

    public void print() {
        if (dev) {
            printDevMenu();
        }
        else {
            printMenu();
        }
    }

    private void printMenu() {
        System.out.println("1. Добавить пользователя");
        System.out.println("2. Посмотреть баланс пользователя");
        System.out.println("3. Осуществить перевод");
        System.out.println("4. Посмотреть все переводы конкретного пользователя");
        System.out.println("7. Завершить выполнение");
    }

    private void printDevMenu() {
        System.out.println("1. Добавить пользователя");
        System.out.println("2. Посмотреть баланс пользователя");
        System.out.println("3. Осуществить перевод");
        System.out.println("4. Посмотреть все переводы конкретного пользователя");
        System.out.println("5. DEV - удалить перевод по ID");
        System.out.println("6. DEV - проверить корректность переводов");
        System.out.println("7. Завершить выполнение");
    }
}
