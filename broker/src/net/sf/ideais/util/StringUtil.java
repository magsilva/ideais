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

import java.util.Map;
import java.util.Set;

/**
 * Utility class for strings.
 */
public class StringUtil
{
	/**
	 * Code for the transformation option that adds a suffix to a string.
	 */
	public static final int PREFIX = 1;

	/**
	 * Code for the transformation option that adds a prefix to a string.
	 */
	public static final int SUFFIX = 2;

	/**
	 * Apply the transformations defined at 'options' into a string. Unknown options
	 * will be ignored.
	 *  
	 * @param str The string to be transformed.
	 * @param options The transformations to be applied.
	 * 
	 * @return The string with the transformations applied.
	 */
	public static final String transform(String str, Map<Integer, String> options)
	{
		if (options == null) {
			return str;
		}
		
		if (options.get(PREFIX) != null) {
			str = options.get(PREFIX)+ str;
		}
		if (options.get(SUFFIX) != null) {
			str = str + options.get(SUFFIX);
		}
		
		return str;
	}

	/**
	 * Check if the strings are similar.
	 * 
	 * @param str1 String to be compared.
	 * @param str2 String to be compared.
	 * 
	 * @return True if similar, False otherwise.
	 */
	public static final boolean isSimilar(String str1, String str2)
	{
		return isSimilar(str1, null, str2, null);
	}
	
	/**
	 * Check if the strings are similar.
	 * 
	 * @param str1 String to be compared. Must not be null.
	 * @param options1 Transformation options that will be applied to str1
	 * @param str2 String to be compared. Must not be null.
	 * @param options2 Transformation options that will be applied to str2
	 * 
	 * @return True if similar, False otherwise. If any of the parameters 'str1' and
	 * 'str2' are 'null', returns False too.
	 */	
	public static final boolean isSimilar(String str1, Map<Integer, String> options1,
			String str2, Map<Integer, String> options2)
	{
		if (str1 == null || str2 == null) {
			return false;
		}
		
		str1 = transform(str1, options1);
		str2 = transform(str2, options2);
		
		if (str1.equals(str2)) {
			return true;
		}
		
		if (str1.equalsIgnoreCase(str2)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Find a similar word within a set of words.
	 * 
	 * @param set Set of words to search within.
	 * @param str Target word.
	 * 
	 * @return The similar word if found, null otherwise. 
	 */
	public static final String findSimilar(Set<String> set, String str)
	{
		return findSimilar(set, str, null);
	}

	/**
	 * Find a similar word within a set of words.
	 * 
	 * @param set Set of words to search within.
	 * @param str Target word.
	 * @param options Options to be applied to the word before matching.
	 * 
	 * @return The similar word if found, null otherwise. 
	 */
	public static final String findSimilar(Set<String> set, String str, Map<Integer, String> options)
	{
		for (String s : set) {
			if (isSimilar(s, options, str, null)) {
				return s;
			}
		}
		return null;
	}

	
}
