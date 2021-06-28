package dev.marker.daos;

import dev.marker.entities.User;
import dev.marker.utils.ConnectionUtil;
import java.sql.*;

public class UserDaoPostgres implements UserDao {

    String table;

    public UserDaoPostgres(String table){
        this.table = table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }

    @Override
    public User createUser(User user) {
        String sql = String.format("INSERT INTO %s (username, password, firstName, lastName, gender, age, height, weight) VALUES (?,?,?,?,?,?,?,?)", this.table);
        try(Connection connection = ConnectionUtil.createConnection()){
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getGender());
            ps.setInt(6, user.getAge());
            ps.setInt(7, user.getHeight());
            ps.setInt(8, user.getWeight());

            ps.execute();
            // ResultSet rs = ps.getGeneratedKeys();
            // rs.next();
            // returnedUser.setUsername(rs.getString("username"));
            // returnedUser.setPassword(rs.getString("password"));
            // returnedUser.setFirstName(rs.getString("first_name"));
            // returnedUser.setLastName(rs.getString("last_name"));
            // returnedUser.setGender(rs.getString("gender"));
            // returnedUser.setAge(rs.getInt("age"));
            // returnedUser.setHeight(rs.getInt("height"));
            // returnedUser.setWeight(rs.getInt("weight"));
            // return returnedUser;
            return user;
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public User getUser(String username) {
        String sql = String.format("SELECT * FROM %s WHERE username = ?", this.table);
        try(Connection connection = ConnectionUtil.createConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,username);
            User returnedUser = new User();
            ResultSet rs = ps.executeQuery();
            rs.next();
            returnedUser.setUsername(rs.getString("username"));
            returnedUser.setPassword(rs.getString("password"));
            returnedUser.setFirstName(rs.getString("first_name"));
            returnedUser.setLastName(rs.getString("last_name"));
            returnedUser.setGender(rs.getString("gender"));
            returnedUser.setAge(rs.getInt("age"));
            returnedUser.setHeight(rs.getInt("height"));
            returnedUser.setWeight(rs.getInt("weight"));
            return returnedUser;
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }

    }


    @Override
    public User updateUser(User user) {
        //(username, password, firstName, lastName, gender, age, height, weight"
        String sql = String.format("UPDATE %s SET firstName=?, lastName=?, gender=?, age=?, height=?, weight=? WHERE username=?", this.table);
        sql.replace("$tableName", this.table);
        try(Connection connection = ConnectionUtil.createConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(0, user.getPassword());
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getGender());
            ps.setInt(4, user.getAge());
            ps.setInt(5, user.getHeight());
            ps.setInt(6, user.getWeight());
            ps.setString(7, user.getUsername());

            ps.executeUpdate();

            return user;
        }
        catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String deleteUser(String username) {
        String sql = "delete from $tableName where username = ?";
        sql.replace("$tableName", this.table);
        try(Connection connection = ConnectionUtil.createConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.execute();
            return username;

        }
        catch (SQLException e) {
            e.printStackTrace();
            return "Error";
        }
    }

}