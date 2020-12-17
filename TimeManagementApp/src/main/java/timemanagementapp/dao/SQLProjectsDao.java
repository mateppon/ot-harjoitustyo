package timemanagementapp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Luokka vie projekteja koskevaa tietoa tietokantaan ja hakee sitä
 * tietokannasta.
 *
 */
public class SQLProjectsDao implements ProjectsDao {

    String database;
    SQLUserDao userDao;

    Connection connection = null;
    Statement statement = null;
    PreparedStatement preStatement = null;
    ResultSet results = null;

    private String sqlDatabase = "jdbc:sqlite:" + this.database;

    public ArrayList<String> projectList;


    /**
     * Konstuktori
     *
     * @param database sovelluksen käyttämä tietokanta
     * @param userDao UserDao-luokan ilmentymä
     */
    public SQLProjectsDao(String database, SQLUserDao userDao) {
        this.database = database;
        this.userDao = userDao;
        projectList = new ArrayList<>();
    }
    
         @Override
         public void updateTimeUsed(int projectId, int timeUsed) {
        try {
            connection = DriverManager.getConnection(sqlDatabase);
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
            System.out.println(e);
        }
    }
        @Override
        public int getTimeUsed(int projectId) {
        int timeUsed = 0;
        try {
            connection = DriverManager.getConnection(sqlDatabase);
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
        public void setTimeUsed(int projectId, int timeUsed) {
        try {
            connection = DriverManager.getConnection(sqlDatabase);
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
            System.out.println(e);
        }
    }
    @Override
    public int getBookedHours(int projectId) {
        int bookedHours = 0;
        try {
            connection = DriverManager.getConnection(sqlDatabase);
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
            connection = DriverManager.getConnection(sqlDatabase);
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
            System.out.println(e);
        }
    }
        @Override
        public void setBookedHours(int projectId, int userId, int bookedTime) {
        try {
            connection = DriverManager.getConnection(sqlDatabase);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "INSERT OR REPLACE INTO Time (projectId, userId, bookedTime)"
                            + " VALUES (?, ?,?)");
            preStatement.setInt(1, projectId);
            preStatement.setInt(2, userId);
            preStatement.setInt(3, bookedTime);
            preStatement.executeUpdate();
      
            preStatement.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
        @Override
        public String getProjectName(int projectId) {
        String projectname ="";
        try {
            connection = DriverManager.getConnection(sqlDatabase);
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
            connection = DriverManager.getConnection(sqlDatabase);
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
            connection = DriverManager.getConnection(sqlDatabase);
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
            connection = DriverManager.getConnection(sqlDatabase);
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
            System.out.println(e);
        }
    }

//

    /**
     * Metodi hakee kaikki kirjautuneen käyttäjän projektit
     *
     * @param userId käyttäjän tunniste
     * @return merkkijonolista projekteista
     */
    @Override
    public ArrayList<String> getAllProjects(int userId) {
        try {
            connection = DriverManager.getConnection(sqlDatabase);
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
    
        /**
//     * Metodi luo käyttäjälle uuden projektin.
//     *
//     * @param projectname projektin nimi
//     * @param userId Käyttäjän identifioiva koodi
//     * @return true, jos tule poikkeusta
//     */
//    @Override
//    public boolean createNewProject(String projectname, int userId) {
//        try {
//            connection = DriverManager.getConnection(sqlDatabase);
//            statement = connection.createStatement();
//            statement.execute("PRAGMA foreign_keys = ON");
//            preStatement = connection.prepareStatement(
//                    "INSERT INTO Projects (projectname, user_id) VALUES (?,?)");
//            preStatement.setString(1, projectname);
//            preStatement.setInt(2, userId);
//            preStatement.executeUpdate();
//            userDao.closeConnections();
//            return true;
//        } catch (SQLException e) {
//            return false;
//        }
////    }
//
//    @Override
//    public int getProjectId(String projectname) {
//        try {
//            connection = DriverManager.getConnection(sqlDatabase);
//            statement = connection.createStatement();
//            statement.execute("PRAGMA foreign_keys = ON");
//            preStatement = connection.prepareStatement(
//                    "SELECT id FROM Projects WHERE projectname = ?");
//            preStatement.setString(1, projectname);
//            preStatement.executeQuery();
//            results = preStatement.executeQuery();
//            while (results.next()) {
//                this.projectId = (results.getInt("id"));
//                System.out.println("daossa" + this.projectId);
//            }
//            results.close();
//            userDao.closeConnections();
//
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        return this.projectId;
//    }
//
//    @Override
//    public void bookTime(int projectId, int bookedTime) {
//        try {
//            connection = DriverManager.getConnection(sqlDatabase);
//            statement = connection.createStatement();
//            statement.execute("PRAGMA foreign_keys = ON");
//            preStatement = connection.prepareStatement(
//                    "INSERT INTO Time (projectname_id, reserved_time) VALUES (?,?)");
//            preStatement.setInt(1, projectId);
//            preStatement.setInt(2, bookedTime);
//            preStatement.executeUpdate();
//            userDao.closeConnections();
//        } catch (SQLException e) {
//        }

    }



