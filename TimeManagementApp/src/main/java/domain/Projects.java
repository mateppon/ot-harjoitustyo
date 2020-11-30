/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;


public class Projects {
    
    private User user;
    private String projectName;
    private int reservedTime;
    private int UsedTime;
    
    public Projects(String projectName, User user) {
        this.projectName = projectName;
        this.user = user;
    }
    
    public String getProjectName(){
        return this.projectName;
    }
            
//    public boolean setReservedTime(){
//        
//    }
//    public int getReservedTime(){
//        
//    }
//    public boolean setUsedTime(){
//        
//    }
//    public int getUsedTime(){
//        
//    }
//    public boolean setTimeToNull(){
//        
//    }
    
}
