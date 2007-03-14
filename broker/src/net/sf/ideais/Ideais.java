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

import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.ideais.apps.Application;
import net.sf.ideais.apps.WebApplication;
import net.sf.ideais.apps.dotproject.DotProject;
import net.sf.ideais.apps.dotproject.Project;
import net.sf.ideais.apps.dotproject.ProjectDAO;
import net.sf.ideais.apps.vtiger.PurchaseOrder;
import net.sf.ideais.apps.vtiger.PurchaseOrderDAO;
import net.sf.ideais.apps.vtiger.Vtiger;
import net.sf.ideais.util.ReflectionUtil;
import net.sf.ideais.util.conf.Configuration;
import net.sf.ideais.util.conf.HardCodedConfiguration;
import net.sf.ideais.util.patterns.DbDataSource;
import net.sf.ideais.util.patterns.HibernateEntityManagerDataSource;

public class Ideais
{
	/**
	* Commons Logging instance.
	*/
	private static final Log log = LogFactory.getLog(Ideais.class);

	private static final String VERSION = "200703XX";
	
	private ApplicationManager appManager;

	private LifeCycleController controller;
	
	/**
	 * Default configuration for DotProject. Just for testing purpose.
	 * 
	 * @return DotProject configuration.
	 */
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
	
	/**
	 * Default configuration for vTiger. Just for testing purpose.
	 * 
	 * @return vTiger configuration.
	 */
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

	/**
	 * Dump all known (and useful) environment settings.
	 */
	private void dumpEnvironmentSettings()
	{
		Properties props = System.getProperties();
		Map env = System.getenv();
		
		/**
		 * Describe the operational system.
		 *
		 * os.arch os.name os.version 
		 */
		log.debug("System: " + props.getProperty("os.arch") + " " + props.getProperty("os.name") + " " + props.getProperty("os.version"));
		
		/**
		 * Describe the Java Virtual Machine:
		 * 
		 * java.version (java.vm.specification.version)
		 * java.specification.version
		 * java.compiler
		 * java.vm.name
		 * java.vendor
		 */
		log.debug("Java Virtual Machine: " + props.getProperty("java.version") + "(" + props.getProperty("java.vm.specification.version") + ")");
		log.debug("Java specification supported: " + props.getProperty("java.specification.version"));
		if ("NONE".equals(props.getProperty("java.compiler"))) {
			log.debug("Java JIT compiler disabled");
		} else {
			if (props.getProperty("java.compiler") == null) {
				log.debug("Java JIT compiler enabled");			
			} else {
				log.debug("Java JIT compiler enabled (" + props.getProperty("java.compiler") + ")");
			}
		}
		log.debug("JVM name: " + props.getProperty("java.vm.name"));
		log.debug("JVM vendor: " + props.getProperty("java.vendor"));
		log.debug("JVM info: " + props.getProperty("java.vm.info"));
		
		/**
		 * Describe the visible packages.
		 * 
		 * java.class.path
		 * java.ext.dirs
		 */
		log.debug("Java classpath: " + props.getProperty("java.class.path"));
		log.debug("Java extensions dir: " + props.getProperty("props.ext.dirs"));
		
		/**
		 * Describe the I/O related properties.
		 * 
		 * java.io.tmpdir
		 * file.separator
		 * path.separator
		 * user.home
		 * user.dir
		 */ 
		log.debug("Dir for temporary files: " + props.getProperty("java.io.tmpdir"));
		log.debug("File separator: " + props.getProperty("file.separator"));
		log.debug("Path separator: " + props.getProperty("path.separator"));
		log.debug("User home directory: " + props.getProperty("user.home"));
		log.debug("User current working directory: " + props.getProperty("user.dir"));
		
		/**
		 * Describe the dynamic libraries properties (needed for JNI) and environment
		 * variables.
		 *
		 * Properties:
		 *  - java.library.path
		 * Environment variables:
		 *  - LD_PRELOAD
		 *  - LD_LIBRARY_PATH
		 *  - LD_BIND_NOW
		 */
		log.debug("Dynamic libraries (.so) path: " + props.getProperty("java.library.path"));
		log.debug("LD_LIBRARY_PATH: " + env.get("LD_LIBRARY_PATH"));
		log.debug("LD_BIND_NOW: " + env.get("LD_BIND_NOW"));
		log.debug("LD_PRELOAD: " + env.get("LD_PRELOAD"));
	}

	/**
	 * Print application (mandatory) information.
	 */
	private void printInfo()
	{
		System.out.println("IDEAIS Application Integrator - version " + Ideais.VERSION);
		System.out.println("Copyright (C) 2007 Marco Aurelio Graciotto Silva <magsilva@gmail.com>");
		System.out.println("This application comes with ABSOLUTELY NO WARRANTY; for details read the license.");
		System.out.println("This is free software. You're welcome to redistribute it under the GPLv2 license");
	}

	private void loadBinder()
	{
		log.info("Starting binder");
		log.info("Loaded binder");
	}
	
	/**
	 * Load the application adapters.
	 */
	private void loadApplicationAdapters()
	{
		Class[] apps = ReflectionUtil.findClasses(Application.class);
		for (Class app : apps) {
			// TODO: Remove this hardcoded configuration loading.
			if (app.equals(DotProject.class)) {
				appManager.get(DotProject.class, getDotProjectDefaultConfiguration());
			} else if (app.equals(Vtiger.class)) {
				appManager.get(Vtiger.class, getVtigerDefaultConfiguration());	
			} else {
				log.error("Tried to load unknown application adapter: " + app.getName());
			}
		}
	}
	
	public void doSomething()
	{
	    DotProject dpApp = (DotProject) appManager.get(DotProject.class, getDotProjectDefaultConfiguration());
	    Vtiger vApp = (Vtiger) appManager.get(Vtiger.class, getVtigerDefaultConfiguration());
	
		PurchaseOrderDAO vtPurchaseDao = (PurchaseOrderDAO) vApp.getDAO(PurchaseOrder.class);
		PurchaseOrder vtPO = vtPurchaseDao.findById(80);
		controller.process(vtPO);
		
		ProjectDAO dpProjectDao = (ProjectDAO) dpApp.getDAO(Project.class);
		Project dpProject = dpProjectDao.create();
		dpProject.setName(vtPO.getName());
		dpProject.setCompanyId(1);
		dpProject.setDescription("Description for Teste abcde");
		dpProject.setOwnerId(1);
		dpProject.setShortName("teste");
		dpProjectDao.update(dpProject);
		controller.process(dpProject);
		/*
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		// dpProjectDao.deleteById(dpProject.getId());
		*/
		// dpProject = dpProjectDao.findById(1);
		controller.process(dpProject);
		dpProject.setDescription(dpProject.getDescription() + "teste 1 2 3");
		dpProjectDao.update(dpProject);
		

	}

	
	
	public Ideais()
	{
		printInfo();
		dumpEnvironmentSettings();

		loadBinder();

		controller = LifeCycleController.instance();
		
		
		log.info("Starting application manager");
		appManager = ApplicationManager.instance();
		log.info("Application manager loaded");
		
	    log.info("Starting application adapters initialization");
	    loadApplicationAdapters();
		log.info("Application adapters initialized");

		
	}
	
		
	public static void main(String[] args)
	{
		Ideais ideais = new Ideais();
		ideais.doSomething();
   	}

}
