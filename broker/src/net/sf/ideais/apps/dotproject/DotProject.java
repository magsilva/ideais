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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.ideais.apps.Application;
import net.sf.ideais.apps.Version;
import net.sf.ideais.util.SqlUtil;
import net.sf.ideais.util.VersionUtil;
import net.sf.ideais.util.conf.ConfigurationMap;
import net.sf.ideais.util.conf.HardCodedConfiguration;
import net.sf.ideais.util.patterns.DataSourceFactory;
import net.sf.ideais.util.patterns.DbDataSource;

public class DotProject implements Application
{
	private ConfigurationMap conf;

	private DotProjectVersion version; 

	private static ConfigurationMap getDefaultConfiguration()
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

	public DotProject()
	{
		conf = getDefaultConfiguration();
		version = discoverVersion();
	}
	
	public DotProjectVersion discoverVersion()
	{
		String tblName = "dpversion";
		String query = "SELECT max(db_version) as latest_db_version from " + tblName;
		DbDataSource ds = (DbDataSource) DataSourceFactory.manufacture(DbDataSource.class.getName(),
				conf);
		Connection conn = ds.getConnection();
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);	
			Integer version = rs.getInt("latest_db_version");
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
		
		return null;
	}
	
	public String getId()
	{
		return "";
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

	public Version getVersion()
	{
		return version;
	}
}
