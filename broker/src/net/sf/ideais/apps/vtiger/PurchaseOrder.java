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

import java.math.BigDecimal;
import java.util.Date;

import net.sf.ideais.util.annotations.Identificator;
import net.sf.ideais.util.annotations.Property;
import net.sf.ideais.util.annotations.Table;

@Table(value="vtiger_purchaseorder")
public class PurchaseOrder extends VtigerObject
{
	@Property("purchaseorderid")
	@Identificator
	public Integer id;
	
	@Property("subject")
	public String name;
	
	@Property("quoteid")
	public Integer quoteId;
	
	@Property("requisition_no")
	public String requisitionId;
	
	@Property("tracking_no")
	public String trackingId;
	
	@Property("contactid")
	public Integer contactid;
	
	@Property("duedate")
	public Date dueDate;
	
	@Property("total")
	public BigDecimal total;
	
	@Property("terms_conditions")
	public String contract;
	
	@Property("postatus")
	public String status;

	public Integer getContactid()
	{
		return contactid;
	}

	public void setContactid(Integer contactid)
	{
		this.contactid = contactid;
	}

	public String getContract()
	{
		return contract;
	}

	public void setContract(String contract)
	{
		this.contract = contract;
	}

	public Date getDueDate()
	{
		return dueDate;
	}

	public void setDueDate(Date dueDate)
	{
		this.dueDate = dueDate;
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

	public Integer getQuoteId()
	{
		return quoteId;
	}

	public void setQuoteId(Integer quoteId)
	{
		this.quoteId = quoteId;
	}

	public String getRequisitionId()
	{
		return requisitionId;
	}

	public void setRequisitionId(String requisitionId)
	{
		this.requisitionId = requisitionId;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public BigDecimal getTotal()
	{
		return total;
	}

	public void setTotal(BigDecimal total)
	{
		this.total = total;
	}

	public String getTrackingId()
	{
		return trackingId;
	}

	public void setTrackingId(String trackingId)
	{
		this.trackingId = trackingId;
	}
}
