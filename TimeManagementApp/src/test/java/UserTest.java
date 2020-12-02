/*

 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import domain.*;



public class UserTest {
    
    @Test
    public void userConstructorSetsUsernameCorrect(){
        User user = new User("Test", "Tester");
        assertEquals("Tester", user.getUsername());
    }
    @Test
    public void userConstructorSetsNameCorrect(){
        User user = new User("Test", "Tester");
        assertEquals("Test", user.getName());
    }
    @Test
    public void projectContructorSetsProjectnameCorrect(){
        User user = new User("Test", "Tester");
        Projects project = new Projects("project", user);
        assertEquals("project", project.getProjectName());
    }
    @Test
    public void projectConstructorSetsUsernameCorrect(){
        User user = new User("Test", "Tester");
        Projects project = new Projects("project", user);
        assertEquals("Tester", project.getUsername());
    }
}
