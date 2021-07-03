package dev.marker.services;

import dev.marker.daos.RoutineExerciseDao;
import dev.marker.daos.RoutineDao;
import dev.marker.entities.RoutineExercise;
import dev.marker.exceptions.ResourceNotFound;
import dev.marker.entities.User;
import dev.marker.exceptions.PermissionException;

import java.util.List;

public class RoutineExerciseServiceImpl implements RoutineExerciseService{
    private RoutineExerciseDao routineExerciseDao;
    private RoutineDao routineDao;

    public RoutineExerciseServiceImpl(RoutineExerciseDao routineExerciseDao, RoutineDao routineDao) {
        this.routineExerciseDao = routineExerciseDao;
        this.routineDao = routineDao;
    }

    @Override
    public RoutineExercise createExercise(User user, RoutineExercise routineExercise) throws PermissionException {
        String curr = this.routineDao.getRoutine(routineExercise.getRoutineId()).getUsername();
        if(curr.equals(user.getUsername())){
            RoutineExercise returnedExercise = this.routineExerciseDao.createExercise(routineExercise);
            if(returnedExercise == null){
                throw new ResourceNotFound("Could not create a routine exercise at this time");
            }
        }
        else{
            throw new PermissionException("Cannot create exercise at this time");
        }
        return routineExercise;
    }

    @Override
    public RoutineExercise getExercise(User user, int routineExerciseId) throws PermissionException{

            RoutineExercise returnedExercise = this.routineExerciseDao.getExercise(routineExerciseId);
            if(returnedExercise == null){
                throw new ResourceNotFound("Could not retrieve that exercise at this time");
            }
            String curr = this.routineDao.getRoutine(returnedExercise.getRoutineId()).getUsername();
            if(curr.equals(user.getUsername())){
                return returnedExercise;
        }
            else{
                throw new PermissionException("You do not have access to that exercise");
            }

    }

    @Override
    public List<RoutineExercise> getAllExercisesInRoutine(User user, int routineId) throws PermissionException{
        String curr = this.routineDao.getRoutine(routineId).getUsername();
        if(curr.equals(user.getUsername())){
            List<RoutineExercise> exercises = this.routineExerciseDao.getAllExercisesInRoutine(routineId);
            if(exercises == null){
                throw new ResourceNotFound("Could not fetch exercises at this time");
            }
            return exercises;
        }
        else{
            throw new PermissionException("Cannot get exercises at this time");
        }
    }

    @Override
    public RoutineExercise updateExercise(User user, RoutineExercise routineExercise) throws PermissionException {
        String curr = this.routineDao.getRoutine(routineExercise.getRoutineId()).getUsername();
        if(curr.equals(user.getUsername())){
            RoutineExercise returnedExercise = this.routineExerciseDao.updateExercise(routineExercise);
            if(returnedExercise == null){
                throw new ResourceNotFound("Could not fetch exercises at this time");
            }
            return returnedExercise;
        }
        else{
            throw new PermissionException("Cannout update that exercise at this time");
        }
    }

    @Override
    public boolean deleteExercise(User user, int routineExerciseId) throws PermissionException{
        String curr = this.routineDao.getRoutine(this.routineExerciseDao.getExercise(routineExerciseId).getRoutineId()).getUsername();
        if(curr.equals(user.getUsername())){
            return this.routineExerciseDao.deleteExercise(routineExerciseId);
        }
        throw new PermissionException("Cannot delete that exercise at this time");
    }
}
