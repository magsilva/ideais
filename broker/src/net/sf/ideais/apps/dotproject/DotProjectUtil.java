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

import net.sf.ideais.annotations.db.DbAnnotations;
import net.sf.ideais.util.AnnotationUtil;

public class DotProjectUtil
{
	/**
	 * Compile the prepared statement for inserting a project into the database.
	 *  
	 * @param table
	 * @param parameterCount
	 * @return
	 */
	final public static String createPreparedStatementInsertString(String table, int parameterCount)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ");
		sb.append(table);
		sb.append(" (");
		for (int i = 0; i < parameterCount; i++) {
			if (i == 0) {
				sb.append("?");
			} else {
				sb.append(", ?");
			}
		}
		sb.append(") values (");
		for (int i = 0; i < parameterCount; i++) {
			if (i == 0) {
				sb.append("?");
			} else {
				sb.append(", ?");
			}
		}
		sb.append(")");

		return sb.toString();
	}

	final public static String createPreparedStatementDeleteString(String table, int parameterCount)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM ");
		sb.append(table);
		sb.append(" WHERE ");

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

	/**
	 * Compile the prepared statement for loading a object from the database.
	 * 
	 * @param clazz The class of the DotProject's object the statement will be created to. 
	 * @return The SQL prepared statement to read a object of the given class.
	 */
	final public static String createStatementSelectId(Class<? extends DotProjectObject> clazz)
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