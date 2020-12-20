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
import java.util.ArrayList;



public class SQLProjectsTest {

    Connection connection = null;
    Statement statement = null;
    PreparedStatement preStatement = null;
    ResultSet results = null;

    SQLProjectsDao projectsDao;
    SQLUserDao userDao;

    private String testsDb = "jdbc:sqlite:timeManagementTests.db";

    public SQLProjectsTest() {
    }

    @Before
    public void setUp() {

        userDao = new SQLUserDao(testsDb);
        userDao.createTables();
        projectsDao = new SQLProjectsDao(testsDb, userDao);
    }

    @Test
    public void initializesBookedTimeToZero() {
        userDao.setNewUser("Test", "Tester");
        int userId = userDao.getUserId("Tester");

        projectsDao.setNewProject("MyProject", userId);
        int projectId = projectsDao.getProjectId("MyProject", userId);

        projectsDao.initTime(projectId, userId);
        int bookedTime = projectsDao.getBookedHours(projectId);

        assertEquals(0, bookedTime);

    }

    @Test
    public void initializesUsedTimeToZero() {
        userDao.setNewUser("Test", "Tester");
        int userId = userDao.getUserId("Tester");

        projectsDao.setNewProject("MyProject", userId);
        int projectId = projectsDao.getProjectId("MyProject", userId);

        projectsDao.initTime(projectId, userId);
        int timeUsed = projectsDao.getTimeUsed(projectId);

        assertEquals(0, timeUsed);
    }

    @Test
    public void updatesTimeUsed() {
        userDao.setNewUser("Test", "Tester");
        int userId = userDao.getUserId("Tester");
        projectsDao.setNewProject("MyProject", userId);
        int projectId = projectsDao.getProjectId("MyProject", userId);

        projectsDao.initTime(projectId, userId);
        assertEquals(0, projectsDao.getTimeUsed(projectId));

        projectsDao.updateTimeUsed(projectId, 9);
        assertEquals(9, projectsDao.getTimeUsed(projectId));
    }

    @Test
    public void initializesTimetableToZero() {
        userDao.setNewUser("Test", "Tester");
        int userId = userDao.getUserId("Tester");
        projectsDao.setNewProject("MyProject", userId);
        int projectId = projectsDao.getProjectId("MyProject", userId);

        projectsDao.initTime(projectId, userId);
        assertEquals(0, projectsDao.getTimeUsed(projectId));

    }

    @Test
    public void updatesBookedHours() {
        userDao.setNewUser("Test", "Tester");
        int userId = userDao.getUserId("Tester");
        projectsDao.setNewProject("MyProject", userId);
        int projectId = projectsDao.getProjectId("MyProject", userId);

        projectsDao.initTime(projectId, userId);
        assertEquals(0, projectsDao.getBookedHours(projectId));

        projectsDao.updateBookedHours(projectId, 15);
        assertEquals(15, projectsDao.getBookedHours(projectId));

    }

    @Test
    public void newProjectWorks() {
        userDao.setNewUser("Test", "Tester");
        int userId = userDao.getUserId("Tester");
        projectsDao.setNewProject("myProj", userId);
        int projectId = projectsDao.getProjectId("myProj", userId);
        assertEquals("myProj", projectsDao.getProjectName(projectId));
    }

    @Test
    public void deletesProject() {
        userDao.setNewUser("Test", "Tester");
        int userId = userDao.getUserId("Tester");
        projectsDao.setNewProject("NewProject", userId);
        int projectId = projectsDao.getProjectId("NewProject", userId);

        assertFalse(projectId == 0);

        projectsDao.deleteProject(projectsDao.getProjectId("NewProject", userId));
        assertEquals(0, projectsDao.getProjectId("NewProject", userId));
    }

    @Test
    public void readsProjectListIfNotEmpty() {
        userDao.setNewUser("Test", "Tester");
        int userId = userDao.getUserId("Tester");
        
        projectsDao.setNewProject("MyProject", userId);
        projectsDao.setNewProject("MySecondProject", userId);
        projectsDao.setNewProject("MyThirdProject", userId);
        
        ArrayList<String> all = projectsDao.getAllProjects(userId);
        
        assertFalse(all.isEmpty());
    }
    @Test
    public void readsProjectListIfEmpty() {
        userDao.setNewUser("Test", "Tester");
        int userId = userDao.getUserId("Tester");
        
        ArrayList<String> all = projectsDao.getAllProjects(userId);
        
        assertTrue(all.isEmpty());
    }

    @Test
    public void createsNewProject() {

        userDao.setNewUser("Test", "Tester");
        int userId = userDao.getUserId("Tester");

        projectsDao.setNewProject("newProject", userId);
        try {
            connection = DriverManager.getConnection(testsDb);
            statement = connection.createStatement();
            results = statement.executeQuery("SELECT * FROM Projects");

            assertTrue(results.next());
            assertEquals("newProject", results.getString("projectname"));
            assertEquals(userId, results.getInt("userId"));
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
