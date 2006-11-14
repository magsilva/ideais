
package net.sf.ideais.dotproject.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "GetTask", namespace = "http://dotproject.ideais.sf.net/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetTask", namespace = "http://dotproject.ideais.sf.net/")
public class GetTask {

    @XmlElement(name = "id", namespace = "")
    private int id;

    /**
     * 
     * @return
     *     returns int
     */
    public int getId() {
        return this.id;
    }

    /**
     * 
     * @param id
     *     the value for the id property
     */
    public void setId(int id) {
        this.id = id;
    }

}
