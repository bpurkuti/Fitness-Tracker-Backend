package dev.marker.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    public static Connection createConnection(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://revaturedb.cw0dgbcoagdz.us-east-2.rds.amazonaws.com:5432/postgres?user=revature&password=revature");
            return connection;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }
}