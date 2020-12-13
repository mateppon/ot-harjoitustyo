/*

 */
package timemanagementapp.domain;

import java.util.*;

/**
 * Luokka sisältää käyttäjän projekteihin liittyvää tietoa.
 *
 */
public class Projects {

    private User user;
    private String projectName;
    private int projectId;

    private ArrayList<String> projectNames;

    /**
     * Konstruktori
     *
     * @param projectName projektin nimi
     * @param user käyttäjäluokan ilmentymän
     */
    public Projects(String projectName, User user) {
        this.projectName = projectName;
        this.user = user;
    }

    /**
     * Palauttaa projektin nimen
     *
     * @return projektin nimi;
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * Palauttaa projektin omistavan käyttäjän nimen.
     *
     * @return käyttäjän nimi
     */
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Lisää projektin projektilistaan
     *
     * @param projectName projektin nimi
     */

    public void setToProjectNames(String projectName) {
        this.projectNames.add(projectName);
    }
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
    public int getProjectId() {
        return this.projectId;
    }
}
