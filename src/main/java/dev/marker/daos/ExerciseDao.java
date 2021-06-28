package dev.marker.daos;

import java.util.List;

import dev.marker.entities.Exercise;

public interface ExerciseDao {

    public abstract Exercise createExercise(Exercise e);

    public abstract Exercise getExercise(String exerciseName);

    public abstract List<Exercise> getAllExercises();

    public abstract Exercise updateExercise(Exercise e);

    public abstract String deleteExercise(String exerciseName);
}
