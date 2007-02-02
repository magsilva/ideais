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

package tests.net.sf.ideais.dotproject;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import net.sf.ideais.Configuration;
import net.sf.ideais.DbDataSource;
import net.sf.ideais.HardCodedConfiguration;
import net.sf.ideais.apps.dotproject.Project;
import net.sf.ideais.apps.dotproject.ProjectDAO;
import tests.net.sf.ideais.dotproject.DotprojectTest;

public class ProjectDAOTest
{
	private ProjectDAO remoteDao;
	private ProjectDAO localDao;
	
	private String remoteProjectDescription = "World domination";
	private String localProjectDescription = "Sausage";
	
	private Long id = 1L;

	private class DummyLocalProjectDAO extends ProjectDAO
	{
		private static final long serialVersionUID = 1L;

		public DummyLocalProjectDAO()
		{
			super();
		}
		
	    protected Configuration getConfiguration()
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

	private class DummyRemoteProjectDAO extends ProjectDAO
	{
		private static final long serialVersionUID = 1L;

		public DummyRemoteProjectDAO()
		{
			super();
		}
		
	    protected Configuration getConfiguration()
	    {
			HardCodedConfiguration conf = new HardCodedConfiguration();
			conf.setProperty(DbDataSource.DBMS, DotprojectTest.dbms);
			conf.setProperty(DbDataSource.HOSTNAME, DotprojectTest.remoteHostname);
			conf.setProperty(DbDataSource.DATABASE, DotprojectTest.remoteDatabase);
			conf.setProperty(DbDataSource.USERNAME, DotprojectTest.remoteUsername);
			conf.setProperty(DbDataSource.PASSWORD, DotprojectTest.remotePassword);
			return conf;
	    }
	}

	
	@Before
	public void setUp()
	{
		remoteDao = new DummyRemoteProjectDAO();
		localDao = new DummyLocalProjectDAO();
	}

	@Test
	public void testLoadProject()
	{
		Project project = remoteDao.find(id);
		assertNotNull(project);
	}
	
	@Test
	public void testLoadProjectFromRemoteDatabase1()
	{
		Project project = remoteDao.find(id);
		assertEquals(project.getName(), remoteProjectDescription);
	}

	@Test
	public void testLoadProjectFromLocalDatabase1()
	{
		Project project = localDao.find(id);
		assertEquals(project.getName(), localProjectDescription);
	}
}
