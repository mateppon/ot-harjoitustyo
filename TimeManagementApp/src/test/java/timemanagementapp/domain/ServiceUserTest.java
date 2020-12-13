/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timemanagementapp.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Luokka testaa TimemanagementService-luokan metodeja, jotka käyttävät
 * UserDao-rajapintaa tietokantayhteyden saamiseksi.
 *
 */
public class ServiceUserTest {

    TimeManagementService serviceTest;
    FakeUserDao fakeUserDao;
    FakeProjectsDao fakeProjectsDao;

   // private String testdb = "jdbc:sqlite:memory:testsdb";
    Connection connection = null;
    Statement statement = null;

    private String schema
            = "CREATE TABLE Users (id INTEGER PRIMARY KEY, "
            + "name TEXT, "
            + "username TEXT UNIQUE)"
            + "CREATE TABLE Projects ("
            + "id INTEGER PRIMARY KEY, "
            + "projectname TEXT UNIQUE, "
            + "user_id INTEGER REFERENCES Users)"
            + "CREATE TABLE Time ("
            + "id INTEGER PRIMARY KEY, "
            + "projectname_id INTEGER REFERENCES Projects, "
            + "reserved_time INTEGER, "
            + "time_used INTEGER, "
            + "user_id INTEGER REFERENCES Users)";

    public ServiceUserTest() {
    }

//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    @Before
//    public void setUp() {
//        fakeUserDao = new FakeUserDao(testdb);
//        fakeProjectsDao = new FakeProjectsDao(testdb, fakeUserDao);
//        serviceTest = new TimeManagementService(fakeUserDao, fakeProjectsDao);
//        
//
//    }

//    @After
//    public void tearDown() {
//    }
//    @Test
//    public void createTablesWorks() {
//        try {
//            assertTrue(service.createTables());
//            //assertEquals(schema, connection.getSchema());
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//    @Test
//    public void schemaIsCorrect() {
//        try {
//        assertTrue(serviceTest.createTables());
//        } catch (Exception e) {
//            System.out.println(e);
//    }
//      @Test
//      public void newUserExists() throws Exception {
          
          
//          boolean result = serviceTest.createNewUser("Test", "Tester");
//          assertFalse(result);
//          
//          boolean userExists = serviceTest.findUser("Tester");
//          assertFalse(userExists);
//          



      }
//      @Test
//      public void getProjects() {
//          serviceTest.createNewUser("Test", "Tester");
//          serviceTest.createNewProject("MyProject");
//          serviceTest.createNewProject("myProject2");
//          
//      }
      
    
//}


 

