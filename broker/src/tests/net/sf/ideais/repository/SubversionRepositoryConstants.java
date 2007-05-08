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

Copyright (C) 2005 Marco Aurélio Graciotto Silva <magsilva@gmail.com>
*/

package tests.net.sf.ideais.repository;

public interface SubversionRepositoryConstants
{
	String svnHTTPRepository = "http://www.magsilva.dynalias.net/svn/test";
	String svnHTTPSRepository = "https://www.magsilva.dynalias.net/svn/test";
	String svnLocalRepository = "file:///workhorse/SVN/test";
	String svnSVNRepository = "svn://www.magsilva.dynalias.net/svn/test";
	String svnSVNSSHRepository = "svn+ssh://www.magsilva.dynalias.net/svn/test";
	
	int THREAD_COUNT = 10000;
	
	int TRANSACTION_COUNT = 10;
	
	String DEFAULT_USERNAME = "wikire";
	String DEFAULT_PASSWORD = "wikire";
}
