package dev.marker.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    private static Connection connection;

    public static Connection createConnection(){
        //  jdbc:postgresql://ranieridb.cyysbedq8cqc.us-east-1.rds.amazonaws.com:5432/postgres?user=adam&password=gatorfan1
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://ranieridb.cyysbedq8cqc.us-east-1.rds.amazonaws.com:5432/postgres?user=adam&password=gatorfan1");
            ConnectionUtil.connection = connection;
            return connection;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }
}