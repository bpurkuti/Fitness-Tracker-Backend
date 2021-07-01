package dev.marker.services;

import dev.marker.entities.Routine;
import dev.marker.entities.User;
import dev.marker.exceptions.IncorrectArguments;
import dev.marker.exceptions.PermissionException;
import dev.marker.exceptions.ResourceNotFound;

import java.util.List;

public interface RoutineService {

    public abstract Routine createRoutine(User user, String routineName, int dateScheduled) throws IncorrectArguments;

    public abstract Routine getRoutine(User user, int routineId) throws PermissionException, ResourceNotFound;

    public abstract List<Routine> getAllRoutinesForUser(User user);

    public abstract Routine updateRoutine(User user, int routineId, String routineName, int dateScheduled) throws PermissionException, IncorrectArguments, ResourceNotFound;

    public abstract boolean deleteRoutine(User user, int routineId) throws PermissionException, ResourceNotFound;

}
