
package net.sf.ideais.dotproject.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.sf.ideais.dotproject.Project;

@XmlRootElement(name = "getProjectResponse", namespace = "http://dotproject.ideais.sf.net/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getProjectResponse", namespace = "http://dotproject.ideais.sf.net/")
public class GetProjectResponse {

    @XmlElement(name = "return", namespace = "")
    private Project _return;

    /**
     * 
     * @return
     *     returns Project
     */
    public Project get_return() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void set_return(Project _return) {
        this._return = _return;
    }

}
