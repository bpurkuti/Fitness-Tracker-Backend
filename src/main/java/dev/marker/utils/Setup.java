package dev.marker.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dev.marker.daos.UserDao;
import dev.marker.daos.UserDaoPostgres;
import dev.marker.entities.User;

public abstract class Setup {

    public static void setupTables(String userTable, String exerciseTable, String routineTable,
            String routineExerciseTable) {
        String sql = String.format("CREATE EXTENSION IF NOT EXISTS citext;");
        try (Connection connection = ConnectionUtil.createConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = String.format("CREATE EXTENSION IF NOT EXISTS pgcrypto;");
        try (Connection connection = ConnectionUtil.createConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "CREATE TABLE IF NOT EXISTS %s(\"username\" citext primary key, \"password\" VARCHAR(64) NOT NULL,\"first_name\" VARCHAR(64),\"last_name\" VARCHAR(64),\"gender\" VARCHAR(64),\"age\" INT,\"height\" INT,\"weight\" INT,\"admin\" BOOLEAN);";
        sql = String.format(sql, userTable);
        try (Connection connection = ConnectionUtil.createConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "CREATE TABLE IF NOT EXISTS %s(\"exercise_name\" citext primary key,\"description\" TEXT NOT NULL,\"type\" VARCHAR(64) NOT NULL,\"video_link\" VARCHAR(64));";
        sql = String.format(sql, exerciseTable);
        try (Connection connection = ConnectionUtil.createConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "CREATE TABLE IF NOT EXISTS %s(\"routine_id\" SERIAL PRIMARY KEY,\"username\" citext NOT NULL,\"routine_name\" VARCHAR(64) NOT NULL,\"date_scheduled\" INT,\"date_completed\" INT, FOREIGN KEY (\"username\") REFERENCES %s(\"username\") ON DELETE CASCADE);";
        sql = String.format(sql, routineTable, userTable);
        try (Connection connection = ConnectionUtil.createConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "CREATE TABLE IF NOT EXISTS %s(\"routine_exercise_id\" SERIAL PRIMARY KEY,\"exercise_name\" citext,\"routine_id\" INT,\"duration\" INT,\"reps\" INT,\"weight\" INT, FOREIGN KEY (\"exercise_name\") REFERENCES %s(\"exercise_name\") ON DELETE CASCADE, FOREIGN KEY (\"routine_id\") REFERENCES %s(\"routine_id\") ON DELETE CASCADE);";
        sql = String.format(sql, routineExerciseTable, exerciseTable, routineTable);
        try (Connection connection = ConnectionUtil.createConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addAdmin(String username, String password, String userTable) {
        User user = new User(username, password, "System", "Admin", "Admin", 0, 0, 0, true);
        UserDao userDao = new UserDaoPostgres(userTable);
        userDao.createUser(user);
    }
}
