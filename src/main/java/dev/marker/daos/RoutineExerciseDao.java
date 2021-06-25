package dev.marker.daos;


import java.util.List;

import dev.marker.entities.RoutineExercise;

public interface RoutineExerciseDao {

    public abstract RoutineExercise createExercise(RoutineExercise e);

    public abstract RoutineExercise getExercise(String routineExerciseId);

    public abstract List<RoutineExercise> getAllExercisesInRoutine(String routineId);

    public abstract RoutineExercise updateExercise(String routineExerciseId);

    public abstract String deleteExercise(String routineExerciseId);

}
