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

package net.sf.ideais.util;


/**
 * Some basic array utils.
 */
public final class ArrayUtil
{
	public static int find(String[] arg, String s)
	{
		int i = 0;
		for (i = 0; i < arg.length && ! arg[i].equals(s); i++);

		if (i == arg.length) {
			i = -1;
		}
		
		return i;
	}
	
	/**
	 * Convert a Array to a single String. It will use a new line character ("\n")
	 * as separator.
	 * 
	 * @param arg The array to be converted.
	 * 
	 * @return The string.
	 */
	public static String ArrayToString( Object[] arg )
	{
		return ArrayUtil.ArrayToString( arg, "\n" );
	}
	
	/**
	 * Convert a Array to a single String.
	 * 
	 * @param arg The array to be converted
	 * @param separator The string to be used to separate each array item.
	 * 
	 * @return The string.
	 */
	public static String ArrayToString( Object[] arg, String separator )
	{
		StringBuffer sb = new StringBuffer();
		for ( Object o : arg ) {
			sb.append( o );
			sb.append( separator );
		}
		sb.replace( sb.lastIndexOf( separator ),
				sb.lastIndexOf( separator ) + separator.length(), "" );
		return sb.toString();
	}
}
