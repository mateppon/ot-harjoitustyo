package timemanagementapp.dao;

//import timemanagementapp.domain.User;

public interface UserDao {
    
    public int getUserId(String username);
    public String getUsername(int userId);
    public int setNewUser(String name, String username);
    public void deleteUser(int userId);
    public void createTables();

//    boolean createUser(User user) throws Exception;
//
//    void createTables() throws Exception;
//
//    boolean findUser(String username) throws Exception;
//
//    int getUserId();
}
