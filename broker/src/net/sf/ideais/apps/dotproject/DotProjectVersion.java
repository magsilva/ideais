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

import net.sf.ideais.apps.Version;
import net.sf.ideais.util.VersionUtil;

public class DotProjectVersion implements Version
{
	private String codeVersion;
	
	private Integer databaseVersion;
	
	public String getCodeVersion()
	{
		return codeVersion;
	}

	public Integer getDatabaseVersion()
	{
		return databaseVersion;
	}

	/**
	 * @throws ClassCastException If the specified object's type prevents it from being compared
	 * to this Object
	 */
	public int compareTo(Version o)
	{
		if (o == null) {
			return 1;
		}

		if (! (o instanceof DotProjectVersion)) {
			throw new ClassCastException();
		}
		
		DotProjectVersion dpo = (DotProjectVersion) o;
		return VersionUtil.compare(codeVersion, dpo.getCodeVersion());
	}
}
