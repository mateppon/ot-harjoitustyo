/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import domain.User;


public class UserTest {
    
    @Test
    public void equalWhenSameName(){
        User user1 = new User("Test Test", "Tester");
        User user2 = new User("Test Test", "Tester");
        assertTrue(user1.equals(user2));
    }
}
