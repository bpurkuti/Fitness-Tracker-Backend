package dev.marker.services;

import dev.marker.daos.RoutineExerciseDaoPostgres;
import dev.marker.entities.RoutineExercise;
import dev.marker.daos.RoutineExerciseDao;
import java.util.List;

public interface RoutineExerciseService {

    public abstract RoutineExercise createExercise(RoutineExercise routineExercise);

    public abstract RoutineExercise getExercise(int routineExerciseId);

    public abstract List<RoutineExercise> getAllExercisesInRoutine(int routineId);

    public abstract RoutineExercise updateExercise(RoutineExercise routineExercise);

    public abstract boolean deleteExercise(int routineExerciseId);
}
