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
 
Copyright (C) 2005 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
*/

package net.sf.ideais.util;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Methods useful for file manipulations (what a shame Java doesn't have them
 * in its standard library).
 * 
 * @author Marco Aurélio Graciotto Silva
 */
public final class IoUtil
{
	/**
	 * We really don't want an instance of this class, so we create this
	 * private constructor.
	 */
	private IoUtil()
	{
	}
	
	public static int BUFFER_SIZE = 8192;
	
	/**
	 * Check if a file exists.
	 * 
	 * @param filename The filename or directory to be checked.
	 * 
	 * @return True if the file exists, false otherwise. 
	 */
	public static boolean exists( String filename )
	{
		File file = new File( filename );
		return file.exists();
	}
	
	/**
	 * Create a directory (any missing parent directory is created too).
	 * 
	 * @param dir The directory to be created.
	 */
	public static File createDir( String dir )
	{
		File file = new File( dir );
		file.mkdirs();
		return file;
	}
	
	/**
	 * Create a a file.
	 * 
	 * @param dirname The directory where the file must reside.
	 * @param filename The file to be created.
	 */
	public static File createFile( String dirname, String filename )
		throws IOException
	{
		File dir = createDir( dirname );
		return createFile( dir, filename );
	}

	/**
	 * Create a a file.
	 * 
	 * @param dirname The directory where the file must reside.
	 * @param filename The file to be created.
	 */
	public static File createFile( File dir, String filename )
		throws IOException
	{
		dir.mkdirs();
		File file = new File( dir, filename );
		file.createNewFile();
		return file;
	}

	/**
	 * Move a file.
	 * 
	 * @param src Source file.
	 * @param dest Destination file.
	 */
	public static void moveFile(String src, String dest) throws IOException
	{
		File srcFile = new File(src);
		File destFile = new File(dest);
		moveFile(srcFile, destFile);
	}

	/**
	 * Move a file.
	 * 
	 * @param src Source file.
	 * @param dest Destination file.
	 */
	public static void moveFile(File src, File dest) throws IOException
	{
		boolean result = false;
		
		result = src.renameTo(dest);
	    if (! result) {
    		copyFile(src.getAbsolutePath(), dest.getAbsolutePath());
    		src.delete();
	    }
	}
	
	/**
	 * Sync a file stream to disk.
	 * 
	 * @param fileStream The file stream to be synchronized to disk.
	 */
	public static void syncFile(FileOutputStream fileStream)
	{
		try {
			FileDescriptor fd = fileStream.getFD();
			fileStream.flush();
			// Block until the system buffers have been written to disk.
			fd.sync();
		} catch (IOException e) {
		}
	}
	
	/**
	 * Copy the source file to the destination file.
	 * 
	 * @param srcFilename
	 *            The source filename.
	 * @param destFilename
	 *            The destination filename.
	 */
	public static void copyFile( String srcFilename, String destFilename )
		throws IOException
	{
		if ( ! exists( srcFilename ) ) {
			throw new IOException();
		}
		
		FileInputStream srcFileStream = new FileInputStream( srcFilename );
		FileOutputStream destFileStream = new FileOutputStream( destFilename );
		byte[] buffer = new byte[ BUFFER_SIZE ];
		int position = 0;
		int bytes;

		do {
			bytes = srcFileStream.read( buffer, position, buffer.length );
			if ( bytes != -1 ) {
				destFileStream.write( buffer, position, bytes );
				position += bytes;
			}
		} while ( bytes != -1 );
		
		srcFileStream.close();
		destFileStream.close();
	}
	
	/**
	 * Copy the files in the source directory to the destination directory.
	 * 
	 * @param srcDir The source directory.
	 * @param destDir The destination directory.
	 */
	public static void copyDir( String srcDirName, String destDirName )
		throws IOException
	{
		copyDir( srcDirName, destDirName, false );
	}

