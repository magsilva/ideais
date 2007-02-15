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

@Table("vtiger_purchaseorder")
public class PurchaseOrder extends VtigerObject
{
	@Property("purchaveorderid")
	@Identificator
	private Integer id;
	
	@Property("subject")
	private String name;
	
	@Property("quoteid")
	private Integer quoteId;
	
	@Property("requisition_no")
	private String requisitionId;
	
	@Property("tracking_no")
	private String trackingId;
	
	@Property("contactid")
	private Integer contactid;
	
	@Property("duedate")
	private Date dueDate;
	
	@Property("total")
	private String total;
	
	@Property("terms_conditions")
	private String contract;
	
	@Property("postatus")
	private String status;
}
