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

package net.sf.ideais.apps.dotproject;

import net.sf.ideais.apps.dotproject.DotProjectDAO;
import net.sf.ideais.apps.dotproject.Project;
import net.sf.ideais.util.conf.Configuration;

import java.sql.ResultSet;


/**
 * Data Transfer Object for a Project available at a DotProject instance.
 * 
 */
public class ProjectDAO extends DotProjectDAO<Project>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ProjectDAO(Configuration conf)
	{
		super(conf);
	}


	/**
	 * Create a Project instance.
	 *
	 * @param rs The ResultSet with the data loaded from the database.
	 * @return The Task object if there is enough data to create a task instance,
	 * null otherwise.
	 */
	protected Project createInstance(ResultSet rs)
	{
		return super.createInstance(Project.class, rs);
	}
	
	
	protected Class<? extends DotProjectObject> getObjectType()
	{
		return Project.class;
	}
}