package dev.marker.services;

import dev.marker.entities.Exercise;
import dev.marker.entities.User;
import dev.marker.daos.ExerciseDao;
import dev.marker.exceptions.DuplicationException;
import dev.marker.exceptions.IncorrectArguments;
import dev.marker.exceptions.PermissionException;
import dev.marker.exceptions.ResourceNotFound;

import java.util.List;

public class ExerciseServiceImpl implements ExerciseService {

    ExerciseDao exerciseDao;

    public ExerciseServiceImpl(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
    }

    @Override
    public Exercise createExercise(User user, String exerciseName, String description, String type, String videoLink)
            throws IncorrectArguments, PermissionException, DuplicationException {
        if (!user.isAdmin())
            throw new PermissionException("You don't have permission to create exercises");
        StringBuilder error = new StringBuilder();
        if (exerciseName == null || exerciseName.length() == 0)
            error.append("Exercise name is empty\n");
        if (description == null || description.length() == 0)
            error.append("Exercise description is empty\n");
        if (type == null || type.length() == 0)
            error.append("Exercise type is empty\n");
        if (error.length() > 0)
            throw new IncorrectArguments(error.toString());
        Exercise exercise = new Exercise(exerciseName, description, type, videoLink);
        exercise = this.exerciseDao.createExercise(exercise);
        if(exercise == null)
            throw new DuplicationException(String.format("Exercise %s already exists", exerciseName));
        return exercise;
    }

    @Override
    public Exercise getExercise(String exerciseName) throws ResourceNotFound {
        Exercise exercise = this.exerciseDao.getExercise(exerciseName);
        if (exercise == null)
            throw new ResourceNotFound(String.format("Exercise %s couldn't be found", exerciseName));
        return exercise;
    }

    @Override
    public List<Exercise> getAllExercises() {
        return this.exerciseDao.getAllExercises();
    }

    @Override
    public Exercise updateExercise(User user, String exerciseName, String description, String type, String videoLink)
            throws IncorrectArguments, PermissionException, ResourceNotFound {
        if (!user.isAdmin())
            throw new PermissionException("You don't have permission to create exercises");
        Exercise exercise = this.exerciseDao.getExercise(exerciseName);
        if (exercise == null)
            throw new ResourceNotFound(String.format("Exercise %s couldn't be found", exerciseName));
        StringBuilder error = new StringBuilder();
        if (exerciseName == null || exerciseName.length() == 0)
            error.append("Exercise name is empty\n");
        if (description == null || description.length() == 0)
            error.append("Exercise description is empty\n");
        if (type == null || type.length() == 0)
            error.append("Exercise type is empty\n");
        if (error.length() > 0)
            throw new IncorrectArguments(error.toString());
        exercise = new Exercise(exerciseName, description, type, videoLink);
        return this.exerciseDao.updateExercise(exercise);
    }

    @Override
    public String deleteExercise(User user, String exerciseName) throws ResourceNotFound, PermissionException {
        if (!user.isAdmin())
            throw new PermissionException("You don't have permission to create exercises");
        exerciseName = this.exerciseDao.deleteExercise(exerciseName);
        if (exerciseName == null)
            throw new ResourceNotFound(String.format("Exercise %s couldn't be found", exerciseName));
        return exerciseName;
    }
}
