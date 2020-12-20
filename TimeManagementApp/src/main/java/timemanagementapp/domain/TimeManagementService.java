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
    Projects projects;

    /**
     * Konstruktori
     *
     * @param userDao UserDaon ilmentyma
     * @param projectsDao ProjektiDaon ilmentyma
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
            return false;
        }
    }

    /**
     * Metodi kutsuu ProjectsDaon projektilistan palauttavaa metodia kayttajan
     * tunnisteen kanssa.
     *
     * @return merkkijonolista käyttäjän projekteista
     */
    public List<String> getAllProjectNames() {
        return projectsDao.getAllProjects(user.getUserId());
    }

    /**
     * Mikali kayttajanimi on vapaa, uusi kayttaja tallennetaan UserDaon avulla
     * tietokantaan ja tiedot valitetaan paikalliselle logIn()-metodille.
     *
     * @param name nimi
     * @param username kayttajanimi
     * @return true, jos onnistuu
     */
    public boolean createNewUser(String name, String username) {
        int userId = userDao.getUserId(username);
        if (userId == 0) {
            userDao.setNewUser(name, username);
            logIn(username, userDao.getUserId(username));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodi etsii parametrina saatua vastaavan kayttajatunnisteen, ja valittaa
     * sen metodille logIn().
     *
     * @param username kayttajanimi
     * @return true, jos käyttäjä on olemassa
     */
    public boolean findIfUserExists(String username) {
        int userId = userDao.getUserId(username);
        if (userId == 0) {
            return false;
        } else {
            logIn(username, userId);
            return true;
        }
    }

    /**
     * Luo User-luokan ilmentymän ja asettaa sen kenttaan kayttajan yksiloivan
     * tunnisteen.
     *
     * @param username kayttjanimi
     * @param userId kayttajan tunniste
     */
    private void logIn(String username, int userId) {
        user = new User(username);
        user.setUserId(userId);
    }

    /**
     * Luo uuden projektin annetun parametrin mukaan, mikali kayttajalla ei ole
     * samannimistä projektia.
     *
     * @param projectname projektin nimi
     * @return true, jos onnistui
     */
    public boolean createNewProject(String projectname) {

        int userId = user.getUserId();
        int projectId = projectsDao.getProjectId(projectname, userId);

        if (projectId == 0) {
            projectsDao.setNewProject(projectname, userId);
            projectId = projectsDao.getProjectId(projectname, userId);
            initTimeTables(projectId, userId);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodi alustaa projektille Time-taulun arvoiksi 0;
     *
     *
     * @param projectname
     * @return true, jos projekti on olemassa ja alustus onnistuu
     */
    private void initTimeTables(int projectId, int userId) {
        projectsDao.initTime(projectId, userId);

    }

    /**
     * Asettaa tai päivittää käyttäjän antamat tunnit valitulle projektille.
     *
     * @param projectname projektin nimi
     * @param bookedTime projektille varatut tunnit
     * @return true, jos onnistuu
     */
    public boolean setBookedTimeForProject(String projectname, int bookedTime) {

        int projectId = projectsDao.getProjectId(projectname, user.getUserId());
        if (projectId == 0) {
            return false;
        }   else {
            projectsDao.updateBookedHours(projectId, bookedTime);
            return true;
        }
    }

    /**
     * Hakee tiedon siitä, kuinka paljon projektiin on käytetty tahan mennessä
     * aikaa, lisaa siihen parametrina tulleen ajan ja tallentaa uuden tiedon
     * tietokantaan.
     *
     * @param projectname projektin nimi
     * @param timeUsed lisattava aika
     */
    public boolean setTimeUsed(String projectname, int timeUsed) {
        int projectId = projectsDao.getProjectId(projectname, user.getUserId());
        if (projectId == 0) {
            return false;
        } else {
            int timeUsedSoFar = projectsDao.getTimeUsed(projectId);
            int timeUsedNow = timeUsedSoFar + timeUsed;
            projectsDao.updateTimeUsed(projectId, timeUsedNow);
            return true;
        }
        

        }
    /**
     * Nollaa usedTime-sarakkeen projektin kohdalta 
     * 
     * @param projectname projektin nimi
     */

    public void setTimeUsedToZero(String projectname) {
        int projectId = projectsDao.getProjectId(projectname, user.getUserId());
        projectsDao.updateTimeUsed(projectId, 0);
    }

    /**
     * Hakee projektille varatut tunnit tietokannasta.
     *
     * @param projectname projektin nimi
     * @return varatut tunnit
     */
    public int getBookedHoursForProject(String projectname) {
        int userId = user.getUserId();
        int projectId = projectsDao.getProjectId(projectname, userId);
        return projectsDao.getBookedHours(projectId);
    }

    /**
     * Metodi palauttaa projektiin käytetyn ajan.
     *
     * @param projectname proejktin nimi
     * @return kaytetty aika
     */
    public int getTimeSpentForProject(String projectname) {
        int userId = user.getUserId();
        int projectId = projectsDao.getProjectId(projectname, userId);
        return projectsDao.getTimeUsed(projectId);
    }

    /**
     * Hakee projektin tunnisteen ja poistaa tunnistetta vastaavan projektin
     * tietokannasta.
     *
     * @param projectname projektin nimi
     */
    public void deleteProject(String projectname) {
        int userId = user.getUserId();
        int projectId = projectsDao.getProjectId(projectname, userId);
        projectsDao.deleteProject(projectId);
    }

}
