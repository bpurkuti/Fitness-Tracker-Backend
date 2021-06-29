package dev.marker.services;

import dev.marker.daos.RoutineDao;

public class RoutineServiceImpl implements RoutineService {

    private RoutineDao routineDao = null;

    public RoutineServiceImpl(RoutineDao routineDao){
        this.routineDao = routineDao;

    }
}
