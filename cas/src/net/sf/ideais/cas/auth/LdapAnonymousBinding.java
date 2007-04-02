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
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchResult;


public class LdapAnonymousBinding extends LdapAuthenticatedBinding
{
	private String usernameFormat = "cn=%s,ou=People,%s";
	
	private String userFilter = "uid";
	
	@Override
	protected void setAuthenticationMethod()
	{
		env.put(Context.SECURITY_AUTHENTICATION, "none");
	}

	@Override
	protected void setBindSecurityParameters(String username, String password)
	{
	}
	
	public LdapAnonymousBinding()
	{
		super();
	}
	
	public void bind(String username, String password)
	{
		env.put(Context.PROVIDER_URL, "ldap://" + serverName + "/" + baseDN);
		
		
		try {
			ctx = new InitialDirContext(env);
			
			Attributes matchAttrs = new BasicAttributes(true);
			matchAttrs.put(new BasicAttribute(userFilter, username));
			NamingEnumeration answer = ctx.search("ou=People", matchAttrs);
			if (! answer.hasMore()) {
				throw new AuthenticationException();
			}
			SearchResult srs = (SearchResult) answer.next();
			Attributes attrs = srs.getAttributes();
			Attribute uid = attrs.get(userFilter);
			if (username.equals(uid.get())) {
				String username2 = String.format(usernameFormat, (String) uid.get(), baseDN);
				Properties p = (Properties) env.clone();
				p.put(Context.SECURITY_PRINCIPAL, username2);
				p.put(Context.SECURITY_CREDENTIALS, password);
				InitialDirContext ctx = new InitialDirContext(env);
			}
		} catch (NameAlreadyBoundException nabe) {
			System.err.println("value has already been bound!");
		} catch (NamingException e) {
			System.err.println(e);
		}
	}
	
	public void setUserFilter(String userFilter)
	{
		// TODO Auto-generated method stub	
	}

	
}
