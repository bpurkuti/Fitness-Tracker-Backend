package dev.marker;

import dev.marker.daos.UserDaoPostgres;
import dev.marker.endpoints.AccountEndpoints;
import dev.marker.services.AccountService;
import dev.marker.services.AccountServiceImpl;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {

        Javalin app = Javalin.create();
        AccountService accountService = new AccountServiceImpl(new UserDaoPostgres("test_users"), 80000);
        AccountEndpoints accountEndpoints = new AccountEndpoints(accountService);

        app.post("/createAccount", accountEndpoints.createAccount);
        app.post("/loginAccount", accountEndpoints.loginAccount);
        app.post("/logoutAccount", accountEndpoints.logoutAccount);
        app.post("/getAccount", accountEndpoints.getAccount);

        app.start();

    }
}
