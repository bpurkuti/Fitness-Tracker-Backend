package dev.marker.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    private static String hostName = "";
    private static String username = "";
    private static String password = "";

    public static Connection createConnection(){
        try {
            Connection connection = DriverManager.getConnection(String.format("jdbc:postgresql://%s:5432/postgres?user=%s&password=%s", hostName, username, password));
            return connection;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    public static void setHostname(String hostname){
        ConnectionUtil.hostName = hostname;
    }

    public static void setUsername(String username){
        ConnectionUtil.username = username;
    }

    public static void setPassword(String password){
        ConnectionUtil.password = password;
    }
}