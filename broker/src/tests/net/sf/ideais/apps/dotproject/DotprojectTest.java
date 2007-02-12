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

package tests.net.sf.ideais.apps.dotproject;

import net.sf.ideais.conf.Configuration;
import net.sf.ideais.conf.HardCodedConfiguration;
import net.sf.ideais.util.DbDataSource;

public class DotprojectTest
{
	static final public String dbms = "mysql";
	
	static final public String remoteHostname = "192.168.1.13";
	static final public String remoteDatabase = "dotproject-dev";
	static final public String remoteUsername = "test";
	static final public String remotePassword = "test";

	static final public String localHostname = "localhost";
	static final public String localDatabase = "dotproject-dev";
	static final public String localUsername = "test";
	static final public String localPassword = "test";

    static public Configuration getRemoteConfiguration()
    {
		HardCodedConfiguration conf = new HardCodedConfiguration();
		conf.setProperty(DbDataSource.DBMS, DotprojectTest.dbms);
		conf.setProperty(DbDataSource.HOSTNAME, DotprojectTest.remoteHostname);
		conf.setProperty(DbDataSource.DATABASE, DotprojectTest.remoteDatabase);
		conf.setProperty(DbDataSource.USERNAME, DotprojectTest.remoteUsername);
		conf.setProperty(DbDataSource.PASSWORD, DotprojectTest.remotePassword);
		return conf;
    }
    
    static public Configuration getLocalConfiguration()
    {
		HardCodedConfiguration conf = new HardCodedConfiguration();
		conf.setProperty(DbDataSource.DBMS, DotprojectTest.dbms);
		conf.setProperty(DbDataSource.HOSTNAME, DotprojectTest.localHostname);
		conf.setProperty(DbDataSource.DATABASE, DotprojectTest.localDatabase);
		conf.setProperty(DbDataSource.USERNAME, DotprojectTest.localUsername);
		conf.setProperty(DbDataSource.PASSWORD, DotprojectTest.localPassword);
		return conf;
    }
}
