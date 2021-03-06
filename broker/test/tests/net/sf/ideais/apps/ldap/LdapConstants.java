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

package tests.net.sf.ideais.apps.ldap;

public interface LdapConstants
{
	final static String serverName = "localhost";
	final static String badServerName = "fafdasdfa";

	final static String rootContext = "dc=m242,dc=numa,dc=intranet";
	final static String badRootContext = "dc=fdafas,dc=numa,dc=intranet";
	
	final static String fullLdapUsername = "cn=Teste da Silva,ou=People,dc=m242,dc=numa,dc=intranet";
	final static String username = "test";
	final static String badUsername = "adsf";
	
	final static String password = "test";
	final static String badPassword = "lfkaj";
}
