package dev.marker;

import dev.marker.daos.*;
import dev.marker.endpoints.AccountEndpoints;
import dev.marker.endpoints.ExerciseEndpoints;
import dev.marker.endpoints.RoutineEndpoints;
import dev.marker.endpoints.RoutineExerciseEndpoints;
import dev.marker.services.*;
import dev.marker.utils.ConnectionUtil;
import dev.marker.utils.Setup;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
            config.enableDevLogging();
        });

        String USER_TABLE = "users";
        String EXERCISE_TABLE = "exercises";
        String ROUTINE_TABLE = "routines";
        String ROUTINE_EXERCISE_TABLE = "routine_exercises";
        long SESSION_TIMEOUT_SECONDS = 86400; // 86400 seconds = 24 hours

        ConnectionUtil.setHostname("revaturedb.cw0dgbcoagdz.us-east-2.rds.amazonaws.com");
        ConnectionUtil.setUsername("revature");
        ConnectionUtil.setPassword("revature");
        Setup.setupTables(USER_TABLE, EXERCISE_TABLE, ROUTINE_TABLE, ROUTINE_EXERCISE_TABLE);
        Setup.addAdmin("admin", "admin", USER_TABLE);

        RoutineDao routineDao = new RoutineDaoPostgres(ROUTINE_TABLE);
        
        RoutineService routineService = new RoutineServiceImpl(routineDao);
        ExerciseService exerciseService = new ExerciseServiceImpl(new ExerciseDaoPostgres(EXERCISE_TABLE));
        AccountService accountService = new AccountServiceImpl(new UserDaoPostgres(USER_TABLE), SESSION_TIMEOUT_SECONDS);
        RoutineExerciseService routineExerciseService = new RoutineExerciseServiceImpl(new RoutineExerciseDaoPostgres(ROUTINE_EXERCISE_TABLE), routineDao);

        AccountEndpoints accountEndpoints = new AccountEndpoints(accountService);
        RoutineEndpoints routineEndpoints = new RoutineEndpoints(routineService, accountService);
        ExerciseEndpoints exerciseEndpoints = new ExerciseEndpoints(exerciseService, accountService);
        RoutineExerciseEndpoints routineExerciseEndpoints = new RoutineExerciseEndpoints(routineExerciseService, accountService);

        app.post("/createAccount", accountEndpoints.createAccount);
        app.post("/loginAccount", accountEndpoints.loginAccount);
        app.post("/logoutAccount", accountEndpoints.logoutAccount);
        app.patch("/updateAccount", accountEndpoints.updateAccount);
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
