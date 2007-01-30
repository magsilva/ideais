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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for JavaBeans.
 */
public class JavaBeanUtil
{
	/**
	 * Prefix for methods that read a JavaBean property.
	 */
	public static final String GETTER = "get";
	
	/**
	 * Prefix for methods that write a JavaBean property.
	 */
	public static final String SETTER = "get";

	/**
	 * Suffix for class attributes that contains the name of a class property.
	 */
	public static final String FIELD_IDENTIFIER = "_FIELD";
	
	/**
	 * Properties to be ignored (actually default Java object's properties.
	 */
	public static final String[] IGNORED_PROPERTIES = {
		"class"
	};
	
	public static final String[] getDefaultPropertiesName()
	{
		ArrayList<String> props = new ArrayList<String>();
		Object bean = new Object();
		Method[] methods = bean.getClass().getMethods();
		
		for (Method m : methods) {
			if (m.getName().startsWith(GETTER)) {
				String key = m.getName();
				String temp = key.substring(GETTER.length());
				key = temp.substring(0, 1).toLowerCase() + temp.substring(1);
				props.add(key);
			}
		}
		return props.toArray(new String[0]);
	}
	
	
	/**
	 * Map a JavaBean to a Map. The map's keys are the JavaBeans' properties'
	 * names (Strings) and the maps' values the properties' values (Objects).
	 * 
	 * @param bean The JavaBean to be mapped.
	 * 
	 * @return The mapping.
	 */
	public static final Map mapBean(Object bean)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		Method[] methods = bean.getClass().getMethods();
		for (Method m : methods) {
			if (m.getName().startsWith(GETTER)) {
				String key = m.getName();
				String temp = key.substring(GETTER.length());
				key = temp.substring(0, 1).toLowerCase() + temp.substring(1);

				// Actually, we should be using the getDefaultPropertiesName() method
				// to get the properties to be ignored, but we are faster this way.
				if (! ArrayUtil.has(IGNORED_PROPERTIES, key)) {
					try {
						Object value = m.invoke(bean, (Object [])null); 
						map.put(key, value);
					} catch (IllegalAccessException iae) {
					} catch (InvocationTargetException ite) {
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * Map a JavaBean to a Map. The map's keys are the fields set at the JavaBean
	 * (public static final Strings) whose names matches the GETTERs names. The map's
	 * values are the properties' values (Objects).
	 * 
	 * @param bean The JavaBean to be mapped.
	 * 
	 * @return The mapping.
	 */
	public static final Map mapBeanUsingFields(Object bean)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, String> fieldsMap = new HashMap<String, String>();
		Field[] fields = bean.getClass().getDeclaredFields();
		Method[] methods = bean.getClass().getMethods();
		
		for (Field f : fields) {
			String key = null;
			String value = null;
			// If we have annotated the class, that's the way to go.
			if (f.isAnnotationPresent(net.sf.ideais.Field.class)) {
				net.sf.ideais.Field ann = (net.sf.ideais.Field)ArrayUtil.find(f.getAnnotations(), net.sf.ideais.Field.class);
				key = ann.name();
				try {
					value = (String) f.get(bean);					
				} catch (IllegalAccessException iae) {
				}
			// Otherwise, stick with that old-fashioned method that relays on Java reflection features.
			} else {
				String fieldName = f.getName();
				if (fieldName.endsWith(FIELD_IDENTIFIER)) {
					Class type = f.getType();
					if (type == String.class) {
						int mode = f.getModifiers();
						if (Modifier.isFinal(mode) && Modifier.isStatic(mode) && Modifier.isPublic(mode)) {
							try {
								key = fieldName.substring(0, fieldName.length() - FIELD_IDENTIFIER.length());
								value = (String) f.get(bean);
							} catch (IllegalAccessException iae) {
							}
						}
					}
				}
			}
			fieldsMap.put(key, value);
		}
		
		for (Method m : methods) {
			if (m.getName().startsWith(GETTER)) {
				String key = m.getName();
				String field = null;
				
				key = key.substring(GETTER.length());
				field = StringUtil.findSimilar(fieldsMap.keySet(), key);
				if (field != null) {
					key = fieldsMap.get(field);
					try {
						Object value = m.invoke(bean, (Object [])null); 
						map.put(key, value);
					} catch (IllegalAccessException iae) {
					} catch (InvocationTargetException ite) {
					}
				}
			}
		}
		return map;
	}
}
