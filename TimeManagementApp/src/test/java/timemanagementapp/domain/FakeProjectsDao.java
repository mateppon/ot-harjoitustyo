/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timemanagementapp.domain;

/**
 *
 * 
 */
import java.util.List;
import timemanagementapp.dao.ProjectsDao;

import java.util.ArrayList;
import java.sql.*;


public class FakeProjectsDao implements ProjectsDao {
    
    private FakeUserDao fakeUserDao;
    
    private List<String> projectList; 
    private int projectId;
    
    Connection connection = null;
    Statement statement = null;
    PreparedStatement preStatement = null;
    ResultSet results = null;
    
    private String testdb;
    private String sqlDatabase = "jdbc:sqlite:" + this.testdb;
    

    public FakeProjectsDao(String testdb, FakeUserDao fakeUserDao) {
        this.testdb = testdb;
        this.fakeUserDao = fakeUserDao;
        projectList = new ArrayList<>();
    }
/**
     * Metodi luo käyttäjälle uuden projektin.
     *
     * @param projectname projektin nimi
     * @param userId Käyttäjän identifioiva koodi
     * @return true, jos tule poikkeusta
     */
    @Override
    public boolean createNewProject(String projectname, int userId) {
        try {
            connection = DriverManager.getConnection(sqlDatabase);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "INSERT INTO Projects (projectname, user_id) VALUES (?,?)");
            preStatement.setString(1, projectname);
            preStatement.setInt(2, userId);
            preStatement.executeUpdate();
            fakeUserDao.closeConnections();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Metodi hakee kaikki kirjautuneen käyttäjän projektit
     *
     * @param userId käyttäjän tunniste
     * @return merkkijonolista projekteista
     */
    @Override
    public List<String> getAllProjects(int userId) {
        try {
            connection = DriverManager.getConnection(sqlDatabase);
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
            fakeUserDao.closeConnections();
        } catch (SQLException e) {
        }
        return projectList;
    }

    @Override
    public int getProjectId(String projectname) {
        try {
            connection = DriverManager.getConnection(sqlDatabase);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "SELECT id FROM Projects WHERE projectname = ?");
            preStatement.setString(1, projectname);
            preStatement.executeQuery();
            results = preStatement.executeQuery();
            while (results.next()) {
                this.projectId = (results.getInt("id"));
                System.out.println("daossa" + this.projectId);
            }
            results.close();
            fakeUserDao.closeConnections();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.projectId;
    }

    @Override
    public void bookTime(int projectId, int bookedTime) {
        try {
            connection = DriverManager.getConnection(sqlDatabase);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "INSERT INTO Time (projectname_id, reserved_time) VALUES (?,?)");
            preStatement.setInt(1, projectId);
            preStatement.setInt(2, bookedTime);
            preStatement.executeUpdate();
            fakeUserDao.closeConnections();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    
}



    

