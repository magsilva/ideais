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

package net.sf.ideais.apps.vtiger;

import java.util.Date;

import net.sf.ideais.util.annotations.Identificator;
import net.sf.ideais.util.annotations.Property;
import net.sf.ideais.util.annotations.Table;

@Table("vtiger_potential")
public class Potential extends VtigerObject
{
	@Property("potentialid")
	@Identificator
	private Integer id;
	
	@Property("accountid")
	private Integer accountId;
	
	@Property("potentialname")
	private String name;
	
	@Property("closingdate")
	private Date closingDate;
	
	@Property("sales_stage")
	private String stage;
	
	@Property("description")
	private String description;

	public Integer getAccountId()
	{
		return accountId;
	}

	public void setAccountId(Integer accountId)
	{
		this.accountId = accountId;
	}

	public Date getClosingDate()
	{
		return closingDate;
	}

	public void setClosingDate(Date closingDate)
	{
		this.closingDate = closingDate;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getStage()
	{
		return stage;
	}

	public void setStage(String stage)
	{
		this.stage = stage;
	}

	public int compareTo(Object o)
	{
		// TODO Auto-generated method stub
		return 0;
	}
}
