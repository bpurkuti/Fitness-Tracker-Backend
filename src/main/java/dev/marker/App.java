package dev.marker;

import dev.marker.daos.ExerciseDaoPostgres;
import dev.marker.daos.UserDaoPostgres;
import dev.marker.endpoints.AccountEndpoints;
import dev.marker.services.AccountService;
import dev.marker.services.AccountServiceImpl;
import io.javalin.Javalin;

import dev.marker.services.ExerciseService;
import dev.marker.services.ExerciseServiceImpl;
import dev.marker.endpoints.ExerciseEndpoints;

public class App {

    public static void main(String[] args) {

        Javalin app = Javalin.create();
        AccountService accountService = new AccountServiceImpl(new UserDaoPostgres("test_users"), 80000);
        AccountEndpoints accountEndpoints = new AccountEndpoints(accountService);

        ExerciseService exerciseService = new ExerciseServiceImpl(new ExerciseDaoPostgres("test_exercises"));
        ExerciseEndpoints exerciseEndpoints = new ExerciseEndpoints(exerciseService, accountService);

        app.post("/createAccount", accountEndpoints.createAccount);
        app.post("/loginAccount", accountEndpoints.loginAccount);
        app.post("/logoutAccount", accountEndpoints.logoutAccount);


        app.start();

    }
}
