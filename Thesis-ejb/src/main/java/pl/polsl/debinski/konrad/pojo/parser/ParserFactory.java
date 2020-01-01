/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.pojo.parser;

/**
 *
 * @author debin
 */
public class ParserFactory {
    
    public Parser createParser(String fileExtension) throws Exception{
        switch(fileExtension){
            case "csv":
                return new Parser(new CSVParser());
            case "xlsx":
                return new Parser(new XLSXParser());
            case "json":
                return new Parser(new JSONParser());
        }
        throw new Exception("wrong file type");
    }
}
