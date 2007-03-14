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

package net.sf.ideais;

import net.sf.ideais.apps.ApplicationObject;
import net.sf.ideais.objects.BusinessObject;
import net.sf.ideais.objects.Project;
import net.sf.ideais.objects.PurchaseOrder;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Control the lifecycle of every business or application object.
 */
public class LifeCycleController
{
	/**
	* Commons Logging instance.
	*/
	private static final Log log = LogFactory.getLog(LifeCycleController.class);
	
	/**
	 * Singleton implementation.
	 */
	private static LifeCycleController instance;
	
	/**
	 * Directory of business and application objects.
	 */
	private ObjectDirectory dir;
	
	/**
	 * Singleton implementation.
	 */
	private LifeCycleController()
	{
		dir = new ObjectDirectory();
	}

	private void __new_project(Project bo, ApplicationObject ao)
	{
		if (ao instanceof net.sf.ideais.apps.dotproject.Project) {
			net.sf.ideais.apps.dotproject.Project dpProject = (net.sf.ideais.apps.dotproject.Project) ao; 
			bo.setName(dpProject.getName());
			bo.setShortName(dpProject.getShortName());
			bo.setDescription(dpProject.getDescription());
			bo.setStart(dpProject.getStartDate());
		}
	}
	
	private void __new_purchase_order(PurchaseOrder po, ApplicationObject ao)
	{
		if (ao instanceof net.sf.ideais.apps.vtiger.PurchaseOrder) {
			net.sf.ideais.apps.vtiger.PurchaseOrder poVtiger = (net.sf.ideais.apps.vtiger.PurchaseOrder) ao;
			po.setCreationDate(poVtiger.getCreationTime());
			po.setDueDate(poVtiger.getDueDate());
			po.setName(poVtiger.getName());
			po.setStatus(poVtiger.getStatus());
			po.setTotal(poVtiger.getTotal());
		}
	}
	
	private BusinessObject[] __new(ApplicationObject ao)
	{
		ArrayList<BusinessObject> boBag = new ArrayList<BusinessObject>();
		
		if (ao instanceof net.sf.ideais.apps.dotproject.Project) {
			BusinessObject bo = new Project();
			boBag.add(bo);
			__new_project((Project)bo, ao);
			
		}
		if (ao instanceof net.sf.ideais.apps.vtiger.PurchaseOrder) {
			PurchaseOrder po = new PurchaseOrder();
			boBag.add(po);
			__new_purchase_order((PurchaseOrder) po, ao);
		}
		
		return (BusinessObject[]) boBag.toArray(new BusinessObject[0]);
	}
	
	private static void startNewInstance()
	{
		log.debug("Loading the life cycle controller");
		instance = new LifeCycleController();
		log.debug("Loaded the life cycle controller");
	}
	
	/**
	 * Get an instance of the LifeCycleController.
	 * 
	 * @return The LifeCycleController.
	 */
	public static synchronized LifeCycleController instance()
	{
		log.info("Getting the life cycle controller");
		if (instance == null) {
			startNewInstance();
		}
		log.info("Got the life cycle controller");
		return instance;
	}
	
	/**
	 * Register an application object and link it to an business object.
	 * 
	 * @param ao Application object.
	 */
	public void process(ApplicationObject ao)
	{
		if (! dir.contains(ao)) {
			BusinessObject[] boBag = null;
			boBag = __new(ao);
			for (BusinessObject bo : boBag) {
				if (bo != null) {
					dir.link(bo, ao);
				}
			}
		}
	}
}
