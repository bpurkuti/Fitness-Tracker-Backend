package daos;

import entities.Exercise;

import java.util.List;

public interface ExerciseDao {

    public abstract Exercise createExercise(Exercise e);

    public abstract Exercise getExercise(Exercise e);

    public abstract List<Exercise> getAllExercises();

    public abstract List<Exercise> getAllExercisesInRoutine(String routineId);

    public abstract Exercise updateExercise(String exerciseName);

    public abstract String deleteExercise(String username);
}
