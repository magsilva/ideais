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

/**
 * Interface for binding with a LDAP server.
 */
public interface LdapBindingMethod
{
	/**
	 * Default LDAP server TCP port.
	 */
	public static final int DEFAULT_SERVER_PORT = 389;
	
	/**
	 * Default secure (TLS) LDAP server TCP port. 
	 */
	public static final int DEFAULT_SECURE_SERVER_PORT = 636;
	
	/**
	 * The scheme for the protocol when connecting using a plain (unsecure)
	 * TCP connection.
	 */
	public static final String DEFAULT_PROTOCOL = "ldap://";
	
	/**
	 * The scheme for the protocol when connecting using a secure TCP
	 * connection.
	 */
	public static final String DEFAULT_SECURE_PROTOCOL = "ldaps://";
	
	void setBaseDN(String baseDN);
	
	void setServerName(String serverName);
	
	void setServerPort(int serverPort);
	
	void setUserFilter(String userFilter);
	
	void bind(String username, String password);
}
