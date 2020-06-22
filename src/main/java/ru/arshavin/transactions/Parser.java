package ru.arshavin.transactions;

import java.util.Scanner;

public class Parser {

    private Menu menu;
    private Scanner scanner;

    public Parser(String[] args, Menu menu, Scanner scanner) {
        this.menu = menu;
        this.scanner = scanner;
        parseArgs(args);
    }

    private void parseArgs(String[] args) {
        if (args.length > 1) {
            throw new IllegalArgumentException("Количество аргументов превышает допустимое значение\n");
        }
        else if (args.length == 1) {
            if (args[0].equals("--profile=dev")) {
                menu.setDev(true);
            }
            else {
                throw new IllegalArgumentException("Аргумент задан неправильно\nВведите без аргумента, либо с аргументом --profile=dev\n");
            }
        }
        menu.print();
        if (scanner.hasNextInt()) {
            System.out.print("\033\143");
            int command = scanner.nextInt();
            if (command > 7 || !menu.isDev()  && (command == 5 || command == 6)) {
                throw new IllegalArgumentException("Введите число от 1 до 7!\n");
            }
            else if (menu.isDev() && command == 6) {
                menu.checkTransactionPairs();
            }
            else {
                menu.showMessage(command);
                menu.doCommand(splitArguments(), command);
            }
        }
        else {
            scanner.next();
            throw new IllegalArgumentException("Команда должна быть числом!\n");
        }
    }

    private String[] splitArguments() {
        scanner.nextLine();
        if (scanner.hasNextLine()) {
            String[] strings = scanner.nextLine().split(" ");
            if (strings.length < 4) {
                return strings;
            }
        }
       throw new IllegalArgumentException("Слишком много аргументов!\n");
    }
}
