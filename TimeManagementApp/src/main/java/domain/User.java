/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;


public class User {
    private String userName;
    private String name;
    
    public User(){    
    }
    
    public User(String name, String userName) {
        this.name = name;
        this.userName = userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return this.userName;
    }
    public String getName() {
        return this.name;
    }
    @Override
    public String toString() {
        return "name: " + this.name + ", username: " + this.userName; 
    }    
}

