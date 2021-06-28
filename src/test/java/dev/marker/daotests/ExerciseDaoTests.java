package dev.marker.daotests;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import dev.marker.daos.ExerciseDao;
import dev.marker.entities.Exercise;

public class ExerciseDaoTests {

    private ExerciseDao exerciseDao;

    @Test
    void createNewExercise() {
        Exercise exercise = new Exercise("Jogging", "Run for a duration of time.", "Cardio", "www.youtube.com");
        Exercise returnedExercise = exerciseDao.createExercise(exercise);
        Assert.assertNotNull(returnedExercise);
        Assert.assertEquals(exercise.getExerciseName(), returnedExercise.getExerciseName());
        Assert.assertEquals(exercise.getDescription(), returnedExercise.getDescription());
        Assert.assertEquals(exercise.getType(), returnedExercise.getType());
        Assert.assertEquals(exercise.getVideoLink(), returnedExercise.getVideoLink());
    }

    @Test
    void createNullExercise1() {
        Exercise exercise = new Exercise("", "Run for a duration of time.", "Cardio", "www.youtube.com");
        Exercise returnedExercise = exerciseDao.createExercise(exercise);
        Assert.assertNull(returnedExercise);
    }

    @Test
    void createNullExercise2() {
        Exercise exercise = new Exercise("Jogging", "", "Cardio", "www.youtube.com");
        Exercise returnedExercise = exerciseDao.createExercise(exercise);
        Assert.assertNull(returnedExercise);
    }

    @Test
    void createNullExercise3() {
        Exercise exercise = new Exercise("Jogging", "Run for a duration of time.", "", "www.youtube.com");
        Exercise returnedExercise = exerciseDao.createExercise(exercise);
        Assert.assertNull(returnedExercise);
    }

    @Test
    void createExistingExercise() {
        Exercise exercise1 = new Exercise("Jogging", "Run for a duration of time.", "Cardio", "www.youtube.com");
        Exercise returnedExercise1 = exerciseDao.createExercise(exercise1);
        Exercise exercise2 = new Exercise("Jogging", "Run for a duration of time.", "Cardio", "www.youtube.com");
        Exercise returnedExercise2 = exerciseDao.createExercise(exercise2);
        Assert.assertNotNull(returnedExercise1);
        Assert.assertNull(returnedExercise2);
    }

    @Test
    void getExistingExercise() {
        Exercise exercise1 = new Exercise("Jogging", "Run for a duration of time.", "Cardio", "www.youtube.com");
        Exercise exercise2 = new Exercise("Sprinting", "Run for a duration of time.", "Cardio", "www.youtube.com");
        Exercise exercise3 = new Exercise("Lifting", "Lifting weights for a specified number of reps.", "Strenth", "www.youtube.com");
        exerciseDao.createExercise(exercise1);
        exerciseDao.createExercise(exercise2);
        exerciseDao.createExercise(exercise3);
        Exercise returnedExercise = exerciseDao.getExercise("Sprinting");
        Assert.assertNotNull(returnedExercise);
    }

    @Test
    void getNonExistingExercise() {
        Exercise returnedExercise = exerciseDao.getExercise("Sprinting");
        Assert.assertNull(returnedExercise);
    }

    @Test
    void getAllExercises1() {
        Exercise exercise1 = new Exercise("Jogging", "Run for a duration of time.", "Cardio", "www.youtube.com");
        Exercise exercise2 = new Exercise("Sprinting", "Run for a duration of time.", "Cardio", "www.youtube.com");
        Exercise exercise3 = new Exercise("Lifting", "Lifting weights for a specified number of reps.", "Strenth", "www.youtube.com");
        exerciseDao.createExercise(exercise1);
        exerciseDao.createExercise(exercise2);
        exerciseDao.createExercise(exercise3);
        List<Exercise> exercises = exerciseDao.getAllExercises();
        Assert.assertEquals(exercises.size(), 3);
    }

    @Test
    void getAllExercises2() {
        List<Exercise> exercises = exerciseDao.getAllExercises();
        Assert.assertEquals(exercises.size(), 0);
    }

    @Test
    void updateExistingExercise(){
        Exercise exercise1 = new Exercise("Jogging", "Run for a duration of time.", "Cardio", "www.youtube.com");
        exerciseDao.createExercise(exercise1);
        Exercise exercise2 = new Exercise("Jogging", "Updated description.", "Updated", "www.youtube.com/updated");
        Exercise returnedExercise = exerciseDao.updateExercise(exercise2);
        Assert.assertEquals(exercise2.getExerciseName(), returnedExercise.getExerciseName());
        Assert.assertEquals(exercise2.getDescription(), returnedExercise.getDescription());
        Assert.assertEquals(exercise2.getType(), returnedExercise.getType());
        Assert.assertEquals(exercise2.getVideoLink(), returnedExercise.getVideoLink());
    }

    @Test
    void updateNonExistingExercise(){
        Exercise exercise = new Exercise("Jogging", "Run for a duration of time.", "Cardio", "www.youtube.com");
        Exercise returnedExercise = exerciseDao.updateExercise(exercise);
        Assert.assertNull(returnedExercise);
    }

    @Test
    void deleteExistingExercise(){
        Exercise exercise = new Exercise("Jogging", "Run for a duration of time.", "Cardio", "www.youtube.com");
        exerciseDao.createExercise(exercise);
        String exerciseName = exerciseDao.deleteExercise(exercise.getExerciseName());
        Assert.assertEquals(exerciseName, exercise.getExerciseName());
    }

    @Test
    void deleteNonExistingExercise(){
        String exerciseName = exerciseDao.deleteExercise("Jogging");
        Assert.assertNull(exerciseName);
    }
}
