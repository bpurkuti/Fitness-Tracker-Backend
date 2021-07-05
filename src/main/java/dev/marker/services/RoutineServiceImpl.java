package dev.marker.services;

import dev.marker.daos.RoutineDao;
import dev.marker.entities.Routine;
import dev.marker.entities.User;
import dev.marker.exceptions.IncorrectArguments;
import dev.marker.exceptions.PermissionException;
import dev.marker.exceptions.ResourceNotFound;

import java.util.List;

public class RoutineServiceImpl implements RoutineService {

    private RoutineDao routineDao;

    public RoutineServiceImpl(RoutineDao routineDao){
        this.routineDao = routineDao;
    }

    @Override
    public Routine createRoutine(User user, String routineName, int dateScheduled) throws IncorrectArguments {
        StringBuilder error = new StringBuilder();
        if(routineName == null || routineName.length() == 0)
            error.append("Routine name cannot be empty\n");
        if(dateScheduled == 0)
            error.append("A scheduled date must be set\n");
        if(error.length() != 0)
            throw new IncorrectArguments(error.toString());
        Routine routine = new Routine(0, user.getUsername(), routineName, dateScheduled, 0);
        this.routineDao.createRoutine(routine);
        return routine;
    }

    @Override
    public Routine getRoutine(User user, int routineId) throws PermissionException, ResourceNotFound {
        Routine routine = this.routineDao.getRoutine(routineId);
        if(routine == null)
            throw new ResourceNotFound(String.format("Routine with id %d couldn't be found", routineId));
        if(!routine.getUsername().equals(user.getUsername()))
            throw new PermissionException("You dont have access to this routine.");
        return routine;
    }

    @Override
    public List<Routine> getAllRoutinesForUser(User user){
        return this.routineDao.getAllRoutinesForUser(user.getUsername());
    }

    @Override
    public Routine updateRoutine(User user, int routineId, String routineName, int dateScheduled, int dateCompleted)
            throws PermissionException, IncorrectArguments, ResourceNotFound {
        Routine routine = this.routineDao.getRoutine(routineId);
        if(routine == null)
            throw new ResourceNotFound(String.format("Routine with id %d couldn't be found", routineId));
        if(!routine.getUsername().equals(user.getUsername()))
            throw new PermissionException("You dont have access to this routine");
        StringBuilder error = new StringBuilder();
        if(dateScheduled <= 0)
            error.append("A valid scheduled date must be set\n");
        if(dateCompleted <= 0)
            error.append("A valid completion date must be set\n");
        if(error.length() > 0)
            throw new IncorrectArguments(error.toString());
        if(routineName != null && routineName.length() > 0)
            routine.setRoutineName(routineName);
        routine.setDateScheduled(dateScheduled);
        return this.routineDao.updateRoutine(routine);
    }

    @Override
    public boolean deleteRoutine(User user, int routineId) throws PermissionException, ResourceNotFound {
        Routine routine = this.routineDao.getRoutine(routineId);
        if(routine == null)
            throw new ResourceNotFound(String.format("Routine with id %d couldn't be found", routineId));
        if(!routine.getUsername().equals(user.getUsername()))
            throw new PermissionException("You don't have access to this routine");
        return this.routineDao.deleteRoutine(routineId);
    }
}
