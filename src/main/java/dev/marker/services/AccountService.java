package dev.marker.services;

import dev.marker.entities.User;
import dev.marker.exceptions.DuplicationException;
import dev.marker.exceptions.IncorrectArguments;
import dev.marker.exceptions.InvalidSession;
import dev.marker.exceptions.ResourceNotFound;

public interface AccountService {

    public abstract String createAccount(String username, String password, String firstName, String lastName, String gender, int age, int height, int weight, boolean admin) throws DuplicationException, IncorrectArguments;

    public abstract String logIn(String username, String password) throws ResourceNotFound;

    public abstract void logOut(String session);

    public abstract User getUser(String session) throws InvalidSession;

    public abstract User updateUser(String session, String password, String firstName, String lastName, String gender, int age, int height, int weight) throws InvalidSession, IncorrectArguments;
}
