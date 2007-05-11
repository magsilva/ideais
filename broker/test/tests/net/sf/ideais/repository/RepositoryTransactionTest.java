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

import java.io.File;

import net.sf.ideais.repository.DummyRepositoryTransaction;
import net.sf.ideais.repository.RepositoryTransaction;
import net.sf.ideais.repository.SubversionRepository;
import net.sf.ideais.repository.TransactionIdFactory;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class RepositoryTransactionTest
{
	private RepositoryTransaction rtx;
	private SubversionRepository repository;
	
	@Before
	protected void setUp() throws Exception
	{
		repository = new SubversionRepository();
		repository.setLocation( "http://www.magsilva.dynalias.net/svn/test" );
		rtx = new DummyRepositoryTransaction(repository);
	}
	
	@After
	protected void tearDown()
	{
		rtx.abort();
	}
	
	@Test
	public void testDummy()
	{
		assertNotNull( rtx );
	}
	
	@Test
	public void testGetWorkDir()
	{
		File dir = new File( rtx.getWorkdir() );
		assertTrue( dir.exists() );
		assertTrue( dir.isDirectory() ); 
	}

	@Test
	public void testGetId()
	{
		assertTrue( rtx.getId() > 0 );
		assertTrue( rtx.getId() < TransactionIdFactory.nextId() );
	}
}