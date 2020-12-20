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

//    @BeforeClass
//    public static void setUpClass() {
//        SQLUserDao userDao = new SQLUserDao("tests.db");
//        userDao.createTables();
//    }
//    @AfterClass
//    public static void tearDownClass() {
//    }
    @Before
    public void setUp() {

        userDao = new SQLUserDao(testsDb);
        userDao.createTables();
        projectsDao = new SQLProjectsDao(testsDb, userDao);
    }

//    @Test
//    public void getsAllProjects () {
//        userDao.setNewUser("User", "User");
//        projectsDao.setNewProject("MyProject", userDao.getUserId("User"));
//        projectsDao.setNewProject("MySecondProject", userDao.getUserId("User"));
//        ArrayList<String> projects = new ArrayList<>();
//        projects.add("MyProject");
//        projects.add("MySecondProject");
//        CustomObject[] 
//        
//        (Object[])projectsDao.getAllProjects(userDao.getUserId("User")
//        
//        assertArrayEquals((Object[])projects, ));
    //   }
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

//    @Test
//    public void getsProjectNameCorrect() {
//        userDao.setNewUser("Test", "Tester");
//        int userId = userDao.getUserId("Tester");
//        
//        try {
//        connection = DriverManager.getConnection(testsDb);
//        statement= connection.createStatement();
//        statement.execute("PRAGMA foreign_keys = ON");
//        preStatement = connection.prepareStatement(
//                "INSERT INTO Projects (projectname, userId) VALUES (?,?)");
//        preStatement.setString(1, "myProject");
//        preStatement.setInt(2, userId);
//        preStatement.close();
//        statement.close();
//        connection.close();  
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        
//        int projectId = projectsDao.getProjectId("myProject", userId);
//        assertEquals("myProject", projectsDao.getProjectName(projectId));
//    }
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
////    @Test
////    public void createNewprojectReturnsTrue() {
////        assertTrue(projectsDao.createNewProject("newProject", 9));
////    }
//    
//    @Test
//    public void getAllProjects() {
//        
//        String project1 = "newProject";
//        String project2 = "secondProject";
//        String project3 = "thirdProject";
//        ArrayList<String> myProjects = new ArrayList<>();
//        myProjects.add(project1);
//        myProjects.add(project2);
//        myProjects.add(project3);
//        ArrayList<String> myProjects = buildListOne();
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
////    
////    @Test
////    public void getProjectIdWorks() {
////        try {
////            connection = DriverManager.getConnection("jdbc:sqlite:tests.db");
////            statement = connection.createStatement();
////            statement.execute("INSERT INTO Projects (id, projectname, user_id) "
////                    + "VALUES (54, 'newProject', 2)");
////            statement.close();
////            connection.close();
////        } catch (SQLException e) {
////        }
////        assertEquals(54 ,projectsDao.getProjectId("newProject"));
////    }
//    @Test
//    public void bookTime() {
//        projectsDao.bookTime(1, 10);
//        try {
//            connection = DriverManager.getConnection("jdbc:sqlite:tests.db");
//            statement = connection.createStatement();
//            results = statement.executeQuery("SELECT * FROM Time");
//
//            assertTrue(results.next());
//            assertEquals(1, results.getInt("project_id"));
//            assertEquals(10, results.getInt("reserved_time"));
//            assertFalse(results.next());
//
//            results.close();
//            statement.close();
//            connection.close();
//        } catch (SQLException e) {
//        }
//    }
//    
//    @After
//    public void tearDown() {
//        try {
//            connection = DriverManager.getConnection("jdbc:sqlite:tests.db");
//            statement = connection.createStatement();
//            statement.execute("BEGIN TRANSACTION");
//            statement.execute("PRAGMA foreign_keys = OFF");
//            statement.execute("DROP TABLE IF EXISTS Users");
//            statement.execute("DROP TABLE IF EXISTS Projects");
//            statement.execute("DROP TABLE IF EXISTS Time");
//            statement.execute("COMMIT");
//            statement.close();
//            connection.close();
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//    }

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
