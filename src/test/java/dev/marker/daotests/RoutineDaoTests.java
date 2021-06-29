package dev.marker.daotests;
import dev.marker.daos.RoutineDao;
import dev.marker.daos.UserDao;
import dev.marker.entities.Routine;
import dev.marker.entities.User;
import dev.marker.exceptions.RoutineDoesntExist;
import dev.marker.exceptions.UserDoesntExist;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class RoutineDaoTests {
    static RoutineDao routineDao = null;
    static UserDao userDao = null;

    //Might have to create these users
    private static User user = new User("TestUser", "password", "Test", "User", "Male", 20, 70, 150, false);
    private static User extraUser = new User("TestUser2", "password", "Test", "User", "Male", 20, 70, 150, false);

    @BeforeClass
    public void testInit(){
        userDao.createUser(user);
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
        Assert.assertNull(routines);
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

    //Confused about what deleteRoutine actually returns
    @Test
    void deleteExistingRoutine(){
        Routine testRoutine = new Routine(0, user.getUsername(), "Glutes Workout", 0, 0);
        Routine routine = routineDao.createRoutine(testRoutine);
        boolean result = routineDao.deleteRoutine(routine.getRoutineId());
        Assert.assertTrue(result);
    }
}
