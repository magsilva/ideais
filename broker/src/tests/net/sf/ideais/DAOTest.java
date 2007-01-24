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

package tests.net.sf.ideais;

import static org.junit.Assert.*;
import org.junit.Test;

import java.sql.Connection;

import net.sf.ideais.DAO;


public class DAOTest
{
	class DummyDAO extends DAO {
		 public DummyDAO(String dbms, String hostname, String database, String username, String password)
		 {
			 super(dbms, hostname, database, username, password);
		 }
		 
		 public Connection getConnection()
		 {
			 return conn;
		 }
	}
	
	final private String unknownSGBD = "abc123";
	final private String knownSGBD = "mysql";
	final private String knownDriver = "com.mysql.jdbc.Driver";
	final private String knownConnectionString = "jdbc:mysql://%1$s/%2$s?user=%3$s&password=%4$s";
	
	final private String knownHostname = "192.168.1.13";
	final private String knownDatabase = "dotproject-dev";
	final private String knownUsername = "test";
	final private String knownPassword = "test";
	
	@Test
	public void testGetDriverName()
	{
		assertEquals(DAO.getDriverName(knownSGBD), knownDriver);
	}

	@Test(expected=RuntimeException.class) 
	public void testGetUnknownDriverName()
	{
		DAO.getDriverName(unknownSGBD);
	}

	
	@Test
	public void testGetConnectionString() {
		assertEquals(DAO.getConnectionString(knownSGBD), knownConnectionString);
	}

	@Test(expected=RuntimeException.class) 
	public void testGetConnectionStringForUnknownDriver()
	{
		DAO.getConnectionString(unknownSGBD);
	}
	
	@Test
	public void testDAOInitialization()
	{
		DAO dao = new DAO(knownSGBD, knownHostname, knownDatabase, knownUsername, knownPassword);
		assertNotNull(dao);
	}
	
	@Test
	public void testDAOConnection()
	{
		DummyDAO dao = new DummyDAO(knownSGBD, knownHostname, knownDatabase, knownUsername, knownPassword);
		assertNotNull(dao.getConnection());
	}
}
