/*
 * Project.java
 *
 * Created on 24 de Agosto de 2006, 19:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

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

Copyright (C) magsilva <EMAIL>
*/

package net.sf.ideais.dotproject;

import java.util.Date;

/**
 *
 * 
 */
public class Project
{
	private int id;
	
	  private String name;
	  
	  private String shortName;
	  
	  private int companyId;
	  
	  private Date startDate;
	  
	  private int priority;
	  
	  private int colour;
	  
	  private int projectType;
	  
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
		return colour;
	}

	public void setColour(int colour)
	{
		this.colour = colour;
	}

	public int getProjectType()
	{
		return projectType;
	}

	public void setProjectType(int projectType)
	{
		this.projectType = projectType;
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