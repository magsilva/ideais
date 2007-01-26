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
 
Copyright (C) 2007 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
*/

package net.sf.ideais.dotproject;

import net.sf.ideais.Configuration;
import net.sf.ideais.DbDAO;
import net.sf.ideais.DbDataSource;
import net.sf.ideais.HardCodedConfiguration;
import net.sf.ideais.util.SqlUtil;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object for a Project available at a DotProject instance.
 * 
 */
public class ProjectDAO extends DbDAO
{
	private	boolean sqlStatementHackEnabled = true;

	public ProjectDAO()
	{
		super();
	}
	
    protected Configuration getConfiguration()
    {
    	String knownDbms = "mysql";
		String knownHostname = "localhost";
		String knownDatabase = "dotproject-dev";
		String knownUsername = "test";
		String knownPassword = "test";

		HardCodedConfiguration conf = new HardCodedConfiguration();
		conf.setProperty(DbDataSource.DBMS, knownDbms);
		conf.setProperty(DbDataSource.HOSTNAME, knownHostname);
		conf.setProperty(DbDataSource.DATABASE, knownDatabase);
		conf.setProperty(DbDataSource.USERNAME, knownUsername);
		conf.setProperty(DbDataSource.PASSWORD, knownPassword);
		
		return conf;
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
        	SqlUtil.dumpSQLException(e);
            // Probably an inexistent task was requested.
            project = null;
        }
        return project;
    }
    
    

	public boolean isSqlStatementHackEnabled()
	{
		return sqlStatementHackEnabled;
	}

	public void setSqlStatementHackEnabled(boolean sqlStatementHackEnabled)
	{
		this.sqlStatementHackEnabled = sqlStatementHackEnabled;
	}

	public Object create()
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int id = 0;

		try {
			String query = "INSERT INTO projects (project_name, project_description, project_owner, project_company) values (?,?,?,?)";
			stmt = conn.prepareStatement(query);
			// Set mandatory attributes
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);
		} catch (SQLException sqe) {
			SqlUtil.dumpSQLException(sqe);
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

		return find(id);
	}

	public void delete(Object object)
	{
		Project project = (Project) object;
		deleteById(project.getId());
	}

	public void deleteById(Object id)
	{
    	int project_id = (Integer) id;
    	Statement stmt = null;
    	try {
    		if (sqlStatementHackEnabled) {
    			stmt = conn.createStatement();
    			String rawQuery = "DELETE FROM projects WHERE project_id=%1$s";
    			String query = String.format(rawQuery, project_id);
    			stmt.executeUpdate(query);
    		} else {
    			PreparedStatement pstmt = null;
    			String query = "DELETE FROM projects WHERE project_id = ?";
    			stmt = conn.prepareStatement(query);
    			pstmt = (PreparedStatement)stmt;
    			pstmt.setInt(1, project_id);
    			pstmt.executeUpdate();
    		}
    	} catch (SQLException sqe) {
    		SqlUtil.dumpSQLException(sqe);
    		// Probably an inexistent task was requested.
    	} finally {
    		// Release resources.
    		if (stmt != null) {
    			try {
    				stmt.close();
    			} catch (SQLException sqlEx) {
    			}
    		}
    	}
    }   

    /**
     * Load the data for a task and create a task instance.
     *
     * @param id The project id.
     * @return The project (if found) or null.
     */
    public Project find(Object id)
    {
    	int project_id = (Integer) id;
    	Statement stmt = null;
    	ResultSet rs = null;
    	Project project = null;
    	try {
    		if (sqlStatementHackEnabled) {
    			stmt = conn.createStatement();
    			String rawQuery = "SELECT * FROM projects WHERE project_id=%1$s";
    			String query = String.format(rawQuery, project_id);
    			rs = stmt.executeQuery(query);
    		} else {
    			PreparedStatement pstmt = null;
    			String query = "SELECT * FROM projects WHERE project_id = ?";
    			stmt = conn.prepareStatement(query);
    			pstmt = (PreparedStatement)stmt;
    			pstmt.setInt(1, project_id);
    			rs = pstmt.executeQuery();
    		}   		
    		rs.next();
    		project = createInstance(rs);
    	} catch (SQLException sqe) {
    		SqlUtil.dumpSQLException(sqe);
    		// Probably an inexistent task was requested.
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
    
	public List findByExample(Object object)
	{
		Project project = (Project) object;
    	Statement stmt = null;
    	ResultSet rs = null;
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put(Project.DESCRIPTION, project.getDescription());
    	

    	try {
    		if (sqlStatementHackEnabled) {
    			stmt = conn.createStatement();
    			String rawQuery = "SELECT * FROM projects WHERE project_id=%1$s";
    			String query = String.format(rawQuery, project_id);
    			rs = stmt.executeQuery(query);
    		} else {
    			PreparedStatement pstmt = null;
    			String query = "SELECT * FROM projects WHERE project_id = ?";
    			stmt = conn.prepareStatement(query);
    			pstmt = (PreparedStatement)stmt;
    			pstmt.setInt(1, project_id);
    			rs = pstmt.executeQuery();
    		}   		
    		rs.next();
    		project = createInstance(rs);
    	} catch (SQLException sqe) {
    		SqlUtil.dumpSQLException(sqe);
    		// Probably an inexistent task was requested.
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

		// TODO Auto-generated method stub
		return null;
	}

	public List findByExample(Map fields)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void update(Object object)
	{
		Project project = (Project)object;
		// assume that conn is an already created JDBC connection
		PreparedStatement stmt = null;

		try {
			String query = "UPDATE projects SET (project_name=?, project_description=?, project_owner=?, project_company=?) WHERE project_id=?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, project.getName());
			stmt.setString(2, project.getDescription());
			stmt.setInt(3, project.getOwnerId());
			stmt.setInt(4, project.getCompanyId());
			stmt.setInt(5, project.getId());
			stmt.executeUpdate();
		} catch (SQLException sqe) {
			SqlUtil.dumpSQLException(sqe);
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
	}

}