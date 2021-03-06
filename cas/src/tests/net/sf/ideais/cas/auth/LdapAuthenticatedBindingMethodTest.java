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

import net.sf.ideais.cas.auth.LdapAuthenticatedBinding;
import net.sf.ideais.cas.auth.LdapBindingMethod;

import org.junit.Before;
import org.junit.Test;

public class LdapAuthenticatedBindingMethodTest
{
	private LdapBindingMethod binding;
	
	@Before
	public void setUp()
	{
		binding = new LdapAuthenticatedBinding();
		binding.setBaseDN(LdapConstants.rootContext);
		binding.setServerName(LdapConstants.serverName);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testBindAnonymousBinding()
	{
		binding.bind(null, null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testBindNullUsername()
	{
		binding.bind(null, LdapConstants.password);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testBindNullPassword()
	{
		binding.bind(LdapConstants.fullLdapUsername, null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testBindBadUsername()
	{
		binding.bind(LdapConstants.badUsername, LdapConstants.password);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testBindBadPassword()
	{
		binding.bind(LdapConstants.fullLdapUsername, LdapConstants.badPassword);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testBindBadUsernameAndPassword()
	{
		binding.bind(LdapConstants.badUsername, LdapConstants.badPassword);
	}

	public void testBindOk()
	{
		binding.bind(LdapConstants.fullLdapUsername, LdapConstants.password);
	}	
}
