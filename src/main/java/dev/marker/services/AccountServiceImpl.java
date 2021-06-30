package dev.marker.services;

import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

import dev.marker.daos.UserDao;
import dev.marker.entities.User;
import dev.marker.exceptions.DuplicateUser;
import dev.marker.exceptions.IncorrectArguments;
import dev.marker.exceptions.InvalidSession;
import dev.marker.exceptions.UserDoesntExist;

public class AccountServiceImpl implements AccountService {

    private UserDao userDao;
    private HashMap<String, Pair<String, Long>> sessions;
    private long sessionTimeoutInSeconds;

    public AccountServiceImpl(UserDao dao, long sessionTimeoutInSeconds) {
        userDao = dao;
        sessions = new HashMap<String, Pair<String, Long>>();
        this.sessionTimeoutInSeconds = sessionTimeoutInSeconds;
    }

    @Override
    public String createAccount(String username, String password, String firstName, String lastName, String gender,
            int age, int height, int weight, boolean admin) throws DuplicateUser, IncorrectArguments {

        StringBuilder errorMessage = new StringBuilder();
        if (username == null)
            errorMessage.append("Username field is empty.\n");
        if (password == null)
            errorMessage.append("Password field is empty.\n");
        if (firstName == null)
            errorMessage.append("First name field is empty.\n");
        if (lastName == null)
            errorMessage.append("Last name field is empty.\n");
        if (errorMessage.length() != 0)
            throw new IncorrectArguments(errorMessage.toString());
        User load = new User(username, password, firstName, lastName, gender, age, height, weight, admin);
        User user = userDao.createUser(load);
        if (user == null)
            throw new DuplicateUser(String.format("Username '%s' already taken", username));
        String session = UUID.randomUUID().toString();
        sessions.put(session,
                new Pair<String, Long>(user.getUsername(), Instant.now().getEpochSecond() + sessionTimeoutInSeconds));
        return session;
    }

    @Override
    public String logIn(String username, String password) throws UserDoesntExist, IncorrectArguments {
        StringBuilder errorMessage = new StringBuilder();
        if (username == null)
            errorMessage.append("Username field is empty.\n");
        if (password == null)
            errorMessage.append("Password field is empty.\n");
        if (errorMessage.length() != 0)
            throw new IncorrectArguments(errorMessage.toString());
        User user = userDao.getUser(username);
        if (user == null)
            throw new UserDoesntExist("User with those credientials couldn't be found");
        String session = UUID.randomUUID().toString();
        sessions.put(session,
                new Pair<String, Long>(user.getUsername(), Instant.now().getEpochSecond() + sessionTimeoutInSeconds));
        return session;
    }

    @Override
    public void logOut(String session) {
        sessions.remove(session);
    }

    @Override
    public User getUser(String session) throws InvalidSession {
        Pair<String, Long> data = sessions.get(session);
        if (data == null)
            throw new InvalidSession("The session is not a valid session");
        if (data.second < Instant.now().getEpochSecond()) {
            throw new InvalidSession("The session has expired");
        }
        User user = userDao.getUser(data.first);
        return user;
    }

    @Override
    public User updateUser(String session, String password, String firstName, String lastName, String gender, int age,
            int height, int weight, boolean admin) throws InvalidSession {
        return null;
    }

    private class Pair<T1, T2> {
        T1 first;
        T2 second;

        Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }
    }
}
