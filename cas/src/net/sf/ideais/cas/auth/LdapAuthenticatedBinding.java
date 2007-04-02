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

import java.util.Properties;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * Class to bind to LDAP server using authenticated binding.
 */
public class LdapAuthenticatedBinding implements LdapBindingMethod
{
	/**
	 * Default LDAP to JNDI adapter class.
	 */
	public static final String ctxFactory = "com.sun.jndi.ldap.LdapCtxFactory";
	
	/**
	 * Server name (or IP) to connect to.
	 */
	protected String serverName;
	
	/**
	 * Port to use to connect to the LDAP server..
	 */
	protected int serverPort = DEFAULT_SERVER_PORT;

	/**
	 * Root directory to access at the LDAP server.
	 */
	protected String baseDN;

	/**
	 * The naming context (JNDI) that represents the LDAP server.
	 */
	protected DirContext ctx;
	
	/**
	 * Environment variables used to connect to the LDAP server.
	 */
	protected Properties env;

	/**
	 * Should connect to the LDAP server using a secure (TLS) connection?
	 */
	protected boolean useSecureConnection;
	
	/**
	 * Set up the authentication method to be used when connecting to the LDAP
	 * server.
	 */
	protected void setAuthenticationMethod()
	{
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
	}

	/**
	 * Set up the security parameters (usually the user's credentials).
	 * 
	 * @param username The username (Context.SECURITY_PRINCIPAL).
	 * @param password The password (Context.SECURITY_CREDENTIALS).
	 */
	protected void setBindSecurityParameters(String username, String password)
	{
		env.put(Context.SECURITY_PRINCIPAL, username);
		env.put(Context.SECURITY_CREDENTIALS, password);
	}
	
	/**
	 * Setup the initial directory context and basic connection settings.
	 */
	public LdapAuthenticatedBinding()
	{
		env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, ctxFactory);
		setAuthenticationMethod();
	}

	/**
	 * Connect to the LDAP server.
	 * 
	 * @throws IllegalArgumentException If the 'baseDN' hasn't been set.
	 */
	public void bind(String username, String password)
	{
		if (baseDN == null) {
			throw new IllegalArgumentException("You haven't set a base directory name");
		}
		
		if (username == null || password == null) {
			throw new IllegalArgumentException("You cannot authenticate with the user or password set as NULL. You must use the LdapAnonymousBind to bind anonymously.");
		}
	
		String ldapURL = null;
		setBindSecurityParameters(username, password);
		if (useSecureConnection) {
			ldapURL = DEFAULT_SECURE_PROTOCOL + serverName + "/" + baseDN;
		} else {
			ldapURL = DEFAULT_PROTOCOL + serverName + "/" + baseDN;
		}
		env.put(Context.PROVIDER_URL, ldapURL);

		try {
			ctx = new InitialDirContext(env);
		} catch (AuthenticationException e) {
			throw new IllegalArgumentException("Could not establish a connection to the LDAP server (wrong username or password)", e);
		} catch (NamingException e) {
			throw new IllegalArgumentException("Could not establish a connection to the LDAP server (invalid base directory name)", e);
		}
	}

	/**
	 * Set the base LDAP directory name.
	 * 
	 * @param baseDN LDAP directory name.
	 */
	public void setBaseDN(String baseDN)
	{
		this.baseDN = baseDN;
	}

	/**
	 * Set the user filter (e.g., "uid"). This class does not require neither uses
	 * the userFilter.
	 */
	public void setUserFilter(String userFilter)
	{
	}

	/**
	 * The name of the server this class will try to connect to.
	 * 
	 * @param serverName The server name or IP address.
	 */
	public void setServerName(String serverName)
	{
		this.serverName = serverName;
	}

	/**
	 * Set the port of the LDAP server this class will try to connect to.
	 * 
	 * @param serverPort Server port to connect to (usually one of these:
	 * DEFAULT_SERVER_PORT or DEFAULT_SECURE_SERVER_PORT). 
	 */
	public void setServerPort(int serverPort)
	{
		this.serverPort = serverPort;
	}

	/**
	 * Enable the utilization of secure connection.
	 * 
	 * @param useSecureConnection True if a secure connection must be used, False
	 * otherwise.
	 */
	public void setUseSecureConnection(boolean useSecureConnection)
	{
		this.useSecureConnection = useSecureConnection;
	}
}
