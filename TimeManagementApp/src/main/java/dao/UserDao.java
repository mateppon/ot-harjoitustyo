package dao;

import domain.User;

public interface UserDao {

    boolean createUser(User user) throws Exception;

    boolean createTables() throws Exception;

    boolean findUser(String username) throws Exception;

    int getUserId();

    void foreignKeysOn();

}
