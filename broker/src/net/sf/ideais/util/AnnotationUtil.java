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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Utilities to use annotations at runtime.
 */
public class AnnotationUtil
{
	/**
	 * Java's annotation default value.
	 */
	public static final String DEFAULT_PROPERTY = "value";
	
	/**
	 * Get the value set for an annotation with a single element.
	 * 
	 * @param obj The annotated object.
	 * @param ann The annotation we need to read. 
	 * @return The annotation's value for the given object.
	 */
	public static final String getAnnotationValue(Object obj, Class ann)
	{
		return getAnnotationValue(obj, ann, DEFAULT_PROPERTY);
	}
	
	/**
	 * Get the value set for an annotation with a single element.
	 * 
	 * @param obj The annotated object.
	 * @param ann The annotation we need to read.
	 * @param field The annotation's value we need to read.
	 *  
	 * @return The annotation's value for the given object.
	 */	
	public static final String getAnnotationValue(Object obj, Class ann, String field)
	{
		Class clazz = obj.getClass();
		Annotation a = clazz.getAnnotation(ann);
		String value = null;

		if (a == null) {
			throw new IllegalArgumentException();
		}

		try {
			Method m  = ann.getDeclaredMethod(field, (Class[])null);
			value = (String) m.invoke(a, (Object [])null);
		} catch (NoSuchMethodException nsme) {
		} catch (IllegalAccessException iae) {
		} catch (InvocationTargetException ite) {
		}
		
		return value;
	}
	
	/**
	 * Check if the object has annotations.
	 * 
	 * @param obj Object to be checked.
	 * @return True if the object is annotated, False otherwise.
	 */
	public static final boolean hasAnnotations(Object obj)
	{
		Class clazz = obj.getClass();
		return hasAnnotations(clazz);
	}
	
	/**
	 * Check if the class has annotations.
	 * 
	 * @param clazz Class to be checked.
	 * @return True if the class is annotated, False otherwise.
	 */
	public static final boolean hasAnnotations(Class clazz)
	{
		if (clazz.getAnnotations().length != 0) {
			return true;
		}
		return false;
	}

	/**
	 * Get the bean properties (fields) that are annotated.
	 * 
	 * @param obj Object to be inspected.
	 * @return The fields that are annotated.
	 */
	public static final Field[] getAnnotatedProperties(Object obj)
	{
		return getAnnotatedProperties(obj, null);
	}

	/**
	 * Get the bean properties (fields) that are annotated with an specific metadata.
	 * 
	 * @param obj Object to be inspected.
	 * @param annClass The annotation.
	 * @return The fields that are annotated.
	 */
	public static final Field[] getAnnotatedProperties(Object obj, Class annClass)
	{
		return getAnnotatedProperties(obj.getClass(), null);
	}

	/**
	 * Get the bean properties (fields) that are annotated.
	 * 
	 * @param clazz Class to be inspected.
	 * @return The fields that are annotated.
	 */
	public static final Field[] getAnnotatedProperties(Class clazz)
	{
		return getAnnotatedProperties(clazz, null);	
	}
	
	/**
	 * Get the bean properties (fields) that are annotated with an specific metadata.
	 * 
	 * @param clazz Class to be inspected.
	 * @param annClass The annotation.
	 * @return The fields that are annotated.
	 */
	public static final Field[] getAnnotatedProperties(Class clazz, Class annClass)
	{
		ArrayList<Field> properties = new ArrayList<Field>();
		Field[] fields = clazz.getDeclaredFields();
		
		for (Field f : fields) {
			Annotation[] annotations = f.getAnnotations();
			for (Annotation ann : annotations) {
				if (ann.annotationType() == annClass || annClass == null) {
					properties.add(f);
				}
			}
		}
		
		return properties.toArray(new Field[0]);
	}
}