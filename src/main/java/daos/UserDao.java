package daos;

import entities.User;

public interface UserDao {

    public abstract User createUser(User user);

    public abstract User getUser(String username);

    public abstract User updateUser(User user);

    public abstract String deleteUser(String username);
    
}
