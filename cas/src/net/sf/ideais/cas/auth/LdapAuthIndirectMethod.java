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

package net.sf.ideais.cas.auth;

public class LdapAuthIndirectMethod implements AuthMethod
{
	private LdapBindingMethod ldapBind;
	
	private static final String DEFAULT_USER_FILTER = "uid";

	public LdapAuthIndirectMethod(String serverName, String baseDN)
	{
		this(serverName, LdapBindingMethod.DEFAULT_SERVER_PORT, baseDN, DEFAULT_USER_FILTER);
	}
	
	public LdapAuthIndirectMethod(String serverName, String baseDN, String userFilter)
	{
		this(serverName, LdapBindingMethod.DEFAULT_SERVER_PORT, baseDN, userFilter);
	}
	
	public LdapAuthIndirectMethod(String serverName, int serverPort, String baseDN, String userFilter)
	{
		ldapBind = new LdapAnonymousBinding();
		ldapBind.setServerName(serverName);
		ldapBind.setServerPort(serverPort);
		ldapBind.setBaseDN(baseDN);
		ldapBind.setUserFilter(userFilter);
	}

	public boolean login(String username, String password)
	{
		try {
			ldapBind.bind(username, password);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

}
