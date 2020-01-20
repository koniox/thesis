/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.pojo.parser;

import javax.resource.NotSupportedException;

/**
 *
 * @author Konrad Dębiński
 * @version 1.0
 */
public class ParserFactory {
    
    public Parsable createParser(String fileExtension) throws Exception{
        switch(fileExtension){
            case "csv":
                return new CSVParser();
            case "xlsx":
                return new XLSXParser();
            case "json":
                return new JSONParser();
            case "xls":
                return new XLSParser();
            default:
                throw new NotSupportedException();
        }
        
    }
}
