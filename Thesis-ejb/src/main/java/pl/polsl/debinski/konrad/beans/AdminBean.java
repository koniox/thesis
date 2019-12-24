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
    
    @PersistenceContext
    private EntityManager em;
    
    public Admin createOrUpdateAdmin(Admin admin){
        if(admin.getId() == null)
            em.persist(admin);
        else
            em.merge(admin);
        
        return admin;
    }
    /**
     * 
     * @param id 
     */
    public void deleteAdmin(Integer id){
        Admin admin = em.find(Admin.class, id);
        if(admin != null)
            em.remove(admin);
    }
    /**
     * 
     * @return 
     */
    public List<Admin> findAll(){
        return em.createNamedQuery("Admin.findAll").getResultList();
    }
    
    public boolean validate(String login, String password){
        List<Admin> admins =  em.createNamedQuery("Admin.isThere")
                .setParameter("validationLogin", login)
                .setParameter("validationPwd", password)
                .getResultList();
 
        return !admins.isEmpty();
    }
}
