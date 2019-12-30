/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import pl.polsl.debinski.konrad.beans.ResourceBean;
import pl.polsl.debinski.konrad.pojo.Resource;
import pl.polsl.debinski.konrad.utils.Model;

/**
 *
 * @author debin
 */
@ManagedBean
@ViewScoped
public class MyBean implements Serializable{
    private List<Model> columns  = new ArrayList<>();
    List<Map<String,String>> data;
    private Resource resource;
    
    @ManagedProperty(value = "#{resourceController}")
    private ResourceController resourceController;
    
    @EJB
    private ResourceBean resourceBean;

    public List<Map<String, String>> getData() {
        return data;
    }

    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }

    public List<Model> getColumns() {
        return columns;
    }

    public void setColumns(List<Model> columns) {
        this.columns = columns;
    }
    
    @PostConstruct
    protected void init(){
        if(!(resourceController.getSelectedResource() == null)){
            resource = resourceBean.findById(resourceController.getSelectedResource());
            data = stringToListOfMaps(resource.getJsonDataString());
            populateColumns();
        }else{
            FacesMessage message = new FacesMessage("wrong", "bong");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
    }
    
    public void populateColumns(){
        List<String> columnKeys = Arrays.asList(resource.getLabels().split(","));
        for(String columnKey:columnKeys){
            columns.add(new Model(columnKey.toUpperCase(), columnKey));
        }
    }

    public ResourceController getResourceController() {
        return resourceController;
    }

    public void setResourceController(ResourceController resourceController) {
        this.resourceController = resourceController;
    }
    
    public List<Map<String,String>> stringToListOfMaps(String json){
        Gson gson = new Gson();
        Type resultType = new TypeToken<List<Map<String,String>>>(){}.getType();
        
        
        return gson.fromJson(json, resultType);
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
    
    
}
