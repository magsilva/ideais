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

package net.sf.ideais.util.patterns;

import java.util.TreeSet;

import net.sf.ideais.util.conf.ConfigurationMap;

public final class DataSourceFactory
{
	private static TreeSet<String> productLines = new TreeSet<String>();
	
	// Initialize factory
	static
	{
		reset();
	}
	
	public static void reset()
	{
		productLines.clear();
		productLines.add(DbDataSource.class.getName());
	}

	/**
	 * We really don't want an instance of this class, so we create this
	 * private constructor.
	 */
	private DataSourceFactory()
	{
	}
	
	/**
	 * Create a data source
	 * 
	 * @param name A name for the humble repository to be born
	 * @param type The repository type
	 * @param location The repository location
	 * @param username The username used to connect to the repository
	 * @param password The password used to connect to the repository
	 * 
	 * @throws RepositoryError If an error is detected when creating
	 * the transaction (repository type not supported or fatal error when
	 * initializing the transacton).
	 */
	public static DataSource manufacture(String productName, ConfigurationMap conf)
	{
		Object product = null;
		
		if (! DataSourceFactory.canManufacture(productName)) {
			throw new IllegalArgumentException("Factory cannot accept the production order");
		}
		
		try {
			Class c = Class.forName(productName);
			DataSource ds = (DataSource) c.newInstance();
			ds.setConfiguration(conf);
			product = ds.instance();
		} catch (Exception e) {
			product = null;
		}
		
		if (product == null) {
			throw new IllegalArgumentException("Factory could not manufacture a product with the given configuration.");
		}
		
		return (DataSource)product;
	}
	
	/**
	 * Get the portfolio (the products the factory can produce).
	 * 
	 * @return The portfolio.
	 */
	public static String[] getPortfolio()
	{
		return DataSourceFactory.productLines.toArray(new String[0]);
	}

	/**
	 * Check if the factory can produce the requested product.
	 * 
	 * @param product The product to be produced.
	 * @return True if the factory can manufacture it, False otherwise.
	 */
	public static boolean canManufacture(String product)
	{
		for (String productLine : productLines) {
			if (productLine.equals(product)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if the factory is ready to start working. More specifically, the factory
	 * must have all the libraries loaded and set up. 
	 * 
	 * Actually this delegates the function for each DataSource implementation
	 * registered in the factory.
	 * 
	 * @return True if ok, False if not ready.
	 */
	public static boolean isReady()
	{
		return DataSourceFactory.isReady(false);
	}
	
	/**
	 * Check if the factory is ready to start working. More specifically, the factory
	 * must have all the libraries loaded and set up.
	 * 
	 * @param force Try to force repository's setup. This is not recommended.
	 * 
	 * @return True if ok, False or an exception if not ready.
	 */
	public static boolean isReady(boolean force)
	{
		boolean ready = true;
		
		if (productLines.size() == 0) {
			return false;
		}
		
		try {
			for (String productLine : productLines) {
				Class c = Class.forName(productLine);
				// The following code had the great help from this forum:
				// http://forum.java.sun.com/thread.jspa?threadID=708343&messageID=4102615
				DataSource ds = (DataSource) c.newInstance();
				ready &= ds.isReady(force);
			}
		} catch ( Throwable e ) {
			ready = false;
		}
		
		return ready;
	}
	
	public static void addProductLine(String productLine)
	{
		productLines.add(productLine);
	}

	public static void removeProductLine(String productLine)
	{
		productLines.remove(productLine);
	} 
}