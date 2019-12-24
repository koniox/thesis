/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.beans;


import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.debinski.konrad.pojo.Resource;

/**
 *
 * @author Konrad Dębiński
 * @version 1.0
 */
@Stateless
@LocalBean
public class ResourceBean{

    /**
     * 
     */
    @PersistenceContext
    private EntityManager em;
    
    /**
     * 
     * @param resource
     * @return 
     */
    public Resource createOrUpdateResource(Resource resource){
        if(resource.getId() == null)
            em.persist(resource);
        else
            em.merge(resource);
        
        return resource;
    }
    /**
     * 
     * @param id 
     */
    public void deleteResource(Integer id){
        Resource resource = em.find(Resource.class, id);
        if(resource != null)
            em.remove(resource);
    }
    /**
     * 
     * @return 
     */
    public List<Resource> findAll(){
        return em.createNamedQuery("Resource.findAll").getResultList();
    }
    
}
