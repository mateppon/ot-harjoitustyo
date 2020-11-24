/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import dao.UserDao;

public class TimeManagementService {
    
    UserDao userDao;
    User user;
   
   
     public TimeManagementService(UserDao userDao) {
     this.userDao = userDao;
      }
       
     public boolean createNewUser(String name, String username) {       
     try{
     this.user = new User(name, username);
     }catch (Exception e) { 
         return false;
     }    
     try {
     userDao.createUser(user);    
     return true;
     }catch (Exception e){
         return false;        
     }
     }
     
     public boolean createTables(){
         try{
         userDao.createTables();
         return true;
         }catch (Exception e){        
             System.out.println(e);
    return false;
     }
       
   
   
 
}
     }
     
  
