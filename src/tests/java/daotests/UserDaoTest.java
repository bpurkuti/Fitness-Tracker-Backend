package daotests;

import daos.UserDao;
import entities.User;

import org.testng.annotations.Test;

import org.testng.Assert;

public class UserDaoTest {

    private UserDao userDao;

    @Test
    void createNewUser() {
        User user = new User("exUsername", "exPassword", "John", "Doe", "Male", 21, 60, 160);
        User returnedUser = userDao.createUser(user);
        Assert.assertNotNull(returnedUser);
        Assert.assertEquals(user.getUsername(), returnedUser.getUsername());
        Assert.assertEquals(user.getPassword(), returnedUser.getPassword());
        Assert.assertEquals(user.getFirstName(), returnedUser.getFirstName());
        Assert.assertEquals(user.getLastName(), returnedUser.getLastName());
        Assert.assertEquals(user.getGender(), returnedUser.getGender());
        Assert.assertEquals(user.getWeight(), returnedUser.getWeight());
        Assert.assertEquals(user.getHeight(), returnedUser.getHeight());
        Assert.assertEquals(user.getAge(), returnedUser.getAge());
    }

    @Test
    void createEmptyUser() {
        User user = new User("", "exPassword", "John", "Doe", "Male", 21, 60, 160);
        User returnedUser = userDao.createUser(user);
        Assert.assertNull(returnedUser);
    }

    @Test
    void createNewUserWithoutPassword() {
        User user = new User("exUsername", "", "John", "Doe", "Male", 21, 60, 160);
        User returnedUser = userDao.createUser(user);
        Assert.assertNull(returnedUser);
    }

    @Test
    void createDuplicateUser() {
        User user1 = new User("exUsername", "exPassword", "John", "Doe", "Male", 21, 60, 160);
        User returnedUser1 = userDao.createUser(user1);
        User user2 = new User("exUsername", "exPassword", "John", "Doe", "Male", 21, 60, 160);
        User returnedUser2 = userDao.createUser(user2);
        Assert.assertNotNull(returnedUser1);
        Assert.assertNull(returnedUser2);
    }

    @Test
    void getExistingUser() {
        User user = new User("exUsername", "exPassword", "John", "Doe", "Male", 21, 60, 160);
        userDao.createUser(user);
        User returnedUser = userDao.getUser(user.getUsername());
        Assert.assertNotNull(returnedUser);
        Assert.assertEquals(user.getUsername(), returnedUser.getUsername());
        Assert.assertEquals(user.getPassword(), returnedUser.getPassword());
        Assert.assertEquals(user.getFirstName(), returnedUser.getFirstName());
        Assert.assertEquals(user.getLastName(), returnedUser.getLastName());
        Assert.assertEquals(user.getGender(), returnedUser.getGender());
        Assert.assertEquals(user.getWeight(), returnedUser.getWeight());
        Assert.assertEquals(user.getHeight(), returnedUser.getHeight());
        Assert.assertEquals(user.getAge(), returnedUser.getAge());
    }

    @Test
    void getNonExistingUser() {
        User returnedUser = userDao.getUser("nonExistant");
        Assert.assertNull(returnedUser);
    }

    @Test
    void updateExistingUser() {
        User user1 = new User("exUsername", "exPassword", "John", "Doe", "Male", 21, 60, 160);
        userDao.createUser(user1);
        User user2 = new User("exUsername", "exPassword2", "Karen", "Donzo", "Female", 25, 69, 200);
        User returnedUser = userDao.updateUser(user2);
        Assert.assertNotNull(returnedUser);
        Assert.assertEquals(user1.getUsername(), returnedUser.getUsername());
        Assert.assertNotEquals(user1.getPassword(), returnedUser.getPassword());
        Assert.assertNotEquals(user1.getFirstName(), returnedUser.getFirstName());
        Assert.assertNotEquals(user1.getLastName(), returnedUser.getLastName());
        Assert.assertNotEquals(user1.getGender(), returnedUser.getGender());
        Assert.assertNotEquals(user1.getWeight(), returnedUser.getWeight());
        Assert.assertNotEquals(user1.getHeight(), returnedUser.getHeight());
        Assert.assertNotEquals(user1.getAge(), returnedUser.getAge());
    }

    @Test
    void updateUserPasswordToNothing() {
        User user1 = new User("exUsername", "exPassword", "John", "Doe", "Male", 21, 60, 160);
        userDao.createUser(user1);
        User user2 = new User("exUsername", "", "Karen", "Donzo", "Female", 25, 69, 200);
        User returnedUser = userDao.updateUser(user2);
        Assert.assertNull(returnedUser);
    }

    @Test
    void updateNonExistingUser() {
        User user = new User("exUsername", "exPassword", "John", "Doe", "Male", 21, 60, 160);
        User returnedUser = userDao.updateUser(user);
        Assert.assertNull(returnedUser);
    }

    @Test
    void deleteExistingUser() {
        User user = new User("exUsername", "exPassword", "John", "Doe", "Male", 21, 60, 160);
        userDao.createUser(user);
        String deleted = userDao.deleteUser(user.getUsername());
        Assert.assertNotNull(deleted);
        Assert.assertEquals(deleted, user.getUsername());
    }

    @Test
    void deleteNonExistingUser() {
        String deleted = userDao.deleteUser("nonExistant");
        Assert.assertNull(deleted);
    }
}
