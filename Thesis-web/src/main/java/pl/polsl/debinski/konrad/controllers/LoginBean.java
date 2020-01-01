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
    
    @EJB
    private AdminBean adminBean;
    
    private static final long serialVersionUID = 1094801825228386363L;
    
    private String login;
    private String password;
    private String msg;
    

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
    
    //validate login
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
            
	//logout event, invalidate session
	public void logout() {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().invalidateSession();
            
            try {
                context.getExternalContext().redirect("adminlogin.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }       
	}
    
    
}
