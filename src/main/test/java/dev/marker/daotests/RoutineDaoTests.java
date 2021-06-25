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
    static User user = new User("TestUser", "password", "Test", "User", "Male", 20, 70, 150);


    @BeforeClass
    public void testInit(){
        userDao.createUser(user);

    }

    @Test(priority = 1)
    void createRoutine1(){
        Routine testRoutine = new Routine(0, "TestUser", "Legs Day", 0, 0);
        Routine routine = routineDao.createRoutine(testRoutine);
        Assert.assertNotEquals(routine.getRoutineId(), 0);
    }

//    Fail test for when the username doesn't exist
    @Test(priority = 2, expectedExceptions = UserDoesntExist.class)
    void createRoutine2(){
        Routine testRoutine = new Routine(0, "UsernameDoesntExist", "Arms Day", 0, 0);
        Routine routine = routineDao.createRoutine(testRoutine);
    }

    @Test(priority =3, dependsOnMethods = "createRoutine1")
    void getRoutine1(){
        Routine testRoutine = new Routine(0, "TestUser", "Arms Day", 0, 0);
        Routine routine = routineDao.createRoutine(testRoutine);
        Routine result = routineDao.getRoutine(routine.getRoutineId());
        Assert.assertEquals(testRoutine.getRoutineName(), result.getRoutineName());
    }

    @Test(priority = 4, dependsOnMethods = "createRoutine1", expectedExceptions = RoutineDoesntExist.class)
    void getRoutine2(){
        Routine result = routineDao.getRoutine(0);
    }

    @Test(priority = 5)
    void getAllRoutines1(){
        Routine routine1 = new Routine(0, "TestUser", "Triceps Day", 0, 0);
        Routine routine2 = new Routine(0, "TestUser", "Chest Day", 0, 0);
        Routine routine3 = new Routine(0, "TestUser", "Shoulder Day", 0, 0);
        Routine routine4 = new Routine(0, "TestUser", "Biceps Day", 0, 0);

        routineDao.createRoutine(routine1);
        routineDao.createRoutine(routine2);
        routineDao.createRoutine(routine3);
        List<Routine> routines = routineDao.getAllRoutines();
        Assert.assertTrue(routines.size()>4);
    }

    @Test(priority =6, dependsOnMethods = "createRoutine1")
    void getAllRoutinesForUser1(){
        User user = new User("TestUser2", "password", "Test2", "User", "Male", 20, 70, 150);
        userDao.createUser(user);
        Routine routine1 = new Routine(0, "TestUser2", "Arms Day", 0, 0);
        Routine routine2 = new Routine(0, "TestUser2", "Chest Day", 0, 0);
        Routine routine3 = new Routine(0, "TestUser2", "Shoulder Day", 0, 0);
        routineDao.createRoutine(routine1);
        routineDao.createRoutine(routine2);
        routineDao.createRoutine(routine3);
        List<Routine> routines = routineDao.getAllRoutinesForUser("TestUser2");
        Assert.assertEquals(routines.size(), 3);
    }

    @Test(priority =7,  expectedExceptions = UserDoesntExist.class)
    void getAllRoutinesForUser2(){
        List<Routine> routines = routineDao.getAllRoutinesForUser("UsernameDoesntExist");
    }

    @Test(priority =8, dependsOnMethods = "createRoutine1")
    void updateRoutine1(){
        Routine testRoutine = new Routine(0, "TestUser", "Glutes Workout", 0, 0);
        Routine result = routineDao.createRoutine(testRoutine);
        result.setRoutineName("Glutes Day");
        Routine updatedRoutine = routineDao.updateRoutine(result);
        Assert.assertEquals(updatedRoutine.getRoutineName(),"Glutes Day" );
    }

    @Test(priority =8,  expectedExceptions = RoutineDoesntExist.class)
    void updateRoutine2(){
        Routine testRoutine = new Routine(0, "TestUser", "Glutes Workout", 0, 0);
        Routine updatedRoutine = routineDao.updateRoutine(testRoutine);
    }

    //Confused about what deleteRoutine actually returns
    @Test(priority =8, dependsOnMethods = "createRoutine1")
    void deleteRoutine1(){
        Routine testRoutine = new Routine(0, "TestUser", "Glutes Workout", 0, 0);
        Routine routine = routineDao.createRoutine(testRoutine);
        boolean result = routineDao.deleteRoutine(routine.getRoutineId());
        Assert.assertTrue(result);
    }

    @Test(priority =9, expectedExceptions = RoutineDoesntExist.class)
    void deleteRoutine2(){
        boolean result = routineDao.deleteRoutine(0);
    }
}
