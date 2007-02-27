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

import net.sf.ideais.apps.dotproject.Project;
import net.sf.ideais.apps.dotproject.ProjectDAO;

public class Ideais
{

	public static void main(String[] args)
	{
		LifeCycleController controller = LifeCycleController.instance();
		
		ProjectDAO dpProjectDao = new ProjectDAO();
		Project dpProject = dpProjectDao.find(1);
		controller.process(dpProject);
		
		dpProject.setDescription(dpProject.getDescription() + "teste 1 2 3");
		dpProjectDao.update(dpProject);
	}

}
