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

import net.sf.ideais.repository.RepositoryTransaction;
import net.sf.ideais.repository.SubversionRepository;
import net.sf.ideais.repository.SubversionRepositoryTransaction;


/**
 * Test cases for Subversion.
 */
public class SubversionHTTPSRepositoryTransactionTest extends AbstractRepositoryTransactionTest
{
	/**
	 * Initialize a SubversionRepositoryTransaction and set the version1 and
	 * version2 to reasonable values.
	 * 
	 * @return The repository transaction that will be tested.
	 */
	protected RepositoryTransaction getRepositoryTransaction()
	{
		SubversionRepository repository = new SubversionRepository();
		repository.setLocation(SubversionRepositoryConstants.HTTPS_REPOSITORY);
		RepositoryTransaction rtx = new SubversionRepositoryTransaction(repository);
		version1 = SubversionRepositoryConstants.DEFAULT_V1;
		version2 = SubversionRepositoryConstants.DEFAULT_V2;
		return rtx;
	}
}