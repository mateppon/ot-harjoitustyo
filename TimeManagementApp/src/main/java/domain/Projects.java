/*

 */
package domain;

import java.util.*;

import dao.ProjectsDao;

public class Projects {

    private User user;
    private String projectName;

    private ArrayList<String> projectNames;

    public Projects(String projectName, User user) {
        this.projectName = projectName;
        this.user = user;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public void setToProjectNames(String projectName) {
        this.projectNames.add(projectName);
        System.out.println("projekteissa: " + projectNames);

    }
}
