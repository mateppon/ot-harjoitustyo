
package dao;

import java.sql.*;
import domain.User;


public class SQLUserDao implements UserDao {
    
    String database;
    int user_id;
    
    private String sqlDatabase = "jdbc:sqlite:" +this.database;
   
    Connection connection = null;
    Statement statement = null;
    PreparedStatement preStatement = null;
    ResultSet results = null;
    
    
    public SQLUserDao(String database) {
        this.database = database;
    }
    
    @Override
    public void foreignKeysOn() {
        try{
            connection = DriverManager.getConnection(sqlDatabase);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_key = ON");
            statement.close();
            connection.close();
        }catch (SQLException e) {
           
        }
    }
    
    @Override
    public boolean createTables() {
        
    try {
        connection = DriverManager.getConnection(sqlDatabase); 
        statement = connection.createStatement();
        statement.execute("BEGIN TRANSACTION"); 
        statement.execute("CREATE TABLE Users ("
                + "id INTEGER PRIMARY KEY, "
                + "name TEXT UNIQUE, "
                + "username TEXT UNIQUE)");
        statement.execute("CREATE TABLE Projects ("
                + "id INTEGER PRIMARY KEY, "
                + "projectname TEXT UNIQUE, "
                + "user_id INTEGER REFERENCES Users)");
        statement.execute("CREATE TABLE Time ("
                + "id INTEGER PRIMARY KEY, "
                + "projectname_id INTEGER REFERENCES Projects, "
                + "reserved_time INTEGER, "
                + "time_used INTEGER, "
                + "user_id INTEGER REFERENCES Users)");
        statement.execute("COMMIT");
        statement.close();
        connection.close();
        return true;
    } catch (SQLException e) {
        System.out.println(e);
    return false;
}
    }
   
    @Override
    public boolean createUser(User user) {  
        System.out.println("create userin alussa");
        try {
        connection = DriverManager.getConnection(sqlDatabase);
        preStatement = connection.prepareStatement(
                "INSERT INTO Users (name, username) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        preStatement.setString(1, user.getName()); 
        preStatement.setString(2, user.getUsername());
        preStatement.executeUpdate();
        
        results = preStatement.getGeneratedKeys();
        results.next();
        this.user_id = results.getInt(1);
        
            System.out.println(user_id);
        
            preStatement.close();
            connection.close();
            System.out.println("create userin lopussa");
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        return false;
    }
    }
    @Override 
    public boolean selectUser(String username) {
        try {
        connection = DriverManager.getConnection(sqlDatabase); 
        preStatement = connection.prepareStatement(
                "SELECT (username) FROM Users WHERE username = ?");
        preStatement.setString(1, username);
        preStatement.executeUpdate();
        
        results = preStatement.executeQuery();
        if(results.next()) {
           return true;
        }
        results.close();
        statement.close();
        connection.close();
        } catch (SQLException e) {
        } return false;
    }   
    
    public int getUser_id(){
        return this.user_id;
    }
}
