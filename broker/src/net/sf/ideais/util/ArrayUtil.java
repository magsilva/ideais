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

import java.util.Arrays;
import java.util.Comparator;


/**
 * Some basic array utils.
 */
public final class ArrayUtil
{
	/**
	 * Check if an array has the given object. If the object is 'null', it will
	 * always return False
	 * 
	 * @param array The array we will search into.
	 * @param o The object to be found.
	 * 
	 * @return True if the object was found in the array, False otherwise.
	 */
	public static boolean has(Object[] array, Object o)
	{
		if (o == null) {
			return false;
		}
		
		for (Object temp : array) {
			if (o.equals(temp)) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Create a copy of an array. It will not do a deep copy (the primitive values
	 * and object references are copied to the targed array, but the objects refereed
	 * by both arrays will be the same).
	 * 
	 * @param array Array to be copied.
	 * 
	 * @return Duplicated array.
	 */
	public static Object[] dup(Object[] array)
	{
		Object[] dupArray = new Object[array.length];
		System.arraycopy(array, 0, dupArray, 0, array.length);
		return dupArray;
	}
	
	public static boolean equalIgnoreOrder(Object[] array1, Object[] array2)
	{
		final class HashComparator<T> implements Comparator<T>
		{
			/**
			 * Compares its two arguments for order. Returns a negative integer, zero, or a positive
			 * integer as the first argument is less than, equal to, or greater than the second.
			 * 
			 * Note: this comparator imposes orderings that are inconsistent with equals."
			 */
			public int compare(T o1, T o2)
			{
				int hash1 = o1.hashCode();
				int hash2 = o2.hashCode();
				return (hash1 - hash2);
			}
		}
		
		if (array1.length != array2.length) {
			return false;
		}
		
		Object[] sortedArray1 = dup(array1);
		Object[] sortedArray2 = dup(array2);
		Arrays.sort(sortedArray1, new HashComparator<Object>());
		Arrays.sort(sortedArray2, new HashComparator<Object>());
		
		return Arrays.equals(sortedArray1, sortedArray2);
	}

	
	/**
	 * Find an object in the array that is an instance of the given targetClass.
	 * 
	 * @param array Array we must look into.
	 * @param targetClass Class that we must match.
	 * 
	 * @return The object (if found) or null otherwise.
	 */
	public static Object find(Object[] array, Class targetClass)
	{
		for (Object o : array) {
			try {
				if (targetClass.equals(o.getClass())) {
					return o;
				}
			} catch (NullPointerException e) {
			}
		}
		
		return null;
	}

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
	
	public static <T> T[] clean(T[] array)
	{
		int count = 0;
		Object[] result = null;
		
		for (Object obj : array) {
			if (obj != null) {
				count++;
			}
		}
		
		result = (T[])java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), count);
		for (Object obj : array) {
			if (obj != null) {
				result[--count] = obj;
			}
		}
			
		return (T[])result;
	}
}
