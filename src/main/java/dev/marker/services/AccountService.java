package dev.marker.services;

import dev.marker.entities.User;
import dev.marker.exceptions.DuplicateUser;
import dev.marker.exceptions.IncorrectArguments;
import dev.marker.exceptions.InvalidSession;
import dev.marker.exceptions.UserDoesntExist;

public interface AccountService {

    public abstract String createAccount(String username, String password, String firstName, String lastName, String gender, int age, int height, int weight, boolean admin) throws DuplicateUser, IncorrectArguments;

    public abstract String logIn(String username, String password) throws UserDoesntExist, IncorrectArguments;

    public abstract void logOut(String session);

    public abstract User getUser(String session) throws InvalidSession;

    //May or may not be used;
    public abstract User updateUser(String session, String password, String firstName, String lastName, String gender, int age, int height, int weight, boolean admin) throws InvalidSession;
}
