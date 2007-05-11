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
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Arrays;
import java.security.SecureRandom;

import net.sf.ideais.objects.ConfigurationItem;
import net.sf.ideais.repository.RepositoryTransaction;
import net.sf.ideais.util.IoUtil;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * To test a repository transaction, you need to setup the repository
 * tree as follows (directories are prefixed with a slash, files are
 * not):
 * <pre>
 * /
 * |____ README
 * |____ /About
 *       |______Tests
 * </pre>
 * The repository must have two versions at least. The file README,
 * in its first version, must have the following content:
 * 
 * <pre>
 *   If a moving particle, carried uniformly at a constant speed,
 *   transverses two distances the time-intervals required are to
 *   each other in the ratio of these distances.   
 * </pre>      
 * 
 * And, in its second version:
 * 
 * <pre>
 *   If a moving particle, carried uniformly at a constant speed,
 *   transverses two distances, the time-intervals required are to
 *   each other in the ratio of these distances.   
 * </pre>
 * 
 * The file "Tests" must have the following content:
 * <pre>
 *   Given an inclined plane and a limited vertical line, it is required
 *   to find a distance on the inclined plane which a body starting from
 *   rest, will transverse in the same time as that needed to transverse
 *   both the vertical and the inclined plane.
 * </pre>
 * 
 * The changelog messages must be always:
 * <pre>
 * 	 Galileo Galilei
 * </pre>
 * 
 * The repository _must_ have only this content. The tests create lots
 * of files that, although not commited, must not conflict with the
 * existing tree.
 * 
 * Every concrete RepositoryTransaction should provide instructions
 * to create such repository or, better yet, provide a dump/backup
 * of a repository ready to use.
 * 
 * The default user and project created are named "TestUser" and
 * "TestProject", respectively. You will probably need to set up
 * authentication required properties for the user, as well as
 * repository information for the project. You _must_ set these
 * within "getRepositoryTransaction()". This _should_ be the only
 * implementation detail needed in concrete repository transaction's
 * test cases, what makes the SCM use quite painless for Wiki/RE.
 */
public abstract class AbstractRepositoryTransactionTest
{
	private RepositoryTransaction rtx;
	
	/**
	 * Method that any concrete RepositoryTransactionTest must override. Not only
	 * it must create the RepositoryTransaction (one suited for the concrete repository)
	 * but it must also:
	 * - set the project's repository's type and URL,
	 * - set the properties "version1" and "version2" to suitable values.
	 * 
	 * @return The repository transaction that will be tested.
	 */
	protected abstract RepositoryTransaction getRepositoryTransaction();

	protected String version1;
	protected String version2;
	
	private Random hackProvider = new SecureRandom();
	private long hack = hackProvider.nextLong();
	private String prefix = File.separator + hack + File.separator;
	private String branchesDirname = prefix + "branches";
	private String trunkDirname = prefix + "trunk";
	private String aboutFilename = prefix + "ABOUT";
	private String randomFilename = trunkDirname + File.separator + "Space - Michener.txt";
	private String readmeFilename = File.separator + "README";

	
	/**
	 * Create the files used by the tests. The tree is set as follows (directories are
	 * prefixed with a slash, files are not):
	 * <pre>
	 * /
	 * |____ README
	 * |____ /branches
	 * |____ /trunk
	 *       |_________ Space - Michener.txt
	 * </pre>
	 */
	private void populateWorkdir() throws Exception
	{
		File branches = IoUtil.createDir( rtx.getWorkdir() + branchesDirname );
		File trunk = IoUtil.createDir( rtx.getWorkdir() + trunkDirname );
		File about = IoUtil.createFile( rtx.getWorkdir(), aboutFilename );
		File random = IoUtil.createFile( rtx.getWorkdir(), randomFilename );
		FileWriter aboutContent = new FileWriter( about );
		aboutContent.write(
			"This directory holds data used to test the RepositoryTransaction\n" +
			"You may delete these files, as well as the whole directory, after\n" +
			"the test end."
		);
		aboutContent.close();
		FileWriter randomContent = new FileWriter( random );
		randomContent.write(
			"Tractor-plus-Apollo-plus-gantry weighed 18,480,000 pounds - 9,240 tons - and how\n" +
			"could such a burden be moved three and a half miles accross Florida swampland?\n" +
			"\n" +
			"What we did was call in the best roadbuilders in the world, and they said, \"Simple.\n" +
			"You build a road wider than an eight-lane superhighway. You go down nine feet, linen\n" +
			"the bottom of your trench with big rocks, then seven feet of aggregate, then eight\n " +
			"inches of pebbles. Cost? We can do it for about $ 20,000,000\".\n"
		);
		randomContent.close();
	}

	
	/**
	 * We expect that the RepositoryTransaction under test call this method _AFTER_
	 * preparing the access to the repository (that means, instantiate a
	 * RepositoryTransaction).
	 */	
	@Before
	protected void setUp() throws Exception
	{
		rtx = getRepositoryTransaction();
		rtx.checkout();
		populateWorkdir();
		rtx.add(rtx.getWorkdir() + prefix, false);
	}

