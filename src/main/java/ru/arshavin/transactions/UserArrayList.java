package ru.arshavin.transactions;

public class UserArrayList implements UsersList {
    private int arraySize = 10;
    private int currentIndex;
    private User[] users = new User[arraySize];

    @Override
    public void addUser(User user) {
        if (currentIndex >= arraySize) {
            arraySize *= 1.5;
            User[] newArray = new User[arraySize];
            for (int i = 0; i < users.length; i++) {
                newArray[i] = users[i];
            }
            users = newArray;
        }
        users[currentIndex] = user;
        currentIndex++;
    }

    @Override
    public User getUserById(int id) {
        for (int i = 0; users[i] != null; i++) {
            if (users[i].getId() == id) {
                return users[i];
            }
        }
        throw new UserNotFoundException("User не найден");
    }

    @Override
    public User getUserByIndex(int index) {
        if (index >= 0 && index < getUserQuantity())
            return users[index];
        throw new UserNotFoundException("User не найден");
    }

    @Override
    public int getUserQuantity() {
        return currentIndex;
    }
}
