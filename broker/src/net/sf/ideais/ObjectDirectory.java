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

import java.util.TreeMap;
import java.util.TreeSet;

import net.sf.ideais.apps.ApplicationObject;
import net.sf.ideais.objects.BusinessObject;

/**
 * Directory of application and business objects.
 */
public class ObjectDirectory
{
	/**
	 * Application objects.
	 */
	private TreeSet<ApplicationObject> aoDir;
	
	/**
	 * Business objects.
	 */
	private TreeSet<BusinessObject> boDir;
	
	/**
	 * Mapping for business objects and application objects.
	 */
	private TreeMap<BusinessObject, TreeSet<ApplicationObject>> dir;
	
	public ObjectDirectory()
	{
		aoDir = new TreeSet<ApplicationObject>();
		boDir = new TreeSet<BusinessObject>();
		dir = new TreeMap<BusinessObject, TreeSet<ApplicationObject>>();
	}
	
	/**
	 * Check if the directory have the given object.
	 * 
	 * @param ao Object to search for.
	 * @return True if the object is registered in the directory.
	 */
	public boolean contains(ApplicationObject ao)
	{
		return aoDir.contains(ao);
	}
	
	/**
	 * Check if the directory have the given object.
	 * 
	 * @param bo Object to search for.
	 * @return True if the object is registered in the directory.
	 */
	public boolean contains(BusinessObject bo)
	{
		return boDir.contains(bo);
	}
	
	/**
	 * Add an application object to the directory.
	 * 
	 * @param ao Application object to be added.
	 */
	public void add(ApplicationObject ao)
	{
		aoDir.add(ao);
	}
	
	/**
	 * Add a business object to the directory.
	 * 
	 * @param bo Business object to be added.
	 */
	public void add(BusinessObject bo)
	{
		boDir.add(bo);
	}
	
	/**
	 * Link a business object to and application object. The business object
	 * or application object are automatically registered in the directory
	 * if necessary.
	 *  
	 * @param bo Business object related to the application object.
	 * @param ao Application object related to the business object.
	 */
	public void link(BusinessObject bo, ApplicationObject ao)
	{
		if (! aoDir.contains(ao)) {
			add(ao);
		}
		if (! boDir.contains(bo)) {
			add(bo);
			dir.put(bo, new TreeSet<ApplicationObject>());
		}
		
		if (! dir.containsKey(bo) ) {
			dir.put(bo, new TreeSet<ApplicationObject>());
		}
		dir.get(bo).add(ao);
	}
}
