package dev.marker.controllers;

import dev.marker.entities.Exercise;
import dev.marker.exceptions.ExerciseDoesntExist;
import dev.marker.services.ExerciseService;
import com.google.gson.Gson;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.List;


public class ExerciseController {
    private ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    public Handler createExercise = ctx -> {
        Gson gson = new Gson();
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
