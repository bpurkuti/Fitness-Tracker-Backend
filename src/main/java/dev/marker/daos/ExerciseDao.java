package dev.marker.daos;

import dev.marker.entities.Exercise;
import java.util.List;

public interface ExerciseDao {

    public abstract Exercise createExercise(Exercise e);

    public abstract Exercise getExercise(String exerciseName);

    public abstract List<Exercise> getAllExercises();

    public abstract Exercise updateExercise(Exercise e);

    public abstract String deleteExercise(String exerciseName);
}
