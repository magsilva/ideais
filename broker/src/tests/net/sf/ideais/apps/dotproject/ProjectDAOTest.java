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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.sf.ideais.apps.dotproject.Project;
import net.sf.ideais.apps.dotproject.ProjectDAO;
import net.sf.ideais.conf.Configuration;

public class ProjectDAOTest
{
	private ProjectDAO remoteDao;
	private ProjectDAO localDao;
	
	private Project dummyProject;
	
	private String remoteProjectName = "World domination";
	private String localProjectName = "Disintegrating Pistol";
	
	private Integer id = 1;

	public class DummyLocalProjectDAO extends ProjectDAO
	{
		private static final long serialVersionUID = 1L;

		public DummyLocalProjectDAO()
		{
			super();
		}
		
		protected Configuration getConfiguration()
	    {
	    	return DotprojectTest.getLocalConfiguration();
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
	    	return DotprojectTest.getRemoteConfiguration();
	    }
	}

	
	@Before
	public void setUp()
	{
		localDao = new DummyLocalProjectDAO();
		remoteDao = new DummyRemoteProjectDAO();
		
		dummyProject = new Project();
		// dummyProject.s
	}

	@Test
	public void testLoadProjectFromRemoteDatabase1()
	{
		Project project = remoteDao.find(id);
		assertEquals(project.getName(), remoteProjectName);
	}

	@Test
	public void testLoadProjectFromLocalDatabase1()
	{
		Project project = localDao.find(id);
		assertEquals(project.getName(), localProjectName);
	}
	
	@Test
	public void testCreateProjectAtLocalDatabase1()
	{
		Project project = localDao.create();
		assertNotNull(project);
	}


}
