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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.ideais.apps.Application;
import net.sf.ideais.apps.WebApplication;
import net.sf.ideais.util.SqlUtil;
import net.sf.ideais.util.conf.Configuration;
import net.sf.ideais.util.conf.ConfigurationMap;
import net.sf.ideais.util.patterns.DAO;
import net.sf.ideais.util.patterns.DataSourceFactory;
import net.sf.ideais.util.patterns.DbDataSource;

/**
 * Represent a DotProject application instance.
 */
public class DotProject implements WebApplication
{
	private ConfigurationMap conf;

	private DotProjectVersion version; 

	public DotProject(Configuration conf)
	{
	    setConfiguration(conf);
	}

	private void setConfiguration(Configuration conf)
	{
		ConfigurationMap tmpConfig = null;
		
		if (! (conf instanceof ConfigurationMap)) {
			throw new IllegalArgumentException("Invalid configuration");
		}
		tmpConfig = (ConfigurationMap) conf;

		if (tmpConfig.getProperty("address") == null) {
			throw new IllegalArgumentException("Missing Web application address");
		}
		this.conf = tmpConfig;
	}
	
	public Configuration getConfiguration()
	{
		return conf;
	}
	
	/**
	 * Get the version of the application.
	 * 
	 * @return Application's version.
	 */
	public DotProjectVersion getVersion()
	{
		String tblName = "dpversion";
		String query = "SELECT code_version,db_version FROM " + tblName + " WHERE last_code_update=(SELECT MAX(last_code_update) FROM " + tblName + ")";
		DbDataSource ds = (DbDataSource) DataSourceFactory.manufacture(DbDataSource.class.getName(), conf);
		Connection conn = ds.getConnection();
		Statement stmt = null;
		DotProjectVersion dpv = null;
		
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);	
			Integer dbVersion = rs.getInt("db_version");
			String codeVersion = rs.getString("code_version");
			dpv = new DotProjectVersion(codeVersion, dbVersion);
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
		
		return dpv;
	}
	
	public String getId()
	{
		return (String) conf.getProperty("address");
	}
	
	public boolean isCompatible(Application app)
	{
		if (! (app instanceof DotProject)) {
			throw new IllegalArgumentException();
		}
		
		DotProject dpApp = (DotProject) app;
		DotProjectVersion dpVersion = (DotProjectVersion) dpApp.getVersion();
		if (version.getDatabaseVersion() == dpVersion.getDatabaseVersion()) {
			return true;
		}
		
		return false;
	}
	
	public DAO getDAO(Class<? extends DotProjectObject> clazz)
	{
		String daoName = clazz.getName() + "DAO";
		Class daoClass = null;
		DAO dao = null;

		try {
			daoClass = Class.forName(daoName);
		} catch (ClassNotFoundException e1) {
			throw new IllegalArgumentException();
		}

		
		try {
			Constructor<DAO> constructor = daoClass.getConstructor(Configuration.class);
			dao = constructor.newInstance(conf);
		} catch (NoSuchMethodException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		
		return dao;
	}
}
