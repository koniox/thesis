package pl.polsl.debinski.konrad.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * Class representing resource entity
 * @author Konrad Dębiński
 * @version 1.0
 */
@Entity
@Table(name = "resources")
public class Resource implements Serializable {

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

    public Resource(String title, String labels, String jsonDataString) {
        this.title = title;
        this.labels = labels;
        this.jsonDataString = jsonDataString;
    }

    public Resource() {
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
