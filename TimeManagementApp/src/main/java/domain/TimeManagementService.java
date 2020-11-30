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
     public void setForeignKeys(){
         userDao.foreignKeysOn();
     }
     
     public boolean createNewUser(String name, String username) {
      try{
         if(userDao.selectUser(username)) {
             return false;
         }
             } catch(Exception e) {
             }   
     try {
          this.user = new User(name, username);
     } catch (Exception e) { 
         return false;
     }    
     try {
          userDao.createUser(user);    
     return true;
     }catch (Exception e) {
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
     
  
