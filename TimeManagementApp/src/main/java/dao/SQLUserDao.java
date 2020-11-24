
package dao;

import java.sql.*;
import domain.User;


public class SQLUserDao implements UserDao {
    
    private static final String DEFAULT_DB = "jdbc:sqlite:sqlitetimemanagement.db";
   
    Connection connection = null;
    Statement statement = null;
    PreparedStatement preStatement = null;
    
    
    public SQLUserDao(){
        
    }
    
    @Override
    public boolean createTables() {
        
    try {
        connection = DriverManager.getConnection(DEFAULT_DB); 
        statement = connection.createStatement();
        
        statement.execute("BEGIN TRANSACTION");
        statement.execute("PRAGMA foreign_key = ON");
        
        statement.execute("CREATE TABLE Users ("
                + "id INTEGER PRIMARY KEY, "
                + "name TEXT UNIQUE, "
                + "username TEXT UNIQUE)");
        statement.execute("COMMIT");
        statement.close();
        connection.close();
        return true;
    } catch (SQLException e) {
    return false;
}
    }
   
    @Override
    public boolean createUser(User user) {  
        try {
        connection = DriverManager.getConnection(DEFAULT_DB);     
        preStatement = connection.prepareStatement(
                "INSERT INTO Users (name, username) VALUES (?, ?)");
        preStatement.setString(1, user.getName()); 
        preStatement.setString(2, user.getUsername());
        preStatement.executeUpdate();
            preStatement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
        return false;
    }
    }
    @Override 
    public void select(){
        try{
        connection = DriverManager.getConnection(DEFAULT_DB); 
        statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Users");
        while (rs.next()){
           System.out.println(rs.getString("name") + rs.getString("username")); 
        }
         rs.close();
         statement.close();
         connection.close();
        }catch (SQLException e){
    }
    
    }   
}
