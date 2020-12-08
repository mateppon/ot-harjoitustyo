/*

 */
package timemanagementapp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import timemanagementapp.domain.*;

public class SQLProjectsDao implements ProjectsDao {

    String database;
    SQLUserDao userDao;

    private Projects projects;
    private TimeManagementService service;


    Connection connection = null;
    Statement statement = null;
    PreparedStatement preStatement = null;
    ResultSet results = null;

    private String sqlDatabase = "jdbc:sqlite:" + this.database;
    private String testDatabase = "jdbc:sqlite:testDB.db";

    public List<String> projectList;

    public SQLProjectsDao(String database, SQLUserDao userDao) {
        this.database = database;
        this.userDao = userDao;
        projectList = new ArrayList<>();
    }

    @Override
    public boolean createNewProject(String projectname, int userId) {
        try {
            connection = DriverManager.getConnection(testDatabase);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "INSERT INTO Projects (projectname, user_id) VALUES (?,?)");
            preStatement.setString(1, projectname);
            preStatement.setInt(2, userId);
            preStatement.executeUpdate();
            userDao.closeConnections();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<String> getAllProjects(int userId) {
        try {
            connection = DriverManager.getConnection(testDatabase);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "SELECT projectname FROM Projects WHERE user_id = ?");
            preStatement.setInt(1, userId);
            preStatement.executeQuery();
            results = preStatement.executeQuery();
            while (results.next()) {
                projectList.add(results.getString("projectname"));
            }
            results.close();
            userDao.closeConnections();
        } catch (SQLException e) {
        }
        return projectList;
    } 
}
