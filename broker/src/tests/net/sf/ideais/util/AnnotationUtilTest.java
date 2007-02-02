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

package tests.net.sf.ideais.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

import net.sf.ideais.util.AnnotationUtil;
import net.sf.ideais.util.ArrayUtil;

import org.junit.Before;
import org.junit.Test;

public class AnnotationUtilTest
{
	private final static String DEFAULT_VALUE = "test123";
	private final static String DEFAULT_FIELD_NAME = AnnotationUtil.DEFAULT_PROPERTY;
	private final static String VALID_FIELD_NAME = "test";
	private final static String INVALID_FIELD_NAME = "asdfg";
	private final static Field[] validFields = new Field[1];
	private final static Field[] invalidFields = new Field[1];
	
	private DummyClass bean;
	
	
	/**
	 * Annotation to describe a JavaBean property.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DummyAnnotation
	{
		String value() default DEFAULT_VALUE;
		String test() default DEFAULT_VALUE;
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DummyDummyAnnotation
	{
		String value() default DEFAULT_VALUE;
		String test() default DEFAULT_VALUE;
	}
	
	@DummyAnnotation
	public class DummyClass
	{
		@DummyAnnotation
		public String test;
		
		public String asdfg;
	}
	
	@Before
	public void setUp() throws Exception
	{
		bean = new DummyClass();
		validFields[0] = bean.getClass().getField("test"); 	
		invalidFields[0] = bean.getClass().getField("asdfg"); 	
	}

	@Test
	public void testGetAnnotationDefaultValue()
	{
		assertEquals(DEFAULT_VALUE, AnnotationUtil.getAnnotationValue(bean, DummyAnnotation.class));
	}

	@Test
	public void testGetAnnotationValue1()
	{
		assertEquals(DEFAULT_VALUE, AnnotationUtil.getAnnotationValue(bean, DummyAnnotation.class, DEFAULT_FIELD_NAME));
	}

	@Test
	public void testGetAnnotationValue2()
	{
		assertEquals(DEFAULT_VALUE, AnnotationUtil.getAnnotationValue(bean, DummyAnnotation.class, VALID_FIELD_NAME));
	}

	@Test
	public void testGetAnnotationValue3()
	{
		assertFalse(DEFAULT_VALUE.equals(AnnotationUtil.getAnnotationValue(bean, DummyAnnotation.class, INVALID_FIELD_NAME)));
	}

	
	@Test
	public void testHasAnnotatiosObject1()
	{
		assertTrue(AnnotationUtil.hasAnnotations(bean));
	}
	
	@Test
	public void testHasAnnotatiosObject2()
	{
		assertFalse(AnnotationUtil.hasAnnotations(DEFAULT_VALUE));
	}
	
	@Test
	public void testHasAnnotatiosClass1()
	{
		assertTrue(AnnotationUtil.hasAnnotations(bean.getClass()));
	}

	@Test
	public void testHasAnnotatiosClass2()
	{
		assertFalse(AnnotationUtil.hasAnnotations(DEFAULT_VALUE.getClass()));
	}

	@Test
	public void testGetAnnotatedPropertiesObject1()
	{
		Field[] fields = AnnotationUtil.getAnnotatedProperties(bean);
		assertTrue(ArrayUtil.equalIgnoreOrder(validFields, fields));
	}

	@Test
	public void testGetAnnotatedPropertiesObject2()
	{
		Field[] fields = AnnotationUtil.getAnnotatedProperties(bean);
		assertFalse(ArrayUtil.equalIgnoreOrder(invalidFields, fields));
	}

	@Test
	public void testGetAnnotatedPropertiesObject3()
	{
		Field[] fields = AnnotationUtil.getAnnotatedProperties(DEFAULT_VALUE);
		assertFalse(ArrayUtil.equalIgnoreOrder(invalidFields, fields));
	}

	
	@Test
	public void testGetAnnotatedPropertiesClass1()
	{
		Field[] fields = AnnotationUtil.getAnnotatedProperties(bean.getClass());
		assertTrue(ArrayUtil.equalIgnoreOrder(validFields, fields));
	}

	@Test
	public void testGetAnnotatedPropertiesClass2()
	{
		Field[] fields = AnnotationUtil.getAnnotatedProperties(bean.getClass());
		assertFalse(ArrayUtil.equalIgnoreOrder(invalidFields, fields));
	}

	@Test
	public void testGetAnnotatedPropertiesClass3()
	{
		Field[] fields = AnnotationUtil.getAnnotatedProperties(DEFAULT_VALUE);
		assertFalse(ArrayUtil.equalIgnoreOrder(invalidFields, fields));
	}

	@Test
	public void testGetAnnotatedPropertiesObjectAnnotation1()
	{
		Field[] fields = AnnotationUtil.getAnnotatedProperties(bean, DummyAnnotation.class);
		assertTrue(ArrayUtil.equalIgnoreOrder(validFields, fields));
	}

	@Test
	public void testGetAnnotatedPropertiesObjectAnnotation2()
	{
		Field[] fields = AnnotationUtil.getAnnotatedProperties(bean, DummyAnnotation.class);
		assertFalse(ArrayUtil.equalIgnoreOrder(invalidFields, fields));
	}

	@Test
	public void testGetAnnotatedPropertiesObjectAnnotation3()
	{
		Field[] fields = AnnotationUtil.getAnnotatedProperties(DEFAULT_VALUE, DummyAnnotation.class);
		assertFalse(ArrayUtil.equalIgnoreOrder(invalidFields, fields));
	}

	
	@Test
	public void testGetAnnotatedPropertiesClassAnnotation1()
	{
		Field[] fields = AnnotationUtil.getAnnotatedProperties(bean.getClass(), DummyAnnotation.class);
		assertTrue(ArrayUtil.equalIgnoreOrder(validFields, fields));
	}

	@Test
	public void testGetAnnotatedPropertiesClassAnnotation2()
	{
		Field[] fields = AnnotationUtil.getAnnotatedProperties(bean.getClass(), DummyAnnotation.class);
		assertFalse(ArrayUtil.equalIgnoreOrder(invalidFields, fields));
	}

	@Test
	public void testGetAnnotatedPropertiesClassAnnotation3()
	{
		Field[] fields = AnnotationUtil.getAnnotatedProperties(DEFAULT_VALUE, DummyAnnotation.class);
		assertFalse(ArrayUtil.equalIgnoreOrder(invalidFields, fields));
	}

	@Test
	public void testGetAnnotatedPropertiesClassAnnotation4()
	{
		Field[] fields = AnnotationUtil.getAnnotatedProperties(bean.getClass(), DummyDummyAnnotation.class);
		assertFalse(ArrayUtil.equalIgnoreOrder(validFields, fields));
	}

	@Test
	public void testGetAnnotatedPropertiesClassAnnotation5()
	{
		Field[] fields = AnnotationUtil.getAnnotatedProperties(bean.getClass(), DummyDummyAnnotation.class);
		assertFalse(ArrayUtil.equalIgnoreOrder(invalidFields, fields));
	}

	@Test
	public void testGetAnnotatedPropertiesClassAnnotation6()
	{
		Field[] fields = AnnotationUtil.getAnnotatedProperties(DEFAULT_VALUE, DummyDummyAnnotation.class);
		assertFalse(ArrayUtil.equalIgnoreOrder(invalidFields, fields));
	}


}
