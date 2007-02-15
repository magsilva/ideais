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


package tests.net.sf.ideais.util.patterns;

/**
 * Test the Singleton pattern. Some ideas and code extracts have been take from
 * the article "Simply Singleton", written by David Geary
 * (http://www.javaworld.com/javaworld/jw-04-2003/jw-0425-designpatterns.html").
 */
public class SingletonTest
{
   private static Singleton singleton = null;
   public SingletonTest(String name) {
      super(name);
   }
   public void setUp() {
      singleton = null;
   }
   public void testUnique() throws InterruptedException {
      // Both threads call Singleton.getInstance().
      Thread threadOne = new Thread(new SingletonTestRunnable()),
             threadTwo = new Thread(new SingletonTestRunnable());
      threadOne.start();
      threadTwo.start();
      threadOne.join();
      threadTwo.join();
   }
   private static class SingletonTestRunnable implements Runnable {
      public void run() {
         // Get a reference to the singleton.
         Singleton s = Singleton.getInstance();
         // Protect singleton member variable from
         // multithreaded access.
         synchronized(SingletonTest.class) {
            if(singleton == null) // If local reference is null...
               singleton = s;     // ...set it to the singleton
         }
         // Local reference must be equal to the one and
         // only instance of Singleton; otherwise, we have two
                  // Singleton instances.
         Assert.assertEquals(true, s == singleton);
      }
   }
}