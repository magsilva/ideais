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

package net.sf.ideais.util;

import net.sf.ideais.util.ExceptionUtil;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public final class SqlUtil
{
    /**
     * Check if the database driver is loaded.
     * 
     * @return True if the driver is loaded, False otherwise.
     */   
    final public static boolean isDriverLoaded(String driver)
    {
    	// Check if the driver has been already loaded
    	Enumeration<Driver> drivers = DriverManager.getDrivers();
    	while (drivers.hasMoreElements()) {
    		Driver d = drivers.nextElement();
    		if (d.getClass().getName().equals(driver)) {
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * Unload the database driver (if it's loaded).
     */
    public static final void unloadDriver(String driver)
    {
    	if (! SqlUtil.isDriverLoaded(driver)) {
    		return;
    	}
    	
    	Enumeration<Driver> drivers = DriverManager.getDrivers();
    	while (drivers.hasMoreElements()) {
    		Driver d = drivers.nextElement();
    		if (d.getClass().getName().equals(driver)) {
    			try {
    				DriverManager.deregisterDriver(d);
    			} catch (SQLException e) {
    			}
    		}
    	}
    }

    /**
     * Load the database driver (if it hasn't been already loaded).
     */
    public static final void loadDriver(String driver)
    {
    	if (SqlUtil.isDriverLoaded(driver)) {
    		return;
    	}
    	
		ReflectionUtil.loadClass(driver);
    }
	
	final public static void dumpSQLException(SQLException e)
	{
		ExceptionUtil.dumpException(e);
		System.out.println("SQLState: " + e.getSQLState());
		System.out.println("VendorError: " + e.getErrorCode());
	}

}
