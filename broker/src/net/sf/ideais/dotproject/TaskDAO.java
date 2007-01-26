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

Copyright (C) 2006 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
*/

package net.sf.ideais.dotproject;

import net.sf.ideais.DbDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Transfer Object for a task available at a DotProject instance.
 * 
 */
public class TaskDAO extends DbDAO
{        
    /**
    * Creates a new instance of TaskDAO
    */
    public TaskDAO(String dbms, String hostname, String database, String username, String password)
    {
    	super(dbms, hostname, database, username, password);
    }
       
    /**
     * Create a task instance.
     *
     * @param rs The ResultSet with the data loaded from the database.
     * @return The Task object if there is enough data to create a task instance,
     * null otherwise.
     */
    private Task createTaskInstance(ResultSet rs)
    {
        Task task = new Task();
        
        try {
            task.setName(rs.getString("task_name"));
        } catch (SQLException sqe) {
            // Probably an inexistent task was requested.
            task = null;
        }
        return task;
    }
    
    /**
     * Load the data for a task and create a task instance.
     *
     * @param task_id The task_id.
     * @return The task (if found) or null.
     */
    public Task loadData(int task_id)
    {
       // assume that conn is an already created JDBC connection
       PreparedStatement stmt = null;
       ResultSet rs = null;
       Task task = null;
       
       try {
           String query = "SELECT * FROM tasks WHERE task_id = ?";
           stmt = this.conn.prepareStatement(query);
           stmt.setInt(1, task_id);
           rs = stmt.executeQuery();
           task = createTaskInstance(rs);
       } catch (SQLException sqe) {
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
 
       return task;
    }    
}