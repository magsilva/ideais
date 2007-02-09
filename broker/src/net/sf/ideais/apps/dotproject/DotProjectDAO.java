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

package net.sf.ideais.apps.dotproject;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.ideais.Configuration;
import net.sf.ideais.DbDAO;
import net.sf.ideais.DbDataSource;
import net.sf.ideais.HardCodedConfiguration;
import net.sf.ideais.util.JavaBeanUtil;
import net.sf.ideais.util.SqlUtil;

public abstract class DotProjectDAO<T> extends DbDAO<T, Integer>
{
	/**
	 * Serializable interface requirement.
	 */
	private static final long serialVersionUID = 1L;
	

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
	protected T[] createInstances(Class<T> clazz, ResultSet rs)
	{
		ArrayList<T> objs = new ArrayList<T>();
	    Map<String, Method> map = JavaBeanUtil.mapBeanPropertiesToSetMethods(clazz);
	    T[] instances = null;

		try {
			// For each row, create a new object.
	    	while (rs.next()) {
	    		T obj = null;
	    		try {
	    			obj = clazz.newInstance();
	    		} catch (IllegalAccessException iae) {
	    		} catch (InstantiationException ie) {
	    		}
	    		
	    		// Feed the object the data from the ResultSet.
	    		for (String key : map.keySet()) {
					try {
						Object valueBO = rs.getObject(key);
						Method m = map.get(key);
						try {
							// Why are you invoking get? You should invoke set!
							m.invoke(obj, valueBO);
						} catch (IllegalAccessException iae) {
						} catch (InvocationTargetException ite) {
						}
					} catch (SQLException sqle) {
						// Just ignore.
					}
				}
				objs.add(obj);
	    	}
		} catch (SQLException e) {
			// Probably an inexistent object was requested.
			SqlUtil.dumpSQLException(e);
		}
		
		instances = (T[])objs.toArray();
	    return instances;
	}

	/**
	 * Create a Project instance.
	 *
	 * @param rs The ResultSet with the data loaded from the database.
	 * @return The Task object if there is enough data to create a task instance,
	 * null otherwise.
	 */
	protected T createInstance(Class<T> clazz, ResultSet rs)
	{
		T objs[] = createInstances(clazz, rs);
		if (objs == null) {
			return null;
		}
		
		return objs[0];
	}

	abstract protected T createInstance(ResultSet rs);

	
	public T create(DotProjectObject object)
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Integer id = null;
		Map<String, Object> map = JavaBeanUtil.mapBeanUsingFields(object);
		int count = map.size() / 2;
	
		try {
			String query = DotProjectUtil.createPstmtInsert(object);
			stmt = conn.prepareStatement(query);
			
			// Set statements
			for (int i = 1; i <= count; i++) {
				for (String key : map.keySet()) {
					stmt.setObject(i, key);
					stmt.setObject(i + count, map.get(key));
				}
			}
			
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

	/*
	public void deleteBySimilarity(DotProjectObject object)
	{
		PreparedStatement stmt = null;
		
		try {
			String query = SqlUtil.createPreparedStatementDeleteString(
					AnnotationUtil.getAnnotationValue(object, Table.class),
					1);
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, project_id);
			stmt.executeUpdate();
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
	*/

	// TODO
	/*
	public void deleteById(Int id)
	{
		PreparedStatement stmt = null;
		
		try {
			String query = DotProjectUtil.createPreparedStatementDeleteString(
					AnnotationUtil.getAnnotationValue(object, Table.class),
					1);
			stmt = conn.prepareStatement(query);
			stmt.setObject(1, )
			stmt.setInt(1, project_id);
			stmt.executeUpdate();
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
		*/


	public void deleteById(Integer id)
	{
/*
		PreparedStatement stmt = null;
		try {
			String query = DotProjectUtil.createPreparedStatementDeleteString(
					AnnotationUtil.getAnnotationValue(object, Table.class),
					table, parameterCount)"DELETE FROM projects WHERE project_id = ?";
			stmt = conn.prepareStatement(query);
			stmt = (PreparedStatement)stmt;
			stmt.setInt(1, project_id);
			stmt.executeUpdate();
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
		*/
	}

	abstract protected Class<? extends DotProjectObject> getObjectType();
	
	/**
	 * Load the data for a task and create a task instance.
	 *
	 * @param id The project id.
	 * @return The project (if found) or null.
	 */
	public T find(Integer id)
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		T obj = null;
		
		try {
			String query = DotProjectUtil.createPstmtSelectId(getObjectType());
			stmt = conn.prepareStatement(query);
			stmt.setObject(1, id);
			rs = stmt.executeQuery();
			obj = createInstance(rs);
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
		
		return obj;
	}

	public List<T> findByExample(T object)
	{
		/*
		PreparedStatement stmt = null;
		ResultSet rs = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		// map.put(Project.DESCRIPTION, project.getDescription());
		
	
		try {
			String query = "SELECT * FROM projects WHERE project_id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, project.getId());
			rs = stmt.executeQuery();
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
		*/
		
		return null;
	}

	public List<T> findByExample(Map<String, Serializable> fields)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void update(T object)
	{
		// assume that conn is an already created JDBC connection
		PreparedStatement stmt = null;
	
		try {
			String query = "UPDATE projects SET (project_name=?, project_description=?, project_owner=?, project_company=?) WHERE project_id=?";
			stmt = conn.prepareStatement(query);
			/*
			stmt.setString(1, object.getName());
			stmt.setString(2, object.getDescription());
			stmt.setInt(3, object.getOwnerId());
			stmt.setInt(4, object.getCompanyId());
			stmt.setInt(5, object.getId());
			*/
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
