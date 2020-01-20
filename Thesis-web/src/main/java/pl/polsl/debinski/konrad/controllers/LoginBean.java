/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import pl.polsl.debinski.konrad.beans.AdminBean;

/**
 *
 * @author Konrad Dębiński
 * @version 1.0
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{
    
    /**
     *  injected admin bean allows to validate credentials passed in frontend
     */
    @EJB
    private AdminBean adminBean;

    /**
     * login provided in form
     */
    private String login;
    /**
     * password provided in form
     */
    private String password;
    
    /**
     * validates login data and adds attribute to session map, then provides redirection
     */
    public void validateUsernamePassword() {
            boolean valid = adminBean.validate(login, password);
            FacesContext context = FacesContext.getCurrentInstance();

            if (valid) {
                context.getExternalContext().getSessionMap().put("user", login);
                try {

                    context.getExternalContext().redirect("restricted/adminpanel.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                    context.addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                    "Authentication failed",
                                                    "Check username or password"));
            }
    }
            
    /**
     * invalidates session and redirect to home page
     */
    public void logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();

        try {
            context.getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}
