package dev.marker.daos;

import dev.marker.entities.User;

public interface UserDao {

    public abstract User createUser(User user);

    public abstract User getUser(String username);

    public abstract User updateUser(User user);

    public abstract String deleteUser(String username);
    
}
