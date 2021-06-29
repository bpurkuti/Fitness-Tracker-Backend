package dev.marker.services;

import dev.marker.entities.Routine;

import java.util.List;

public interface RoutineService {

    public abstract Routine createRoutine(Routine r);

    public abstract Routine getRoutine(int routineId);

    //Not sure if we need
    public abstract List<Routine> getAllRoutines();

    public abstract List<Routine> getAllRoutinesForUser(String username);

    public abstract Routine updateRoutine(Routine r);

    public abstract boolean deleteRoutine(int routineId);

}
