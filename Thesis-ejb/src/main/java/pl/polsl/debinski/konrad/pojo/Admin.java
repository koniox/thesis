/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Class representing administrator entity
 * @author Konrad Dębiński
 * @version 1.0
 */
@Entity
@Table(name = "admins")
@NamedQueries({
    @NamedQuery(name = Admin.FIND_ALL,
            query = "SELECT a FROM Admin a"),
    @NamedQuery(name = Admin.IS_IN_DATABASE,
            query = "SELECT a FROM Admin a where a.login LIKE :validationLogin and a.password LIKE :validationPwd"),
})
public class Admin implements Serializable {
    /**
     * query name
     */
    public static final String FIND_ALL = "Admin.findAll";
    /**
     * query name
     */
    public static final String IS_IN_DATABASE = "Admin.isThere";
    
    /**
     * id of the record
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    /**
     * login
     */
    @Column(name = "login")
    private String login;
    /**
     * password
     */
    @Column(name = "password")
    private String password;
    /**
     * constructor
     * @param login login
     * @param password password
     */
    public Admin(String login, String password) {
        this.login = login;
        this.password = password;
    }
    /**
     * default constructor
     */
    public Admin() {
    }
     
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Admin)) {
            return false;
        }
        Admin other = (Admin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.polsl.debinski.konrad.pojo.admin.Admin[ id=" + id + " ]";
    }
    
}
