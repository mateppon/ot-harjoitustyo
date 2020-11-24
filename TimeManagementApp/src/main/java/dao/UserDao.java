

package dao;

import domain.User;

public interface UserDao {
    
    boolean createUser(User user) throws Exception;
    
    boolean createTables() throws Exception;
    
    void select() throws Exception;
    
    
    
    
    
    
}
