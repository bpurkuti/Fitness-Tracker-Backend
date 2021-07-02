package dev.marker;

import dev.marker.daos.*;
import dev.marker.endpoints.AccountEndpoints;
import dev.marker.endpoints.ExerciseEndpoints;
import dev.marker.endpoints.RoutineEndpoints;
import dev.marker.endpoints.RoutineExerciseEndpoints;
import dev.marker.services.*;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
            config.enableDevLogging();
        });

        RoutineDao routineDao = new RoutineDaoPostgres("test_routines");
        AccountService accountService = new AccountServiceImpl(new UserDaoPostgres("test_users"), 86400); // 86400 seconds = 24 hours
        RoutineService routineService = new RoutineServiceImpl(routineDao);
        ExerciseService exerciseService = new ExerciseServiceImpl(new ExerciseDaoPostgres("test_exercises"));
        AccountEndpoints accountEndpoints = new AccountEndpoints(accountService);
        RoutineEndpoints routineEndpoints = new RoutineEndpoints(routineService, accountService);
        ExerciseEndpoints exerciseEndpoints = new ExerciseEndpoints(exerciseService, accountService);
        RoutineExerciseService routineExerciseService = new RoutineExerciseServiceImpl(new RoutineExerciseDaoPostgres("test_routine_exercises"), routineDao);
        RoutineExerciseEndpoints routineExerciseEndpoints = new RoutineExerciseEndpoints(routineExerciseService, accountService);

        app.post("/createAccount", accountEndpoints.createAccount);
        app.post("/loginAccount", accountEndpoints.loginAccount);
        app.post("/logoutAccount", accountEndpoints.logoutAccount);
        app.post("/getAccount", accountEndpoints.getAccount);
        
        app.post("/createRoutine", routineEndpoints.createRoutine);
        app.post("/getRoutineById", routineEndpoints.getRoutineById);
        app.post("/getRoutinesForUser", routineEndpoints.getRoutinesForUser);
        app.patch("/updateRoutine", routineEndpoints.updateRoutine);
        app.delete("/deleteRoutine", routineEndpoints.deleteRoutine);

        app.post("/createExercise", exerciseEndpoints.createExercise);
        app.post("/getExercise", exerciseEndpoints.getExercise);
        app.post("/getAllExercises", exerciseEndpoints.getAllExercises);
        app.patch("/updateExercise", exerciseEndpoints.updateExercise);
        app.delete("/deleteExercise", exerciseEndpoints.deleteExercise);

        app.post("/createRoutineExercise", routineExerciseEndpoints.createRoutineExercise);
        app.post("/getRoutineExercise", routineExerciseEndpoints.getRoutineExercise);
        app.post("/getAllExercisesInRoutine", routineExerciseEndpoints.getAllExercisesInRoutine);
        app.patch("/updateRoutineExercise", routineExerciseEndpoints.updateRoutineExercise);
        app.delete("/deleteRoutineExercise", routineExerciseEndpoints.deleteRoutineExercise);

        app.start();

    }
}
