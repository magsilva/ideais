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

public class ObjectDirectory
{
	private TreeSet<ApplicationObject> aoDir;
	
	private TreeSet<BusinessObject> boDir;
	
	private TreeMap<BusinessObject, TreeSet<ApplicationObject>> dir;
	
	public ObjectDirectory()
	{
		aoDir = new TreeSet<ApplicationObject>();
		boDir = new TreeSet<BusinessObject>();
		dir = new TreeMap<BusinessObject, TreeSet<ApplicationObject>>();
	}
	
	public boolean contains(ApplicationObject ao)
	{
		return aoDir.contains(ao);
	}
	
	public boolean contains(BusinessObject bo)
	{
		return boDir.contains(bo);
	}
	
	public void add(ApplicationObject ao)
	{
		aoDir.add(ao);
	}
	
	public void add(BusinessObject bo)
	{
		boDir.add(bo);
	}
	
	public void link(BusinessObject bo, ApplicationObject ao)
	{
		if (! aoDir.contains(ao)) {
			add(ao);
		}
		if (! boDir.contains(bo)) {
			add(bo);
		}
		
		if (! dir.containsKey(bo) ) {
			dir.put(bo, new TreeSet<ApplicationObject>());
		}
		dir.get(bo).add(ao);
	}
}
