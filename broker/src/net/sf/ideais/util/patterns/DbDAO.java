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

package net.sf.ideais.util.patterns;

import java.sql.Connection;

import net.sf.ideais.conf.Configuration;
import net.sf.ideais.util.DataSourceFactory;
import net.sf.ideais.util.DbDataSource;

/**
 * Data Transfer Object for a task available at a DotProject instance.
 * 
 */
public abstract class DbDAO<T, I> extends GenericDAO<T, I>
{
	protected Connection conn;
	
	public DbDAO()
	{
		DbDataSource ds = (DbDataSource) DataSourceFactory.manufacture("net.sf.ideais.DbDataSource",
						getConfiguration());
		conn = ds.getConnection();
	}
	
	abstract protected Configuration getConfiguration();
}