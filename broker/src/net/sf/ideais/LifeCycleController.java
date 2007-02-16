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

public class LifeCycleController
{
	private static LifeCycleController controller;
	
	private ObjectDirectory dir;
	
	
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
	
	private BusinessObject __new(ApplicationObject ao)
	{
		BusinessObject bo = null;
		
		if (ao instanceof net.sf.ideais.apps.dotproject.Project) {
			bo = new Project();
			__new_project((Project)bo, ao);
			return bo;
		}
		
		return null;
	}
	
	public static synchronized LifeCycleController instance()
	{
		if (controller == null) {
			controller = new LifeCycleController();
		}
		return controller;
	}
	
	public void process(ApplicationObject ao)
	{
		if (! dir.contains(ao)) {
			BusinessObject bo = null;
			bo = __new(ao);
			if (bo != null) {
				dir.link(bo, ao);
			}
		}
	}
	
}
