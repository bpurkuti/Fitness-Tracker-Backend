package dev.marker.services;

import dev.marker.entities.Exercise;

import java.util.List;

public interface ExerciseService {
    public abstract Exercise createExercise(Exercise e);

    public abstract Exercise getExercise(String exerciseName);

    public abstract List<Exercise> getAllExercises();

    public abstract Exercise updateExercise(Exercise e);

    public abstract String deleteExercise(String exerciseName);
}