	/**
	 * Copy the files in the source directory to the destination directory.
	 * 
	 * @param srcDir The source directory.
	 * @param destDir The destination directory.
	 * @param recurse Enable the recursive copy of directories.
	 */
	public static void copyDir( String srcDirName, String destDirName, boolean recurse )
		throws IOException
	{
		File srcDir = new File( srcDirName );
		File destDir = new File( destDirName );
		
		if ( ! srcDir.isDirectory() || ! destDir.isDirectory() ) {
			File[] files = srcDir.listFiles();
			for ( File file : files ) {
				if ( file.isDirectory() && recurse ) {
					copyDir( file.getAbsolutePath(), destDirName + File.separator
						+ file.getName() );
				} else {
					copyFile( file.getAbsolutePath(), destDirName + File.separator
						+ file.getName() );
				}
			}
		}
	}
	
	/**
	 * Get all the files within the directory.
	 */
	public static ArrayList<File> find( String path )
	{
		ArrayList<File> files = new ArrayList<File>();
		File dir = new File( path );
		for ( File file : dir.listFiles() ) {
			if ( file.isFile() ) {
				files.add( file );
			} else if ( file.isDirectory() ) {
				files.addAll( find( file.getAbsolutePath() ) );
			}
		}
		return files;
	}
	
	/**
	 * Remove a file or a directory.
	 */
	public static void remove( String path )
	{
		File file = new File( path );
		if ( file.isDirectory() ) {
			removeDir( path );
		} else if ( file.isFile() ) {
			removeFile( path );
		}
	}
	
	/**
	 * Remove a file.
	 * 
	 * @param filename The file to be removed
	 */
	public static void removeFile( String filename )
	{
		File file = new File( filename );
		if ( file.isFile() ) {
			file.delete();
		}
	}
	
	/**
	 * Remove a directory and all of it's content.
	 * 
	 * @param dirname The directory to be removed
	 */
	public static void removeDir( String dirname )
	{
		File dir = new File( dirname );
		if ( dir.isDirectory() ) {
			File[] listing = dir.listFiles();
			for ( File file : listing ) {
				if ( file.isDirectory() ) {
					removeDir( file.getAbsolutePath() );
				}
				file.delete();
			}
			dir.delete();
		}
	}

	/**
	 * Create a temporary directory.
	 * 
	 * @param prefix Prefix for the directory to be created.
	 * @return Temporary directory.
	 * @throws IOException
	 */
	public static File createTempDir( String prefix )
		throws IOException
	{
		return createTempDir( prefix, "" );
	}
	
	/**
	 * Create a temporary directory.
	 * 
	 * @param prefix Prefix for the directory to be created.
	 * @param suffix Sufix for the directory to be created.
	 * 
	 * @return Temporary directory.
	 * @throws IOException
	 */
	public static File createTempDir( String prefix, String suffix )
		throws IOException
	{
		return createTempDir( prefix, suffix, null );
	}

	/**
	 * Create a temporary directory.
	 * 
	 * @param prefix Prefix for the directory to be created.
	 * @param suffix Sufix for the directory to be created.
	 * @param directory Directory where the temporary directory should be created into.
	 * 
	 * @return Temporary directory.
	 * @throws IOException
	 */
	public static File createTempDir( String prefix, String suffix, File directory )
		throws IOException
	{
		File file = File.createTempFile( prefix, suffix, directory );
		String name = file.getAbsolutePath();
		file.delete();
		file = new File( name );
		file.mkdirs();
		return file;
	}

	/**
	 * Dump a file content to an array of bytes.
	 * 
	 * @param filename The name of the file to be dumped.
	 */
	public static byte[] dumpFile( String filename )
		throws IOException
	{
		return dumpFile( new File( filename ) );
	}
	
	/**
	 * Dump a file content to an array of bytes.
	 * 
	 * @param file The file to be dumped.
	 */
	public static byte[] dumpFile( File file )
		throws IOException
	{
		FileInputStream stream = new FileInputStream( file );
		byte[] data = new byte[ (int)file.length() ];
		stream.read( data, 0, (int)file.length() );
		stream.close();
		return data;
	}
	
}