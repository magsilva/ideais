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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.ideais.apps.Application;
import net.sf.ideais.util.conf.Configuration;

/**
 * The ApplicationManager controls the loading of application adapter's.
 * Every application is unique (if you try to load an application with the
 * sama configuration, it will return the application previously loaded).
 */
public class ApplicationManager
{
	/**
	* Commons Logging instance.
	*/
	private static final Log log = LogFactory.getLog(ApplicationManager.class);

	/**
	 * Singleton implementation for the ApplicationManager.
	 */
	private static ApplicationManager instance; 
	
	/**
	 * Applications managed.
	 */
	private ArrayList<Application> apps;
	
	/**
	 * Singleton implementation for the ApplicationManager.
	 */
	private ApplicationManager()
	{
		apps = new ArrayList<Application>();
	}

	/**
	 * Load an application adapter.
	 * 
	 * @param clazz The class for the application adapter.
	 * @param conf The configuration to be used to load the application adapter.
	 * @return The application adapter or null if couldn't load any.
	 */
	private Application loadApplication(Class<? extends Application> clazz, Configuration conf)
	{
		Application app = null;
	    log.debug("Loading " + clazz.getName() + " application adapter");
		
		try {
			Constructor<? extends Application> constructor = clazz.getConstructor(Configuration.class);
			app = constructor.newInstance(conf);
		} catch (NoSuchMethodException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}

		if (app == null) {
			log.error("Couldn't load " + clazz.getName() + " application adapter");
		} else {
			log.debug("Loaded application adapter " + clazz.getName());
		}
		
		return app;
	}

	/**
	 * Register an application. We can assure that no duplicated application adapter
	 * (same class and configuration) will be registered.
	 * 
	 * @param app The application to be registered.
	 * @return The registered application. If there was an already running application
	 * adapter (with the same configuration as app), that application adapter is
	 * returned instead of the one given as argument.
	 */
	private Application registerApplication(Application app)
	{
	    log.debug("Registering " + app.getClass().getName() + " application adapter");
		
		for (Application tmp : apps) {
			if (tmp.getId().equals(app.getId())) {
				log.debug("Ignored registration request: " + app.getClass().getName() + " was already registered");	
				return tmp;
			}
		}

		apps.add(app);
	    log.debug("Registered application adapter " + app.getClass().getName());
		return app;
	}

	
	/**
	 * Get the instance of the ApplicationManager.
	 */
	public synchronized static ApplicationManager instance()
	{
		if (instance == null) {
			instance = new ApplicationManager();
		}
		return instance;
	}
	
	/**
	 * Get an instance of the given application. The ApplicationManager ensures that
	 * there's only one application with a given configuration running.
	 * 
	 * @param clazz The application adapter.
	 * @param conf The configuration to be used when loading the application adapter.
	 * @return
	 */
	public synchronized Application get(Class<? extends Application> clazz, Configuration conf)
	{
	    log.info("Getting adapter for " + clazz.getName());
	    Application app = null;
	    
		if (clazz == null) {
			throw new IllegalArgumentException("Invalid application adapter " + clazz);
		}
		app = loadApplication(clazz, conf);
		
		if (app == null) {
			throw new IllegalArgumentException("Invalid application adapter " + clazz);
		}
		
		app = registerApplication(app);
	    log.info("Got adapter for " + clazz.getName() + " " + app.getVersion() + " at " + app.getId());			
		return app;
	}
}