package timemanagementapp.dao;

import java.sql.*;
import java.util.ArrayList;

/**
 * Luokka vie projekteja koskevaa tietoa tietokantaan ja hakee sitä
 * tietokannasta.
 *
 */
public class SQLProjectsDao implements ProjectsDao {

    private String database;
    SQLUserDao userDao;

    Connection connection = null;
    Statement statement = null;
    PreparedStatement preStatement = null;
    ResultSet results = null;
    
    /**
     * Konstuktori
     *
     * @param database tietokannan url
     * @param userDao UserDao-luokan ilmentymä
     */
    public SQLProjectsDao(String database, SQLUserDao userDao) {
        this.database = database;
        this.userDao = userDao;
    }

    /**
     * Paivittaa projektiin kaytetyn ajan.
     *
     * @param projectId projektin tunniste
     * @param timeUsed paivitetty kaytetty aika yhteensa
     */
    @Override
    public void updateTimeUsed(int projectId, int timeUsed) {
        try {
            connection = DriverManager.getConnection(database);
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

    /**
     * Palauttaa projektiin kaytetyn ajan.
     *
     * @param projectId projektin tunniste
     * @return kaytetty aika
     */
    @Override
    public int getTimeUsed(int projectId) {
        int timeUsed = 0;
        try {
            connection = DriverManager.getConnection(database);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "SELECT timeUsed FROM Time WHERE projectId = ? ");
            preStatement.setInt(1, projectId);
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

    /**
     * Palauttaa tiedon projektiin varatuista tunneista.
     *
     * @param projectId projektin tunniste
     * @return varatut tunnit
     */
    @Override
    public int getBookedHours(int projectId) {
        int bookedHours = 0;
        try {
            connection = DriverManager.getConnection(database);
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

    /**
     * Paivittaa varatut tunnit.
     *
     * @param projectId projektin tunniste
     * @param bookedTime varattu aika
     */

    @Override
    public void updateBookedHours(int projectId, int bookedTime) {
        try {
            connection = DriverManager.getConnection(database);
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

    /**
     * Alustaa Time-taulun siten, että projektin alkuarvot ovat nollia.
     *
     * @param projectId projektin tunniste
     * @param userId kayttajan tunniste
     */
    @Override
    public void initTime(int projectId, int userId) {
        System.out.println(projectId);
        int bookedTime = 0;
        int usedTime = 0;
        try {
            connection = DriverManager.getConnection(database);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "INSERT INTO Time (projectId, bookedTime, timeUsed, userId) VALUES (?, ?,?,?)");
            preStatement.setInt(1, projectId);
            preStatement.setInt(2, bookedTime);
            preStatement.setInt(3, usedTime);
            preStatement.setInt(4, userId);
            preStatement.executeUpdate();

            preStatement.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }
    }

    /**
     * Palauttaa projektin nimen.
     *
     * @param projectId projektin tunniste
     * @return projektin nimi
     */
    @Override
    public String getProjectName(int projectId) {
        String projectname = "";
        try {
            connection = DriverManager.getConnection(database);
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

    /**
     * Palautta projektin tunnisteen.
     *
     * @param projectname projektin nimi
     * @param userId kayttajan tunniste
     * @return projektin tunniste
     */
    @Override
    public int getProjectId(String projectname, int userId) {
        int projectId = 0;
        try {
            connection = DriverManager.getConnection(database);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "SELECT id FROM Projects WHERE projectname = ? "
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

    /**
     * Vie tietokantaa uuden projektin.
     *
     * @param projectname projektin nimi
     * @param userId kayttajan tunniste
     */

    @Override
    public void setNewProject(String projectname, int userId) {
        try {
            connection = DriverManager.getConnection(database);
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

    /**
     * Poistaa tietokannassa olevan projektin.
     *
     * @param projectId projektin tunniste
     */

    @Override
    public void deleteProject(int projectId) {
        try {
            connection = DriverManager.getConnection(database);
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

    /**
     * Hakee kaikki kirjautuneen käyttäjän projektit
     *
     * @param userId käyttäjän tunniste
     * @return merkkijonolista projekteista
     */
    @Override
    public ArrayList<String> getAllProjects(int userId) {
        ArrayList<String> projectList = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(database);
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
