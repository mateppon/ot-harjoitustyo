package timemanagementapp.domain;

/**
 * Luokka sisaltaa kayttajaan liittyvia tietoja.
 *
 */
public class User {

    private String userName;
    private int userId;

    /**
     * Konstruktori ilman parametreja.
     *
     */
    public User() {

    }

    /**
     * Konstruktori
     *
     * @param username kayttajanimi
     */
    public User(String username) {
        this.userName = username;
    }

    /**
     * Metodi palauttaa kayttajanimen
     *
     * @return kayttajanimi
     */
    public String getUsername() {
        return this.userName;
    }

    /**
     * Metodi asettaa saadun parametrin kayttajan tunnisteeksi
     *
     * @param userId kayttajan tunniste
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Metodi palauttaa kayttajan tunnisteen
     *
     * @return kayttajan tunniste
     */
    public int getUserId() {
        return this.userId;
    }

}
