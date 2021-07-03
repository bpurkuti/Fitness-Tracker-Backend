package dev.marker.services;

import dev.marker.entities.RoutineExercise;
import dev.marker.entities.User;
import dev.marker.exceptions.PermissionException;

import java.util.List;

public interface RoutineExerciseService {

    public abstract RoutineExercise createExercise(User user, RoutineExercise routineExercise) throws PermissionException;

    public abstract RoutineExercise getExercise(User user, int routineExerciseId) throws PermissionException;

    public abstract List<RoutineExercise> getAllExercisesInRoutine(User user, int routineId) throws PermissionException;

    public abstract RoutineExercise updateExercise(User user, RoutineExercise routineExercise) throws PermissionException;

    public abstract boolean deleteExercise(User user, int routineExerciseId) throws PermissionException;
}
