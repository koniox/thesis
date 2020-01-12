package pl.polsl.debinski.konrad.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * Class representing resource entity
 * @author Konrad Dębiński
 * @version 1.0
 */
@Entity
@Table(name = "resources")
@NamedQueries({@NamedQuery(name = Resource.FIND_ALL,query = "SELECT r FROM Resource r")})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resource implements Serializable {
    
    /**
     * Query name
     */
    public static final String FIND_ALL = "Resource.findAll";

    /**
     * Primary key
     */
    @Id
    @Column(name = "resource_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * Title of resource
     */
    @Column(name = "title",length = 1000)
    private String title;
    
    /**
     * Resource labels
     */
    @Column(name = "labels")
    @Lob
    private String labels;
    
    /**
     * Resource in JSON format  
     */
    @Column(name = "resource_data")
    @Lob
    private String jsonDataString;

    /**
     * address url of resource
     */
    @Column(name ="resource_address")
    private String addressURL;

    public Resource(String title, String labels, String jsonDataString, String addressURL) {
        this.title = title;
        this.labels = labels;
        this.jsonDataString = jsonDataString;
        this.addressURL = addressURL;
    }
  

    public Resource() {
    }

    public String getAddressURL() {
        return addressURL;
    }

    public void setAddressURL(String addressURL) {
        this.addressURL = addressURL;
    }
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getJsonDataString() {
        return jsonDataString;
    }

    public void setJsonDataString(String jsonDataString) {
        this.jsonDataString = jsonDataString;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resource)) {
            return false;
        }
        Resource other = (Resource) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.polsl.debinski.konrad.pojo.Resource[ id=" + id + " ]";
    }
    
}
