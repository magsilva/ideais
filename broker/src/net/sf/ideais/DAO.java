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

Copyright (C) 2006 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
*/

package net.sf.ideais;

import java.util.Enumeration;
import java.util.PropertyResourceBundle;
import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Data Transfer Object for a task available at a DotProject instance.
 * 
 */
public class DAO
{
    protected Connection conn;

    private String sgbd;
    
    private String hostname;
    
    private String database;
    
    private String username;
    
    private String password;
    
        
    /**
    * Creates a new instance of TaskDAO
    */
    public DAO(String sgbd, String hostname, String database, String username, String password)
    {
    	setSgbd(sgbd);
    	setHostname(hostname);
        setDatabase(database);
        setUsername(username);
        setPassword(password);
        
        loadDriver();
        connectToDatabase();
    }
    
    public void finalize()
    {
    	try {
    		conn.close();
    	} catch (SQLException e) {
    	}
    }

    private void loadDriver()
    {
    	// Check if the driver has been already loaded
    	String driver = getDriverName(sgbd);
    	Enumeration<Driver> drivers = DriverManager.getDrivers();
    	while (drivers.hasMoreElements()) {
    		Driver d = drivers.nextElement();
    		if (d.getClass().getName().equals(driver)) {
    			return;
    		}
		}
    	
    	// Load the driver
       try {
    	   Class.forName(driver);
       } catch (ClassNotFoundException cnfe) {
    	   dumpException(cnfe);
       } catch (ExceptionInInitializerError eiie) {
    	   dumpException(eiie);
       } catch (LinkageError le) {
    	   dumpException(le);
       }
    }

    public static String getDriverName(String sgbd)
    {
    	InputStream in = DAO.class.getClass().getResourceAsStream("/net/sf/ideais/dbDriver.properties");
    	PropertyResourceBundle res = null;
    	try {
    		res = new PropertyResourceBundle(in);
    	} catch (IOException ioe) {
    	}
    	
    	Enumeration<String> knownDrivers = res.getKeys();
    	while (knownDrivers.hasMoreElements()) {
    		if (knownDrivers.nextElement().equals(sgbd)) {
    			return res.getString(sgbd);
    		}
    	}
    	
    	throw new RuntimeException("Unknown SBGD");   		
    }
    
    public static String getConnectionString(String sgbd)
    {
    	InputStream in = DAO.class.getClass().getResourceAsStream("/net/sf/ideais/dbConnString.properties");
    	PropertyResourceBundle res = null;
    	try {
    		res = new PropertyResourceBundle(in);
    	} catch (IOException ioe) {
    	}
    	
    	Enumeration<String> knownDrivers = res.getKeys();
    	while (knownDrivers.hasMoreElements()) {
    		if (knownDrivers.nextElement().equals(sgbd)) {
    			return res.getString(sgbd);
    		}
    	}
    	
    	throw new RuntimeException("Unknown SBGD");
    }

    
    
    /**
     * Connect to the DotProject database.
     */
    protected void connectToDatabase()
    {
    	try {
    		String connString = getConnectionString(sgbd);
    		conn = DriverManager.getConnection(
    				String.format(connString, hostname, database, username, password));
    	} catch (SQLException e) {
    		dumpSQLException(e);
        }
    }
    
    protected void dumpStackTrace(StackTraceElement[] st)
    {
    }

    protected void dumpException(Throwable e)
    {
    	System.out.println(e.getClass().getName() + " " + e.getMessage());
    	dumpStackTrace(e.getStackTrace());
    }

    protected void dumpSQLException(SQLException e)
    {
    	dumpException(e);
        System.out.println("SQLState: " + e.getSQLState());
        System.out.println("VendorError: " + e.getErrorCode());
    }

	private void setSgbd(String sgbd)
	{
		if (sgbd == null) {
			throw new NullPointerException();
		}
		this.sgbd = sgbd;
	}

	private void setHostname(String hostname)
	{
		if (hostname == null) {
			throw new NullPointerException();
		}
		this.hostname = hostname;
	}

    private void setDatabase(String database)
	{
		if (database == null) {
			throw new NullPointerException();
		}
		this.database = database;
	}

	private void setPassword(String password)
	{
		if (password == null) {
			password = "";
		}
		this.password = password;
	}

	private void setUsername(String username)
	{
		if (password == null) {
			password = "";
		}
		this.username = username;
	}
}