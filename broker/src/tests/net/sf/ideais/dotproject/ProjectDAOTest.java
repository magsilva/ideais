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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.sf.ideais.dotproject.Project;
import net.sf.ideais.dotproject.ProjectDAO;
import tests.net.sf.ideais.dotproject.DotprojectTest;

public class ProjectDAOTest
{
	private ProjectDAO remoteDao;
	private ProjectDAO localDao;
	
	private String remoteProjectDescription = "World domination";
	private String localProjectDescription = "Sausage";

	@Before
	public void setUp()
	{
		remoteDao = new ProjectDAO(DotprojectTest.sgbd, DotprojectTest.remoteHostname,
				DotprojectTest.remoteDatabase, DotprojectTest.remoteUsername, DotprojectTest.remotePassword);

		localDao = new ProjectDAO(DotprojectTest.sgbd, DotprojectTest.localHostname,
			DotprojectTest.localDatabase, DotprojectTest.localUsername, DotprojectTest.localPassword);

	}

	@After
	public void tearDown()
	{
		remoteDao.finalize();
	}

	@Test
	public void testLoadProject()
	{
		Project project = remoteDao.loadData(1);
		assertNotNull(project);
	}
	
	@Test
	public void testLoadProjectFromRemoteDatabase1()
	{
		Project project = remoteDao.loadData(1);
		assertEquals(project.getName(), remoteProjectDescription);
	}

	@Test
	public void testLoadProjectFromRemoteDatabase2()
	{
		remoteDao.setSqlStatementHackEnabled(true);
		Project project = remoteDao.loadData(1);
		assertEquals(project.getName(), remoteProjectDescription);
	}

	
	@Test
	public void testLoadProjectFromLocalDatabase1()
	{
		Project project = localDao.loadData(1);
		assertEquals(project.getName(), localProjectDescription);
	}

	@Test
	public void testLoadProjectFromLocalDatabase2()
	{
		localDao.setSqlStatementHackEnabled(true);
		Project project = localDao.loadData(1);
		assertEquals(project.getName(), localProjectDescription);
	}	
	
}
