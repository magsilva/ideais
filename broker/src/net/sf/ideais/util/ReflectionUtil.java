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

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Java Reflection utilities.
 */
public final class ReflectionUtil
{
	/**
	 * We really don't want an instance of this class, so we create this
	 * private constructor.
	 */
	private ReflectionUtil()
	{
	}
	
	public static String PACKAGE_DELIMITER = ".";
	
	public static String CLASS_FILE_EXTENSION = ".class";

	public static String JAR_FILE_EXTENSION = ".jar";

	
	public static final class ClassFileFilter implements FileFilter
	{
		public boolean accept(File filename)
		{
			return filename.getName().endsWith(ReflectionUtil.CLASS_FILE_EXTENSION);
		}
	}
	
	public static final class DirectoryFilter implements FileFilter
	{
		public boolean accept(File filename)
		{
			return filename.isDirectory();
		}
	}

	private static String[] paths;
	static {
		Properties props = System.getProperties();
		List<String> paths = new ArrayList<String>();
		String[] defaultPaths = {"java.ext.dir", "java.endorsed.dirs", "java.class.path"};
		
		for (String pathProperty : defaultPaths) {
			String path = props.getProperty(pathProperty);
			if (path != null) {
				String[] splittedPaths = path.split((String)props.get("path.separator"));
				for (String str : splittedPaths) {
					paths.add(str);
				}
			}
		}
		
		ReflectionUtil.paths = paths.toArray(new String[0]);
	}
	
	
	/**
	 * Load a class.
	 * 
	 * @param name The class to be loaded. It must be the fully qualified
	 * class name (package.class, i.e. "java.lang.String".
	 * 
	 * @return The classes loaded if the action was successfull, NULL
	 * otherwise.
	 */
	public static Class loadClass(String name)
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
	
	public static Class[] findClasses(String packageName)
	{
		ArrayList<Class> result = new ArrayList<Class>();
		Class[] classes = null;
		
		for (String path : ReflectionUtil.paths) {
			if (path.endsWith(ReflectionUtil.JAR_FILE_EXTENSION)) {
				JarFile jar = null;
				try {
					jar = new JarFile(path);
				} catch (IOException e) {
				}
				classes = ReflectionUtil.findClasses(jar, packageName);
			} else {
				File dir = new File(path);
				classes = ReflectionUtil.findClasses(dir, packageName);
				
			}
			for (Class clazz : classes) {
				result.add(clazz);
			}
		}
				
		return result.toArray(new Class[0]);
	}

	private static Class[] findClasses(JarFile jar, String packageName)
	{
		Enumeration<JarEntry> entries = jar.entries();
		ArrayList<Class> classes = new ArrayList<Class>();
		boolean useFastMethod = true;

		if (StringUtil.isEmpty(packageName)) {
			packageName = "";
		}
		
		if (useFastMethod) {
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				String filename = entry.getName();
				String packageDirname = "";
				if (! StringUtil.isEmpty(packageName)) {
					packageDirname += packageName.replace(ReflectionUtil.PACKAGE_DELIMITER, File.separator);
				}
				if (filename.startsWith(packageDirname)) {
					String className = packageName + ReflectionUtil.PACKAGE_DELIMITER + filename;
					className = className.replaceAll(ReflectionUtil.CLASS_FILE_EXTENSION + "$", "");
					try {
						classes.add(Class.forName(className));
					} catch (ClassNotFoundException e) {
					}
				}
			}
		}
		return classes.toArray(new Class[0]);
	}

	private static Class[] findClasses(File file, String packageName)
	{
		File[] files = null;
		ArrayList<Class> classes = new ArrayList<Class>();
		boolean useFastMethod = true;
		file = file.getAbsoluteFile();
		
		if (StringUtil.isEmpty(packageName)) {
			packageName = "";
		}
		
		if (useFastMethod) {
			String packageDirname = file.getAbsolutePath() + File.separator;
			if (! StringUtil.isEmpty(packageName)) {
				packageDirname += packageName.replace(ReflectionUtil.PACKAGE_DELIMITER, File.separator);
			}
			File packageDir = new File(packageDirname);
			if (packageDir.exists()) {
				files = packageDir.listFiles(new ClassFileFilter());
				for (File f : files) {
					String className = packageName + ReflectionUtil.PACKAGE_DELIMITER + f.getName();
					className = className.replaceAll(ReflectionUtil.CLASS_FILE_EXTENSION + "$", "");
					try {
						classes.add(Class.forName(className));
					} catch (ClassNotFoundException e) {
					}
				}
			}
		}
		return classes.toArray(new Class[0]);
	}
		
	public static Class[] findClasses(Class<?> clazz)
	{
		ArrayList<Class> result = new ArrayList<Class>();
		Class[] classes = null;
		
		if (clazz == null) {
			return null;
		}
		
		for (String path : ReflectionUtil.paths) {
			if (path.endsWith(ReflectionUtil.JAR_FILE_EXTENSION)) {
				JarFile jar = null;
				try {
					jar = new JarFile(path);
				} catch (IOException e) {
				}
	//			classes = ReflectionUtil.findClasses(jar, clazz);
			} else {
				File dir = new File(path);
				classes = ReflectionUtil.findClasses(dir, null, clazz);
			}
			for (Class c : classes) {
				result.add(c);
			}
		}
				
		return result.toArray(new Class[0]);
	}
