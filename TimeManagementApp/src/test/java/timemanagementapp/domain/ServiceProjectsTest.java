/*
 * 
 */
package timemanagementapp.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.*;

public class ServiceProjectsTest {

    TimeManagementService serviceTest;
    FakeUserDao fakeUserDao;
    FakeProjectsDao fakeProjectsDao;

    private String testsDb = "jdbc:sqlite:timeManagementTests.db";

    Connection connection = null;
    Statement statement = null;

    public ServiceProjectsTest() {
    }

    @Before
    public void setUp() {
        fakeUserDao = new FakeUserDao(testsDb);
        fakeProjectsDao = new FakeProjectsDao(testsDb, fakeUserDao);
        serviceTest = new TimeManagementService(fakeUserDao, fakeProjectsDao);
        fakeUserDao.createTables();
    }

    @Test
    public void whenCreateNewProjectBookedTimeSetsToZero() {
        serviceTest.createNewUser("Test", "Tester");
        int userId = fakeUserDao.getUserId("Tester");

        serviceTest.createNewProject("MyProject");
        int projectId = fakeProjectsDao.getProjectId("MyProject", userId);
        int bookedHours = fakeProjectsDao.getBookedHours(projectId);

        assertEquals(0, bookedHours);
    }

    @Test
    public void whenCreateNewProjectTimeSpentSetsToZero() {
        serviceTest.createNewUser("Test", "Tester");
        int userId = fakeUserDao.getUserId("Tester");

        serviceTest.createNewProject("MyProject");
        int projectId = fakeProjectsDao.getProjectId("MyProject", userId);
        int timeSpent = fakeProjectsDao.getTimeUsed(projectId);

        assertEquals(0, timeSpent);
    }

    @Test
    public void getsBookedHoursForProject() {
        serviceTest.createNewUser("Test", "Tester");
        int userId = fakeUserDao.getUserId("Tester");

        serviceTest.createNewProject("MyProject");
        int projectId = fakeProjectsDao.getProjectId("MyProject", userId);

        serviceTest.setBookedTimeForProject("MyProject", 27);

        assertEquals(27, serviceTest.getBookedHoursForProject("MyProject"));
    }
    
    @Test
    public void setTimeusedReturnsTrueIfProjectExists () {
        serviceTest.createNewUser("Test", "Tester");
        int userId = fakeUserDao.getUserId("Tester");
        serviceTest.createNewProject("MyProject");
        
        assertTrue(serviceTest.setTimeUsed("MyProject", 5));
    }
    
    @Test
    public void setTimeReturnFalseIfProjectDoesNotExist() {
        serviceTest.createNewUser("Test", "Tester");
        assertFalse(serviceTest.setTimeUsed("MyProject", 4));
    }
    
    @Test
    public void setBookedTimeReturnsTrueIfProjectExists() {
        serviceTest.createNewUser("Test", "Tester");
        int userId = fakeUserDao.getUserId("Tester");
        serviceTest.createNewProject("MyProject");
        
        assertTrue(serviceTest.setBookedTimeForProject("MyProject", 12));
        
    }
    @Test
    public void setBookedTimeReturnsFalseIfProjectDoesNotExist() {
        serviceTest.createNewUser("Test", "Tester");
        assertFalse(serviceTest.setBookedTimeForProject("MyProject", 12));
    }
    

    @Test
    public void setsTimeSpentForProject() {
        serviceTest.createNewUser("Test", "Tester");
        int userId = fakeUserDao.getUserId("Tester");

        serviceTest.createNewProject("MyProject");
        int projectId = fakeProjectsDao.getProjectId("MyProject", userId);

        serviceTest.setTimeUsed("MyProject", 5);

        assertEquals(5, serviceTest.getTimeSpentForProject("MyProject"));
    }

    @Test
    public void sumsTimeSpentIfUpdated() {
        serviceTest.createNewUser("Test", "Tester");
        int userId = fakeUserDao.getUserId("Tester");

        serviceTest.createNewProject("MyProject");
        int projectId = fakeProjectsDao.getProjectId("MyProject", userId);

        serviceTest.setTimeUsed("MyProject", 5);
        assertEquals(5, serviceTest.getTimeSpentForProject("MyProject"));
        
        serviceTest.setTimeUsed("MyProject", 2);
        assertEquals(7, serviceTest.getTimeSpentForProject("MyProject"));
    }
    @Test
    public void deletesProject() {
        serviceTest.createNewUser("Test", "Tester");
        int userId = fakeUserDao.getUserId("Tester");

        serviceTest.createNewProject("MyProject");
        int projectId = fakeProjectsDao.getProjectId("MyProject", userId);
        
        assertTrue(projectId != 0);
        
        serviceTest.deleteProject("MyProject");
        assertTrue(fakeProjectsDao.getProjectId("MyProject", userId) == 0);
    }

    @Test
    public void notCreateProjectIfItExists() {
        serviceTest.createNewUser("Test", "Tester");
        serviceTest.createNewProject("MyProject");

        assertFalse(serviceTest.createNewProject("MyProject"));

    }

    @Test
    public void createsNewProjectIfNotExist() {
        serviceTest.createNewUser("Test", "Tester");

        assertTrue(serviceTest.createNewProject("MyProject"));
    }

    @Test
    public void twoDifferentUserCanHaveProjectsWithSameName() {
        serviceTest.createNewUser("Test", "Tester");
        serviceTest.createNewProject("MyProject");

        serviceTest.createNewUser("Second Tester", "TesterTester");
        assertTrue(serviceTest.createNewProject("MyProject"));
    }

    @Test
    public void serviceSetsUsedTimeCorrect() {
        serviceTest.createNewUser("Test", "Tester");
        int userId = fakeUserDao.getUserId("Tester");

        serviceTest.createNewProject("MyProject");
        int projectId = fakeProjectsDao.getProjectId("MyProject", userId);

        serviceTest.setTimeUsed("MyProject", 10);
        assertEquals(10, fakeProjectsDao.getTimeUsed(projectId));
    }

    @Test
    public void getsBookedHoursCorrect() {
        serviceTest.createNewUser("Test", "Tester");
        int userId = fakeUserDao.getUserId("Tester");

        serviceTest.createNewProject("MyProject");
        int projectId = fakeProjectsDao.getProjectId("MyProject", userId);

        serviceTest.setBookedTimeForProject("MyProject", 10);
        assertEquals(10, fakeProjectsDao.getBookedHours(projectId));
    }
    @Test
    public void setUsedTimeSetsTozero() {
         serviceTest.createNewUser("Test", "Tester");
        int userId = fakeUserDao.getUserId("Tester");

        serviceTest.createNewProject("MyProject");
        int projectId = fakeProjectsDao.getProjectId("MyProject", userId);
        serviceTest.setTimeUsed("MyProject", 10);
        assertEquals(10, fakeProjectsDao.getTimeUsed(projectId));
        
        serviceTest.setTimeUsedToZero("MyProject");
        assertEquals(0, fakeProjectsDao.getTimeUsed(projectId));
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
        }
    }
}
