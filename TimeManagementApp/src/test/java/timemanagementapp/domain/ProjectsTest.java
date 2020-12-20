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

/**
 *
 *
 */
public class ProjectsTest {

    public ProjectsTest() {
    }

    @Test
    public void projectContructorSetsProjectnameCorrect() {
        User user = new User("Tester");
        Projects project = new Projects("project", user);
        assertEquals("project", project.getProjectName());
    }

    @Test
    public void setsProjectIdCorrect() {
        User user = new User("Tester");
        Projects projects = new Projects("Projectname", user);
        projects.setProjectId(3);

        assertEquals(3, projects.getProjectId());
    }

}
