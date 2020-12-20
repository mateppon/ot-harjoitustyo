/*
 
 */
package timemanagementapp.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.*;


/**
 * Luokka testaa TimemanagementService-luokan metodeja, jotka käyttävät
 * UserDao-rajapintaa tietokantayhteyden saamiseksi.
 *
 */
public class ServiceUserTest {

    TimeManagementService serviceTest;
    FakeUserDao fakeUserDao;
    FakeProjectsDao fakeProjectsDao;

    private String testsDb = "jdbc:sqlite:timeManagementTests.db";

    Connection connection = null;
    Statement statement = null;

    public ServiceUserTest() {
    }

    @Before
    public void setUp() {
        fakeUserDao = new FakeUserDao(testsDb);
        fakeProjectsDao = new FakeProjectsDao(testsDb, fakeUserDao);
        serviceTest = new TimeManagementService(fakeUserDao, fakeProjectsDao);
        fakeUserDao.createTables();
    }
    @Test
    public void createNewUserReturnsFalseIfUsernameAlreadyExists() {
        serviceTest.createNewUser("Test", "Tester");
        assertFalse(serviceTest.createNewUser("Test", "Tester"));
    }
    
    @Test
    public void createNewUserReturnsTrueIfSucceed() {
        
        assertTrue(serviceTest.createNewUser("Test", "Tester"));
    }

    @Test
    public void findIfUserExistsReturnsFalseWhenUserNotExist() {
        assertFalse(serviceTest.findIfUserExists("Tester"));
    }

    @Test
    public void findIfUserExistsReturnsTrueWhenUserExists() {
        serviceTest.createNewUser("Test", "Tester");
        assertTrue(serviceTest.findIfUserExists("Tester"));
    }
    @Test
    public void createsTables() {
        assertTrue(serviceTest.createTables());
    }

    @After
    public void tearDown() {
        try {
            connection = DriverManager.getConnection(testsDb);
            statement = connection.createStatement();
            statement.execute("DELETE FROM Users");
            statement.execute("DELETE FROM Projects");
            statement.execute("DELETE FROM Time");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}


