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

Copyright (C) 2006 Marco Aurélio Graciotto Silva <magsilva@gmail.com>
*/

package net.sf.ideais.dotproject;

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
    
    private String hostname;
    
    private String database;
    
    private String username;
    
    private String password;
    
        
    /**
    * Creates a new instance of TaskDAO
    */
    public DAO(String hostname, String database, String username, String password)
    {
        this.hostname = hostname;
        this.database = database;
        this.username = username;
        this.password = password;

	loadDriver();
	connectToDatabase();
    }

    private void loadDriver()
    {
	      /*
       try {
		Driver driver = null;
		driver = DriverManager.getDriver("jdbc:mysql://");
		if (driver != null) {
			  return;
		}
       } catch ( SQLException e ) {
       }
*/
       try {
	   
	   Class.forName("com.mysql.jdbc.Driver");
       } catch (ClassNotFoundException cnfe) {
	    dumpException(cnfe);
       } catch (ExceptionInInitializerError eiie) {
	    dumpException(eiie);
       } catch (LinkageError le) {
	    dumpException(le);
       }
    }

    /**
     * Connect to the DotProject database.
     */
    protected void connectToDatabase()
    {
       try {
           conn = DriverManager.getConnection(
	       String.format("jdbc:mysql://%1$s/%2$s?user=%3$s&password=%4$s",
	       this.hostname,
	       this.database,
	       this.username,
	       this.password)
	   );
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
}