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

package net.sf.ideais.apps.dotproject;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

import net.sf.ideais.annotations.db.DbAnnotations;
import net.sf.ideais.util.AnnotationUtil;
import net.sf.ideais.util.ArrayUtil;
import net.sf.ideais.util.JavaBeanUtil;

public class DotProjectUtil
{
	/**
	 * Compile the prepared statement for inserting a project into the database.
	 *  
	 * @param table
	 * @param parameterCount
	 * @return
	 */
	final public static String createPstmtInsert(DotProjectObject obj)
	{
		return createPstmtInsert(obj.getClass());
		/*
		StringBuffer sb = new StringBuffer();
		String table = null;
		Map<String, Object> map = JavaBeanUtil.mapBeanUsingFields(obj);
		String[] fields;
		
		table = AnnotationUtil.getAnnotationValue(obj.getClass(), DbAnnotations.TABLE_ANNOTATION);
		sb.append("INSERT INTO ");
		sb.append(table);
		
		sb.append(" (");
		fields = map.keySet().toArray(new String[0]);
		
		if (fields.length == 0) {
			throw new IllegalArgumentException();
		}
		Arrays.sort(fields);
		// public static final Map<String, Object> mapBeanUsingFields(Object bean)
		for (int i = 0; i < fields.length; i++) {
			if (i != 0) {
				sb.append(", ");
			}
			sb.append(fields[i]);
		}
		
		sb.append(") values (");
		for (int i = 0; i < fields.length; i++) {
			if (i == 0) {
				sb.append("?");
			} else {
				sb.append(", ?");
			}
		}
		sb.append(")");
		
		return sb.toString();
		*/
	}

	/**
	 * Compile the prepared statement for inserting a project into the database.
	 *  
	 * @param table
	 * @param parameterCount
	 * @return
	 */
	final public static String createPstmtInsert(Class<? extends DotProjectObject> clazz)
	{
		StringBuffer sb = new StringBuffer();
		String table = null;
		Field[] beanFields = null;
		String[] dbFields = null;
		
		table = AnnotationUtil.getAnnotationValue(clazz, DbAnnotations.TABLE_ANNOTATION);
		sb.append("INSERT INTO ");
		sb.append(table);
		
		beanFields = AnnotationUtil.getAnnotatedProperties(clazz, DbAnnotations.PROPERTY_ANNOTATION);
		dbFields = new String[beanFields.length];
		for (int i = 0; i < beanFields.length; i++) {
			if (! beanFields[i].isAnnotationPresent(DbAnnotations.IDENTIFICATOR_ANNOTATION)) {
				dbFields[i] = AnnotationUtil.getAnnotationValue(beanFields[i], DbAnnotations.PROPERTY_ANNOTATION);
			}
		}
		dbFields = (String[])ArrayUtil.clean(dbFields);
		Arrays.sort(dbFields);
			
		sb.append(" (");
		for (int i = 0; i < dbFields.length; i++) {
			if (i != 0) {
				sb.append(", ");
			}
			sb.append(dbFields[i]);
		}
		
		sb.append(") values (");
		for (int i = 0; i < dbFields.length; i++) {
			if (i != 0) {
				sb.append(", ");
			}
			sb.append("?");
		}
		sb.append(")");

		return sb.toString();
	}


	/**
	 * Compile the prepared statement for updating a project into the database.
	 *  
	 * @param table
	 * @param parameterCount
	 * @return
	 */
	final public static String createPstmtUpdate(Class<? extends DotProjectObject> clazz)
	{
		StringBuffer sb = new StringBuffer();
		String table = null;
		Field[] beanFields = null;
		Field[] idFields = null;
		String[] dbFields = null;
		
		table = AnnotationUtil.getAnnotationValue(clazz, DbAnnotations.TABLE_ANNOTATION);
		sb.append("UPDATE ");
		sb.append(table);
		
		beanFields = AnnotationUtil.getAnnotatedProperties(clazz, DbAnnotations.PROPERTY_ANNOTATION);
		dbFields = new String[beanFields.length];
		for (int i = 0; i < beanFields.length; i++) {
			if (! beanFields[i].isAnnotationPresent(DbAnnotations.IDENTIFICATOR_ANNOTATION)) {
				dbFields[i] = AnnotationUtil.getAnnotationValue(beanFields[i], DbAnnotations.PROPERTY_ANNOTATION);
			}
			dbFields[i] = AnnotationUtil.getAnnotationValue(beanFields[i], DbAnnotations.PROPERTY_ANNOTATION);
		}
		dbFields = (String[])ArrayUtil.clean(dbFields);
		Arrays.sort(dbFields);

		sb.append(" SET (");
		for (int i = 0; i < dbFields.length; i++) {
			if (i != 0) {
				sb.append(", ");
			}
			sb.append(dbFields[i]);
			sb.append("=?");
		}
		sb.append(")");
		
		sb.append(" WHERE ");
		
		table = AnnotationUtil.getAnnotationValue(clazz, DbAnnotations.TABLE_ANNOTATION);
		idFields = AnnotationUtil.getAnnotatedProperties(clazz, DbAnnotations.IDENTIFICATOR_ANNOTATION);
		Arrays.sort(idFields);
		for (int i = 0; i < idFields.length; i++) {
			if (i != 0) {
				sb.append(" AND ");
			}
			sb.append(AnnotationUtil.getAnnotationValue(idFields[i], DbAnnotations.PROPERTY_ANNOTATION));
			sb.append("=?");
		}

		return sb.toString();
	}
	
