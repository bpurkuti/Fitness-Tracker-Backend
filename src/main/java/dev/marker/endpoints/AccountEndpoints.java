package dev.marker.endpoints;

import com.google.gson.Gson;
import dev.marker.services.AccountService;
import io.javalin.http.Handler;
import dev.marker.entities.User;
import dev.marker.exceptions.*;

public class AccountEndpoints {

    private AccountService accountService;
    private Gson gson;

    public AccountEndpoints(AccountService service) {
        this.accountService = service;
        this.gson = new Gson();
    }

    /**
     * Creates an account using the specified values
     * 
     * @input json => JSON(User.class)
     * @returns json => {"session": (session)}
     */
    public Handler createAccount = (ctx) -> {
        try {
            User user = this.gson.fromJson(ctx.body(), User.class);
            Session session = new Session(accountService.createAccount(user.getUsername(), user.getPassword(),
                    user.getFirstName(), user.getLastName(), user.getGender(), user.getAge(), user.getHeight(),
                    user.getWeight(), false));
            ctx.status(201);
            ctx.result(this.gson.toJson(session));
        } catch (DuplicationException e) {
            ctx.status(409);
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
     * Logs into an existing account
     * 
     * @input json => {"username": (username), "password": (password)}
     * @returns json => {"session": (session)}
     */
    public Handler loginAccount = (ctx) -> {
        try {
            User user = this.gson.fromJson(ctx.body(), User.class);
            Session session = new Session(accountService.logIn(user.getUsername(), user.getPassword()));
            ctx.status(200);
            ctx.result(this.gson.toJson(session));
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
     * Logs out of an account and closes the session
     * 
     * @input json => {"session": (session)}
     * @returns json => Nothing
     */
    public Handler logoutAccount = (ctx) -> {
        try {
            Session session = this.gson.fromJson(ctx.body(), Session.class);
            accountService.logOut(session.getSession());
            ctx.status(200);
            ctx.result("Logout successful");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result("The server encountered an error");
        }
    };

    /**
     * Updates an account
     * 
     * @input json => {JSON(User.class), "session": (session)}
     * @returns json => JSON(User.class)
     */
    public Handler updateAccount = (ctx) -> {
        try {
            User user = this.gson.fromJson(ctx.body(), User.class);
            Session session = this.gson.fromJson(ctx.body(), Session.class);
            user = accountService.updateUser(session.getSession(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getGender(), user.getAge(), user.getHeight(), user.getWeight());
            ctx.status(200);
            ctx.result(this.gson.toJson(user));
        } catch (InvalidSession e) {
            ctx.status(401);
            ctx.result(e.getMessage());
        }  catch (IncorrectArguments e) {
            ctx.status(400);
            ctx.result(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result("The server encountered an error");
        }
    };

    /**
     * Gets the account owning the session
     * 
     * @input json => {"session": (session)}
     * @returns json => [JSON(routineExercise.class), JSON(routineExercise.class), ...]
     */
    public Handler getAccount = (ctx) -> {
        try {
            Session session = this.gson.fromJson(ctx.body(), Session.class);
            SafeUser user = new SafeUser(this.accountService.getUser(session.getSession()));
            ctx.status(200);
            ctx.result(this.gson.toJson(user));
        } catch (InvalidSession e) {
            ctx.status(401);
            ctx.result(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result("The server encountered an error");
        }
    };

    private class SafeUser {
        String username;
        String firstName;
        String lastName;
        String gender;
        int age;
        int height;
        int weight;
        boolean admin;

        SafeUser(User user) {
            this.username = user.getUsername();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.gender = user.getGender();
            this.age = user.getAge();
            this.height = user.getHeight();
            this.weight = user.getWeight();
            this.admin = user.isAdmin();
        }
    }
}
