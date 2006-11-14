
package net.sf.ideais.dotproject.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "sample_operation", namespace = "http://dotproject.ideais.sf.net/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sample_operation", namespace = "http://dotproject.ideais.sf.net/")
public class Operation {

    @XmlElement(name = "param_name", namespace = "")
    private String param_name;

    /**
     * 
     * @return
     *     returns String
     */
    public String getParam_name() {
        return this.param_name;
    }

    /**
     * 
     * @param param_name
     *     the value for the param_name property
     */
    public void setParam_name(String param_name) {
        this.param_name = param_name;
    }

}
