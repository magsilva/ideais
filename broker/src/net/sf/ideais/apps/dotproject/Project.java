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

import net.sf.ideais.Field;
import net.sf.ideais.Table;
import net.sf.ideais.Identificator;

import java.util.Date;

/**
 * POJO for a Dotproject's project.
 */
@Table(value="projects")
public class Project implements DotProjectObject
{
	/*
	SelectList 	ProjectPriority 	-1|low 0|normal 1|high 	
	SelectList 	ProjectPriorityColor 	-1|#E5F7FF 0| 1|#FFDCB3 	
	SelectList 	ProjectStatus 	0|Not Defined 1|Proposed 2|In Planning 3|In Progress 4|On Hold 5|Complete 6|Template 	
	SelectList 	ProjectType 	0|Unknown 1|Administrative 2|Operative
	*/
	
	@Field(value="project_id")
	@Identificator
	private Long id;

	@Field(value="project_name")
	private String name;

	@Field(value="project_shortname")
	private String shortName;

	@Field(value="project_company")
	private int companyId;

	@Field(value="project_start_date")
	private Date startDate;

	@Field(value="project_priority")
	private int priority;

	@Field(value="project_color_identifier")
	private int color;

	@Field(value="project_type")
	private int type;

	@Field(value="project_status")
	private int status;

	@Field(value="project_owner")
	private int ownerId;

	private String description;

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

	public String getObjectType()
	{
		// TODO Auto-generated method stub
		return null;
	}
}