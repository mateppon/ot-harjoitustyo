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


public class SQLUserDaoTest {
    
  
            
            

    SQLUserDao userDao;
    SQLUserDao setUpDao;

    //String testsDb = "jdbc:sqlite:tests.db";
    private String testsDb = "jdbc:sqlite:timeManagementTests.db";

    Connection connection = null;
    Statement statement = null;
    PreparedStatement preStatement = null;
    ResultSet results = null;

    public SQLUserDaoTest() {
    }

//    @BeforeClass
//    public static void setUpClass() {
////        SQLUserDao setUpDao = new SQLUserDao("tests.db");
////        setUpDao.createTables();
//    }



    @Before
    public void setUp() {
        userDao = new SQLUserDao(testsDb);
        userDao.createTables();
    }

    @Test
    public void tablesExists() {
        assertTrue(this.userDao != null);
    }

    @Test
    public void createNewUser() {
        userDao.setNewUser("Test", "Tester");
        int userId = userDao.getUserId("Tester");
        assertEquals("Tester", userDao.getUsername(userId));
    }

    @Test
    public void createNewUserSecondTest() {
        User user = new User("Tester");
        userDao.setNewUser("Test", "Tester");
        try {
            connection = DriverManager.getConnection(testsDb);
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
    public void deletesUser() {
        userDao.setNewUser("Test", "Tester");
        userDao.deleteUser(userDao.getUserId("Tester"));
        assertEquals(0, userDao.getUserId("Tester"));
    }

    @Test
    public void getsUsername() {
        try {
            connection = DriverManager.getConnection(testsDb);
            statement = connection.createStatement();
            statement.execute("INSERT INTO Users (name, username) "
                    + "VALUES ('Test', 'Tester')");
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }
        int userId = userDao.getUserId("Tester");
        assertEquals("Tester", userDao.getUsername(userId));
    }
    
// 
//    @Test
//    public void getsUserId() {
//       int testId = 0;
//        try {
//            connection = DriverManager.getConnection(testsDb);
//            statement = connection.createStatement();
//            statement.execute("INSERT INTO Users (name, username) VALUES"
//                    + "('Test', 'Tester')");
//            results = statement.executeQuery(
//                    "SELECT id FROM Users WHERE username = Tester");
//            results.next();
//            testId = results.getInt("id");
//            results.close();
//            statement.close();
//        } catch (SQLException e) {
//        }
//        int userId = userDao.getUserId("Tester");
//        assertEquals(testId, userId);
//    }
//
    @After
    public void tearDown() {
        try {
            connection = DriverManager.getConnection(testsDb);
            statement = connection.createStatement();
            statement.execute("DELETE FROM Users");
            //statement.execute("DELETE FROM Projects");
           // statement.execute("DELETE FROM Time");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

//    }
//        @AfterClass
//    public static void tearDownClass() {
//        try {
//            connection = DriverManager.getConnection(testsDb);
//            statement = connection.createStatement();
//
//            statement.execute("BEGIN TRANSACTION");
//            statement.execute("PRAGMA foreign_keys = OFF");
//            statement.execute("DROP TABLE IF EXISTS Users");
//            statement.execute("DROP TABLE IF EXISTS Projects");
//            statement.execute("DROP TABLE IF EXISTS Time");
//            statement.execute("COMMIT");
//            statement.close();
//            connection.close();
//        } catch (SQLException e) {
//
//        }
    }
}



