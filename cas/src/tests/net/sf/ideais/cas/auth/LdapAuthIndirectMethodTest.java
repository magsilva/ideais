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

package tests.net.sf.ideais.cas.auth;

import static org.junit.Assert.*;

import net.sf.ideais.cas.auth.AuthMethod;
import net.sf.ideais.cas.auth.LdapAuthIndirectMethod;

import org.junit.Before;
import org.junit.Test;

public class LdapAuthIndirectMethodTest
{
	private AuthMethod auth;
	
	@Before
	public void setUp() throws Exception
	{
		auth = new LdapAuthIndirectMethod(LdapConstants.serverName, LdapConstants.rootContext);
	}

	@Test
	public void testLoginOk()
	{
		boolean result = false;
		result = auth.login(LdapConstants.username, LdapConstants.password);
		assertTrue(result);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testLoginNullUsernameAndPassword()
	{
		auth.login(null, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testLoginNullPassword()
	{
		auth.login(LdapConstants.username, null);
	}

	public void testLoginWrongPassword()
	{
		boolean result = false;
		result = auth.login(LdapConstants.username, LdapConstants.badPassword);
		assertFalse(result);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testLoginNullUsername()
	{
		auth.login(null, LdapConstants.password);
	}
	
	public void testLoginWrongUsername()
	{
		boolean result = false;
		result = auth.login(LdapConstants.badUsername, LdapConstants.password);
		assertFalse(result);
	}

	public void testLoginWrongUsernameAndPassword()
	{
		boolean result = false;
		result = auth.login(LdapConstants.badUsername, LdapConstants.badPassword);
		assertFalse(result);
	}
}
