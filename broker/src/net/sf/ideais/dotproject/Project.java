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

import net.sf.ideais.Field;

import java.util.Date;

/**
 *
 * 
 */
public class Project
{
	@Field(name="project_id")
	private Long id;

	@Field(name="project_name")
	private String name;

	@Field(name="project_shortname")
	private String shortName;

	@Field(name="project_company")
	private int companyId;

	@Field(name="project_start_date")
	private Date startDate;

	@Field(name="project_priority")
	private int priority;

	@Field(name="project_color_identifier")
	private int color;

	@Field(name="project_type")
	private int type;

	@Field(name="project_status")
	private int status;

	@Field(name="project_owner")
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

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

}
