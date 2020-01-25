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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import pl.polsl.debinski.konrad.beans.ResourceBean;
import pl.polsl.debinski.konrad.pojo.Resource;
import pl.polsl.debinski.konrad.utils.Model;

/**
 *
 * @author Konrad Dębiński
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class ResourcesController implements Serializable{
    private List<Model> columns  = new ArrayList<>();
    private List<Map<String,String>> data;
    private List<Map<String,String>> filteredData;
    private String title = "empty";
    private Resource resource;
    private String selectedColumnKey;
    private String filterType;
    private List<String> filterValues;
    private String filterValue;
    Map<String,List<String>> uniqueValuesMap;
//    Map<String,List<String>> notUsedUniqueValues;
    Map<String,String> filterHistoryMap = new HashMap<>();

    
    @ManagedProperty(value = "#{resourceView}")
    private ResourceViewBean resourceViewBean;
    
    @EJB
    private ResourceBean resourceBean;

    @PostConstruct
    protected void init(){
        if(resourceViewBean.getSelectedResource() != null){
            data = stringToListOfMaps(resourceBean.findById(resourceViewBean.getSelectedResource()).getJsonDataString());
            filteredData = data;
            title = resourceBean.findById(resourceViewBean.getSelectedResource()).getTitle();
            populateColumns();
            uniqueValuesMap = numberOfUniqueValues();
        //    notUsedUniqueValues = uniqueValuesMap;
        }
        
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public void onChangeFilterAdd(){
        if(filterValues == null)
            filterValues = new ArrayList<>();
        if(filterValue!=null && !filterValue.isEmpty() && !filterValues.contains(filterValue)){
            filterValues.add(filterValue);
        }
    }
    
    public void removeUsedUniqueValue(){
        
        if(uniqueValuesMap.get(selectedColumnKey) != null){
            Iterator<String> iterator = uniqueValuesMap.get(selectedColumnKey).iterator();
            while(iterator.hasNext()){
               String entry = iterator.next();
               boolean isFound = false;
                for(String temp:filterValues){
                    if(temp.equals(entry))
                        isFound = true;
                }
                if(!isFound)
                    iterator.remove();
            }
        }
            
        }
    public List<String> valuesOfKeyList(){
        return uniqueValuesMap.get(selectedColumnKey);
    }
    public boolean isFewBoolean(){
        return uniqueValuesMap.containsKey(selectedColumnKey);
        
    }
    public boolean isNotNull(){
        return (selectedColumnKey != null && !"empty".equals(selectedColumnKey) && !isFewBoolean());
    }
    
    
    public void filter(){
        
        if(("empty".equals(selectedColumnKey) || "empty".equals(filterType)) || filterValues.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Filter error",
                            "Incorrect filter usage, provide column/filter/value and press submit"));
        }else{
            if(isFewBoolean()){
                filterType="equals";
            }
            List<Map<String,String>> temp = new ArrayList<>();
            for(Map<String,String> row : filteredData){
                for(String filterValue:filterValues){
                    switch(filterType){
                        case "greaterOrEqual":{
                            if(isNumeric(row.get(selectedColumnKey)) && isNumeric(filterValue)){
                                Double val = Double.parseDouble(row.get(selectedColumnKey));
                                if(val.compareTo(Double.parseDouble(filterValue)) >= 0){
                                    temp.add(row);
                                }
                            }else{
                                if(row.get(selectedColumnKey).compareTo(filterValue) >= 0){
                                    temp.add(row);
                                }
                            }
                            break;
                        }
                        case "lessOrEqual":{
                            if(isNumeric(row.get(selectedColumnKey)) && isNumeric(filterValue)){
                                Double val = Double.parseDouble(row.get(selectedColumnKey));
                                if(val.compareTo(Double.parseDouble(filterValue)) <= 0){
                                    temp.add(row);
                                }
                            }else{
                                if(row.get(selectedColumnKey).compareTo(filterValue) <= 0){
                                    temp.add(row);
                                }
                            }
                            break;
                        }
                        case "contains":{
                            if(row.get(selectedColumnKey).contains(filterValue)){
                                temp.add(row);
                            }
                            break;
                        }
                        case "equals":{
                            if(row.get(selectedColumnKey).equals(filterValue)){
                                temp.add(row);
                            }
                            break;
                        }
                    }
                }
                
            }
            
            removeUsedUniqueValue();
            filterHistoryMap.put(selectedColumnKey, ".");
            selectedColumnKey = null;
            filterType = null;
            filterValue = null;
            filterValues = null;
            filteredData = temp;
        }
    }

    public Map<String, String> getFilterHistoryMap() {
        return filterHistoryMap;
    }

    public void setFilterHistoryMap(Map<String, String> filterHistoryMap) {
        this.filterHistoryMap = filterHistoryMap;
    }
    
    
    public Map<String,List<String>> numberOfUniqueValues(){
        Map<String,List<String>> values = new HashMap<>();
        for(Map<String,String> map : data){
            for(Map.Entry<String,String> entry: map.entrySet()){
                if(!values.containsKey(entry.getKey())){
                    List<String> list = new ArrayList<>();
                    values.put(entry.getKey(),list);
                }
                if(!values.get(entry.getKey()).contains(entry.getValue())){
                    values.get(entry.getKey()).add(entry.getValue());
                }
            }
        }
        
        Iterator<Map.Entry<String ,List<String>>> iterator = values.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,List<String>> entry = iterator.next();
            if(entry.getValue().size() > 5){
                iterator.remove();
            }
        }

        return values;
    }
    
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    
    public boolean isBig(){
        return columns.size() > 20;
    }

    public List<Map<String,String>> stringToListOfMaps(String json){
        Gson gson = new Gson();
        Type resultType = new TypeToken<List<Map<String,String>>>(){}.getType();
        
        
        return gson.fromJson(json, resultType);
    }

    //regex - split by commas, but no inside quotation marks
    public void populateColumns(){
        List<String> columnKeys = Arrays.asList(resourceBean.findById(resourceViewBean.getSelectedResource()).
                                                                                       getLabels().
                                                                                       split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"));
        for(String columnKey:columnKeys){
            //added quoatation marks for all column labels for better "," escaping and here we are deleting it for formating
            String columnKeyString = columnKey.replaceAll("\"", "");
            columns.add(new Model(columnKeyString.toUpperCase(), columnKeyString));
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
    
    public Map<String, List<String>> getUniqueValuesMap() {
        return uniqueValuesMap;
    }

    public void setUniqueValuesMap(Map<String, List<String>> uniqueValuesMap) {
        this.uniqueValuesMap = uniqueValuesMap;
    }
    
    
    public List<String> getFilterValues() {
        return filterValues;
    }

    public void setFilterValues(List<String> filterValues) {
        this.filterValues = filterValues;
    }
    
    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }
    
    public String getSelectedColumnKey() {
        return selectedColumnKey;
    }

    public void setSelectedColumnKey(String selectedColumnKey) {
        this.selectedColumnKey = selectedColumnKey;
    }
    
    public List<Map<String, String>> getFilteredData() {
        return filteredData;
    }

    public void setFilteredData(List<Map<String, String>> filteredData) {
        this.filteredData = filteredData;
    }
}
