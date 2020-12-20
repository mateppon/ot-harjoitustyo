package timemanagementapp.dao;

import java.sql.*;

/**
 * Luokka vie ja lukee tietokannasta kayttajaa koskevaa tietoa
 *
 */
public class SQLUserDao implements UserDao {

    private String database;

    Connection connection = null;
    Statement statement = null;
    PreparedStatement preStatement = null;
    ResultSet results = null;

    /**
     * Konstruktori
     *
     * @param database tietokannan url
     */
    public SQLUserDao(String database) {
        this.database = database;
    }

    /**
     * Hakee kayttajanimea vastaavan tunnuksen tietokannasta.
     *
     * @param username kayttajanimi
     * @return yksiloiva tunnus
     */

    @Override
    public int getUserId(String username) {
        int userId = 0;
        try {
            connection = DriverManager.getConnection(database);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "SELECT id FROM Users WHERE username = ?");
            preStatement.setString(1, username);
            results = preStatement.executeQuery();
            results.next();
            userId = results.getInt("id");

            results.close();
            statement.close();
            preStatement.close();
            connection.close();
        } catch (SQLException e) {
        }
        return userId;
    }

    /**
     * Hakee kayttajanimen annetun kayttajatunnuksen perusteella.
     *
     * @param userId kayttajatunnus
     * @return kayttajanimi
     */

    @Override
    public String getUsername(int userId) {
        String username = "";
        try {
            connection = DriverManager.getConnection(database);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "SELECT username FROM Users WHERE id = ?");
            preStatement.setInt(1, userId);
            results = preStatement.executeQuery();
            results.next();
            username = results.getString("username");
            results.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }
        return username;
    }

    /**
     * Vie tietokantaan uuden kayttajan.
     *
     * @param name nimi
     * @param username kayttajanimi
     * @return kayttajatunnus
     */

    @Override
    public int setNewUser(String name, String username) {
        int userId = 0;
        try {
            connection = DriverManager.getConnection(database);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "INSERT INTO Users (name, username) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            preStatement.setString(1, name);
            preStatement.setString(2, username);
            preStatement.executeUpdate();
            results = preStatement.getGeneratedKeys();
            results.next();
            userId = (results.getInt(1));

            results.close();
            statement.close();
            preStatement.close();
            connection.close();
        } catch (SQLException e) {
        }
        return userId;
    }

    /**
     * Poistaa kayttajan tietokannasta.
     *
     * @param userId kayttajatunnus
     */

    @Override
    public void deleteUser(int userId) {
        try {
            connection = DriverManager.getConnection(database);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "DELETE FROM Users WHERE id = ?");
            preStatement.setInt(1, userId);
            preStatement.executeUpdate();
            preStatement.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }
    }

    /**
     * Luo ohjelman kayttaman tietokannan.
     *
     */

    @Override
    public void createTables() {

        try {
            connection = DriverManager.getConnection(database);
            statement = connection.createStatement();
            statement.execute("BEGIN TRANSACTION");
            statement.execute("PRAGMA foreign_keys = ON");
            statement.execute("CREATE TABLE IF NOT EXISTS Users ("
                    + "id INTEGER PRIMARY KEY, "
                    + "name TEXT, "
                    + "username TEXT UNIQUE)");
            statement.execute("CREATE TABLE IF NOT EXISTS Projects ("
                    + "id INTEGER PRIMARY KEY, "
                    + "projectname TEXT, "
                    + "userId INTEGER REFERENCES Users ON DELETE CASCADE)");
            statement.execute("CREATE TABLE IF NOT EXISTS Time ("
                    + "id INTEGER PRIMARY KEY, "
                    + "projectId INTEGER REFERENCES Projects ON DELETE CASCADE, "
                    + "bookedTime INTEGER, "
                    + "timeUsed INTEGER, "
                    + "userId INTEGER REFERENCES Users ON DELETE CASCADE)");
            statement.execute("COMMIT");
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }
    }
}
