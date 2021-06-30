package dev.marker.servicetests;

import dev.marker.daos.UserDao;
import dev.marker.entities.Exercise;
import dev.marker.entities.User;
import dev.marker.exceptions.ExerciseDoesntExist;
import dev.marker.exceptions.ResourceNotFound;
import dev.marker.services.ExerciseService;
import dev.marker.daos.ExerciseDao;
import dev.marker.services.ExerciseServiceImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ExerciseServiceTests {
    //Tests if exceptions are thrown at the right time
    //GetEmptyExercises only works on a table that started empty
    ExerciseDao exerciseDao = null;
    ExerciseService exerciseService = new ExerciseServiceImpl(exerciseDao);

    Exercise validExercise = new Exercise("validExercise", "real exercise", "real", "youtube.com");
    Exercise validExercise2 = new Exercise("validExercise2", "real exercise", "real", "youtube.com");

    Exercise invalidExercise = new Exercise("invalidExercise", "not a real exercise", "fake", "notavirus.net");

    @BeforeClass
    void createTestExercise(){
        exerciseDao.createExercise(validExercise);
    }

    @Test(expectedExceptions = ExerciseDoesntExist.class)
    void createExistingExercise(){
        exerciseService.createExercise(validExercise);
    }

    @Test(priority = 3)
    void createNewExercise(){
        Assert.assertNotNull(exerciseService.createExercise(validExercise2));
    }

    @Test(expectedExceptions = ExerciseDoesntExist.class)
    void getInvalidExercise(){
        exerciseService.getExercise(invalidExercise.getExerciseName());
    }
    @Test()
    void getValidExercise(){
        Assert.assertNotNull(exerciseService.getExercise(validExercise.getExerciseName()));
    }

    @Test()
    void getExistingExercises(){
        Assert.assertNotNull(exerciseService.getAllExercises());
    }
    //Will execute after delete test when table should be empty
    @Test(priority = 2, expectedExceptions = ExerciseDoesntExist.class)
    void getEmptyExercises(){
        exerciseService.getAllExercises();
    }

    @Test()
    void updateValidExercise(){
        validExercise.setExerciseName("updated");
        Assert.assertNotNull(exerciseService.updateExercise(validExercise));
    }

    @Test(expectedExceptions = ExerciseDoesntExist.class)
    void updateInvalidExercise(){
        exerciseService.updateExercise(invalidExercise);
    }

    @Test()
    void deleteValidExercise(){
        Assert.assertNotNull(exerciseService.deleteExercise(validExercise.getExerciseName()));
    }

    @Test(expectedExceptions = ExerciseDoesntExist.class)
    void deleteInvalidExercise(){
        exerciseService.deleteExercise(invalidExercise.getExerciseName());
    }



}
