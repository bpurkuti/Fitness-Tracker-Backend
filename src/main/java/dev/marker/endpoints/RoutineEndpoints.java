package dev.marker.endpoints;

import com.google.gson.Gson;

import dev.marker.entities.Routine;
import dev.marker.entities.User;
import dev.marker.services.AccountService;
import dev.marker.services.RoutineService;
import dev.marker.exceptions.*;
import io.javalin.http.Handler;
import java.util.*;

public class RoutineEndpoints {
    private AccountService accountService;
    private RoutineService routineService;
    private Gson gson;

    public RoutineEndpoints(RoutineService routineService, AccountService service) {
        this.accountService = service;
        this.routineService = routineService;
        this.gson = new Gson();
    }

    /**
     * Creates a routine
     * 
     * @input json => {JSON(Routine.class), session: (session)} 
     * @returns json => {JSON(Routine.class)}
     */
    public Handler createRoutine = (ctx) -> {
        try {
            Session session = this.gson.fromJson(ctx.body(), Session.class);
            Routine routine = this.gson.fromJson(ctx.body(), Routine.class);
            User user = this.accountService.getUser(session.getSession());
            routine = this.routineService.createRoutine(user, routine.getRoutineName(), routine.getDateScheduled());
            ctx.status(201);
            ctx.result(this.gson.toJson(routine));
        } catch (InvalidSession e) {
            ctx.status(401);
            ctx.result(e.getMessage());
        } catch (IncorrectArguments e) {
            ctx.status(400);
            ctx.result(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result("The server encountered an error");
        }
    };

    /**
     * Gets a routine based on id
     * 
     * @input json => {"routineId": (routineId), session: (session)} 
     * @returns json => JSON(Routine.class)
     */
    public Handler getRoutineById = (ctx) -> {
        try {
            Session session = this.gson.fromJson(ctx.body(), Session.class);
            Routine routine = this.gson.fromJson(ctx.body(), Routine.class);
            User user = this.accountService.getUser(session.getSession());
            routine = this.routineService.getRoutine(user, routine.getRoutineId());
            ctx.status(200);
            ctx.result(this.gson.toJson(routine));
        } catch (InvalidSession e) {
            ctx.status(401);
            ctx.result(e.getMessage());
        } catch (PermissionException e) {
            ctx.status(403);
            ctx.result(e.getMessage());
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
     * Gets all routines for user
     * 
     * @input json => {session: (session)} 
     * @returns json => [JSON(Routine.class), JSON(Routine.class), ...]
     */
    public Handler getRoutinesForUser = (ctx) -> {
        try {
            Session session = this.gson.fromJson(ctx.body(), Session.class);
            User user = this.accountService.getUser(session.getSession());
            List<Routine> routines = this.routineService.getAllRoutinesForUser(user);
            ctx.status(200);
            ctx.result(this.gson.toJson(routines));
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
     * Updates a routine
     * 
     * @input json => {JSON(Routine.class), session: (session)} 
     * @returns json => JSON(Routine.class)
     */
    public Handler updateRoutine = (ctx) -> {
        try {
            Session session = this.gson.fromJson(ctx.body(), Session.class);
            Routine routine = this.gson.fromJson(ctx.body(), Routine.class);
            User user = this.accountService.getUser(session.getSession());
            routine = this.routineService.updateRoutine(user, routine.getRoutineId(), routine.getRoutineName(), routine.getDateScheduled(), routine.getDateCompleted());
            ctx.status(200);
            ctx.result(this.gson.toJson(routine));
        } catch (InvalidSession e) {
            ctx.status(401);
            ctx.result(e.getMessage());
        } catch (PermissionException e) {
            ctx.status(403);
            ctx.result(e.getMessage());
        } catch (IncorrectArguments e) {
            ctx.status(400);
            ctx.result(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result("The server encountered an error");
        }
    };

    /**
     * Deletes a routine
     * 
     * @input json => {"routineId": (routineId), session: (session)}
     * @returns json => None
     */
    public Handler deleteRoutine = (ctx) -> {
        try {
            Session session = this.gson.fromJson(ctx.body(), Session.class);
            Routine routine = this.gson.fromJson(ctx.body(), Routine.class);
            User user = this.accountService.getUser(session.getSession());
            if(this.routineService.deleteRoutine(user, routine.getRoutineId())){
            ctx.status(200);
            ctx.result("");
            }
            else{
                throw new Exception("Error deleting request");
            }
        } catch (InvalidSession e) {
            ctx.status(401);
            ctx.result(e.getMessage());
        } catch (PermissionException e) {
            ctx.status(403);
            ctx.result(e.getMessage());
        } catch (IncorrectArguments e) {
            ctx.status(400);
            ctx.result(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result("The server encountered an error");
        }
    };
}
