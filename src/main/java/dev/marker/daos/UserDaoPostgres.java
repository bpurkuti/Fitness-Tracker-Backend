package dev.marker.daos;

import dev.marker.entities.User;
import dev.marker.utils.ConnectionUtil;
import java.sql.*;

public class UserDaoPostgres implements UserDao {

    private String tableName;

    public UserDaoPostgres(String tableName){
        this.tableName = tableName;
    }

    public void setTable(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return this.tableName;
    }

    @Override
    public User createUser(User user) {
        String sql = String.format("INSERT INTO %s (username, password, first_name, last_name, gender, age, height, weight, admin) VALUES (?,?,?,?,?,?,?,?,?)", this.tableName);
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
            ps.setBoolean(9, user.isAdmin());
            ps.execute();
            return user;
        }
        catch(SQLException e){
            return null;
        }
    }


    @Override
    public User getUser(String username) {
        String sql = String.format("SELECT * FROM %s WHERE username = ?", this.tableName);
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
            returnedUser.setAdmin(rs.getBoolean("admin"));
            return returnedUser;
        }
        catch(SQLException e){
            return null;
        }
    }


    @Override
    public User updateUser(User user) {
        String sql = String.format("UPDATE %s SET password=?, first_name=?, last_name=?, gender=?, age=?, height=?, weight=?, admin=? WHERE username=?", this.tableName);
        try(Connection connection = ConnectionUtil.createConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getGender());
            ps.setInt(5, user.getAge());
            ps.setInt(6, user.getHeight());
            ps.setInt(7, user.getWeight());
            ps.setBoolean(8, user.isAdmin());
            ps.setString(9, user.getUsername());
            int rtnCount = ps.executeUpdate();
            if(rtnCount == 0)
                return null;
            else
                return user;
        }
        catch(SQLException e) {
            return null;
        }
    }

    @Override
    public String deleteUser(String username) {
        String sql = String.format("DELETE FROM %s WHERE username = ?", this.tableName);
        try(Connection connection = ConnectionUtil.createConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            int rowsChanged = ps.executeUpdate();
            if(rowsChanged == 0)
                return null;
            else
                return username;
        }
        catch (SQLException e) {
            return null;
        }
    }
}