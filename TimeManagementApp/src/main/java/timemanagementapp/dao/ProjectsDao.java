/*

 */
package timemanagementapp.dao;


import java.util.List;


public interface ProjectsDao {
    
    public boolean createNewProject(String projectname, int userId);
    
    public List<String> getAllProjects(int userId);
    
    public int getProjectId(String projectname);
    
    public void bookTime(int projectId, int bookedTime);
    
}
