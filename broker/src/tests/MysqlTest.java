/*
Wiki/RE - A requirements engineering wiki
Copyright (C) 2005 Marco Aurélio Graciotto Silva

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
*/

package tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import static org.junit.Assert.*;
import org.junit.Test;

public class MysqlTest
{
	static final public String localHostname = "localhost";
	static final public String localDatabase = "dotproject-dev";
	static final public String localUsername = "test";
	static final public String localPassword = "test";
	
	@Test
	public void testDriver()
	{
		try {
            Class.forName( "com.mysql.jdbc.Driver" );
		} catch ( ExceptionInInitializerError eiie ) {
			fail();
		} catch ( LinkageError le ) {
			fail();
		} catch ( ClassNotFoundException cnfe ) {
			fail();
		}
	}
	
	@Test
	public void testConnection()
	{
		try {
            Class.forName( "com.mysql.jdbc.Driver" );
		} catch ( Exception e ) {
		}

		ResultSet rs = null;
		try {
			Connection conn = DriverManager.getConnection( "jdbc:mysql://" +
					localHostname + "/" +
					localDatabase + "?" +
					"user=" + localUsername + "&" +
					"password=" + localPassword + "&" +
					"&useCompression=true&autoReconnect=true" +
					"&cacheCallableStmts=true&cachePrepStmts=true&cacheResultSetMetadata=true&" +
					"useFastIntParsing=true&useNewIO=true"
				);
			Statement stmt = conn.createStatement();
			stmt.execute( "SELECT 1" );
			rs = stmt.getResultSet();
		} catch ( SQLException ex ) {
			System.out.println( "SQLException: " + ex.getMessage() );
			System.out.println( "SQLState: " + ex.getSQLState() );
			System.out.println( "VendorError: " + ex.getErrorCode() );
			fail();
		} finally {
			if ( rs != null ) {
				try {
					rs.close();
				} catch ( SQLException e ) {
				}
			}
			rs = null;
		}
	}
}