/*

 */
package timemanagementapp.domain;

import timemanagementapp.dao.UserDao;
import timemanagementapp.dao.ProjectsDao;
import java.util.List;

/**
 * Luokka sisältää sovelluksen logiikan, ja se välittää tietoa eri luokkien
 * välillä.
 *
 */
public class TimeManagementService {

    UserDao userDao;
    ProjectsDao projectsDao;

    User user;

    /**
     * Konstruktori
     *
     * @param userDao UserDaon ilmentymä
     * @param projectsDao ProjektiDaon ilmentymä
     */

    public TimeManagementService(UserDao userDao, ProjectsDao projectsDao) {
        this.userDao = userDao;
        this.projectsDao = projectsDao;
    }

    /**
     * Metodi kutsuu tietokannan luovaa metodia
     *
     * @return true, jos onnistuu
     */

    public boolean createTables() {
        try {
            userDao.createTables();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Metodi luo uuden käyttäjän ja kutsuu tietokannasta vastaavaa metodia
     *
     * @param name nimi
     * @param username käyttäjänimi
     * @return true, jos onnistuu
     */

    public boolean createNewUser(String name, String username) {
        try {
            if (userDao.findUser(username)) {
                return false;
            } else {
                this.user = new User(name, username);
                if (userDao.createUser(user)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Metodi etsii löytyykö käyttäjä tietokannasta
     *
     * @param username käyttäjänimi
     * @return true, jos löytyy
     */

    public boolean findUser(String username) {
        try {
            if (userDao.findUser(username)) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * Metodi luo uuden projektin käyttäjälle ja antaa sen parametriksi
     * tietokantaan vietävälle projektille.
     *
     * @param projectname Projektille annettava nimi
     * @return true, jos onnistuu
     */
    public boolean createNewProject(String projectname) {

        Projects project = new Projects(projectname, user);
        if (projectsDao.createNewProject(
                project.getProjectName(), user.getUserId())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodi hakee kirjautuneen käyttäjän tunnuksen ja asettaa sen luokan User
     * muuttujaan userId.
     */
    public void loggedIn() {
        try {
            this.user = new User();
            user.setUserId(userDao.getUserId());
        } catch (Exception e) {
        }
    }

    /**
     * Metodi kutsuu projectsDaon projektilistan palauttavaa metodia käyttäjän
     * tunnisteen kanssa.
     *
     * @return merkkijonolista käyttäjän projekteista
     */
    public List<String> getAllProjectNames() {
        return projectsDao.getAllProjects(user.getUserId());
    }
}
