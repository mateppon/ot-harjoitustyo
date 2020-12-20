package timemanagementapp.domain;

import timemanagementapp.dao.ProjectsDao;

import java.util.ArrayList;
import java.sql.*;

public class FakeProjectsDao implements ProjectsDao {

    String database;
    FakeUserDao userDao;

    Connection connection = null;
    Statement statement = null;
    PreparedStatement preStatement = null;
    ResultSet results = null;

    private String testsDb;

    public FakeProjectsDao(String testsDb, FakeUserDao userDao) {
        this.testsDb = testsDb;
        this.userDao = userDao;
    }

    @Override
    public void updateTimeUsed(int projectId, int timeUsed) {
        try {
            connection = DriverManager.getConnection(testsDb);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "UPDATE Time SET timeUsed = ? WHERE projectId = ?");
            preStatement.setInt(1, timeUsed);
            preStatement.setInt(2, projectId);
            preStatement.executeUpdate();

            preStatement.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }
    }

    @Override
    public int getTimeUsed(int projectId) {
        int timeUsed = 0;
        try {
            connection = DriverManager.getConnection(testsDb);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "SELECT timeUsed FROM Time WHERE projectId = ? ");
            preStatement.setInt(1, projectId);
            preStatement.executeQuery();
            results = preStatement.executeQuery();
            results.next();
            timeUsed = results.getInt("timeUsed");
            results.close();
            statement.close();
            preStatement.close();
            connection.close();
        } catch (SQLException e) {
        }
        return timeUsed;
    }

    @Override
    public void initTime(int projectId, int userId) {
        try {
            connection = DriverManager.getConnection(testsDb);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "INSERT OR REPLACE INTO Time ("
                    + "projectId, bookedTime, timeUsed, userId)"
                    + " VALUES (?, ?,?,?)");
            preStatement.setInt(1, projectId);
            preStatement.setInt(2, 0);
            preStatement.setInt(3, 0);
            preStatement.setInt(4, userId);
            preStatement.executeUpdate();

            preStatement.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }
    }

    @Override
    public int getBookedHours(int projectId) {
        int bookedHours = 0;
        try {
            connection = DriverManager.getConnection(testsDb);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "SELECT bookedTime FROM Time WHERE projectId = ? ");
            preStatement.setInt(1, projectId);
            preStatement.executeQuery();
            results = preStatement.executeQuery();
            results.next();
            bookedHours = results.getInt("bookedTime");
            results.close();
            statement.close();
            preStatement.close();
            connection.close();
        } catch (SQLException e) {
        }
        return bookedHours;
    }

    @Override
    public void updateBookedHours(int projectId, int bookedTime) {
        try {
            connection = DriverManager.getConnection(testsDb);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "UPDATE Time SET bookedTime = ? WHERE projectId = ?");
            preStatement.setInt(1, bookedTime);
            preStatement.setInt(2, projectId);
            preStatement.executeUpdate();
            preStatement.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }
    }

    @Override
    public String getProjectName(int projectId) {
        String projectname = "";
        try {
            connection = DriverManager.getConnection(testsDb);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "SELECT projectname FROM Projects WHERE id = ?");
            preStatement.setInt(1, projectId);
            results = preStatement.executeQuery();
            results.next();
            projectname = results.getString("projectname");
            results.close();
            statement.close();
            preStatement.close();
            connection.close();
        } catch (SQLException e) {
        }
        return projectname;
    }

    @Override
    public int getProjectId(String projectname, int userId) {
        int projectId = 0;
        try {
            connection = DriverManager.getConnection(testsDb);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "SELECT * FROM Projects WHERE projectname = ? "
                    + "AND userId = ?");
            preStatement.setString(1, projectname);
            preStatement.setInt(2, userId);
            preStatement.executeQuery();
            results = preStatement.executeQuery();
            results.next();
            projectId = results.getInt("id");

            results.close();
            statement.close();
            preStatement.close();
            connection.close();
        } catch (SQLException e) {
        }
        return projectId;
    }

    @Override
    public void setNewProject(String projectname, int userId) {
        try {
            connection = DriverManager.getConnection(testsDb);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "INSERT INTO Projects (projectname, userId) VALUES (?,?)");
            preStatement.setString(1, projectname);
            preStatement.setInt(2, userId);
            preStatement.executeUpdate();
            preStatement.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }
    }

    @Override
    public void deleteProject(int projectId) {
        try {
            connection = DriverManager.getConnection(testsDb);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "DELETE FROM Projects WHERE id = ?");
            preStatement.setInt(1, projectId);
            preStatement.executeUpdate();
            preStatement.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }
    }

    @Override
    public ArrayList<String> getAllProjects(int userId) {
        ArrayList<String> projectList = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(testsDb);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "SELECT projectname FROM Projects WHERE userId = ?");
            preStatement.setInt(1, userId);
            preStatement.executeQuery();
            results = preStatement.executeQuery();
            while (results.next()) {
                projectList.add(results.getString("projectname"));
            }
            results.close();
            preStatement.close();
            statement.close();
        } catch (SQLException e) {
        }
        return projectList;
    }
}
