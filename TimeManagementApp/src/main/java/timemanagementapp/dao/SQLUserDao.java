package timemanagementapp.dao;

import java.sql.*;
import timemanagementapp.domain.User;

/**
 * Luokka vie ja lukee tietokannasta käyttäjää koskevaa tietoa
 *
 */
public class SQLUserDao implements UserDao {

    String database;
    //int userId;

    private String sqlDatabase = "jdbc:sqlite:" + this.database;

    Connection connection = null;
    Statement statement = null;
    PreparedStatement preStatement = null;
    ResultSet results = null;

    /**
     * Konstruktori
     *
     * @param database tietokannan nimi
     */
    public SQLUserDao(String database) {
        this.database = database;
    }
    
    @Override
    public int getUserId(String username) {
        int userId = 0;
        try {
            connection = DriverManager.getConnection(sqlDatabase);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "SELECT * FROM Users WHERE username = ?");
            preStatement.setString(1, username);
            preStatement.executeQuery();
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
    @Override
    public String getUsername(int userId) {
        String username = "";
        try {
            connection = DriverManager.getConnection(sqlDatabase);
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

    @Override
    public int setNewUser(String name, String username) {
        int userId = 0;
        try {
            connection = DriverManager.getConnection(sqlDatabase);
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
            statement.close();
            preStatement.close();
            connection.close();
        } catch (SQLException e) {
        }
        return userId;
    }

    @Override
    public void deleteUser(int userId) {
        try {
            connection = DriverManager.getConnection(sqlDatabase);
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
            System.out.println(e);
        }
    }

    @Override
    public void createTables() {

        try {
            connection = DriverManager.getConnection(sqlDatabase);
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
            System.out.println(e);
        }
    }
}
//    /**
//     * Metodi luo taulut tietokantaa varten.
//     *
//     */
//    @Override
//    public void createTables() {
//        try {
//            connection = DriverManager.getConnection(sqlDatabase);
//            statement = connection.createStatement();
//            statement.execute("BEGIN TRANSACTION");
//            statement.execute("PRAGMA foreign_keys = ON");
//            statement.execute("CREATE TABLE IF NOT EXISTS Users ("
//                    + "id INTEGER PRIMARY KEY, "
//                    + "name TEXT, "
//                    + "username TEXT UNIQUE)");
//            statement.execute("CREATE TABLE IF NOT EXISTS Projects ("
//                    + "id INTEGER PRIMARY KEY, "
//                    + "projectname TEXT, "
//                    + "user_id INTEGER REFERENCES Users)");
//            statement.execute("CREATE TABLE IF NOT EXISTS Time ("
//                    + "id INTEGER PRIMARY KEY, "
//                    + "projectname_id INTEGER REFERENCES Projects, "
//                    + "reserved_time INTEGER, "
//                    + "time_used INTEGER, "
//                    + "user_id INTEGER REFERENCES Users)");
//            statement.execute("COMMIT");
//            closeConnections();
//        } catch (SQLException e) {
//        }
//    }
//
//    /**
//     * Metodi luo uuden käyttäjän tietokantaan
//     *
//     * @param user käyttäjä
//     * @return true, jos onnistuu
//     */
//    @Override
//    public boolean createUser(User user) {
//        try {
//            connection = DriverManager.getConnection(sqlDatabase);
//            statement = connection.createStatement();
//            statement.execute("PRAGMA foreign_keys = ON");
//            preStatement = connection.prepareStatement(
//                    "INSERT INTO Users (name, username) VALUES (?, ?)",
//                    Statement.RETURN_GENERATED_KEYS);
//            preStatement.setString(1, user.getName());
//            preStatement.setString(2, user.getUsername());
//            preStatement.executeUpdate();
//            results = preStatement.getGeneratedKeys();
//            results.next();
//            user.setUserId(results.getInt(1));
//            closeConnections();
//            return true;
//        } catch (SQLException e) {
//            return false;
//        }
//    }
//
//    /**
//     * Metodi etsii, löytyykö tietokannasta annettua käytäjänimeä
//     *
//     * @param username etsittävä käyttäjä
//     * @return true, jos käyttäjä on olemassa
//     */
//    @Override
//    public boolean findUser(String username) {
//        try {
//            connection = DriverManager.getConnection(sqlDatabase);
//            statement = connection.createStatement();
//            statement.execute("PRAGMA foreign_keys = ON");
//            preStatement = connection.prepareStatement(
//                    "SELECT * FROM Users WHERE username = ?");
//            preStatement.setString(1, username);
//            preStatement.executeQuery();
//            results = preStatement.executeQuery();
//            if (results.next()) {
//                setUserId(results.getInt("id"));
//                results.close();
//                closeConnections();
//                return true;
//            } else {
//                return false;
//            }
//        } catch (SQLException e) {
//        }
//        return true;
//    }
//
//    /**
//     * Palauttaa käyttäjän tunnisteen
//     *
//     * @return userId käyttäjän yksilöivä tunniste
//     */
//    @Override
//    public int getUserId() {
//        return this.userId;
//    }
//
//    /**
//     * Asettaa luokan sisäiseen muuttujaan UserId käyttäjän yksilöivän
//     * tunnisteen.
//     *
//     * @param userId käyttäjätunniste
//     */
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    /**
//     * Metodi sulkee kaikki tietokantaan otetut yhteydet
//     */
//    void closeConnections() {
//        try {
// //           preStatement.close();
//            statement.close();
//            connection.close();
//        } catch (SQLException e) {
//        }
//    }
//}
