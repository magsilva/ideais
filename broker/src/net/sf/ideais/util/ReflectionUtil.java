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


package net.sf.ideais.util;

/**
 * Java Reflection utilities.
 */
public final class ReflectionUtil
{
	/**
	 * Load a class.
	 * 
	 * @param name The class to be loaded. It must be the fully qualified
	 * class name (package.class, i.e. "java.lang.String".
	 * 
	 * @return The classes loaded if the action was successfull, NULL
	 * otherwise.
	 */
	public final static Class loadClass(String name)
	{
		Class c = null;
		try {
			c = Class.forName(name);
		} catch (ClassNotFoundException cnfe) {
			ExceptionUtil.dumpException(cnfe);
		} catch (ExceptionInInitializerError eiie) {
			ExceptionUtil.dumpException(eiie);
		} catch (LinkageError le) {
			ExceptionUtil.dumpException(le);
		}
		
		return c;
	}
}