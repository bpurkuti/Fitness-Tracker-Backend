package dev.marker.services;

import dev.marker.entities.Exercise;
import dev.marker.entities.User;
import dev.marker.exceptions.DuplicationException;
import dev.marker.exceptions.IncorrectArguments;
import dev.marker.exceptions.PermissionException;
import dev.marker.exceptions.ResourceNotFound;

import java.util.List;

public interface ExerciseService {
    public abstract Exercise createExercise(User user, String exerciseName, String description, String type, String videoLink) throws DuplicationException, IncorrectArguments, PermissionException;

    public abstract Exercise getExercise(String exerciseName) throws ResourceNotFound;

    public abstract List<Exercise> getAllExercises();

    public abstract Exercise updateExercise(User user, String exerciseName, String description, String type, String videoLink) throws IncorrectArguments, PermissionException, ResourceNotFound;

    public abstract String deleteExercise(User user, String exerciseName) throws ResourceNotFound, PermissionException;
}
