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

import javax.naming.AuthenticationException;

/**
 * Authentication method based upon LDAP. It bindS to a LDAP server anonymously
 * and try to find a fully valid LDAP username based upon a simpler username
 * (like, for a user named 'test', it's fully valid LDAP entry would be
 * 'cn=Teste da Silva,ou=People,dc=m242,dc=numa,dc=intranet').
 */
public class LdapAuthIndirectMethod implements AuthMethod
{
	private LdapBindingMethod ldapBind;
	
	/**
	 * The attribute we use to look for a fully valid LDAP username.
	 */
	private static final String DEFAULT_USER_FILTER = "uid";

	/**
	 * Setup the authentication method to a LDAP server.
	 * 
	 * @param serverName The server name or IP address.
	 * @param baseDN The LDAP's base directory name.
	 */
	public LdapAuthIndirectMethod(String serverName, String baseDN)
	{
		this(serverName, LdapBindingMethod.DEFAULT_SERVER_PORT, baseDN, DEFAULT_USER_FILTER);
	}
	
	/**
	 * Setup the authentication method to a LDAP server.
	 * 
	 * @param serverName The server name or IP address.
	 * @param baseDN The LDAP's base directory name.
	 * @param userFilter The attribute to use when looking for a fully valid
	 * LDAP entry.
	 */
	public LdapAuthIndirectMethod(String serverName, String baseDN, String userFilter)
	{
		this(serverName, LdapBindingMethod.DEFAULT_SERVER_PORT, baseDN, userFilter);
	}
	
	/**
	 * Setup the authentication method to a LDAP server.
	 * 
	 * @param serverName The server name or IP address.
	 * @param baseDN The LDAP's base directory name.
	 * @param serverPort the server's port.
	 * @param userFilter The attribute to use when looking for a fully valid
	 * LDAP entry.
	 */
	public LdapAuthIndirectMethod(String serverName, int serverPort, String baseDN, String userFilter)
	{
		ldapBind = new LdapAnonymousBinding();
		ldapBind.setServerName(serverName);
		ldapBind.setServerPort(serverPort);
		ldapBind.setBaseDN(baseDN);
		ldapBind.setUserFilter(userFilter);
	}

	/**
	 * Login to a LDAP server.
	 * 
	 * @param username The username.
	 * @param password The password.
	 * 
	 * @throws IllegalArgumentException If any issue was found when trying to
	 * connect to the LDAP server (not authentication related issues).
	 * 
	 * @return True if the username and password were correct, False otherwise.
	 */
	public boolean login(String username, String password)
	{
		try {
			ldapBind.bind(username, password);
			return true;
		} catch (IllegalArgumentException e) {
			Throwable cause = e.getCause();
			if (cause != null && cause.getClass().equals(AuthenticationException.class)) {
				return false;
			}
			throw e;
		}
	}
}