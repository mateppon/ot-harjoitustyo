package dao;

import java.sql.*;
import domain.User;

public class SQLUserDao implements UserDao {

    String database;
    int userId;

    private String sqlDatabase = "jdbc:sqlite:" + this.database;

    private String testDatabase = "jdbc:sqlite:testDB.db";

    Connection connection = null;
    Statement statement = null;
    PreparedStatement preStatement = null;
    ResultSet results = null;

    public SQLUserDao(String database) {
        this.database = database;
    }

    @Override
    public void foreignKeysOn() {
        try {
            connection = DriverManager.getConnection(testDatabase);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_key = ON");
            statement.close();
            connection.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public boolean createTables() {
        try {
            connection = DriverManager.getConnection(testDatabase);
            statement = connection.createStatement();
            statement.execute("BEGIN TRANSACTION");
            statement.execute("PRAGMA foreign_keys = ON");

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
            return false;
        }
    }

    @Override
    public boolean createUser(User user) {
        try {
            connection = DriverManager.getConnection(testDatabase);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "INSERT INTO Users (name, username) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            preStatement.setString(1, user.getName());
            preStatement.setString(2, user.getUsername());
            preStatement.executeUpdate();
            results = preStatement.getGeneratedKeys();
            results.next();
            user.setUserId(results.getInt(1));
            preStatement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /*
    Palauttaa true, jos on olemassa käyttäjä user 
     */
    @Override
    public boolean findUser(String username) {
        try {
            connection = DriverManager.getConnection(testDatabase);
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            preStatement = connection.prepareStatement(
                    "SELECT * FROM Users WHERE username = ?");
            preStatement.setString(1, username);
            preStatement.executeQuery();
            results = preStatement.executeQuery();

            if (results.next()) {
                userId = results.getInt("id");
                System.out.println(userId);
                results.close();
                preStatement.close();
                statement.close();
                connection.close();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    @Override
    public int getUserId() {
        return this.userId;
    }

    public void closeAllConnections() {
        try {
            results.close();
            preStatement.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {

        }
    }
}
