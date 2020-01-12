/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pl.polsl.debinski.konrad.beans.ResourceBean;
import pl.polsl.debinski.konrad.pojo.Resource;

/**
 *
 * @author debin
 */


@Path("/resources")
@RequestScoped
public class ResourceService{ 
    @EJB
    private ResourceBean resourceBean;

    public ResourceService() {
    }
    
    
    @GET
    //@Path("/resources")
    @Produces(MediaType.APPLICATION_XML) 
    public List<Resource> getResources(){
        return resourceBean.findAll();
    }
    
    @GET
    @Path("/{resourceid}")
    @Produces(MediaType.APPLICATION_XML)
    public Resource getResource(@PathParam("resourceid") int resourceId){
        return resourceBean.findById(resourceId);
    }
    
    
}
