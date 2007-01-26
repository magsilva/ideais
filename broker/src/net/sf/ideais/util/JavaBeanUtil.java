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
	 * Map a JavaBean to a Map. The map's keys are the JavaBeans' properties'
	 * names and the maps' values the properties' values.
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
				key = key.substring(GETTER.length());
				try {
					map.put(key, m.invoke(bean));
				} catch (IllegalAccessException iae) {
				} catch (InvocationTargetException ite) {
				}
			}
		}
		return map;
	}
	
	public static final Map mapBeanUsingFields(Object bean)
	{
		return mapBeanUsingFields(bean, null);
	}

	public static final Map mapBeanUsingPrefixedFields(Object bean, String prefix)
	{
		HashMap<Integer, String> options = new HashMap<Integer, String>();
		options.put(StringUtil.PREFIX, prefix);
		return mapBeanUsingFields(bean, options);
	}

	
	public static final Map mapBeanUsingFields(Object bean, Map<Integer, String> options)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, String> fieldsMap = new HashMap<String, String>();
		Field[] fields = bean.getClass().getFields();
		Method[] methods = bean.getClass().getMethods();
		
		for (Field f : fields) {
			Class type = f.getType();
			if (type == String.class) {
				int mode = f.getModifiers();
				if (Modifier.isFinal(mode) && Modifier.isStatic(mode) && Modifier.isPublic(mode)) {
					try {
						String value = (String) f.get(bean);
						fieldsMap.put(f.getName(), value);
					} catch (IllegalAccessException iae) {
					}
				}
			}
		}
		
		for (Method m : methods) {
			if (m.getName().startsWith(GETTER)) {
				String key = m.getName();
				String field = null;
				
				key = key.substring(GETTER.length());
				field = StringUtil.findSimilar(fieldsMap.keySet(), key, options);
				if (field != null) {
					key = field;
				}
				try {
					Object value = m.invoke(bean);
					map.put(key, value);
				} catch (IllegalAccessException iae) {
				} catch (InvocationTargetException ite) {
				}
			}
		}
		return map;
	}

}
