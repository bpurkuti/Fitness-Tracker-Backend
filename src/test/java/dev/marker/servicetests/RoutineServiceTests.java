package dev.marker.servicetests;
import dev.marker.daos.UserDao;
import dev.marker.entities.Routine;
import dev.marker.entities.User;
import dev.marker.exceptions.ResourceNotFound;
import dev.marker.services.RoutineService;
import dev.marker.daos.RoutineDao;
import dev.marker.services.RoutineServiceImpl;
import org.testng.annotations.Test;

import java.util.List;

public class RoutineServiceTests {

//    RoutineDao routineDao = Mockito.mock((RoutineDao.class));
    RoutineDao routineDao = null;
    RoutineService routineService = new RoutineServiceImpl(routineDao);
    UserDao userDao = null;

    User user = new User("RServiceUser", "password", "Test", "User", "Male", 20, 70, 150, false);

    //There is only tests for exceptions here currently
    @Test(expectedExceptions = ResourceNotFound.class)
    void createRoutineWithoutUser(){
        Routine testRoutine = new Routine(0, "UsernameDoesntExist", "Arms Day", 0, 0);
        Routine routine = routineService.createRoutine(testRoutine);
    }

    @Test(expectedExceptions = ResourceNotFound.class)
    void getNonExistingRoutine(){
        Routine routine = routineService.getRoutine(0);
    }

    //If empty
//    @Test(expectedExceptions = ResourceNotFound.class)
//    void emptyGetAllRoutines(){
//        List<Routine> routines = routineService.getAllRoutines();
//    }

    @Test(expectedExceptions = ResourceNotFound.class)
    void invalidGetAllRoutinesForUser(){
        List<Routine> routines = routineService.getAllRoutinesForUser("UsernameDoesntExist");
    }

    @Test(expectedExceptions = ResourceNotFound.class)
    void updateNonExistingRoutine(){
        User user = new User("RServiceUser", "password", "Test", "User", "Male", 20, 70, 150, false);
        userDao.createUser(user);
        Routine testRoutine = new Routine(0, user.getUsername(), "Glutes Workout", 0, 0);
        Routine updatedRoutine = routineService.updateRoutine(testRoutine);
    }

    @Test(expectedExceptions = ResourceNotFound.class)
    void deleteNonExistingRoutine(){
        boolean result = routineService.deleteRoutine(0);
    }
}

