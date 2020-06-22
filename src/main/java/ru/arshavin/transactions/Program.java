package ru.arshavin.transactions;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        UsersList usersList = new UserArrayList();
        TransactionsService transactionsService = new TransactionsService(usersList);
        Menu menu = new Menu(transactionsService);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                Parser parser = new Parser(args, menu, scanner);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
}
