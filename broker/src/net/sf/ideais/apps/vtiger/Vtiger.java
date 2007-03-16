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


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import net.sf.ideais.apps.Application;
import net.sf.ideais.apps.WebApplication;
import net.sf.ideais.apps.Version;
import net.sf.ideais.util.conf.Configuration;
import net.sf.ideais.util.conf.ConfigurationMap;
import net.sf.ideais.util.patterns.DAO;

/**
 * Represent a DotProject application instance.
 */
public class Vtiger implements WebApplication
{
	private ConfigurationMap conf;

	private VtigerVersion version; 

	public Vtiger(Configuration conf)
	{
	    setConfiguration(conf);
	    version = new VtigerVersion("5.0.2");	
	}

	private void setConfiguration(Configuration conf)
	{
		ConfigurationMap tmpConfig = null;
		
		if (! (conf instanceof ConfigurationMap)) {
			throw new IllegalArgumentException("Invalid configuration");
		}
		tmpConfig = (ConfigurationMap) conf;

		if (tmpConfig.getProperty("address") == null) {
			throw new IllegalArgumentException("Missing Web application address");
		}
		this.conf = tmpConfig;
	}
	
	public Configuration getConfiguration()
	{
		return conf;
	}
	
	/**
	 * Get the version of the application.
	 * 
	 * @return Application's version.
	 */
	public Version getVersion()
	{
		return version;
	}
	
	public String getId()
	{
		return (String) conf.getProperty("address");
	}
	
	public boolean isCompatible(Application app)
	{
		if (! (app instanceof Vtiger)) {
			throw new IllegalArgumentException();
		}
		
		Vtiger vApp = (Vtiger) app;
		VtigerVersion version = (VtigerVersion) vApp.getVersion();
		if (getVersion().equals(version)) {
			return true;
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public DAO getDAO(Class<? extends VtigerObject> clazz)
	{
		String daoName = clazz.getName() + "DAO";
		Class<? extends DAO<VtigerObject, Integer>> daoClass = null;
		DAO dao = null;

		try {
			daoClass = (Class<? extends DAO<VtigerObject, Integer>>) Class.forName(daoName);
		} catch (ClassNotFoundException e1) {
			throw new IllegalArgumentException();
		}

		
		try {
			Constructor<DAO> constructor = (Constructor<DAO>) daoClass.getConstructor(Configuration.class);
			dao = constructor.newInstance(conf);
		} catch (NoSuchMethodException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		
		return dao;
	}
}