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
import pl.polsl.debinski.konrad.pojo.Admin;

/**
 *
 * 
 * @author Konrad Dębiński
 * @version 1.0
 */
@Stateless  
@LocalBean
public class AdminBean {
    
    /**
     * manager of entity instances
     */
    @PersistenceContext
    private EntityManager em;
    
    /**
     * creates or updates the record in database
     * @param admin represents administrator object
     * @return object persisted into database
     */
    public Admin createOrUpdateAdmin(Admin admin){
        if(admin.getId() == null)
            em.persist(admin);
        else
            em.merge(admin);
        
        return admin;
    }
    /**
     * deletes record from database
     * @param id of record, which will be deleted
     */
    public void deleteAdmin(Integer id){
        Admin admin = em.find(Admin.class, id);
        if(admin != null)
            em.remove(admin);
    }
    /**
     * reads all records from database
     * @return list of records from Admins table 
     */
    public List<Admin> findAll(){
        return em.createNamedQuery("Admin.findAll").getResultList();
    }
    
    /**
     * checks if there is in database record with provided data
     * @param login 
     * @param password
     * @return boolean information if there is in database record with provided data
     */
    public boolean validate(String login, String password){
        List<Admin> admins =  em.createNamedQuery("Admin.isThere")
                .setParameter("validationLogin", login)
                .setParameter("validationPwd", password)
                .getResultList();
 
        return !admins.isEmpty();
    }
}
