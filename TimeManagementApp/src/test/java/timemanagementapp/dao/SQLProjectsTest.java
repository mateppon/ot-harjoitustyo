/*
 *
 */
package timemanagementapp.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import timemanagementapp.domain.User;
import timemanagementapp.domain.Projects;

/**
 * Testaa luokan SQLProjects toimintaa.
 *
 */
public class SQLProjectsTest {

    Connection connection = null;
    Statement statement = null;
    PreparedStatement preStatement = null;
    ResultSet results = null;
    
    SQLProjectsDao projectsDao;
    SQLUserDao userDao;

    public SQLProjectsTest() {
    }

//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
    @Before
    public void setUp() {
        userDao = new SQLUserDao("tests.db");
        projectsDao = new SQLProjectsDao("tests.db", userDao);
        try {
            userDao.createTables();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    @Test
    public void createNewProjectWorks() {
        User user = new User("Test", "Tester");
        Projects project = new Projects("newProject", user);
        int userId = 3;
        projectsDao.createNewProject("newProject", 3);  
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:tests.db");
            statement = connection.createStatement();
            results = statement.executeQuery("SELECT * FROM Projects");

            assertTrue(results.next());
            assertEquals("newProject", results.getString("projectname"));
            assertEquals(3, results.getInt("user_id"));
            assertFalse(results.next());

            results.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }
    }
//    @Test
//    public void createNewprojectReturnsTrue() {
//        assertTrue(projectsDao.createNewProject("newProject", 9));
//    }
    
//    @Test
//    public void getAllProjects() {
//        int userId = 2;
//        String project1 = "newProject";
//        String project2 = "secondProject";
//        String project3 = "thirdProject";
//        ArrayList<String> myProjects = new ArrayList<>();
//        myProjects.add(project1);
//        myProjects.add(project2);
//       // myProjects.add(project3);
//        
//        try {
//            connection = DriverManager.getConnection("jdbc:sqlite:tests.db");
//            statement = connection.createStatement();
//            statement.execute("INSERT INTO Projects (projectname, user_id) "
//                    + "VALUES ('newProject', 2)");
//            statement.execute("INSERT INTO Projects (projectname, user_id) "
//                    + "VALUES ('secondProject', 2)");
//            statement.close();
//            connection.close();
//        } catch (SQLException e) {
//        }
//        assertTrue(Arrays.equals(myProjects, projectsDao.getAllProjects(2)));
//    }
//    
//    @Test
//    public void getProjectIdWorks() {
//        try {
//            connection = DriverManager.getConnection("jdbc:sqlite:tests.db");
//            statement = connection.createStatement();
//            statement.execute("INSERT INTO Projects (id, projectname, user_id) "
//                    + "VALUES (54, 'newProject', 2)");
//            statement.close();
//            connection.close();
//        } catch (SQLException e) {
//        }
//        assertEquals(54 ,projectsDao.getProjectId("newProject"));
//    }
    @Test
    public void bookTime() {
        projectsDao.bookTime(1, 10);
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:tests.db");
            statement = connection.createStatement();
            results = statement.executeQuery("SELECT * FROM Time");

            assertTrue(results.next());
            assertEquals(1, results.getInt("project_id"));
            assertEquals(10, results.getInt("reserved_time"));
            assertFalse(results.next());

            results.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }
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
