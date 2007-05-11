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


import java.util.List;
import java.util.ArrayList;

import net.sf.ideais.repository.RepositoryTransaction;
import net.sf.ideais.repository.RepositoryTransactionError;
import net.sf.ideais.repository.RepositoryTransactionFactory;
import net.sf.ideais.repository.SubversionRepository;
import net.sf.ideais.repository.TransactionIdFactory;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class RepositoryTransactionFactoryTest
{
	SubversionRepository repository;

	@Before
	protected void setUp() throws Exception
	{
		repository = new SubversionRepository();
		repository.setLocation( "http://www.magsilva.dynalias.net/svn/test" );
	}

	/**
	 * Try to create a repository transaction without enough parameters
	 * (project without url or repository type filed).
	 */
	@Test
	public final void testCreateRepositoryTransaction1()
	{
		try {
			RepositoryTransactionFactory.createRepositoryTransaction(repository);
			fail();
		} catch ( RepositoryTransactionError e ) {
		}
	}

	/**
	 * Try to create a SVN repository transaction.
	 */
	@Test
	public final void testCreateRepositoryTransaction3()
	{
		try {
			RepositoryTransaction rtx = RepositoryTransactionFactory.createRepositoryTransaction(repository);
			rtx.abort();
		} catch ( RepositoryTransactionError e ) {
			fail();
		}
	}

	/**
	 * Check if the generated id are unique and sequential.
	 */
	@Test
	public final void testNextId1()
	{
		int[] ids = new int[SubversionRepositoryConstants.TRANSACTION_COUNT ];
		for ( int i = 0; i < ids.length; i++ ) {
			ids[ i ] = TransactionIdFactory.nextId();
		}
		for ( int i = 0; i < ( ids.length - 1 ); i++ ) {
			if ( ( ids[ i + 1 ] -  ids[ i ] ) != 1 ) {
				fail();
			}
		}	
	}

	/**
	 * Check if the generated id are unique and sequential, even when multiple
	 * threads are run simultaneosly.
	 * 
	 * It will fill the array with the value 1. The indexes are the ids generated
	 * by the factory minus a constant (the initial value for the id).
	 */
	@Test
	public final void testNextId2()
	{
		int[] ids = new int[SubversionRepositoryConstants.THREAD_COUNT];
		int skew = TransactionIdFactory.nextId() + 1;
		List<Thread> threads = new ArrayList<Thread>( ids.length ); 
		
		// Helper class
		class Filler extends Thread
		{
			private int[] ids;
			private int skew;
			public Filler( int[] ids, int skew )
			{
				super();
				this.ids = ids;
				this.skew = skew;
			}
			public void run()
			{
				int i = TransactionIdFactory.nextId();
				i -= skew;
				ids[ i ] = 1;
			}
		}
		
		Thread t = null;
		for ( int i = 0; i < ids.length; i++ ) {
			t = new Filler( ids, skew );
			threads.add( t );
			t.start();
		}
		
		for ( int i = 0; i < threads.size(); i++ ) {
			try {
				threads.get( i ).join();
			} catch ( InterruptedException e ) {
			}
		}
		
		for ( int i = 0; i < ids.length; i++ ) {
			if ( ids[ i ] != 1 ) {
				fail();
			}
		}	
	}
}