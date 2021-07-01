package dev.marker.services;

import dev.marker.daos.RoutineExerciseDao;
import dev.marker.entities.RoutineExercise;
import dev.marker.exceptions.ResourceNotFound;


import java.util.List;

public class RoutineExerciseServiceImpl implements  RoutineExerciseService{
    private RoutineExerciseDao routineExerciseDao;

    public RoutineExerciseServiceImpl(RoutineExerciseDao routineExerciseDao) {
        this.routineExerciseDao = routineExerciseDao;
    }

    @Override
    public RoutineExercise createExercise(RoutineExercise routineExercise) {
        RoutineExercise returnedExercise = this.routineExerciseDao.createExercise(routineExercise);
        if(returnedExercise == null){
            throw new ResourceNotFound("Could not create a routine exercise at this time");
        }
        return routineExercise;
    }

    @Override
    public RoutineExercise getExercise(int routineExerciseId) {
        RoutineExercise returnedExercise = this.routineExerciseDao.getExercise(routineExerciseId);
        if(returnedExercise == null){
            throw new ResourceNotFound("Could not retrieve that exercise at this time");
        }
        return returnedExercise;
    }

    @Override
    public List<RoutineExercise> getAllExercisesInRoutine(int routineId) {
        List<RoutineExercise> exercises = this.routineExerciseDao.getAllExercisesInRoutine(routineId);
        if(exercises == null){
            throw new ResourceNotFound("Could not fetch exercises at this time");
        }
        return exercises;
    }

    @Override
    public RoutineExercise updateExercise(RoutineExercise routineExercise) {
        RoutineExercise returnedExercise = this.routineExerciseDao.updateExercise(routineExercise);
        if(returnedExercise == null){
            throw new ResourceNotFound("Could not fetch exercises at this time");
        }
        return returnedExercise;
    }

    @Override
    public boolean deleteExercise(int routineExerciseId) {
        return this.deleteExercise(routineExerciseId);
    }
}
