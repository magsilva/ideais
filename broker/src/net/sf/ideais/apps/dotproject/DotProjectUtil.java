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

import net.sf.ideais.Identificator;
import net.sf.ideais.Property;
import net.sf.ideais.Table;
import net.sf.ideais.apps.dotproject.Project;
import net.sf.ideais.util.AnnotationUtil;

public class DotProjectUtil
{
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

	final public static String createStatementSelectId()
	{
		StringBuffer sb = new StringBuffer();
		String table = null;
		String idField = null;
		Field[] fields = null;

		table = AnnotationUtil.getAnnotationValue(Project.class, Table.class);
		fields = AnnotationUtil.getAnnotatedProperties(Project.class, Identificator.class);
		for (Field f : fields) {
			idField = AnnotationUtil.getAnnotationValue(f, Property.class);
			/*
			Annotation a = f.getAnnotation(Identificator.class);
			String value = null;
			
			if (a == null) {
				throw new IllegalArgumentException();
			}

			try {
				Method m  = Identificator.class.getDeclaredMethod(AnnotationUtil.DEFAULT_PROPERTY, (Class[])null);
				value = (String) m.invoke(a, (Object [])null);
			} catch (NoSuchMethodException nsme) {
			} catch (IllegalAccessException iae) {
			} catch (InvocationTargetException ite) {
			}
				
			idField = value;
			*/
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

		table = AnnotationUtil.getAnnotationValue(dpobject, Table.class);
		fields = AnnotationUtil.getAnnotatedProperties(dpobject, Identificator.class);
		for (Field f : fields) {
			idField = AnnotationUtil.getAnnotationValue(f.getClass(), Identificator.class);
		}

		sb.append("SELECT * FROM ");
		sb.append(table);
		sb.append(" WHERE ");
		sb.append(idField);
		sb.append("=?");

		return sb.toString();
	}
}