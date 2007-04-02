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
 * Authentication method based upon LDAP. It tries to bind to a LDAP server
 * with an username and password (instead of anonymously binding).
 */
public class LdapAuthDirectMethod implements AuthMethod
{
	/**
	 * Binding method. We use the interface, but it's a LdapAuthenticatedBinding
	 * instance.
	 */
	private LdapBindingMethod ldapBind;

	/**
	 * Setup the authentication method to a LDAP server.
	 * 
	 * @param serverName The server name or IP address.
	 * @param baseDN The LDAP's base directory name.
	 */
	public LdapAuthDirectMethod(String serverName, String baseDN)
	{
		this(serverName, LdapBindingMethod.DEFAULT_SERVER_PORT, baseDN);
	}

	/**
	 * Setup the authentication method to a LDAP server.
	 * 
	 * @param serverName The server's name or IP address.
	 * @param serverPort the server's port.
	 * @param baseDN The LDAP's base directory name.
	 */
	public LdapAuthDirectMethod(String serverName, int serverPort, String baseDN)
	{
		ldapBind = new LdapAuthenticatedBinding();
		ldapBind.setServerName(serverName);
		ldapBind.setServerPort(serverPort);
		ldapBind.setBaseDN(baseDN);
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