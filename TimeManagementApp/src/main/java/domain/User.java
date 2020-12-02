/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;


public class User {
    private String userName;
    private String name;
    
    public User(String name, String userName) {
        this.name = name;
        this.userName = userName;
    }
    public String getUsername() {
        return this.userName;
    }
    public String getName() {
        return this.name;
    }
 
}

