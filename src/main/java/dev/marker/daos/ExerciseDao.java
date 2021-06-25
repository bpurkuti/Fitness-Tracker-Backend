package dev.marker.daos;

import java.util.List;

import dev.marker.entities.Exercise;

public interface ExerciseDao {

    public abstract Exercise createExercise(Exercise e);

    public abstract Exercise getExercise(String exerciseName);

    public abstract List<Exercise> getAllExercises();

    public abstract Exercise updateExercise(String exerciseName);

    public abstract String deleteExercise(String exerciseName);
}
