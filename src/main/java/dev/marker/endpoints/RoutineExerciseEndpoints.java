package dev.marker.endpoints;

import com.google.gson.Gson;
import dev.marker.entities.Exercise;
import dev.marker.entities.RoutineExercise;
import dev.marker.exceptions.ExerciseDoesntExist;
import dev.marker.exceptions.ResourceNotFound;
import dev.marker.services.RoutineExerciseService;
import dev.marker.services.RoutineExerciseServiceImpl;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.List;
import dev.marker.services.AccountService;
import dev.marker.services.AccountServiceImpl;
import dev.marker.entities.User;

public class RoutineExerciseEndpoints {
    RoutineExerciseService routineExerciseService;
    Gson gson;

    public RoutineExerciseEndpoints(RoutineExerciseService routineExerciseService){
        this.routineExerciseService = routineExerciseService;
        this.gson = new Gson();
    }

    public Handler createRoutineExercise = ctx -> {
        RoutineExercise routineExercise = gson.fromJson(ctx.body(), RoutineExercise.class);
        try{
            routineExercise = routineExerciseService.createExercise(routineExercise);
            String routineExerciseJSON = gson.toJson(routineExercise);
            ctx.result(routineExerciseJSON);
            ctx.status(201);
        }
        catch(ResourceNotFound e){
            ctx.result(e.getMessage());
            ctx.status(409);
        }
    };

    public Handler getRoutineExercise = ctx -> {
        int routineExerciseId = Integer.parseInt(ctx.pathParam("routineExerciseId"));
        try{
            RoutineExercise routineExercise= routineExerciseService.getExercise(routineExerciseId);
            String routineExerciseJSON = gson.toJson(routineExercise);
            ctx.result(routineExerciseJSON);
            ctx.status(200);
        }
        catch(ResourceNotFound e){
            ctx.result(e.getMessage());
            ctx.status(404);
        }
    };

    public Handler getAllExercisesInRoutine = ctx -> {
        int routineId = Integer.parseInt(ctx.pathParam("routineId"));
        try{
            List<RoutineExercise> routineExercises= routineExerciseService.getAllExercisesInRoutine(routineId);
            String routineExercisesJSON = gson.toJson(routineExercises);
            ctx.result(routineExercisesJSON);
            ctx.status(200);
        }
        catch(ResourceNotFound e){
            ctx.result(e.getMessage());
            ctx.status(404);
        }
    };

    public Handler updateRoutineExercise = ctx -> {
        RoutineExercise routineExercise = gson.fromJson(ctx.body(), RoutineExercise.class);
        try{
            routineExercise = this.routineExerciseService.updateExercise(routineExercise);
            String routineExerciseJSON = gson.toJson(routineExercise);
            ctx.result(routineExerciseJSON);
            ctx.status(201);
        }
        catch(ResourceNotFound e){
            ctx.result(e.getMessage());
            ctx.status(404);
        }
    };

    public Handler deleteRoutineExercise = ctx -> {
            int routineExerciseId = Integer.parseInt(ctx.pathParam("routineExerciseId"));
            try{
                boolean result = this.routineExerciseService.deleteExercise(routineExerciseId);
                String resultString = String.valueOf(result);
                ctx.result(resultString);
                ctx.status(200);
            }
            catch(ResourceNotFound e){
                ctx.result(e.getMessage());
                ctx.status(404);
            }
    };
}
