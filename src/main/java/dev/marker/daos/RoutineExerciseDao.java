package dev.marker.daos;


import java.util.List;

import dev.marker.entities.RoutineExercise;

public interface RoutineExerciseDao {

    public abstract RoutineExercise createExercise(RoutineExercise routineExercise);

    public abstract RoutineExercise getExercise(int routineExerciseId);

    public abstract List<RoutineExercise> getAllExercisesInRoutine(int routineId);

    public abstract RoutineExercise updateExercise(RoutineExercise routineExercise);

    public abstract boolean deleteExercise(int routineExerciseId);

}