	/**
	 * Compile the prepared statement for updating a project into the database.
	 *  
	 * @param table
	 * @param parameterCount
	 * @return
	 */
	final public static String createPstmtUpdate(DotProjectObject obj)
	{
		return createPstmtUpdate(obj.getClass());
	}

	
	
	/**
	 * Compile the prepared statement for deleting an object from the database.
	 * 
	 * @param clazz The class of the DotProject's object the statement will be created to.
	 * @param fields Fields used to find the objects to be deleted.
	 *  
	 * @return The SQL prepared statement to read an object of the given class.
	 */
	final public static String createPstmtDeleteId(Class<? extends DotProjectObject> clazz)
	{
		StringBuffer sb = new StringBuffer();
		String table = null;
		String idField = null;
		Field[] fields = null;
		
		table = AnnotationUtil.getAnnotationValue(clazz, DbAnnotations.TABLE_ANNOTATION);
		fields = AnnotationUtil.getAnnotatedProperties(clazz, DbAnnotations.IDENTIFICATOR_ANNOTATION);
		for (Field f : fields) {
			idField = AnnotationUtil.getAnnotationValue(f, DbAnnotations.PROPERTY_ANNOTATION);
		}

		sb.append("DELETE FROM ");
		sb.append(table);
		sb.append(" WHERE ");
		sb.append(idField);
		sb.append("=?");
		
		return sb.toString();
	}
	
	/**
	 * Compile the prepared statement for deleting an object from the database.
	 * 
	 * @param clazz The class of the DotProject's object the statement will be created to.
	 * @param fields Fields used to find the objects to be deleted.
	 *  
	 * @return The SQL prepared statement to read an object of the given class.
	 */
	/*
	final public static String createPreparedStatementDeleteId(DotProjectObject object)
	{
		if ((parameterCount % 2) == 0) {
			parameterCount = parameterCount / 2;
		}

		for (int i = 0; i < parameterCount; i++) {
			if (i == 0) {
				sb.append("?=?");
			} else {
				sb.append(", ?=?");
			}
		}

		return sb.toString();
	}
	*/

	/**
	 * Compile the prepared statement for loading an object from the database.
	 * 
	 * @param clazz The class of the DotProject's object the statement will be created to. 
	 * @return The SQL prepared statement to read an object of the given class.
	 */
	final public static String createPstmtSelectId(Class<? extends DotProjectObject> clazz)
	{
		StringBuffer sb = new StringBuffer();
		String table = null;
		String idField = null;
		Field[] fields = null;

		table = AnnotationUtil.getAnnotationValue(clazz, DbAnnotations.TABLE_ANNOTATION);
		fields = AnnotationUtil.getAnnotatedProperties(clazz, DbAnnotations.IDENTIFICATOR_ANNOTATION);
		for (Field f : fields) {
			idField = AnnotationUtil.getAnnotationValue(f, DbAnnotations.PROPERTY_ANNOTATION);
		}

		sb.append("SELECT * FROM ");
		sb.append(table);
		sb.append(" WHERE ");
		sb.append(idField);
		sb.append("=?");

		return sb.toString();
	}

	
	final public static String createStatementSelectExample(Class dpobject)
	{
		StringBuffer sb = new StringBuffer();
		String table = null;
		String idField = null;
		Field[] fields = null;

		table = AnnotationUtil.getAnnotationValue(dpobject, DbAnnotations.TABLE_ANNOTATION);
		fields = AnnotationUtil.getAnnotatedProperties(dpobject, DbAnnotations.IDENTIFICATOR_ANNOTATION);
		for (Field f : fields) {
			idField = AnnotationUtil.getAnnotationValue(f.getClass(), DbAnnotations.IDENTIFICATOR_ANNOTATION);
		}

		sb.append("SELECT * FROM ");
		sb.append(table);
		sb.append(" WHERE ");
		sb.append(idField);
		sb.append("=?");

		return sb.toString();
	}
}