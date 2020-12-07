/*

 */
package domain;

import dao.UserDao;
import dao.ProjectsDao;
import java.util.List;
import java.util.ArrayList;

public class TimeManagementService {

    UserDao userDao;
    ProjectsDao projectsDao;

    //List<String> lista = new ArrayList<>();
    User user;

    public TimeManagementService(UserDao userDao, ProjectsDao projectsDao) {
        this.userDao = userDao;
        this.projectsDao = projectsDao;
    }

    public boolean createTables() {
        try {
            userDao.createTables();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public void setForeignKeys() {
        userDao.foreignKeysOn();
    }

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

    public boolean findUser(String username) {
        try {
            if (userDao.findUser(username)) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    /**
     * Metodi luo uuden projektin käyttäjälle ja vie sen tietokantaan
     *
     * @param projectname Projektille annettava nimi
     * @return true, jos onnistuu ja false, jos ei onnistu
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
     * muuttujaan userId
     *
     * @param
     */
    public void loggedIn() {
        try {
            this.user = new User();
            user.setUserId(userDao.getUserId());
        } catch (Exception e) {
        }
    }

    
    public List<String> getAllProjectNames() {
        System.out.println("servicessä: ");
        return projectsDao.getAllProjects(user.getUserId());
    }
}
