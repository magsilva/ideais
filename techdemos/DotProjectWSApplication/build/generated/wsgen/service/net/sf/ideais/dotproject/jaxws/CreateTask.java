
package net.sf.ideais.dotproject.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "CreateTask", namespace = "http://dotproject.ideais.sf.net/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateTask", namespace = "http://dotproject.ideais.sf.net/", propOrder = {
    "name",
    "description",
    "owner_id",
    "project_id"
})
public class CreateTask {

    @XmlElement(name = "name", namespace = "")
    private String name;
    @XmlElement(name = "description", namespace = "")
    private String description;
    @XmlElement(name = "owner_id", namespace = "")
    private int owner_id;
    @XmlElement(name = "project_id", namespace = "")
    private int project_id;

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
