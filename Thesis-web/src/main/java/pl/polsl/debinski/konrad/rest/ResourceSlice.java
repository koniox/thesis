/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.rest;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author debin
 */
@XmlRootElement(name = "Resource")
@XmlAccessorType(XmlAccessType.NONE)
public class ResourceSlice implements Serializable{
    
    @XmlAttribute
    private String id;
    @XmlElement(name = "Title")
    private String title;
    @XmlElement(name = "URL")
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ResourceSlice(String id, String title, String address) {
        this.id = id;
        this.title = title;
        this.address = address;
    }

    

    public ResourceSlice() {
    }
    
    
    
}
