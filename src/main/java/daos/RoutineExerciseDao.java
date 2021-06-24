package daos;


import entities.RoutineExercise;

import java.util.List;

public interface RoutineExerciseDao {

    public abstract RoutineExercise createExercise(RoutineExercise e);

    public abstract RoutineExercise getExercise(String exerciseName);

    //Don't know if we need
    public abstract List<RoutineExercise> getAllRoutineExercises(String exerciseName);

    public abstract List<RoutineExercise> getAllExercisesInRoutine(String routineId);

    //Used both id and exercise name in case someone has the same exercise in 2 separate routines
    public abstract RoutineExercise updateExercise(String routineId, String exerciseName);

    public abstract String deleteExercise(String routineId, String exerciseName);

}
