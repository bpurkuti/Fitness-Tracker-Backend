package dev.marker.daotests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import dev.marker.daos.RoutineExerciseDao;
import dev.marker.entities.Routine;
import dev.marker.entities.RoutineExercise;

public class RoutineExerciseDaoTests {
    
    private static RoutineExerciseDao routineExerciseDao;
    private static Routine routine;

    @Test
    void createNewExercise() {
        RoutineExercise exercise = new RoutineExercise(1, "Jogging", routine.getRoutineId(), 1, 1, 1);
        RoutineExercise returnedExercise = routineExerciseDao.createExercise(exercise);
        Assert.assertEquals(exercise.getExerciseName(), returnedExercise.getExerciseName());
        Assert.assertEquals(exercise.getReps(), returnedExercise.getReps());
        Assert.assertEquals(exercise.getWeight(), returnedExercise.getWeight());
        Assert.assertEquals(exercise.getDuration(), returnedExercise.getDuration());
        Assert.assertNotEquals(exercise.getRoutineExerciseId(), 0);
    }

    @Test
    void getExistingExercise() {
        RoutineExercise exercise = new RoutineExercise(1, "Jogging", routine.getRoutineId(), 1, 1, 1);
        exercise = routineExerciseDao.createExercise(exercise);
        RoutineExercise returnedExercise = routineExerciseDao.getExercise(exercise.getRoutineExerciseId());
        Assert.assertNotNull(returnedExercise);
        Assert.assertEquals(exercise.getRoutineExerciseId(), returnedExercise.getRoutineExerciseId());
        Assert.assertEquals(exercise.getExerciseName(), returnedExercise.getExerciseName());
        Assert.assertEquals(exercise.getDuration(), returnedExercise.getDuration());
        Assert.assertEquals(exercise.getReps(), returnedExercise.getReps());
        Assert.assertEquals(exercise.getRoutineId(), returnedExercise.getRoutineId());
        Assert.assertEquals(exercise.getWeight(), returnedExercise.getWeight());
    }

    @Test
    void getNonExistingExercise() {
        RoutineExercise returnedExercise = routineExerciseDao.getExercise(1);
        Assert.assertNull(returnedExercise);
    }

    @Test
    void getAllExercises() {
        RoutineExercise exercise1 = new RoutineExercise(1, "Running", routine.getRoutineId(), 1, 1, 1);
        exercise1 = routineExerciseDao.createExercise(exercise1);
        RoutineExercise exercise2 = new RoutineExercise(1, "Jogging", routine.getRoutineId(), 1, 1, 1);
        exercise2 = routineExerciseDao.createExercise(exercise2);
        RoutineExercise exercise3 = new RoutineExercise(1, "Sprinting", routine.getRoutineId(), 1, 1, 1);
        exercise3 = routineExerciseDao.createExercise(exercise3);
        List<RoutineExercise> returnedExercises = routineExerciseDao.getAllExercisesInRoutine(exercise1.getRoutineId());
        Assert.assertNotNull(returnedExercises);
        Assert.assertEquals(returnedExercises.size(), 3);
        Assert.assertEquals(returnedExercises.get(0).getExerciseName(), "Running");
        Assert.assertEquals(returnedExercises.get(1).getExerciseName(), "Jogging");
        Assert.assertEquals(returnedExercises.get(2).getExerciseName(), "Sprinting");
    }

    @Test
    void updateExistingExercise() {
        RoutineExercise exercise1 = new RoutineExercise(1, "Jogging", routine.getRoutineId(), 1, 1, 1);
        RoutineExercise exercise2 = routineExerciseDao.createExercise(exercise1);
        exercise2.setWeight(200);
        exercise2.setReps(100);
        RoutineExercise returnedExercise = routineExerciseDao.updateExercise(exercise2);
        Assert.assertNotNull(returnedExercise);
        Assert.assertEquals(exercise1.getExerciseName(), returnedExercise.getExerciseName());
        Assert.assertEquals(exercise2.getWeight(), returnedExercise.getWeight());
        Assert.assertEquals(exercise2.getReps(), returnedExercise.getReps());
    }

    @Test
    void updateNonExistingExercise() {
        RoutineExercise exercise1 = new RoutineExercise(1, "Jogging", routine.getRoutineId(), 1, 1, 1);
        RoutineExercise returnedExercise = routineExerciseDao.updateExercise(exercise1);
        Assert.assertNull(returnedExercise);
    }

    @Test
    void deleteExistingExercise() {
        RoutineExercise exercise1 = new RoutineExercise(1, "Jogging", routine.getRoutineId(), 1, 1, 1);
        RoutineExercise exercise2 = routineExerciseDao.createExercise(exercise1);
        boolean deleted = routineExerciseDao.deleteExercise(exercise2.getRoutineExerciseId());
        Assert.assertTrue(deleted);
    }

    @Test
    void deleteNonExistingExercise() {
        boolean deleted = routineExerciseDao.deleteExercise(1);
        Assert.assertFalse(deleted);
    }
}
