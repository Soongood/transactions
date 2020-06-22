package ru.arshavin.transactions;

public class UserIdsGenerator {

    private static final UserIdsGenerator USER_IDS_GENERATOR = new UserIdsGenerator();
    private static int id;

    private UserIdsGenerator() {
        generateId();
    }

    public static int getId() {
        return id;
    }

    public int generateId() {
        return id++;
    }

    public static UserIdsGenerator getInstance() {
        return USER_IDS_GENERATOR;
    }
}