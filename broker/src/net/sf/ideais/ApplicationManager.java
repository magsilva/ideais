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
		Application app = null;
		try {
			Constructor<? extends Application> constructor = clazz.getConstructor(Configuration.class);
			app = constructor.newInstance(conf);
		} catch (NoSuchMethodException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	
		for (Application tmp : apps) {
			if (tmp.getId().equals(app.getId())) {
				return tmp;
			}
		}
		
		apps.add(app);
		return app;
	}
}