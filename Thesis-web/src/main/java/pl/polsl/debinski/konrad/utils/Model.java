/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.utils;

import java.io.Serializable;

/**
 *
 * @author Konrad Dębiński
 * @version 1.0
 */
public class Model implements Serializable{
    private String header;
    private String property;

    public Model(String header, String property) {
        this.header = header;
        this.property = property;
    }

    public String getHeader() {
        return header;
    }

    public String getProperty() {
        return property;
    }

    
    
    
}
