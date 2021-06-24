package daos;


import entities.RoutineExercise;

import java.util.List;

public interface RoutineExerciseDao {

    public abstract RoutineExercise createExercise(RoutineExercise e);

    public abstract RoutineExercise getExercise(String routineExerciseId);

    public abstract List<RoutineExercise> getAllExercisesInRoutine(String routineId);

    public abstract RoutineExercise updateExercise(String routineExerciseId);

    public abstract String deleteExercise(String routineExerciseId);

}
