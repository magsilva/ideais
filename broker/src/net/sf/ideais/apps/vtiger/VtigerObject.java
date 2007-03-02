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

import net.sf.ideais.apps.ApplicationObject;
import net.sf.ideais.util.annotations.Identificator;
import net.sf.ideais.util.annotations.Property;
import net.sf.ideais.util.annotations.Table;

@Table("vtiger_crmentity")
public abstract class VtigerObject implements ApplicationObject
{
	@Property("crmid")
	@Identificator
	public Integer crmid;
	
	@Property("smcreatorid")
	public Integer creatorId;
	
	@Property("smownerid")
	public Integer ownerId;
	
	@Property("createdtime")
	public Date creationTime;
	
	@Property("modifiedtime")
	public Date lastModificationTime;
	
	public String getObjectType()
	{
		return this.getClass().getName();
	}

	public Date getCreationTime()
	{
		return creationTime;
	}

	public void setCreationTime(Date creationTime)
	{
		this.creationTime = creationTime;
	}

	public Integer getCreatorId()
	{
		return creatorId;
	}

	public void setCreatorId(Integer creatorId)
	{
		this.creatorId = creatorId;
	}

	public Integer getCrmid()
	{
		return crmid;
	}

	public void setCrmid(Integer crmid)
	{
		this.crmid = crmid;
	}

	public Date getLastModificationTime()
	{
		return lastModificationTime;
	}

	public void setLastModificationTime(Date lastModificationTime)
	{
		this.lastModificationTime = lastModificationTime;
	}

	public Integer getOwnerId()
	{
		return ownerId;
	}

	public void setOwnerId(Integer ownerId)
	{
		this.ownerId = ownerId;
	}
	
	
}
