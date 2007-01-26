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

package net.sf.ideais.dotproject;

import java.util.Date;

/**
 *
 * 
 */
public class Project
{
	public static final String ID = "project_id";
	public static final String NAME = "project_name";
	public static final String SHORT_NAME = "project_shortname";
	public static final String OWNER_ID = "project_owner";
	public static final String COMPANY_ID = "project_company";
	public static final String START_DATE = "project_start_date";
	public static final String PRIORITY = "project_priority";
	public static final String COLOR = "project_color_identifier";
	public static final String TYPE = "project_type";
	public static final String STATUS = "project_status";
	
	private int id;
	
	  private String name;
	  
	  private String shortName;
	  
	  private int companyId;
	  
	  private Date startDate;
	  
	  private int priority;
	  
	  private int color;
	  
	  private int type;
	  
	  private int status;
	  
	  private int ownerId;
	  
	  private String description;

    /**
    * Creates a new instance of Project
    */
    public Project()
    {
    }

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getShortName()
	{
		return shortName;
	}

	public void setShortName(String shortName)
	{
		this.shortName = shortName;
	}

	public int getCompanyId()
	{
		return companyId;
	}

	public void setCompanyId(int company)
	{
		this.companyId = company;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public int getPriority()
	{
		return priority;
	}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	public int getColour()
	{
		return color;
	}

	public void setColour(int colour)
	{
		this.color = colour;
	}

	public int getProjectType()
	{
		return type;
	}

	public void setProjectType(int projectType)
	{
		this.type = projectType;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getOwnerId()
	{
		return ownerId;
	}

	public void setOwnerId(int ownerId)
	{
		this.ownerId = ownerId;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

}