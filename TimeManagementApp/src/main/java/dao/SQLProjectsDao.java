/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.*;

import domain.Projects;

public class SQLProjectsDao implements ProjectsDao {
    
    String database;
    SQLUserDao userDao;
    
    private int project_id;
    
    Connection connection = null;
    Statement statement = null;
    PreparedStatement preStatement = null;
    ResultSet results = null;
    
    private String sqlDatabase = "jdbc:sqlite:" +this.database;
    
    public SQLProjectsDao(String database, SQLUserDao userDao) {
        this.database = database;
        this.userDao = userDao;
        
    }
    @Override
    public boolean createNewProject(Projects project) {
        try {
        connection= DriverManager.getConnection(sqlDatabase);
        preStatement = connection.prepareStatement(
                "INSERT INTO Projects (projectname, user_id) VALUES (?,?)",
                Statement.RETURN_GENERATED_KEYS);
        preStatement.setString(1,project.getProjectName());
        preStatement.setInt(2, userDao.getUser_id());
        preStatement.executeUpdate();
        
        results = preStatement.getGeneratedKeys();
        results.next();
        this.project_id = results.getInt(1);
        
            System.out.println(project_id);
        
        results.close();
        preStatement.close();
        connection.close();
        
        return true;
        
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
}
