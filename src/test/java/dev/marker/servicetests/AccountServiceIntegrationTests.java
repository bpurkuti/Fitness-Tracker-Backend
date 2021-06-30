package dev.marker.servicetests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dev.marker.daos.UserDaoPostgres;
import dev.marker.entities.User;
import dev.marker.exceptions.DuplicateUser;
import dev.marker.exceptions.IncorrectArguments;
import dev.marker.exceptions.InvalidSession;
import dev.marker.exceptions.UserDoesntExist;
import dev.marker.services.AccountService;
import dev.marker.services.AccountServiceImpl;
import dev.marker.utils.ConnectionUtil;

public class AccountServiceIntegrationTests {

    private static String tableName = "test_users";
    private static Connection connection;
    private static AccountService accountService = new AccountServiceImpl(new UserDaoPostgres(tableName), 86400);

    @BeforeClass
    void setup() {
        connection = ConnectionUtil.createConnection();
        try {
            String sql = String.format("DELETE FROM %s", tableName);
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.execute();
        } catch (Exception e) {

        }
    }

    @AfterClass
    void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void createAccountAndLogOutSuccess() {
        String session = null;
        try {
            session = accountService.createAccount("testUser", "testPass", "John", "Doe", "Male", 10, 10, 10, false);
            Assert.assertNotNull(session);
            Assert.assertTrue(session.length() > 0);
            User user = accountService.getUser(session);
            Assert.assertEquals(user.getFirstName(), "John");
            Assert.assertEquals(user.getLastName(), "Doe");
        } catch (InvalidSession | DuplicateUser | IncorrectArguments e) {
            Assert.assertFalse(true);
        }
        accountService.logOut(session);
        try {
            accountService.getUser(session);
            Assert.assertFalse(true);
        } catch (InvalidSession e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    void createAccountMissingParamsFailure() {
        try {
            accountService.createAccount(null, "testPass", "John", "Doe", "Male", 10, 10, 10, false);
            Assert.assertFalse(true);
        } 
        catch(IncorrectArguments e){
            Assert.assertTrue(true);
        }
        catch (DuplicateUser e) {
            Assert.assertFalse(true);
        }
        try {
            accountService.createAccount("testUser", null, "John", "Doe", "Male", 10, 10, 10, false);
            Assert.assertFalse(true);
        } 
        catch(IncorrectArguments e){
            Assert.assertTrue(true);
        }
        catch (DuplicateUser e) {
            Assert.assertFalse(true);
        }
        try {
            accountService.createAccount("testUser", "testPass", null, "Doe", "Male", 10, 10, 10, false);
            Assert.assertFalse(true);
        } 
        catch(IncorrectArguments e){
            Assert.assertTrue(true);
        }
        catch (DuplicateUser e) {
            Assert.assertFalse(true);
        }
        try {
            accountService.createAccount("testUser", "testPass", "John", null, "Male", 10, 10, 10, false);
            Assert.assertFalse(true);
        } 
        catch(IncorrectArguments e){
            Assert.assertTrue(true);
        }
        catch (DuplicateUser e) {
            Assert.assertFalse(true);
        }
    }

    @Test(dependsOnMethods = { "createAccountAndLogOutSuccess" })
    void createDuplicateAccountFailure() {
        try {
            accountService.createAccount("testUser", "testPass", "John", "Doe", "Male", 10, 10, 10, false);
        } catch (IncorrectArguments e) {
            Assert.assertFalse(true);
        }
        catch(DuplicateUser e){
            Assert.assertTrue(true);
        }
    }

    @Test(dependsOnMethods = { "createAccountAndLogOutSuccess" })
    void logInAndOutOfAccountSuccess() {
        String session = null;
        try {
            session = accountService.logIn("testUser", "testPass");
            Assert.assertNotNull(session);
            Assert.assertTrue(session.length() > 0);
            User user = accountService.getUser(session);
            Assert.assertEquals(user.getFirstName(), "John");
            Assert.assertEquals(user.getLastName(), "Doe");
        } catch (InvalidSession | UserDoesntExist | IncorrectArguments e) {
            Assert.assertFalse(true);
        }
        accountService.logOut(session);
        try {
            accountService.getUser(session);
            Assert.assertFalse(true);
        } catch (InvalidSession e) {
            Assert.assertTrue(true);
        }
    }

    @Test(dependsOnMethods = { "createAccountAndLogOutSuccess" })
    void logInAccountMissingParamsFailure() {
        try {
            accountService.logIn(null, "testPass");
        } catch (UserDoesntExist e) {
            Assert.assertFalse(true);
        }
        catch(IncorrectArguments e){
            Assert.assertTrue(true);
        }
        try {
            accountService.logIn("testUser", null);
        } catch (UserDoesntExist e) {
            Assert.assertFalse(true);
        }
        catch(IncorrectArguments e){
            Assert.assertTrue(true);
        }
    }
}
