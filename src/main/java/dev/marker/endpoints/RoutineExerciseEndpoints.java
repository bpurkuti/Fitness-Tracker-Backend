package dev.marker.endpoints;

import com.google.gson.Gson;
import dev.marker.entities.Routine;
import dev.marker.entities.RoutineExercise;
import dev.marker.exceptions.PermissionException;
import dev.marker.exceptions.ResourceNotFound;
import dev.marker.services.RoutineExerciseService;
import io.javalin.http.Handler;

import java.util.List;
import dev.marker.services.AccountService;
import dev.marker.entities.User;

public class RoutineExerciseEndpoints {
    RoutineExerciseService routineExerciseService;
    AccountService accountService;
    Gson gson;


    public RoutineExerciseEndpoints(RoutineExerciseService routineExerciseService, AccountService accountService) {
        this.routineExerciseService = routineExerciseService;
        this.accountService = accountService;
        this.gson = new Gson();
    }

    /**
     * Creates a routine exercise using the specified values
     *
     * @input json => {JSON(routineExercise.class), "session": (session)}
     * @returns json => JSON(routineExercise.class)
     */
    public Handler createRoutineExercise = ctx -> {
        try {
            Session session = this.gson.fromJson(ctx.body(), Session.class);
            User user = this.accountService.getUser(session.getSession());

            RoutineExercise routineExercise = gson.fromJson(ctx.body(), RoutineExercise.class);
            routineExercise = routineExerciseService.createExercise(user, routineExercise);
            String routineExerciseJSON = gson.toJson(routineExercise);
            ctx.result(routineExerciseJSON);
            ctx.status(201);
        } catch (ResourceNotFound e) {
            ctx.result(e.getMessage());
            ctx.status(404);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result("The server encountered an error");
        }
    };

    /**
     * Gets the routine exercise with the associated ID
     *
     * @input json => {"routineExerciseId": (routineExerciseId), session": (session)}
     * @returns json => JSON(routineExercise.class)
     */

    public Handler getRoutineExercise = ctx -> {
        try {
            Session session = this.gson.fromJson(ctx.body(), Session.class);
            RoutineExercise routineExercise = this.gson.fromJson(ctx.body(), RoutineExercise.class);
            User user = this.accountService.getUser(session.getSession());

            routineExercise = routineExerciseService.getExercise(user, routineExercise.getRoutineExerciseId());
            String routineExerciseJSON = gson.toJson(routineExercise);
            ctx.result(routineExerciseJSON);
            ctx.status(200);
        } catch (ResourceNotFound e) {
            ctx.result(e.getMessage());
            ctx.status(404);
        } catch (PermissionException e) {
            ctx.result(e.getMessage());
            ctx.status(403);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result("The server encountered an error");
        }
    };

    /**
     * Gets all routine exercises for the given routine
     *
     * @input json => {"routineId": (routineId), session": (session)}
     * @returns json => JSON(routineExercise.class)
     */
    public Handler getAllExercisesInRoutine = ctx -> {
        try {
            Session session = this.gson.fromJson(ctx.body(), Session.class);
            User user = this.accountService.getUser(session.getSession());
            Routine routine = this.gson.fromJson(ctx.body(), Routine.class);
            List<RoutineExercise> routineExercises = routineExerciseService.getAllExercisesInRoutine(user,
                    routine.getRoutineId());
            String routineExercisesJSON = gson.toJson(routineExercises);
            ctx.result(routineExercisesJSON);
            ctx.status(200);
        } catch (ResourceNotFound e) {
            ctx.result(e.getMessage());
            ctx.status(404);
        } catch (PermissionException e) {
            ctx.result(e.getMessage());
            ctx.status(403);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result("The server encountered an error");
        }
    };

    /**
     * Updates the given routine exercise
     *
     * @input json => {JSON(routineExercise.class), session": (session)}
     * @returns json => JSON(routineExercise.class)
     */

    public Handler updateRoutineExercise = ctx -> {
        try {
            RoutineExercise routineExercise = gson.fromJson(ctx.body(), RoutineExercise.class);
            Session session = this.gson.fromJson(ctx.body(), Session.class);
            User user = this.accountService.getUser(session.getSession());
            routineExercise = this.routineExerciseService.updateExercise(user, routineExercise);
            String routineExerciseJSON = gson.toJson(routineExercise);
            ctx.result(routineExerciseJSON);
            ctx.status(200);
        } catch (ResourceNotFound e) {
            ctx.result(e.getMessage());
            ctx.status(404);
        } catch (PermissionException e) {
            ctx.result(e.getMessage());
            ctx.status(403);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result("The server encountered an error");
        }
    };

    /**
     * Deletes the routine exercise with the associated ID
     *
     * @input json => {"routineExerciseId": (routineExerciseId), session": (session)}
     * @returns json => JSON(routineExercise.class)
     */

    public Handler deleteRoutineExercise = ctx -> {
        try {
            RoutineExercise routineExercise = gson.fromJson(ctx.body(), RoutineExercise.class);
            Session session = this.gson.fromJson(ctx.body(), Session.class);
            User user = this.accountService.getUser(session.getSession());

            boolean result = this.routineExerciseService.deleteExercise(user, routineExercise.getRoutineExerciseId());
            String resultString = String.valueOf(result);
            ctx.result(resultString);
            ctx.status(200);
        } catch (ResourceNotFound e) {
            ctx.result(e.getMessage());
            ctx.status(404);
        } catch (PermissionException e) {
            ctx.result(e.getMessage());
            ctx.status(403);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result("The server encountered an error");
        }
    };
}
