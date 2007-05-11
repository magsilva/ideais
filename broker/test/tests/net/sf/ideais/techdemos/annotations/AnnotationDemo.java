/*
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should
 * have received a copy of the GNU General Public License along with this program; if not, write to
 * the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * Copyright (C) 2007 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
 */

package tests.net.sf.ideais.techdemos.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import tests.net.sf.ideais.techdemos.annotations.Metadata;
import tests.net.sf.ideais.techdemos.annotations.AnnotatedDummyBean;

/**
 * The Annotation Demo is a learning tool about Java's annotation feature.
 * 
 * I created it because I was getting unexpected results with the first
 * annotation I created, the Field (net.sf.ideais.Field): the runtime values
 * weren't shown. After reading the Java's documentation, I found, at the
 * very end of the Java Tutorial [1] that, if I intended to use annotation
 * data at runtime, I must explicitly tell so.
 * 
 * <code>
 * import tests.net.sf.ideais.techdemos.annotations.Metadata;
 * import tests.net.sf.ideais.techdemos.annotations.AnnotatedDummyBean;
 * 
 * @Retention(RetentionPolicy.RUNTIME)
 * </code>
 * 
 * That information I couldn't find at Java Language Specification (at least
 * at a first glance), what really get me off. Well, this tech demos, albeit
 * it's simplicity, demonstrates how to use the powerfull anotation mechanism.
 * 
 * [1] http://java.sun.com/docs/books/tutorial/java/javaOO/annotations.html
 */
public class AnnotationDemo
{
	private AnnotatedDummyBean bean;
	
	@Before
	public void setUp() throws Exception
	{
		bean = new AnnotatedDummyBean();
	}
	
	@Test
	public void testFields()
	{
		Field[] fields = bean.getClass().getFields();
		for (Field f : fields) {
			assertTrue(f.isAnnotationPresent(Metadata.class));
		}
	}
	
	@Test
	public void testDeclaredFields()
	{
		Field[] fields = bean.getClass().getDeclaredFields();
		HashMap<String, Integer> count = new HashMap<String, Integer>();
		for (Field f : fields) {
			if (f.isAnnotationPresent(Metadata.class)) {
				String fieldName = f.getName();
				if (count.get(fieldName) == null) {
					count.put(fieldName, 1);
				} else {
					count.put(fieldName, count.get(fieldName + 1));
				}
			}
		}
		for (Integer i : count.values()) {
			assertTrue(i == 1);
		}
	}
	
	@Test
	public void testAnnotatedFieldValue()
	{
		Field[] fields = bean.getClass().getFields();
		Field annField = null;
		
		for (Field f : fields) {
			if (f.isAnnotationPresent(Metadata.class)) {
				annField = f;
			}
		}

		Annotation[] annotations = annField.getAnnotations();
		for (Annotation a : annotations) {
			if (a.annotationType() == Metadata.class) {
				Metadata beanMetadata = (Metadata) a; 
				assertNotNull(beanMetadata.name()); 
			}
		}
	}
}