	/*
	 * Checkout the latest version of the entire tree.
	 */
	@Test
	public void testCheckout()
	{
		rtx.checkout();
	}

	/*
	 * Checkout a specif version of the entire tree.
	 */
	@Test
	public void testCheckout(String version)
	{
		rtx.checkout( version );
	}

	@Test
	public void testAdd0()
	{
		rtx.add( rtx.getWorkdir() + aboutFilename );
	}
	
	@Test
	public void testAdd1()
	{
		rtx.add( rtx.getWorkdir() + trunkDirname, false );
		rtx.add( rtx.getWorkdir() + randomFilename );
	}

	@Test
	public void testAdd2()
	{
		rtx.add( rtx.getWorkdir() + branchesDirname );
	}

	@Test
	public void testRemove()
	{
		File f = new File( rtx.getWorkdir() + readmeFilename );
		rtx.remove( f.getAbsolutePath() );
	}

	@Test
	public void testRevert()
	{
		rtx.add( rtx.getWorkdir() + aboutFilename );
		rtx.revert( rtx.getWorkdir() + aboutFilename );
	}

	@Test
	public void testInfo()
	{
		ConfigurationItem[] ci = rtx.info( rtx.getWorkdir() + readmeFilename );
		assertNotNull( ci );
		assertNotNull( ci[ 0 ] );
		assertTrue( ci.length == 1 );
	}

	/*
	 * Class under test for void update(String)
	 */
	@Test
	public void testUpdate1()
	{
		File f = new File( rtx.getWorkdir() + readmeFilename );
		f.delete();
		rtx.update( f.getAbsolutePath() );
		assertTrue( f.exists() );
	}

	/*
	 * Class under test for void update(String, String)
	 */
	@Test
	public void testUpdate2() throws IOException
	{
		File f = new File( rtx.getWorkdir() + readmeFilename );
		byte[] originalData = IoUtil.dumpFile( f ); 
		f.delete();
		rtx.update( f.getAbsolutePath(), version1 );
		byte[] newData = IoUtil.dumpFile( f );
		assertFalse( Arrays.equals( originalData, newData ) );
	}

	@Test
	public void testBranch()
	{
		rtx.branch( trunkDirname, branchesDirname + File.separator + "test" );
	}

	/*
	 * Class under test for String diff(String, String)
	 */
	@Test
	public void testDiff1()
	{
		rtx.diff( aboutFilename, randomFilename );
	}

	/*
	 * Class under test for String diff(String, String, String)
	 */
	@Test
	public void testDiff2()
	{
		rtx.diff( aboutFilename, version1, version2 );
	}

	/*
	 * Class under test for String diff(String, String, String, String)
	 */
	@Test
	public void testDiffStringStringStringString()
	{
		rtx.diff( aboutFilename, version1, randomFilename, version2 );
	}

	@Test
	public void testGetChangelog1()
	{
		rtx.setChangelog( null );
		assertNotNull( rtx.getChangelog() );
		assertEquals( rtx.getChangelog(), "" );
	}

	@Test
	public void testSetChangelog()
	{
		rtx.setChangelog( "abc" );
		assertEquals( rtx.getChangelog(), "abc" );
	}
	
	@Test
	public void testAddChangelog1()
	{
		rtx.addChangelog( "abc" );
		assertTrue( rtx.getChangelog().endsWith( "abc" ) );
	}

	@Test
	public void testAddChangelog2()
	{
		rtx.setChangelog( null );
		rtx.addChangelog( "abc" );
		assertEquals( rtx.getChangelog(), "abc" );
	}
	
	@Test
	public void testAddChangelog3()
	{
		rtx.setChangelog( "" );
		rtx.addChangelog( "abc" );
		rtx.addChangelog( "abc" );
		assertEquals( rtx.getChangelog(), "abc\nabc" );
	}
}