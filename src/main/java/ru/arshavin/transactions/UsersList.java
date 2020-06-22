package ru.arshavin.transactions;

public interface UsersList {
    void addUser(User user);
    User getUserById(int id) throws UserNotFoundException;
    User getUserByIndex(int index);
    int getUserQuantity();
}
