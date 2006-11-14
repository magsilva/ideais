
package net.sf.ideais.dotproject.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "CreateProject", namespace = "http://dotproject.ideais.sf.net/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateProject", namespace = "http://dotproject.ideais.sf.net/", propOrder = {
    "name",
    "description",
    "owner_id",
    "company_id"
})
public class CreateProject {

    @XmlElement(name = "name", namespace = "")
    private String name;
    @XmlElement(name = "description", namespace = "")
    private String description;
    @XmlElement(name = "owner_id", namespace = "")
    private int owner_id;
    @XmlElement(name = "company_id", namespace = "")
    private int company_id;

    /**
     * 
     * @return
     *     returns String
     */
    public String getName() {
        return this.name;
    }

    /**
     * 
     * @param name
     *     the value for the name property
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 
     * @param description
     *     the value for the description property
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     returns int
     */
    public int getOwner_id() {
        return this.owner_id;
    }

    /**
     * 
     * @param owner_id
     *     the value for the owner_id property
     */
    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    /**
     * 
     * @return
     *     returns int
     */
    public int getCompany_id() {
        return this.company_id;
    }

    /**
     * 
     * @param company_id
     *     the value for the company_id property
     */
    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

}
