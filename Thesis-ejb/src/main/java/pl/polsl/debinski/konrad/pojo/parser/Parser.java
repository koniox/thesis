/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.pojo.parser;

import java.io.File;
import java.io.IOException;
import java.util.Map;


/**
 *
 * @author debin
 */
public class Parser {
    private Parsable parser;
    
    
    public Parser(){
        
    }
    
    public Parser(Parsable parser){
        this.parser = parser;
    }
    
    public Map<String,String> getData(File file) throws IOException{
        return parser.parse(file);
    }
    
   
}
