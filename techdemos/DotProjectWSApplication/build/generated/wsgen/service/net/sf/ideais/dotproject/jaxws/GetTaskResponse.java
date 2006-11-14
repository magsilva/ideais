
package net.sf.ideais.dotproject.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.sf.ideais.dotproject.Task;

@XmlRootElement(name = "GetTaskResponse", namespace = "http://dotproject.ideais.sf.net/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetTaskResponse", namespace = "http://dotproject.ideais.sf.net/")
public class GetTaskResponse {

    @XmlElement(name = "return", namespace = "")
    private Task _return;

    /**
     * 
     * @return
     *     returns Task
     */
    public Task get_return() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void set_return(Task _return) {
        this._return = _return;
    }

}
