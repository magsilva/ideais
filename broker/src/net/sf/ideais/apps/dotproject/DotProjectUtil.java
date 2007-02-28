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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import net.sf.ideais.util.AnnotationUtil;
import net.sf.ideais.util.ArrayUtil;
import net.sf.ideais.util.annotations.DbAnnotations;

public class DotProjectUtil
{
	/**
	 * Compile the prepared statement for inserting a project into the database. The fields
	 * are sorted before writing the statement.
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
		
		beanFields = AnnotationUtil.getAnnotatedFields(clazz, DbAnnotations.PROPERTY_ANNOTATION);
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
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(dbFields[i]);
		}
		
		sb.append(") values (");
		for (int i = 0; i < dbFields.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append("?");
		}
		sb.append(")");

		return sb.toString();
	}

	/**
	 * Compile the prepared statement for inserting a project into the database.
	 */
	final public static PreparedStatement createPstmtInsert(Connection conn, DotProjectObject obj)
	{
		Class<? extends DotProjectObject> clazz = obj.getClass();
		StringBuffer sb = new StringBuffer();
		String table = null;
		Field[] beanFields = null;
		String[] dbFields = null;
		Object[] dbValues = null;
		PreparedStatement stmt = null;
		
		table = AnnotationUtil.getAnnotationValue(clazz, DbAnnotations.TABLE_ANNOTATION);
		sb.append("INSERT INTO ");
		sb.append(table);
		
		beanFields = AnnotationUtil.getAnnotatedFields(clazz, DbAnnotations.PROPERTY_ANNOTATION);
		dbFields = new String[beanFields.length];
		dbValues = new Object[beanFields.length];
		for (int i = 0; i < beanFields.length; i++) {
			// Identificator fields are automatically set by the database.
			if (beanFields[i].isAnnotationPresent(DbAnnotations.IDENTIFICATOR_ANNOTATION)) {
				continue;
			}

			dbFields[i] = AnnotationUtil.getAnnotationValue(beanFields[i], DbAnnotations.PROPERTY_ANNOTATION);
			try {
				dbValues[i] = beanFields[i].get(obj);
			} catch (Exception iae) {
				dbFields[i] = null;
				dbValues[i] = null;
			}
		}
		dbFields = ArrayUtil.clean(dbFields);
		dbValues = ArrayUtil.clean(dbValues);
			
		sb.append(" (");
		for (int i = 0; i < dbFields.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(dbFields[i]);
		}
		
		sb.append(") values (");
		for (int i = 0; i < dbFields.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append("?");
		}
		sb.append(")");

		try {
			stmt = conn.prepareStatement(sb.toString());
			for (int i = 1; i <= dbValues.length; i++) {
				stmt.setObject(i, dbValues[i - 1]);
			}
		} catch (SQLException se) {
			stmt = null;
		}
		
		return stmt;
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
		
		beanFields = AnnotationUtil.getAnnotatedFields(clazz, DbAnnotations.PROPERTY_ANNOTATION);
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
		idFields = AnnotationUtil.getAnnotatedFields(clazz, DbAnnotations.IDENTIFICATOR_ANNOTATION);
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
	 * Compile the prepared statement for inserting a project into the database.
	 */
	final public static PreparedStatement createPstmtUpdate(Connection conn, DotProjectObject obj)
	{
		Class<? extends DotProjectObject> clazz = obj.getClass();
		StringBuffer sb = new StringBuffer();
		String table = null;
		Field[] beanFields = null;
		String[] dbFields = null;
		Object[] dbValues = null;
		PreparedStatement stmt = null;
		
		table = AnnotationUtil.getAnnotationValue(clazz, DbAnnotations.TABLE_ANNOTATION);
		sb.append("UPDATE ");
		sb.append(table);
		
		beanFields = AnnotationUtil.getAnnotatedFields(clazz, DbAnnotations.PROPERTY_ANNOTATION);
		dbFields = new String[beanFields.length];
		dbValues = new Object[beanFields.length];
		for (int i = 0; i < beanFields.length; i++) {
			// Identificator fields cannot be changed when updating.
			if (beanFields[i].isAnnotationPresent(DbAnnotations.IDENTIFICATOR_ANNOTATION)) {
				continue;
			}

			dbFields[i] = AnnotationUtil.getAnnotationValue(beanFields[i], DbAnnotations.PROPERTY_ANNOTATION);
			try {
				dbValues[i] = beanFields[i].get(obj);
			} catch (IllegalAccessException iae) {
				dbFields[i] = null;
				dbValues[i] = null;
			}
		}
		dbFields = ArrayUtil.clean(dbFields);
		dbValues = ArrayUtil.clean(dbValues);
			
		sb.append(" SET (");
		for (int i = 0; i < dbFields.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(dbFields[i]);
		}
		sb.append(")");

		// Update restriction on identificator
		sb.append(" WHERE ");
		beanFields = AnnotationUtil.getAnnotatedFields(clazz, DbAnnotations.IDENTIFICATOR_ANNOTATION);
		dbFields = new String[beanFields.length];
		dbValues = new Object[beanFields.length];
		for (int i = 0; i < beanFields.length; i++) {
			dbFields[i] = AnnotationUtil.getAnnotationValue(beanFields[i], DbAnnotations.PROPERTY_ANNOTATION);
			try {
				dbValues[i] = beanFields[i].get(obj);
			} catch (Exception iae) {
				dbFields[i] = null;
				dbValues[i] = null;
			}
		}
		dbFields = ArrayUtil.clean(dbFields);
		dbValues = ArrayUtil.clean(dbValues);

		for (int i = 0; i < dbFields.length; i++) {
			if (i != 0) {
				sb.append(" AND ");
			}
			sb.append(dbFields[i]);
			sb.append("=?");
		}			
						
		try {
			stmt = conn.prepareStatement(sb.toString());
			for (int i = 1; i <= dbValues.length; i++) {
				stmt.setObject(i, dbValues[i - 1]);
			}
		} catch (SQLException se) {
			stmt = null;
		}
		
		return stmt;
	}


	/**
	 * Compile the prepared statement for deleting an object from the database.
	 * 
	 * @param clazz The class of the DotProject's object the statement will be created to.
	 * @param fields Fields used to find the objects to be deleted.
	 *  
	 * @return The SQL prepared statement to read an object of the given class.
	 */
	final public static String createPstmtDeleteById(Class<? extends DotProjectObject> clazz)
	{
		StringBuffer sb = new StringBuffer();
		String table = null;
		String idField = null;
		Field[] fields = null;
		
		table = AnnotationUtil.getAnnotationValue(clazz, DbAnnotations.TABLE_ANNOTATION);
		fields = AnnotationUtil.getAnnotatedFields(clazz, DbAnnotations.IDENTIFICATOR_ANNOTATION);
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
	final public static PreparedStatement createPstmtDeleteById(Connection conn, DotProjectObject obj)
	{
		Class<? extends DotProjectObject> clazz = obj.getClass();
		StringBuffer sb = new StringBuffer();
		String table = null;
		Field[] beanFields = null;
		String[] dbFields = null;
		Object[] dbValues = null;
		PreparedStatement stmt = null;
		
		table = AnnotationUtil.getAnnotationValue(clazz, DbAnnotations.TABLE_ANNOTATION);
		sb.append("DELETE FROM ");
		sb.append(table);

		beanFields = AnnotationUtil.getAnnotatedFields(clazz, DbAnnotations.IDENTIFICATOR_ANNOTATION);
		dbFields = new String[beanFields.length];
		dbValues = new Object[beanFields.length];
		for (int i = 0; i < beanFields.length; i++) {
			dbFields[i] = AnnotationUtil.getAnnotationValue(beanFields[i], DbAnnotations.PROPERTY_ANNOTATION);
			try {
				dbValues[i] = beanFields[i].get(obj);
			} catch (Exception iae) {
				dbFields[i] = null;
				dbValues[i] = null;
			}
		}
		dbFields = ArrayUtil.clean(dbFields);
		dbValues = ArrayUtil.clean(dbValues);

		sb.append(" WHERE ");
		for (int i = 0; i < dbFields.length; i++) {
			if (i != 0) {
				sb.append(" AND ");
			}
			sb.append(dbFields[i]);
			sb.append("=?");
		}			

		try {
			stmt = conn.prepareStatement(sb.toString());
			for (int i = 1; i <= dbValues.length; i++) {
				stmt.setObject(i, dbValues[i - 1]);
			}
		} catch (SQLException se) {
			stmt = null;
		}
		

		return stmt;
	}

	
	/**
	 * Compile the prepared statement for deleting an object from the database.
	 * 
	 * @param clazz The class of the DotProject's object the statement will be created to.
	 * @param fields Fields used to find the objects to be deleted.
	 *  
	 * @return The SQL prepared statement to read an object of the given class.
	 */
	final public static String createPstmtDeleteByExample(Class<? extends DotProjectObject> clazz)
	{
		StringBuffer sb = new StringBuffer();
		String table = null;
		Field[] beanFields = null;
		String[] dbFields = null;

		table = AnnotationUtil.getAnnotationValue(clazz, DbAnnotations.TABLE_ANNOTATION);
		sb.append("DELETE FROM ");
		sb.append(table);

		beanFields = AnnotationUtil.getAnnotatedFields(clazz, DbAnnotations.PROPERTY_ANNOTATION);
		dbFields = new String[beanFields.length];
		for (int i = 0; i < beanFields.length; i++) {
			dbFields[i] = AnnotationUtil.getAnnotationValue(beanFields[i], DbAnnotations.PROPERTY_ANNOTATION);
		}
		dbFields = ArrayUtil.clean(dbFields);

		sb.append(" WHERE ");
		for (int i = 0; i < dbFields.length; i++) {
			if (i != 0) {
				sb.append(" AND ");
			}
			sb.append(dbFields[i]);
			sb.append("=?");
		}
		
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
	final public static PreparedStatement createPstmtDeleteByExample(Connection conn, DotProjectObject obj)
	{
		Class<? extends DotProjectObject> clazz = obj.getClass();
		StringBuffer sb = new StringBuffer();
		String table = null;
		Field[] beanFields = null;
		PreparedStatement stmt = null;
		String[] dbFields = null;
		Object[] dbValues = null;

		table = AnnotationUtil.getAnnotationValue(clazz, DbAnnotations.TABLE_ANNOTATION);
		sb.append("DELETE FROM ");
		sb.append(table);

		beanFields = AnnotationUtil.getAnnotatedFields(clazz, DbAnnotations.PROPERTY_ANNOTATION);
		dbFields = new String[beanFields.length];
		dbValues = new Object[beanFields.length];
		for (int i = 0; i < beanFields.length; i++) {
			dbFields[i] = AnnotationUtil.getAnnotationValue(beanFields[i], DbAnnotations.PROPERTY_ANNOTATION);
			try {
				dbValues[i] = beanFields[i].get(obj);
			} catch (Exception iae) {
				dbFields[i] = null;
				dbValues[i] = null;
			}
		}
		dbFields = ArrayUtil.clean(dbFields);
		dbValues = ArrayUtil.clean(dbValues);

		sb.append(" WHERE ");
		for (int i = 0; i < dbFields.length; i++) {
			if (i != 0) {
				sb.append(" AND ");
			}
			sb.append(dbFields[i]);
			sb.append("=?");
		}
		
		try {
			stmt = conn.prepareStatement(sb.toString());
			for (int i = 1; i <= dbValues.length; i++) {
				stmt.setObject(i, dbValues[i - 1]);
			}
		} catch (SQLException se) {
			stmt = null;
		}
		
		return stmt;
	}

		
	/**
	 * Compile the prepared statement for loading an object from the database.
	 * 
	 * @param clazz The class of the DotProject's object the statement will be created to. 
	 * @return The SQL prepared statement to read an object of the given class.
	 */
	final public static String createPstmtSelectById(Class<? extends DotProjectObject> clazz)
	{
		StringBuffer sb = new StringBuffer();
		String table = null;
		String idField = null;
		Field[] fields = null;

		table = AnnotationUtil.getAnnotationValue(clazz, DbAnnotations.TABLE_ANNOTATION);
		fields = AnnotationUtil.getAnnotatedFields(clazz, DbAnnotations.IDENTIFICATOR_ANNOTATION);
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

	/**
	 * Compile the prepared statement for loading an object from the database.
	 * 
	 * @param clazz The class of the DotProject's object the statement will be created to. 
	 * @return The SQL prepared statement to read an object of the given class.
	 */
	final public static PreparedStatement createPstmtSelectById(Connection conn, Class<? extends DotProjectObject> clazz,
			Object id)
	{
		StringBuffer sb = new StringBuffer();
		String table = null;
		String idField = null;
		Field[] fields = null;
		PreparedStatement stmt = null;

		table = AnnotationUtil.getAnnotationValue(clazz, DbAnnotations.TABLE_ANNOTATION);
		sb.append("SELECT * FROM ");
		sb.append(table);

		fields = AnnotationUtil.getAnnotatedFields(clazz, DbAnnotations.IDENTIFICATOR_ANNOTATION);
		for (Field f : fields) {
			idField = AnnotationUtil.getAnnotationValue(f, DbAnnotations.PROPERTY_ANNOTATION);
		}
		sb.append(" WHERE ");
		sb.append(idField);
		sb.append("=?");

		try {
			stmt = conn.prepareStatement(sb.toString());
			stmt.setObject(1, id);
		} catch (SQLException se) {
			stmt = null;
		}
		
		return stmt;
	}

	
	final public static String createPstmtSelectByExample(Class<? extends DotProjectObject> dpobject)
	{
		StringBuffer sb = new StringBuffer();
		String table = null;
		String idField = null;
		Field[] fields = null;

		table = AnnotationUtil.getAnnotationValue(dpobject, DbAnnotations.TABLE_ANNOTATION);
		fields = AnnotationUtil.getAnnotatedFields(dpobject, DbAnnotations.IDENTIFICATOR_ANNOTATION);
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
	
	final public static PreparedStatement createPstmtSelectByExample(Connection conn, DotProjectObject obj)
	{
		Class<? extends DotProjectObject> clazz = obj.getClass();
		StringBuffer sb = new StringBuffer();
		String table = null;
		Field[] beanFields = null;
		String[] dbFields = null;
		Object[] dbValues = null;
		PreparedStatement stmt = null;
		
		table = AnnotationUtil.getAnnotationValue(clazz, DbAnnotations.TABLE_ANNOTATION);
		sb.append("SELECT * FROM ");
		sb.append(table);
		
		beanFields = AnnotationUtil.getAnnotatedFields(clazz, DbAnnotations.PROPERTY_ANNOTATION);
		dbFields = new String[beanFields.length];
		dbValues = new Object[beanFields.length];
		for (int i = 0; i < beanFields.length; i++) {
			dbFields[i] = AnnotationUtil.getAnnotationValue(beanFields[i], DbAnnotations.PROPERTY_ANNOTATION);
			try {
				dbValues[i] = beanFields[i].get(obj);
			} catch (IllegalAccessException iae) {
				dbFields[i] = null;
				dbValues[i] = null;
			}
		}
		dbFields = ArrayUtil.clean(dbFields);
		dbValues = ArrayUtil.clean(dbValues);
			
		sb.append(" WHERE ");
		for (int i = 0; i < dbFields.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(dbFields[i]);
			sb.append("=?");
		}
		
		try {
			stmt = conn.prepareStatement(sb.toString());
			for (int i = 1; i <= dbValues.length; i++) {
				stmt.setObject(i, dbValues[i - 1]);
			}
		} catch (SQLException se) {
			stmt = null;
		}
		
		return stmt;
	}

}