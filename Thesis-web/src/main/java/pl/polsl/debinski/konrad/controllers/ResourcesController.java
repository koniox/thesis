/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import pl.polsl.debinski.konrad.beans.ResourceBean;
import pl.polsl.debinski.konrad.pojo.Resource;
import pl.polsl.debinski.konrad.utils.Model;

/**
 *
 * @author debin
 */
@ManagedBean
@ViewScoped
public class ResourcesController implements Serializable{
    private List<Model> columns  = new ArrayList<>();
    private List<Map<String,String>> data;
    private String title = "empty";
    private Resource resource;
    
    @ManagedProperty(value = "#{resourceView}")
    private ResourceViewBean resourceViewBean;
    
    @EJB
    private ResourceBean resourceBean;

    @PostConstruct
    protected void init(){
        if(resourceViewBean.getSelectedResource() != null){
            data = stringToListOfMaps(resourceBean.findById(resourceViewBean.getSelectedResource()).getJsonDataString());
            title = resourceBean.findById(resourceViewBean.getSelectedResource()).getTitle();
            populateColumns();
        }
        
    }
    
    public List<Map<String,String>> stringToListOfMaps(String json){
        Gson gson = new Gson();
        Type resultType = new TypeToken<List<Map<String,String>>>(){}.getType();
        
        
        return gson.fromJson(json, resultType);
    }

    
    public void populateColumns(){
        List<String> columnKeys = Arrays.asList(resourceBean.findById(resourceViewBean.getSelectedResource()).getLabels().split(","));
        for(String columnKey:columnKeys){
            columns.add(new Model(columnKey.toUpperCase(), columnKey));
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ResourceViewBean getResourceViewBean() {
        return resourceViewBean;
    }

    public void setResourceViewBean(ResourceViewBean resourceViewBean) {
        this.resourceViewBean = resourceViewBean;
    }
    
    
    
    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
    
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
    
}
