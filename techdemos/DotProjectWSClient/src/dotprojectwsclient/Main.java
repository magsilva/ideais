/*
 * Main.java
 *
 * Created on 9 de Agosto de 2006, 21:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dotprojectwsclient;

/**
 *
 * @author magsilva
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try { // Call Web Service Operation
            net.sf.ideais.dotproject.DotProjectWSService service = new net.sf.ideais.dotproject.DotProjectWSService();
            net.sf.ideais.dotproject.DotProjectWS port = service.getDotProjectWSPort();
            // TODO initialize WS operation arguments here
            int id = 0;
            // TODO process result here
            net.sf.ideais.dotproject.Task task = port.getTask(id);
            System.out.println("Result = "+ task.getName());
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        
    }
    
}
