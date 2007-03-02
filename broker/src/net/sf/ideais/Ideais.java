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

import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.ideais.apps.WebApplication;
import net.sf.ideais.apps.dotproject.DotProject;
import net.sf.ideais.apps.dotproject.Project;
import net.sf.ideais.apps.dotproject.ProjectDAO;
import net.sf.ideais.apps.vtiger.PurchaseOrder;
import net.sf.ideais.apps.vtiger.PurchaseOrderDAO;
import net.sf.ideais.apps.vtiger.Vtiger;
import net.sf.ideais.util.conf.Configuration;
import net.sf.ideais.util.conf.HardCodedConfiguration;
import net.sf.ideais.util.patterns.DbDataSource;

public class Ideais
{
	public static final String VERSION = "200703XX";
	
	private static Configuration getDotProjectDefaultConfiguration()
	{
		String knownDbms = "mysql";
		String knownHostname = "localhost";
		String knownDatabase = "dotproject-dev";
		String knownUsername = "test";
		String knownPassword = "test";
		String knownWebAddress = "http://localhost/dotproject/";

		HardCodedConfiguration conf = new HardCodedConfiguration();
		conf.setProperty(DbDataSource.DBMS, knownDbms);
		conf.setProperty(DbDataSource.HOSTNAME, knownHostname);
		conf.setProperty(DbDataSource.DATABASE, knownDatabase);
		conf.setProperty(DbDataSource.USERNAME, knownUsername);
		conf.setProperty(DbDataSource.PASSWORD, knownPassword);
		conf.setProperty(WebApplication.ID_PROPERTY, knownWebAddress);
		
		return conf;
	}
	
	private static Configuration getVtigerDefaultConfiguration()
	{
		String knownDbms = "mysql";
		String knownHostname = "localhost";
		String knownDatabase = "vtiger5-dev";
		String knownUsername = "test";
		String knownPassword = "test";
		String knownWebAddress = "http://localhost/vtiger/";
	
		HardCodedConfiguration conf = new HardCodedConfiguration();
		conf.setProperty(DbDataSource.DBMS, knownDbms);
		conf.setProperty(DbDataSource.HOSTNAME, knownHostname);
		conf.setProperty(DbDataSource.DATABASE, knownDatabase);
		conf.setProperty(DbDataSource.USERNAME, knownUsername);
		conf.setProperty(DbDataSource.PASSWORD, knownPassword);
		conf.setProperty(WebApplication.ID_PROPERTY, knownWebAddress);
		
		return conf;
	}

	
	public static void main(String[] args)
	{
	    final Logger log = Logger.getLogger(Ideais.class.getName());
	    ApplicationManager appManager = null;
	    DotProject dpApp = null;
	    Vtiger vApp;

		System.out.println("IDEAIS Application Integrator - version " + Ideais.VERSION);
		System.out.println("Copyright (C) 2007 Marco Aurelio Graciotto Silva <magsilva@gmail.com>");
		System.out.println("IDEAIS Application Integrator comes with ABSOLUTELY NO WARRANTY;\n" +
				"for details read the license.");
		System.out.println("This is free software, and you are welcome to redistribute it\n" +
						"under the GPLv2 license");

		log.log(Level.INFO, "Starting life cycle controller");
		LifeCycleController controller = LifeCycleController.instance();
		log.log(Level.INFO, "Life cycle controller loaded");
		
		
		log.log(Level.INFO, "Starting application manager");
		appManager = ApplicationManager.instance();
		log.log(Level.INFO, "Application manager loaded");
		
	    log.log(Level.INFO, "Starting application adapters initialization");
	    log.log(Level.INFO, "Starting DotProject adapter");
	    dpApp = (DotProject) appManager.get(DotProject.class, getDotProjectDefaultConfiguration());
		log.log(Level.INFO, "Loaded adapter for DotProject " + dpApp.getVersion() + " at " + dpApp.getId());	
		
		log.log(Level.INFO, "Starting vTiger adapter");
	    vApp = (Vtiger) appManager.get(Vtiger.class, getVtigerDefaultConfiguration());
		log.log(Level.INFO, "Loaded adapter for vTiger " + vApp.getVersion() + " at " + vApp.getId());	
		log.log(Level.INFO, "Application adapters initialized");
		
		
		
		ProjectDAO dpProjectDao = (ProjectDAO) dpApp.getDAO(Project.class);
		Project dpProject = dpProjectDao.findById(1);
		controller.process(dpProject);
		
		PurchaseOrderDAO vtPurchaseDao = (PurchaseOrderDAO) vApp.getDAO(PurchaseOrder.class);
		PurchaseOrder vtPO = vtPurchaseDao.findById(80);
		controller.process(vtPO);
		
		dpProject.setDescription(dpProject.getDescription() + "teste 1 2 3");
		dpProjectDao.update(dpProject);
	}

}
