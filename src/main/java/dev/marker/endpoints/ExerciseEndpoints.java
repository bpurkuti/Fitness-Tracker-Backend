package dev.marker.endpoints;

import com.google.gson.Gson;
import dev.marker.entities.Exercise;
import dev.marker.exceptions.ExerciseDoesntExist;
import dev.marker.services.ExerciseService;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.List;
import dev.marker.services.AccountService;
import dev.marker.services.AccountServiceImpl;
import dev.marker.entities.User;

public class ExerciseEndpoints {

    private ExerciseService exerciseService;
    private AccountService accountService;

    public ExerciseEndpoints(ExerciseService exerciseService, AccountService accountService) {
        this.exerciseService = exerciseService;
        this.accountService = accountService;
    }


    public Handler createExercise = ctx -> {
        Gson gson = new Gson();
        User curr = accountService.getUser("");
        if(curr.isAdmin()){
            Exercise exercise = gson.fromJson(ctx.body(),Exercise.class);
            try {
                exercise = this.exerciseService.createExercise(exercise);
                String exerciseJSON = gson.toJson(exercise);
                ctx.result(exerciseJSON);
                ctx.status(201);
            }
            catch(ExerciseDoesntExist e){
                ctx.result(e.getMessage());
                ctx.status(409);
            }
        }
        else{
            ctx.result("Only Admins can create exercises");
            ctx.status(403);
        }

    };

    public Handler getExercise = ctx -> {
        String exerciseName = ctx.pathParam("exerciseName");
        try{
            Exercise exercise = this.exerciseService.getExercise(exerciseName);
            Gson gson = new Gson();
            String exerciseJSON = gson.toJson(exercise);
            ctx.result(exerciseJSON);
            ctx.status(200);
        }
        catch(ExerciseDoesntExist e){
            ctx.result(e.getMessage());
            ctx.status(404);
        }
    };

    public Handler getAllExercise = ctx -> {
        try{
            List<Exercise> exerciseList = new ArrayList<Exercise>();
            Gson gson = new Gson();
            exerciseList = this.exerciseService.getAllExercises();
            //Might not work
            String exerciseJSON = gson.toJson(exerciseList);
            ctx.result(exerciseJSON);
            ctx.status(200);
        }
        catch(ExerciseDoesntExist e){
            ctx.result(e.getMessage());
            ctx.status(404);
        }
    };

    public Handler updateExercise = ctx -> {
        Gson gson = new Gson();
        Exercise exercise = gson.fromJson(ctx.body(),Exercise.class);
        try{
            exercise = this.exerciseService.updateExercise(exercise);
            String exerciseJSON = gson.toJson(exercise);
            ctx.result(exerciseJSON);
            ctx.status(201);
        }
        catch(ExerciseDoesntExist e){
            ctx.result(e.getMessage());
            ctx.status(404);
        }
    };

    public Handler deleteExercise = ctx -> {
        Gson gson = new Gson();
        String exerciseName = ctx.pathParam("exerciseName");
        try{
            String result = this.exerciseService.deleteExercise(exerciseName);
            ctx.result(result);
            ctx.status(200);
        }
        catch(ExerciseDoesntExist e){
            ctx.result(e.getMessage());
            ctx.status(404);
        }
    };


}
