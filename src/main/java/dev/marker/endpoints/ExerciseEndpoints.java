package dev.marker.endpoints;

import java.util.List;

import com.google.gson.Gson;

import dev.marker.entities.Exercise;
import dev.marker.entities.User;
import dev.marker.exceptions.DuplicationException;
import dev.marker.exceptions.IncorrectArguments;
import dev.marker.exceptions.PermissionException;
import dev.marker.exceptions.ResourceNotFound;
import dev.marker.exceptions.InvalidSession;
import dev.marker.services.AccountService;
import dev.marker.services.ExerciseService;
import io.javalin.http.Handler;

public class ExerciseEndpoints {
    private AccountService accountService;
    private ExerciseService exerciseService;
    private Gson gson;

    public ExerciseEndpoints(ExerciseService exerciseService, AccountService service) {
        this.accountService = service;
        this.exerciseService = exerciseService;
        this.gson = new Gson();
    }

    /**
     * Creates an exercise
     * 
     * @input json => {JSON(Exercise.class), session: (session)}
     * @returns json => {JSON(Exercise.class)}
     */
    public Handler createExercise = (ctx) -> {
        try {
            Session session = this.gson.fromJson(ctx.body(), Session.class);
            Exercise exercise = this.gson.fromJson(ctx.body(), Exercise.class);
            User user = this.accountService.getUser(session.getSession());
            exercise = this.exerciseService.createExercise(user, exercise.getExerciseName(), exercise.getDescription(),
                    exercise.getType(), exercise.getVideoLink());
            ctx.status(201);
            ctx.result(this.gson.toJson(exercise));
        } catch (DuplicationException e) {
            ctx.status(409);
            ctx.result(e.getMessage());
        } catch (PermissionException e) {
            ctx.status(403);
            ctx.result(e.getMessage());
        } catch (IncorrectArguments e) {
            ctx.status(400);
            ctx.result(e.getMessage());
        } catch (InvalidSession e) {
            ctx.status(401);
            ctx.result(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result("The server encountered an error");
        }
    };

    /**
     * Gets an exercise
     * 
     * @input json => {"exerciseName": (exerciseName)}
     * @returns json => {JSON(Exercise.class)}
     */
    public Handler getExercise = (ctx) -> {
        try {
            Exercise exercise = this.gson.fromJson(ctx.body(), Exercise.class);
            exercise = this.exerciseService.getExercise(exercise.getExerciseName());
            ctx.status(200);
            ctx.result(this.gson.toJson(exercise));
        } catch (ResourceNotFound e) {
            ctx.status(404);
            ctx.result(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result("The server encountered an error");
        }
    };

    /**
     * Gets all exercises
     * 
     * @input json => None
     * @returns json => [JSON(Exercise.class), JSON(Exercise.class), ...]
     */
    public Handler getAllExercises = (ctx) -> {
        try {
            List<Exercise> exercises = this.exerciseService.getAllExercises();
            ctx.status(200);
            ctx.result(this.gson.toJson(exercises));
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result("The server encountered an error");
        }
    };

    /**
     * Updates an exercise
     * 
     * @input json => {JSON(Exercise.class), session: (session)}
     * @returns json => {JSON(Exercise.class)}
     */
    public Handler updateExercise = (ctx) -> {
        try {
            Session session = this.gson.fromJson(ctx.body(), Session.class);
            Exercise exercise = this.gson.fromJson(ctx.body(), Exercise.class);
            User user = this.accountService.getUser(session.getSession());
            exercise = this.exerciseService.updateExercise(user, exercise.getExerciseName(), exercise.getDescription(),
                    exercise.getType(), exercise.getVideoLink());
            ctx.status(200);
            ctx.result(this.gson.toJson(exercise));
        } catch (ResourceNotFound e) {
            ctx.status(404);
            ctx.result(e.getMessage());
        } catch (PermissionException e) {
            ctx.status(403);
            ctx.result(e.getMessage());
        } catch (IncorrectArguments e) {
            ctx.status(400);
            ctx.result(e.getMessage());
        } catch (InvalidSession e) {
            ctx.status(401);
            ctx.result(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result("The server encountered an error");
        }
    };

    /**
     * Updates an exercise
     * 
     * @input json => {"exerciseName": (exerciseName), session: (session)}
     * @returns json => "exerciseName"
     */
    public Handler deleteExercise = (ctx) -> {
        try {
            Session session = this.gson.fromJson(ctx.body(), Session.class);
            Exercise exercise = this.gson.fromJson(ctx.body(), Exercise.class);
            User user = this.accountService.getUser(session.getSession());
            String exerciseStr = this.exerciseService.deleteExercise(user, exercise.getExerciseName());
            ctx.status(200);
            ctx.result(this.gson.toJson(exerciseStr));
        } catch (ResourceNotFound e) {
            ctx.status(404);
            ctx.result(e.getMessage());
        } catch (PermissionException e) {
            ctx.status(403);
            ctx.result(e.getMessage());
        } catch (InvalidSession e) {
            ctx.status(401);
            ctx.result(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result("The server encountered an error");
        }
    };
}
