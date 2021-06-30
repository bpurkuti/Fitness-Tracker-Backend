package dev.marker.services;

import dev.marker.entities.Exercise;
import dev.marker.daos.ExerciseDao;
import dev.marker.daos.ExerciseDaoPostgres;
import dev.marker.exceptions.ExerciseDoesntExist;

import java.util.List;

public class ExerciseServiceImpl implements ExerciseService{

    ExerciseDao exerciseDao;

    public ExerciseServiceImpl(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
    }


    @Override
    public Exercise createExercise(Exercise e) {
        Exercise returnedExercise = exerciseDao.createExercise(e);
        if(returnedExercise == null){
            throw new ExerciseDoesntExist(String.format("Cannot create exercise with name %s", e.getExerciseName()));
        }
        return returnedExercise;
    }

    @Override
    public Exercise getExercise(String exerciseName) {
        Exercise returnedExercise = exerciseDao.getExercise(exerciseName);
        if(returnedExercise == null){
            throw new ExerciseDoesntExist(String.format("No existing exercise with name %s", exerciseName));
        }
        return returnedExercise;
    }

    @Override
    public List<Exercise> getAllExercises() {
        List<Exercise> returnedExercises = exerciseDao.getAllExercises();
        if(returnedExercises == null){
            throw new ExerciseDoesntExist("Cannot retrieve exercises at this time");
        }
        return returnedExercises;
    }

    @Override
    public Exercise updateExercise(Exercise e) {
        Exercise returnedExercise = exerciseDao.updateExercise(e);
        if(returnedExercise == null){
            throw new ExerciseDoesntExist(String.format("Cannot update exercise with name %s", e.getExerciseName()));
        }
        return returnedExercise;
    }

    @Override
    public String deleteExercise(String exerciseName) {
        String result = exerciseDao.deleteExercise(exerciseName);
        if(result == null){
            throw new ExerciseDoesntExist(String.format("Cannot delete exercise with name %s", exerciseName));
        }
        return result;
    }
}
