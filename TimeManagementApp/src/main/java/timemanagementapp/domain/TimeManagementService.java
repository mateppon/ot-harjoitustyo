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
            return false;
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

    public boolean createNewUser(String name, String username) {
        int userId = userDao.getUserId(username);
        if (userId == 0) {
            userDao.setNewUser(name, username);
            logIn(username, userId);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodi etsii parametrina annetulle käyttäjänimelle tunnistetta, ja
     * välittää sen metodille logIn().
     *
     * @param username käyttäjänimi
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
     * Luo User-luokan ilmentymän, ja asettaa sille käyttäjän tunnisteen.
     *
     * @param username käyttjänimi
     * @param userId käyttäjän tunniste
     */
    private void logIn(String username, int userId) {
        user = new User(username);
        user.setUserId(userId);
    }

   /**
    * Luo uuden projektin annetun parametrin mukaan, mikäli käyttäjällä ei ole
     * samannimistä projektia.
     * 
    * @param projectname
    * @return true, jos onnistui
    */
    public boolean createNewProject(String projectname) {
        int projectId = projectsDao.getProjectId(projectname, user.getUserId());
        if (projectId == 0) {
            projectsDao.setNewProject(projectname, user.getUserId());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Asettaa tai päivittää käyttäjän antamat tunnit valitulle projektille.
     *
     * @param projectname
     * @param bookedTime
     * @return true, jos onnistuu
     */

    public boolean setBookedTimeForProject(String projectname, int bookedTime) {
//        if (bookedTime == 0) {
//            return false;
//        } else {

        int projectId = projectsDao.getProjectId(projectname, user.getUserId());

        if (projectsDao.getBookedHours(projectId) == 0) {
            projectsDao.setBookedHours(projectId, user.getUserId(), bookedTime);
        } else {
            projectsDao.updateBookedHours(projectId, bookedTime);
            return true;
        }
        return true;
    }
}

//    /**
//     * Metodi luo uuden käyttäjän ja kutsuu tietokannasta vastaavaa metodia
//     *
//     * @param name nimi
//     * @param username käyttäjänimi
//     * @return true, jos onnistuu
//     */
//    public boolean createNewUser(String name, String username) {
//        try {
//            
//            if (userDao.findUser(username)) {
//                return false;
//            } else {
//                this.user = new User(name, username);
//                if (userDao.createUser(user)) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//            return false;
//        }
//    }
//
//    /**
//     * Metodi etsii löytyykö käyttäjä tietokannasta
//     *
//     * @param username käyttäjänimi
//     * @return true, jos löytyy
//     */
//    public boolean findUser(String username) {
//        try {
//            if (userDao.findUser(username)) {
//                return true;
//            }
//        } catch (Exception e) {
//        }
//        return false;
//    }
//
//    /**
//     * Metodi luo uuden projektin käyttäjälle ja antaa sen parametriksi
//     * tietokantaan vietävälle projektille.
//     *
//     * @param projectname Projektille annettava nimi
//     * @return true, jos onnistuu
//     */
//    public boolean createNewProject(String projectname) {
//
//        Projects project = new Projects(projectname, user);
//        if (projectsDao.createNewProject(
//                project.getProjectName(), user.getUserId())) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * Metodi hakee kirjautuneen käyttäjän tunnuksen ja asettaa sen luokan User
//     * muuttujaan userId.
//     */
//    public void loggedIn() {
//        try {
//            this.user = new User();
//            user.setUserId(userDao.getUserId());
//        } catch (Exception e) {
//        }
//    }
//
//
//    /**
//     * Tarkistaa käyttäjän antaman syötteen, 
//     * ja kutsuu ajan asettamiseen tarvittavia metodeita, 
//     * mikäli syöte on kunnossa. 
//     * 
//     * @param projectname projektin nimi
//     * @param bookedTime varattava aika
//     * @return true, jos syöte ok
//     */
//
//    public boolean setTimeIfOk(String projectname, int bookedTime) {
//        //Tarkista syöte: if syöte ok
//
//        try { 
//        getProjectId(projectname);
//        setBookedTime(bookedTime);
//        
//        } catch (Exception e) {
//            System.out.println("servicessä"+e);
//        }
//        return true;
//                
//
//    }
//    /**
//     * Luo uuden Projects-olion annetulla nimellä
//     * ja hakee nimeä vastaavan id-tunnuksen tietokannasta.
//     * 
//     * @param projectname projektin nimi
//     */
//
//    private void getProjectId(String projectname) {
//        projects = new Projects(projectname, user);
//        projects.setProjectId(projectsDao.getProjectId(projectname));
//    }
//
//    /**
//     * Kutsuu ProjectsDaon bookTime()-metodia parametreilla projektin id ja
//     * projektille varattava aika.
//     *
//     * @param bookedTime projektille varattava aika
//     */
//
//    private void setBookedTime(int bookedTime) {
//        projectsDao.bookTime(projects.getProjectId(), bookedTime);
//    }
//
//}
