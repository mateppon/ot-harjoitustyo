/*

 */
package domain;

import dao.UserDao;
import dao.ProjectsDao;

public class TimeManagementService {
    
    UserDao userDao;
    ProjectsDao projectsDao;
    
    User user;

     public TimeManagementService(UserDao userDao, ProjectsDao projectsDao) {
     this.userDao = userDao;
     this.projectsDao = projectsDao;
      }
       
     public boolean createTables() {
         try{
         userDao.createTables();
         return true;
         }catch (Exception e){        
             System.out.println(e);
    return false;
     }
     }
     public void setForeignKeys() {
         userDao.foreignKeysOn();
     }
     
     public boolean createNewUser(String name, String username) {
         try {      
         if(userDao.findUser(username)) {
             return false;
         } else {
             this.user = new User(name, username);
             if(userDao.createUser(user)) { 
                 return true;
             } else 
                 return false;
         }  
             } catch(Exception e) {
                 System.out.println(e);
                 return false;
             }
         }
     
     public boolean createNewProject(String projectname){
         
         Projects project = new Projects(projectname, user);
         if (projectsDao.createNewProject(project)) {
         return true;
         } else 
             return false;
         }
     
     
     }
     
  
