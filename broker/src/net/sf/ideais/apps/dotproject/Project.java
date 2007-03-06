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

import net.sf.ideais.apps.dotproject.DotProjectObject;
import net.sf.ideais.util.annotations.Identificator;
import net.sf.ideais.util.annotations.Property;
import net.sf.ideais.util.annotations.Table;

import java.util.Date;

/**
 * POJO for a Dotproject's project.
 */
@Table(value="projects")
public class Project extends DotProjectObject
{
	/*
	SelectList 	ProjectPriority 	-1|low 0|normal 1|high 	
	SelectList 	ProjectPriorityColor 	-1|#E5F7FF 0| 1|#FFDCB3 	
	SelectList 	ProjectStatus 	0|Not Defined 1|Proposed 2|In Planning 3|In Progress 4|On Hold 5|Complete 6|Template 	
	SelectList 	ProjectType 	0|Unknown 1|Administrative 2|Operative
	*/
	
	@Property(value="project_id")
	@Identificator
	protected Integer id;

	@Property(value="project_name")
	public String name;

	@Property(value="project_short_name")
	public String shortName;

	@Property(value="project_company")
	public int companyId;

	@Property(value="project_start_date")
	public Date startDate;

	@Property(value="project_priority")
	public int priority;

	@Property(value="project_color_identifier")
	public int color;

	@Property(value="project_type")
	public int type;

	@Property(value="project_status")
	public int status;

	@Property(value="project_owner")
	public int ownerId;

	@Property(value="project_description")
	public String description;

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

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public int compareTo(Object o)
	{
		int result = super.compareTo(o);
		if (result == 0) {
			Project p = (Project) o;
			result = getId().compareTo(p.getId());
		}
		return result;
	}
}