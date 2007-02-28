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

package tests.net.sf.ideais.apps.dotproject;

import static org.junit.Assert.*;

import net.sf.ideais.apps.dotproject.DotProjectObject;
import net.sf.ideais.apps.dotproject.DotProjectUtil;
import net.sf.ideais.util.annotations.Identificator;
import net.sf.ideais.util.annotations.Property;
import net.sf.ideais.util.annotations.Table;

import org.junit.Before;
import org.junit.Test;

public class DotProjectUtilTest
{
	private DummyDPObject bean;
	
	@Table(value="dummies")
	private class DummyDPObject extends DotProjectObject
	{
		private static final String pstmtInsert = "INSERT INTO dummies (dummy_age, dummy_name) values (?, ?)";
		private static final String pstmtUpdate = "UPDATE dummies SET (dummy_age=?, dummy_id=?, dummy_name=?) WHERE dummy_id=?";
		private static final String pstmtDeleteId = "DELETE FROM dummies WHERE dummy_id=?";
		private static final String pstmtSelectId = "SELECT * FROM dummies WHERE dummy_id=?";
		
		@SuppressWarnings("unused")
		@Identificator
		@Property(value="dummy_id")
		private Long id;
		
		@SuppressWarnings("unused")
		@Property("dummy_name")
		private String name;
		
		@SuppressWarnings("unused")
		@Property("dummy_age")
		private Integer age;

		public String getObjectType()
		{
			return this.getClass().getName();
		}
		
	}
	
	@Before
	public void setUp()
	{
		bean = new DummyDPObject();
	}

	@Test
	public void testCreateStatementInsert1()
	{
		assertEquals(DummyDPObject.pstmtInsert, DotProjectUtil.createPstmtInsert(bean.getClass()));
	}

	@Test
	public void testCreateStatementUpdate1()
	{
		assertEquals(DummyDPObject.pstmtUpdate, DotProjectUtil.createPstmtUpdate(bean.getClass()));
	}

	@Test
	public void testCreateStatementDeleteId()
	{
		assertEquals(DummyDPObject.pstmtDeleteId, DotProjectUtil.createPstmtDeleteById(bean.getClass()));
	}

	
	@Test
	public void testCreateStatementSelectId()
	{
		assertEquals(DummyDPObject.pstmtSelectId, DotProjectUtil.createPstmtSelectById(bean.getClass()));
	}
}