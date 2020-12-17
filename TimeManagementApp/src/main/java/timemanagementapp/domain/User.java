/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timemanagementapp.domain;

/**
 * Luokka sisältää käyttäjään liittyviä tietoja
 *
 */
public class User {

    private String userName;
//    private String name;
    private int userId;

    /**
     * Konstruktori
     */
    public User() {

    }

    /**
     * Konstruktori
     *
     * @param userName käyttäjänimi
     */
    public User(String userName) {
 //       this.name = name;
        this.userName = userName;
    }

    /**
     * Metodi palauttaa käyttäjänimen
     *
     * @return käyttäjänimi
     */

    public String getUsername() {
        return this.userName;
    }

//    /**
//     * Metodi palauttaa nimen
//     *
//     * @return nimi
//     */
//    public String getName() {
//        return this.name;
//    }

    /**
     * Metodi asettaa saadun parametrin käyttäjän tunnisteeksi
     *
     * @param userId käyttäjän tunniste
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Metodi palauttaa käyttäjän tunnisteen
     *
     * @return käyttäjän tunniste
     */
    public int getUserId() {
        return this.userId;
    }

}
