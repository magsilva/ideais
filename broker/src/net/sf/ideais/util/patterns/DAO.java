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

package net.sf.ideais.util.patterns;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * The implementation is based upon the ideas and examples from Michael Slattery
 * (http://jroller.com/page/MikeSlattery/20050811) and Christian
 * (http://blog.hibernate.org/cgi-bin/blosxom.cgi/2005/09/08#genericdao).
 * 
 * @param <T> The business object implementation class.
 * @param <I> The primary key for the business object.
 */
public interface DAO<T, I> extends Serializable
{
	T create();
	
	T find(I id);
	List<T> findByProperty(String key, Serializable value);
	List<T> findByExample(T example);
	List<T> findByExample(Map<String, Serializable> fields);

	void update(T entity);
	
	void delete(T entity);
	void deleteById(I id);	
}