/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timemanagementapp.domain;

public class User {

    private String userName;
    private String name;
    private int userId;

    public User() {

    }

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

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        System.out.println("userissa");
        return this.userId;
    }

}
