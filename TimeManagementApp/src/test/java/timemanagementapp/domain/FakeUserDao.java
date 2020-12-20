
package timemanagementapp.domain;

import timemanagementapp.dao.UserDao;
import java.sql.*;

public class FakeUserDao implements UserDao {

    private String testsDb;

    Connection connection = null;
    Statement statement = null;
    PreparedStatement preStatement = null;
    ResultSet results = null;

    
    public FakeUserDao(String testsdb) {
        this.testsDb = testsdb;
    }

    @Override
    public int getUserId(String username) {
        int userId = 0;
        try {
            connection = DriverManager.getConnection(testsDb);
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
            connection = DriverManager.getConnection(testsDb);
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
            connection = DriverManager.getConnection(testsDb);
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
            connection = DriverManager.getConnection(testsDb);
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
            connection = DriverManager.getConnection(testsDb);
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
    

