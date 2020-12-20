
package timemanagementapp.domain;

/**
 * Luokka sisältää käyttäjän projekteihin liittyvää tietoa.
 *
 */
public class Projects {

    private User user;
    private String projectName;
    private int projectId;

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
     * Asettaa projektin tunnisteen.
     *
     * @param projectId projektin tunniste
     */

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    /**
     * Palauttaa projektin tunnisteen
     *
     * @return projektin tunniste
     */

    public int getProjectId() {
        return this.projectId;
    }
}
