/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.pojo.parser;

import java.io.File;
import java.io.IOException;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author debin
 */
public class Parser {
    private Parsable parser;
    
    public Parser(Parsable parser){
        this.parser = parser;
    }
    
    public String getJson(File file) throws IOException{
        return parser.parse(file);
    }
    
    
}
