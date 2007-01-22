/*
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 
Copyright (C) magsilva <EMAIL>
 */

package net.sf.ideais.dotproject;

import net.sf.ideais.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Transfer Object for a Project available at a DotProject instance.
 * 
 */
public class ProjectDAO extends DAO
{        
    /**
    * Creates a new instance of ProjectDAO
    */
    public ProjectDAO(String sgbd, String hostname, String database, String username, String password)
    {
        super(sgbd, hostname, database, username, password);
    }
  
    /**
     * Create a Project instance.
     *
     * @param rs The ResultSet with the data loaded from the database.
     * @return The Task object if there is enough data to create a task instance,
     * null otherwise.
     */
    private Project createInstance(ResultSet rs)
    {
        Project project = new Project();
        
        try {
            project.setName(rs.getString("project_name"));
        } catch (SQLException e) {
	    dumpSQLException(e);
            // Probably an inexistent task was requested.
            project = null;
        }
        return project;
    }
    
    /**
     * Load the data for a task and create a task instance.
     *
     * @param task_id The task_id.
     * @return The task (if found) or null.
     */
    public Project loadData(int project_id)
    {
       // assume that conn is an already created JDBC connection
       PreparedStatement stmt = null;
       ResultSet rs = null;
       Project project = null;
       
       try {
           String query = "SELECT * FROM projects WHERE project_id = ?";
           stmt = conn.prepareStatement(query);
           stmt.setInt(1, project_id);
           rs = stmt.executeQuery();
	   rs.next();
           project = createInstance(rs);
       } catch (SQLException sqe) {
	    dumpSQLException(sqe);
           // Probably an inexistent task was request. We may ignore the 
           // Now do something with the ResultSet ....
       } finally {
           // Release resources.
           if (stmt != null) {
               try {
                   stmt.close();
               } catch (SQLException sqlEx) {
               }
           }
       }
 
       return project;
    }    
    
    
  	public int save(Project project)
    {
	      // assume that conn is an already created JDBC connection
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      int task_id = 0;
	      
	      try
	      {
			String query = "INSERT INTO projects (project_name, project_description, project_owner, project_company) values (?,?,?,?)";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, project.getName());
			stmt.setString(2, project.getDescription());
			stmt.setInt(3, project.getOwnerId());
			stmt.setInt(4, project.getCompanyId());
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			rs.next();
			task_id = rs.getInt(1);
	      }
	      catch (SQLException sqe)
	      {
			dumpSQLException(sqe);
			// Probably an inexistent task was request. We may ignore the
			// Now do something with the ResultSet ....
	      }
	      finally
	      {
			// Release resources.
			if (stmt != null)
			{
				  try
				  {
					    stmt.close();
				  }
				  catch (SQLException sqlEx)
				  {
				  }
			}
	      }
	      
	      return task_id;
    }

}