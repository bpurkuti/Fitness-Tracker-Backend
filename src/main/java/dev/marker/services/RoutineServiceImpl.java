package dev.marker.services;

import dev.marker.daos.RoutineDao;
import dev.marker.entities.Routine;

import java.util.List;

public class RoutineServiceImpl implements RoutineService {

    private RoutineDao routineDao = null;

    public RoutineServiceImpl(RoutineDao routineDao){
        this.routineDao = routineDao;

    }

    @Override
    public Routine createRoutine(Routine r) {
        return routineDao.createRoutine(r);
    }

    @Override
    public Routine getRoutine(int routineId) {
        return routineDao.getRoutine(routineId);
    }

    @Override
    public List<Routine> getAllRoutines() {
        return routineDao.getAllRoutines();
    }

    @Override
    public List<Routine> getAllRoutinesForUser(String username) {
        return routineDao.getAllRoutinesForUser(username);
    }

    @Override
    public Routine updateRoutine(Routine r) {
        return routineDao.updateRoutine(r);
    }

    @Override
    public boolean deleteRoutine(int routineId) {
        return routineDao.deleteRoutine(routineId);
    }
}
