/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
         System.out.println("createuser servisessä");

         try {      
         if(userDao.findUser(username)) {
             return false;
         } else {
             System.out.println("elsessä");
             this.user = new User(name, username);
             System.out.println("ennen userdaota");
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
     
  
