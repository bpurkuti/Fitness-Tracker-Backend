package dev.marker;

import dev.marker.daos.ExerciseDaoPostgres;
import dev.marker.daos.RoutineDaoPostgres;
import dev.marker.daos.UserDaoPostgres;
import dev.marker.endpoints.AccountEndpoints;
import dev.marker.endpoints.ExerciseEndpoints;
import dev.marker.endpoints.RoutineEndpoints;
import dev.marker.services.AccountService;
import dev.marker.services.AccountServiceImpl;
import dev.marker.services.ExerciseService;
import dev.marker.services.ExerciseServiceImpl;
import dev.marker.services.RoutineService;
import dev.marker.services.RoutineServiceImpl;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {

        Javalin app = Javalin.create();
        AccountService accountService = new AccountServiceImpl(new UserDaoPostgres("test_users"), 86400); // 86400 seconds = 24 hours
        RoutineService routineService = new RoutineServiceImpl(new RoutineDaoPostgres("test_routines"));
        ExerciseService exerciseService = new ExerciseServiceImpl(new ExerciseDaoPostgres("test_exercises"));
        AccountEndpoints accountEndpoints = new AccountEndpoints(accountService);
        RoutineEndpoints routineEndpoints = new RoutineEndpoints(routineService, accountService);
        ExerciseEndpoints exerciseEndpoints = new ExerciseEndpoints(exerciseService, accountService);
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

        app.start();

    }
}
