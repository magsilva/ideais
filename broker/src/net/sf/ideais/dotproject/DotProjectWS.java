/*
 * DotProjectWS.java
 *
 * Created on 9 de Agosto de 2006, 21:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.ideais.dotproject;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author magsilva
 */
@WebService()
public class DotProjectWS {
    
   
    /* Sample Web Service Operation */
    
    @WebMethod(operationName="sample_operation")
    public String operation(@WebParam(name="param_name") String param) {
        // implement the web service operation here
        return param;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public Task GetTask(@WebParam(name = "id") int id) {
        Task task = new Task();
        task.setName("hello");
        task.setDescription("Hi hi");
        return task;
    }
    
}
