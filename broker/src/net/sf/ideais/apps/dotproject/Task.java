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

package net.sf.ideais.apps.dotproject;

import java.util.Date;

import net.sf.ideais.util.StringUtil;
import net.sf.ideais.util.annotations.Property;

/**
 * POJO for a Dotproject's project.
 */
public class Task
{
	/*
	 * The settings for the enumarations below can be found in the table
	 * 'sysvals' at DotProject. The table description is as follows:
	 * 
	 *  - sysval_id: The enumeration unique id.
	 *  - sysval_key_id: The type of input interface to set the enumeration. We
	 *  can ignore this for the task sake.
	 *  - sysval_title: The name of the enumeration.
	 *  - sysval_value The values of the enumeration. It's defined in tuples, each
	 *  one with one number and a string, with an '|' between those values and a '\n'
	 *  between tuples:
	 *  
	 *  	0|Not Defined
	 *  	1|Email
	 *  	2|Helpdesk
	 *  
	 *  Actually, the separators used are defined in the table 'syskeys', using the
	 *  'sysval_key_id' as selector. The ' syskey_sep1' is the tuples' separator and the
	 *  'syskey_sep2' the tuple's terms separator.
	 */

	public enum TaskType
	{
		Unknown(0, "Unknown"),
		Administrative(1, "Administrative"),
		Operative(2, "Operative");
		
	    private final int id;
	    private final String typeName;
	    
	    /**
	     * @param id Must not be negative.
	     */
	    TaskType(int id, String typeName)
	    {
			if (id < 0) {
				throw new IllegalArgumentException("The id must be a non-negative number.");
			}
			if (StringUtil.isEmpty(typeName)) {
				throw new IllegalArgumentException("Name must not be null.");
			}
	    	this.id = id;
	    	this.typeName = typeName;
	    }
	    
	    public int getId()
	    {
	    	return id;
	    }
	    
	    public String getTypeName()
	    {
	    	return typeName;
	    }
	}
	
	/**
	 * The time at DotProject is normalized at hours. So a day has 24 hours,
	 * an hour as 1 hour, and so on.
	 */
	public enum TaskDurationType
	{
		hours(1, "hours"),
		days(24, "days");
		
		private final String typeName;
		private final double hoursPerType;
		
		/**
		 * @param hoursPerType Must not be negative or zero.
		 */
		TaskDurationType(double hoursPerType, String typeName)
		{
			if (hoursPerType <= 0) {
				throw new IllegalArgumentException("Hours per type must be a positive number.");
			}
			if (StringUtil.isEmpty(typeName)) {
				throw new IllegalArgumentException("Name must not be null.");
			}
			this.hoursPerType = hoursPerType;
			this.typeName = typeName;
		}
		
		public String typeName()
		{
			return typeName;
		}
		
		public double hoursPerType()
		{
			return hoursPerType;
		}
	}
	
	public enum TaskLogReference
	{
		NotDefined(0, "Not Defined"),
		Email(1, "Email"),
		Helpdesk(2, "Helpdesk"),
		PhoneCall(3, "Phone Call"),
		Fax(4, "Fax"); 	

		int id;
		String name;
		
		TaskLogReference(int id, String name)
		{
			this.id = id;
			this.name = name;
		}
	}
	
	public enum TaskPriority
	{
		Low(-1, "low"),
		Normal(0, "normal"),
		High(1, "high");
		
		int id;
		String name;
		
		TaskPriority(int id, String name)
		{
			this.id = id;
			this.name = name;
		}
	}

	public enum TaskStatus
	{
		Active(0, "Active"),
		Inactive(-1, "Inactive");
		
		int id;
		String name;
		
		TaskStatus(int id, String name)
		{
			this.id = id;
			this.name = name;
		}
	}
	
	
	@Property(value="task_id")
	private Long id;
	
	// Usefull fields (but not really required by DotProject).
	@Property(value="task_name")
	private String name;

	@Property(value="task_description")
	private String description;
	
	@Property(value="task_start_date")
	private Date startDate;
	
	@Property(value="task_end_date")
	private Date endDate;
	 
	// Required fields
	@Property(value="task_owner")
	private Long ownerId = 0L;
	
	@Property(value="task_project")
	private Long projectId = 0L;
	
	@Property(value="task_duration_type")
	private Long durationTypeId = 1L;
	
	@Property(value="task_creator")
	private Long creatorId = 0L;
	
	@Property(value="task_order")
	private Long orderId = 0L;
	
	@Property(value="task_client_publish")
	private boolean publishAtClient = false;
	
	@Property(value="task_dynamic")
	private boolean isDynamic = false;
	
	// ID
	@Property(value="task_access")
	private Long access = 0L;
	
	// ID
	@Property(value="task_notify")
	private Long notify = 0L;
	
	// ID ?
	@Property(value="task_type")
	private Long type = 0L; 
	

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return this.description;
	}
}
