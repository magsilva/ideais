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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ideais.util.JavaBeanUtil;
import net.sf.ideais.util.SqlUtil;
import net.sf.ideais.util.conf.Configuration;
import net.sf.ideais.util.patterns.DbDAO;

public abstract class DotProjectDAO<T> extends DbDAO<T, Integer>
{
	/**
	 * Serializable interface requirement.
	 */
	private static final long serialVersionUID = 1L;

	public DotProjectDAO(Configuration conf)
	{
		super(conf);
	}

	/**
	 * Create a Project instance.
	 *
	 * @param rs The ResultSet with the data loaded from the database.
	 * @return The Task object if there is enough data to create a task instance,
	 * null otherwise.
	 */
	@SuppressWarnings("unchecked")
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

	
	public T create()
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Integer id = null;
		Map<String, Object> map = JavaBeanUtil.mapBeanUsingFields(getObjectType());
		int count = map.size() / 2;
	
		try {
			String query = DotProjectUtil.createPstmtInsert(getObjectType());
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
	
		return findById(id);
	}

	public void delete(T object)
	{
		/*
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
		*/
	}


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
	public T findById(Integer id)
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		T obj = null;
		
		try {
			String query = DotProjectUtil.createPstmtSelectById(getObjectType());
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
		Map<String, Object> map = JavaBeanUtil.mapBeanUsingFields(getObjectType());
		Set<String> mapKeySet = map.keySet();
		String[] fields = mapKeySet.toArray(new String[mapKeySet.size()]);
		Arrays.sort(fields);
		PreparedStatement stmt = null;
	
		try {
			String query = DotProjectUtil.createPstmtUpdate(getObjectType());
			stmt = conn.prepareStatement(query);
			
			// Set statements
			for (int i = 0; i < fields.length; i++) {
				stmt.setObject(i, map.get(fields[i]));
			}
			
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
