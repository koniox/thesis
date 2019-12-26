/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.controllers;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import pl.polsl.debinski.konrad.beans.ResourceBean;
import pl.polsl.debinski.konrad.pojo.Resource;

/**
 *
 * @author debin
 */
@ManagedBean
@ViewScoped
public class ResourceController implements Serializable{
    
    @EJB
    ResourceBean resourceBean;
    
    public List<Resource> getResources(){
        return resourceBean.findAll();
    }
}
