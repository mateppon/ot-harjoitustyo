package timemanagementapp.domain;

/*

 */

import org.junit.Test;
import static org.junit.Assert.*;


public class UserTest {
    
    @Test
    public void userConstructorSetsUsernameCorrect(){
        User user = new User("Tester");
        assertEquals("Tester", user.getUsername());
    }
    @Test
    public void constructorWithOutParametresNotNull() {
        User user = new User();
        assertTrue(user != null);
    }

    @Test
    public void setUserIdCorrect() {
        User user = new User("Tester");
        user.setUserId(3);
        assertEquals(3, user.getUserId());
    }
    
}
