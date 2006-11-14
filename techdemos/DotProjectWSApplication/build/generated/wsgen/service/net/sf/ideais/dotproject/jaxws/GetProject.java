
package net.sf.ideais.dotproject.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getProject", namespace = "http://dotproject.ideais.sf.net/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getProject", namespace = "http://dotproject.ideais.sf.net/")
public class GetProject {

    @XmlElement(name = "project_id", namespace = "")
    private int project_id;

    /**
     * 
     * @return
     *     returns int
     */
    public int getProject_id() {
        return this.project_id;
    }

    /**
     * 
     * @param project_id
     *     the value for the project_id property
     */
    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

}
