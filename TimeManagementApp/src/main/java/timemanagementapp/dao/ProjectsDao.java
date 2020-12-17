/*

 */
package timemanagementapp.dao;


import java.util.List;


public interface ProjectsDao {
    
//    public boolean createNewProject(String projectname, int userId);
//    public int getProjectId(String projectname);
//    public void bookTime(int projectId, int bookedTime);
//    
    public void updateTimeUsed(int projectId, int timeUsed);
    public int getTimeUsed(int projectId);
    public void setTimeUsed(int projectId, int timeUsed);
    public int getBookedHours(int projectId);
    public void updateBookedHours(int projectId, int bookedTime);
    public void setBookedHours(int projectId, int userId, int bookedTime);
    
    public String getProjectName(int projectId);
    public int getProjectId(String projectname, int userId);
    public void setNewProject(String projectname, int userId);
    public void deleteProject(int projectId);
    public List<String> getAllProjects(int userId);
}
