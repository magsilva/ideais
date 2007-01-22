/*
 * Task.java
 *
 * Created on 9 de Agosto de 2006, 21:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.ideais.dotproject;

/**
 *
 * @author magsilva
 */
public class Task {
   
    private String name;
    
    private String description;
    
    /** Creates a new instance of Task */
    public Task() {
    }
    
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
    
        
    public void setDescription(String description)
    {
        this.description = description;
    }
    
            
    public String getDescription()
    {
        return this.description;
    }
    
}
