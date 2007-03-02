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

/**
 * POJO for a Product.
 */
@Table("vtiger_products")
public class Product extends VtigerObject
{
	@Property("id")
	@Identificator
	private Integer productid;
	
	@Property("productname")
	private String name;
	
	@Property("product_description")
	private String description;
	
	@Property("sales_start_date")
	private Date salesStartDate;
	
	@Property("sales_end_date")
	private Date salesEndDate;
	
	@Property("start_date")
	private Date startDate;
	
	@Property("expiry_date")
	private Date endDate;

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getProductid()
	{
		return productid;
	}

	public void setProductid(Integer productid)
	{
		this.productid = productid;
	}

	public Date getSalesEndDate()
	{
		return salesEndDate;
	}

	public void setSalesEndDate(Date salesEndDate)
	{
		this.salesEndDate = salesEndDate;
	}

	public Date getSalesStartDate()
	{
		return salesStartDate;
	}

	public void setSalesStartDate(Date salesStartDate)
	{
		this.salesStartDate = salesStartDate;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public int compareTo(Object o)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
}
