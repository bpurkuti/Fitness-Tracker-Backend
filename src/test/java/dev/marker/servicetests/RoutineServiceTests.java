package dev.marker.servicetests;

import dev.marker.entities.Routine;
import dev.marker.entities.User;
import dev.marker.exceptions.IncorrectArguments;
import dev.marker.exceptions.PermissionException;
import dev.marker.exceptions.ResourceNotFound;
import dev.marker.services.RoutineService;
import dev.marker.daos.RoutineDaoPostgres;
import dev.marker.daos.UserDao;
import dev.marker.daos.UserDaoPostgres;
import dev.marker.services.RoutineServiceImpl;
import dev.marker.utils.ConnectionUtil;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class RoutineServiceTests {

    private static String routineTableName = "test_routines";
    private static String userTableName = "test_users";
    private static Connection connection;
    private static RoutineService routineService = new RoutineServiceImpl(new RoutineDaoPostgres(routineTableName));
    private static User user = new User("RServiceUser", "password", "Test", "User", "Male", 20, 70, 150, false);
    private static User userFail = new User("RServiceUserFail", "password", "Test", "User", "Male", 20, 70, 150, false);
    private static Routine routine;

    @BeforeClass
    void setup() {
        connection = ConnectionUtil.createConnection();
        try {
            String sql = String.format("DELETE FROM %s", userTableName);
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.execute();
        } catch (Exception e) {
        }
        UserDao userDao = new UserDaoPostgres(userTableName);
        userDao.createUser(user);
        userDao.createUser(userFail);
        try {
            String sql = String.format("DELETE FROM %s", routineTableName);
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
    void createValidRoutine() {
        try {
            routine = routineService.createRoutine(user, "Arms Day", 1000);
            Assert.assertNotEquals(routine.getRoutineId(), 0);
            Assert.assertEquals(routine.getRoutineName(), "Arms Day");
        } catch (IncorrectArguments e) {

        }
    }

    @Test
    void createRoutineWithIncorrectArugments() {
        try {
            routineService.createRoutine(user, null, 1000);
            Assert.assertFalse(true);
        } catch (IncorrectArguments e) {
            Assert.assertTrue(true);
        }
        try {
            routineService.createRoutine(user, "Arms Day", 0);
            Assert.assertFalse(true);
        } catch (IncorrectArguments e) {
            Assert.assertTrue(true);
        }
    }

    @Test(dependsOnMethods = { "createValidRoutine" })
    void getValidRoutine() {
        try {
            Routine returnedRoutine = routineService.getRoutine(user, routine.getRoutineId());
            Assert.assertEquals(returnedRoutine.getRoutineName(), routine.getRoutineName());
        } catch (ResourceNotFound | PermissionException e) {
            Assert.assertFalse(true);
        }
    }

    @Test(dependsOnMethods = { "createValidRoutine" })
    void getInvalidRoutine() {
        try {
            routineService.getRoutine(user, 0);
            Assert.assertFalse(true);
        } catch (PermissionException e) {
            Assert.assertFalse(true);
        } catch (ResourceNotFound e) {
            Assert.assertTrue(true);
        }
        try {
            routineService.getRoutine(userFail, routine.getRoutineId());
            Assert.assertFalse(true);
        } catch (PermissionException e) {
            Assert.assertTrue(true);
        } catch (ResourceNotFound e) {
            Assert.assertFalse(true);
        }
    }

    @Test(dependsOnMethods = { "createValidRoutine" })
    void getAllRoutinesForUser() {
        List<Routine> routines = routineService.getAllRoutinesForUser(user);
        Assert.assertEquals(routines.size(), 1);
    }

    @Test(dependsOnMethods = { "createValidRoutine" })
    void updateValidRoutine() {
        try {
            routine = routineService.updateRoutine(user, routine.getRoutineId(), "Updated Routine", 2000, 0);
            Assert.assertEquals(routine.getRoutineName(), "Updated Routine");
        } catch (PermissionException | IncorrectArguments | ResourceNotFound e) {
            Assert.assertFalse(true);
        }
    }

    @Test(dependsOnMethods = { "createValidRoutine" })
    void updateInvalidRoutine() {
        try {
            routineService.updateRoutine(userFail, routine.getRoutineId(), "Updated Routine", 2000, 0);
            Assert.assertFalse(true);
        } catch (PermissionException e) {
            Assert.assertTrue(true);
        } catch (ResourceNotFound e) {
            Assert.assertFalse(true);
        } catch (IncorrectArguments e) {
            Assert.assertFalse(true);
        }
        try {
            routineService.updateRoutine(user, 0, "Updated Routine", 2000, 0);
            Assert.assertFalse(true);
        } catch (PermissionException e) {
            Assert.assertFalse(true);
        } catch (ResourceNotFound e) {
            Assert.assertTrue(true);
        } catch (IncorrectArguments e) {
            Assert.assertFalse(true);
        }
        try {
            routineService.updateRoutine(user, routine.getRoutineId(), "Updated Routine", 0, 0);
            Assert.assertFalse(true);
        } catch (PermissionException e) {
            Assert.assertFalse(true);
        } catch (ResourceNotFound e) {
            Assert.assertFalse(true);
        } catch (IncorrectArguments e) {
            Assert.assertTrue(true);
        }
        try {
            routineService.updateRoutine(user, routine.getRoutineId(), "Updated Routine", 2000, 0);
            Assert.assertFalse(true);
        } catch (PermissionException e) {
            Assert.assertFalse(true);
        } catch (ResourceNotFound e) {
            Assert.assertFalse(true);
        } catch (IncorrectArguments e) {
            Assert.assertTrue(true);
        }
    }

    @Test(dependsOnMethods = { "createValidRoutine" })
    void deleteInvalidRoutine() {
        try {
            routineService.deleteRoutine(userFail, routine.getRoutineId());
            Assert.assertFalse(true);
        } catch (ResourceNotFound e) {
            Assert.assertFalse(true);
        } catch (PermissionException e) {
            Assert.assertTrue(true);
        }
        try {
            routineService.deleteRoutine(user, 0);
            Assert.assertFalse(true);
        } catch (ResourceNotFound e) {
            Assert.assertTrue(true);
        } catch (PermissionException e) {
            Assert.assertFalse(true);
        }
    }

    @Test(dependsOnMethods = { "createValidRoutine" , "deleteInvalidRoutine"})
    void deleteValidRoutine() {
        try{
            boolean deleted = routineService.deleteRoutine(user, routine.getRoutineId());
            Assert.assertTrue(deleted);
        }
        catch(PermissionException | ResourceNotFound e){
            Assert.assertFalse(true);
        }
    }
}