/*
	private static Class[] findClasses(JarFile jar, Class<?> clazz)
	{
		Enumeration<JarEntry> entries = jar.entries();
		ArrayList<Class> classes = new ArrayList<Class>();
		boolean useFastMethod = true;

		if (StringUtil.isEmpty(packageName)) {
			packageName = "";
		}
		
		if (useFastMethod) {
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				String filename = entry.getName();
				String packageDirname = "";
				if (! StringUtil.isEmpty(packageName)) {
					packageDirname += packageName.replace(ReflectionUtil.PACKAGE_DELIMITER, File.separator);
				}
				if (filename.startsWith(packageDirname)) {
					String className = packageName + ReflectionUtil.PACKAGE_DELIMITER + filename;
					className = className.replaceAll(ReflectionUtil.CLASS_FILE_EXTENSION + "$", "");
					try {
						classes.add(Class.forName(className));
					} catch (ClassNotFoundException e) {
					}
				}
			}
		}
		return classes.toArray(new Class[0]);
	}
	*/

	// TODO: Check why it's loading a class several times.
	private static Class[] findClasses(File packageRoot, File currentPackageDir, Class<?> clazz)
	{
		File[] files = null;
		ArrayList<Class> classes = new ArrayList<Class>();
	
		assert(packageRoot != null);
		assert(packageRoot.isDirectory());
		
		if (currentPackageDir == null) {
			currentPackageDir = packageRoot;
		}
		
		files = currentPackageDir.listFiles(new ClassFileFilter());
		if (files != null) {
			for (File f : files) {
				String absolutePackageRoot = packageRoot.getAbsolutePath() + File.separator;
				String absoluteCurrentPackageDir = currentPackageDir.getAbsolutePath();
				String packageName = absoluteCurrentPackageDir.replaceFirst(absolutePackageRoot, "");
				packageName = packageName.replaceAll(File.separator, ReflectionUtil.PACKAGE_DELIMITER);
				String className = packageName + ReflectionUtil.PACKAGE_DELIMITER + f.getName().replaceAll(ReflectionUtil.CLASS_FILE_EXTENSION + "$", "");
				try {
					Class<?> c = Class.forName(className);
					int modifiers = c.getModifiers();
					if (c.isArray() || c.isInterface() || Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)) {
						continue;
					}
					c.asSubclass(clazz);
					classes.add(c);
				} catch (ClassNotFoundException e) {
				} catch (ClassCastException e) {
				}
			}
		}

		files = currentPackageDir.listFiles(new DirectoryFilter());
		if (files != null) {
			for (File f : files) {
				for (Class c : ReflectionUtil.findClasses(packageRoot, f, clazz)) {
					classes.add(c);
				}
			}
		}

		if (files == null) {
			return classes.toArray(new Class[0]);
		}
		
		return classes.toArray(new Class[0]);
	}
}