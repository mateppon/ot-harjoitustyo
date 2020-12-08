/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timemanagementapp.dao;


import java.util.List;


public interface ProjectsDao {
    
    public boolean createNewProject(String projectname, int userId);
    
    public List<String> getAllProjects(int userId);
    
}
