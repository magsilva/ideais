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

package net.sf.ideais.cas;

import net.sf.ideais.cas.auth.AuthMethod;
import net.sf.ideais.cas.auth.LdapAuthDirectMethod;
import net.sf.ideais.cas.auth.LdapAuthIndirectMethod;

public class TestLDAP
{
	final static String serverName = "localhost";
	final static String rootContext = "dc=m242,dc=numa,dc=intranet";
	final static String rootdn = "cn=admin,dc=m242,dc=numa,dc=intranet";
	final static String rootpass = "admin";

	final static String username = "cn=Teste da Silva,ou=People,dc=m242,dc=numa,dc=intranet";
	final static String userpassword = "test";

	public static void main(String[] args)
	{
		AuthMethod auth = null;
		
		// auth = new LdapAuth(serverName, rootContext);
		// auth.login(username, userpassword);
		
		auth = new LdapAuthIndirectMethod(serverName, rootContext);
		auth.login(username, userpassword);
	}
}
