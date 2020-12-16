/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timemanagementapp.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.*;
import timemanagementapp.domain.User;

/**
 * Testaa luokkaa SQLUserDao.
 *
 */
public class SQLUserDaoTest {

    SQLUserDao userDao;

    Connection connection = null;
    Statement statement = null;
    PreparedStatement preStatement = null;
    ResultSet results = null;

    public SQLUserDaoTest() {
    }

//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//         
    @Before
    public void setUp() {
        userDao = new SQLUserDao("tests.db");
        try {
            userDao.createTables();
        } catch (Exception e) {
        }
    }

    @Test
    public void tablesExists() {
        assertTrue(this.userDao != null);
    }

    @Test
    public void createNewUser() {
        User user = new User("Test", "Tester");
        userDao.createUser(user);
        assertTrue(userDao.findUser("Tester"));
    }

    @Test
    public void createNewUserSecondTest() {
        User user = new User("Test", "Tester");
        userDao.createUser(user);
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:tests.db");
            statement = connection.createStatement();
            results = statement.executeQuery("SELECT * FROM Users");

            assertTrue(results.next());
            assertEquals("Test", results.getString("name"));
            assertEquals("Tester", results.getString("username"));
            assertFalse(results.next());

            results.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }
    }

    @Test
    public void findUserWorks() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:tests.db");
            statement = connection.createStatement();
            statement.execute("INSERT INTO Users (name, username) "
                    + "VALUES ('Test', 'Tester')");
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }
        assertTrue(userDao.findUser("Tester"));
    }

    @Test
    public void getUserid() {
        userDao.setUserId(5);
        assertEquals(5, userDao.getUserId());
    }

    @After
    public void tearDown() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:tests.db");
            statement = connection.createStatement();
            statement.execute("BEGIN TRANSACTION");
            statement.execute("PRAGMA foreign_keys = OFF");
            statement.execute("DROP TABLE IF EXISTS Users");
            statement.execute("DROP TABLE IF EXISTS Projects");
            statement.execute("DROP TABLE IF EXISTS Time");
            statement.execute("COMMIT");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

}
