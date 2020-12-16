package timemanagementapp.dao;

import timemanagementapp.domain.User;

public interface UserDao {

    boolean createUser(User user) throws Exception;

    void createTables() throws Exception;

    boolean findUser(String username) throws Exception;

    int getUserId();

}
