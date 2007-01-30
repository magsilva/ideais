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

package tests.net.sf.ideais.techdemos.annotations;

import tests.net.sf.ideais.techdemos.annotations.Metadata;

public class AnnotatedDummyBean
{
	private static final String DEFAULT_NAME = "John Due";
	private static final int DEFAULT_AGE = 60;
	
	@Metadata(name="bean_name")
	public String name;

	@Metadata(name="bean_age")
	public int age;
	
	public AnnotatedDummyBean()
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