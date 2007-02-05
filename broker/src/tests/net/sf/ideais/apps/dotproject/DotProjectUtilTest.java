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

import net.sf.ideais.apps.dotproject.DotProjectUtil;

import org.junit.Test;

public class DotProjectUtilTest
{
	private static final String pstmtInsert = "INSERT INTO projects (project_name, project_description, project_owner, project_company) values (?,?,?,?)";
	private static final String pstmtUpdate = "UPDATE projects SET (project_name=?, project_description=?, project_owner=?, project_company=?) WHERE project_id=?";
	private static final String pstmtDelete = "DELETE FROM projects WHERE project_id = ?";
	private static final String pstmtSelectId = "SELECT * FROM projects WHERE project_id=?";
	
	/*
	@Test
	public void testCreatePreparedStatementInsertString()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testCreatePreparedStatementDeleteString()
	{
		assertEquals(pstmtDelete, DotProjectUtil.createPreparedStatementDeleteString();
		fail("Not yet implemented");
	}
*/
	
	@Test
	public void testCreateStatementSelectId()
	{
		assertEquals(pstmtSelectId, DotProjectUtil.createStatementSelectId());
	}

}
