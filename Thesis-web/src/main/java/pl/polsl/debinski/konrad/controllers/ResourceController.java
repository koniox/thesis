/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.controllers;


import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import pl.polsl.debinski.konrad.beans.ResourceBean;
import pl.polsl.debinski.konrad.pojo.Resource;


/**
 *
 * @author debin
 */
@ManagedBean
@ViewScoped
public class ResourceController implements Serializable{
    
    private Resource resource;
    private Resource selectedResource;
    
    @ManagedProperty(value = "#{fileBean}")
    private FileBean fileBean;
    
    @EJB
    private ResourceBean resourceBean;

    
    
    @PostConstruct
    protected void init(){
        resource = (Resource) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().
                remove("resource");
        if(resource == null){
            resource = new Resource();
        }
    }
    
    public void fileUpload(){
        resource.setJsonDataString(fileBean.getFileData().get("json"));
        resource.setLabels(fileBean.getFileData().get("labels"));
        resource.setTitle(fileBean.getFileName());
        resourceBean.createOrUpdateResource(resource);
        FacesMessage message = new FacesMessage("dsdsa", " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public String actionSave(){   
        resourceBean.createOrUpdateResource(resource);
        return "adminpanel";
    }

    public Resource getResource() {
        return resource;
    }
    
    
    public List<Resource> getResources(){
        return resourceBean.findAll();
    }
    
    public Resource getSelectedResource() { 
       return selectedResource;
    }

    public void setSelectedResource(Resource selectedResource) {
        this.selectedResource = selectedResource;
    }

    public void setFileBean(FileBean fileBean) {
        this.fileBean = fileBean;
    }
    
    
}
