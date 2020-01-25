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
 * Parser interface
 * @author Konrad Dębiński
 * @version 1.0
 */
public interface Parsable {
    public Map<String,String> parse(File file) throws IOException;
}
