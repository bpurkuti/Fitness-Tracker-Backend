package dev.marker.daotests;
import dev.marker.daos.RoutineDao;
import dev.marker.daos.RoutineDaoPostgres;
import dev.marker.daos.UserDao;
import dev.marker.daos.UserDaoPostgres;
import dev.marker.entities.Routine;
import dev.marker.entities.User;
import dev.marker.utils.ConnectionUtil;
import dev.marker.utils.Setup;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class RoutineDaoTests {

    private static User user = new User("TestUser", "password", "Test", "User", "Male", 20, 70, 150, false);
    private static User extraUser = new User("TestUser2", "password", "Test", "User", "Male", 20, 70, 150, false);

    private static String userTableName = "test_users";
    private static String routineTableName = "test_routines";
    private static UserDao userDao = new UserDaoPostgres(userTableName);
    private static RoutineDao routineDao = new RoutineDaoPostgres(routineTableName);
    private static Connection connection;


    @BeforeClass
    void testInit(){
        ConnectionUtil.setHostname("revaturedb.cw0dgbcoagdz.us-east-2.rds.amazonaws.com");
        ConnectionUtil.setUsername("revature");
        ConnectionUtil.setPassword("revature");
        Setup.setupTables(userTableName, "test_exercises", routineTableName, "test_routine_exercises");
        connection = ConnectionUtil.createConnection();
        try{
            String sql = String.format("DELETE FROM %s", userTableName);
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.execute();
            userDao.createUser(user);
            userDao.createUser(extraUser);
        }
        catch(Exception e){

        }
    }

    @BeforeMethod
    void emptyTables(){
        try{
            String sql = String.format("DELETE FROM %s", routineTableName);
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.execute();
        }
        catch(Exception e){

        }
    }

    @AfterClass
    void closeConnection(){
        try{
            connection.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void createNewRoutine(){
        Routine testRoutine = new Routine(0, user.getUsername(), "Legs Day", 0, 0);
        Routine routine = routineDao.createRoutine(testRoutine);
        Assert.assertNotEquals(routine.getRoutineId(), 0);
        Assert.assertEquals(routine.getRoutineName(), testRoutine.getRoutineName());
        Assert.assertEquals(routine.getUsername(), testRoutine.getUsername());
        Assert.assertEquals(routine.getDateCompleted(), testRoutine.getDateCompleted());
        Assert.assertEquals(routine.getDateScheduled(), testRoutine.getDateScheduled());
    }

    @Test
    void createRoutineWithoutUser(){
        Routine testRoutine = new Routine(0, "UsernameDoesntExist", "Arms Day", 0, 0);
        Routine routine = routineDao.createRoutine(testRoutine);
        Assert.assertNull(routine);
    }

    @Test
    void getExisitingRoutine(){
        Routine testRoutine = new Routine(0, user.getUsername(), "Arms Day", 0, 0);
        Routine routine = routineDao.createRoutine(testRoutine);
        Routine result = routineDao.getRoutine(routine.getRoutineId());
        Assert.assertEquals(testRoutine.getRoutineName(), result.getRoutineName());
    }

    @Test
    void getNonExistingRoutine(){
        Routine routine = routineDao.getRoutine(0);
        Assert.assertNull(routine);
    }

    @Test
    void getAllRoutines(){
        Routine routine1 = new Routine(0, user.getUsername(), "Triceps Day", 0, 0);
        Routine routine2 = new Routine(0, user.getUsername(), "Chest Day", 0, 0);
        Routine routine3 = new Routine(0, user.getUsername(), "Shoulder Day", 0, 0);

        routineDao.createRoutine(routine1);
        routineDao.createRoutine(routine2);
        routineDao.createRoutine(routine3);
        List<Routine> routines = routineDao.getAllRoutines();
        Assert.assertEquals(routines.size(), 3);
    }

    @Test
    void getAllRoutinesForUser(){
        Routine routine1 = new Routine(0, user.getUsername(), "Arms Day", 0, 0);
        Routine routine2 = new Routine(0, user.getUsername(), "Chest Day", 0, 0);
        Routine routine3 = new Routine(0, user.getUsername(), "Shoulder Day", 0, 0);
        Routine routine4 = new Routine(0, extraUser.getUsername(), "Triceps Day", 0, 0);
        routineDao.createRoutine(routine1);
        routineDao.createRoutine(routine2);
        routineDao.createRoutine(routine3);
        routineDao.createRoutine(routine4);
        List<Routine> routines = routineDao.getAllRoutinesForUser(user.getUsername());
        Assert.assertEquals(routines.size(), 3);
    }

    @Test
    void getAllRoutinesForNonExistingUser(){
        List<Routine> routines = routineDao.getAllRoutinesForUser("UsernameDoesntExist");
        Assert.assertEquals(routines.size(), 0);
    }

    @Test
    void updateRoutine(){
        Routine testRoutine = new Routine(0, user.getUsername(), "Glutes Workout", 0, 0);
        Routine result = routineDao.createRoutine(testRoutine);
        result.setRoutineName("Glutes Day");
        Routine updatedRoutine = routineDao.updateRoutine(result);
        Assert.assertEquals(updatedRoutine.getRoutineName(), result.getRoutineName());
    }

    @Test
    void updateNonExistingRoutine(){
        Routine testRoutine = new Routine(0, user.getUsername(), "Glutes Workout", 0, 0);
        Routine updatedRoutine = routineDao.updateRoutine(testRoutine);
        Assert.assertNull(updatedRoutine);
    }

    @Test
    void deleteExistingRoutine(){
        Routine testRoutine = new Routine(0, user.getUsername(), "Glutes Workout", 0, 0);
        Routine routine = routineDao.createRoutine(testRoutine);
        boolean result = routineDao.deleteRoutine(routine.getRoutineId());
        Assert.assertTrue(result);
    }

    @Test
    void deleteNonExistingRoutine(){
        boolean result = routineDao.deleteRoutine(0);
        Assert.assertFalse(result);
    }
}
