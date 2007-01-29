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

import net.sf.ideais.util.ArrayUtil;
import net.sf.ideais.util.JavaBeanUtil;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class JavaBeanUtilTest
{
	private DummyBean bean;
	
	private Map<String, Object> goodBeanMapping;
	private Map<String, Object> badBeanMapping;

	public class DummyBean
	{
		private static final String DEFAULT_NAME = "John Due";
		private static final int DEFAULT_AGE = 60;
			
		public static final String NAME_FIELD = "name";
		public static final String AGE_FIELD = "age";

		private String name;
		private int age;
		
		public DummyBean()
		{
			name = DEFAULT_NAME;
			age = DEFAULT_AGE;
		}
		
		public int getAge()
		{
			return age;
		}

		public void setAge(int age)
		{
			this.age = age;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}
	}


	@Before
	public void setUp() throws Exception
	{
		bean = new DummyBean();

		goodBeanMapping = new HashMap<String, Object>();
		goodBeanMapping.put(DummyBean.NAME_FIELD, DummyBean.DEFAULT_NAME);
		goodBeanMapping.put(DummyBean.AGE_FIELD, DummyBean.DEFAULT_AGE);
		
		badBeanMapping = new HashMap<String, Object>();
		badBeanMapping.put("dkljlsa", DummyBean.DEFAULT_NAME);
		badBeanMapping.put("j2309j9", DummyBean.DEFAULT_AGE);
	}

	@Test
	public void testIgnoredProperties()
	{
		Object[] expectedIgnoredProperties = JavaBeanUtil.getDefaultPropertiesName();
		Object[] ignoredProperties = JavaBeanUtil.IGNORED_PROPERTIES;
		assertTrue(ArrayUtil.equalIgnoreOrder(expectedIgnoredProperties, ignoredProperties));
	}
	
	@Test
	public void testMapBean1()
	{
		Map mapping = JavaBeanUtil.mapBean(bean);
		assertEquals(goodBeanMapping, mapping);
	}

	@Test
	public void testMapBean2()
	{
		assertFalse(badBeanMapping.equals(JavaBeanUtil.mapBean(bean)));
	}

	
	@Test
	public void testMapBeanUsingFieldsObject()
	{
		Map mapping = JavaBeanUtil.mapBeanUsingFields(bean);
		assertEquals(goodBeanMapping, mapping);
	}
}
