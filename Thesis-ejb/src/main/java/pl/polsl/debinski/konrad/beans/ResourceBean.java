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
     * manager of entity instances
     */
    @PersistenceContext
    private EntityManager em;
    
    /**
     * creates or updates the record in database 
     * @param resource represents resource object
     * @return object persisted into database
     */
    public Resource createOrUpdateResource(Resource resource){
        if(resource.getId() == null)
            em.persist(resource);
        else
            em.merge(resource);
        
        return resource;
    }
    /**
     * deletes record from database
     * @param id of record, which will be deleted
     */
    public void deleteResource(Integer id){
        Resource resource = em.find(Resource.class, id);
        if(resource != null)
            em.remove(resource);
    }
    /**
     * reads all record from database
     * @return list of records from Resources table
     */
    public List<Resource> findAll(){
        return em.createNamedQuery("Resource.findAll").getResultList();
    }
    
    /**
     * reads record with specific id
     * @param id of record to be read
     * @return record with provided id
     */
    public Resource findById(Integer id){
        return em.find(Resource.class, id);
    }
    
}
