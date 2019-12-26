/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.pojo.parser;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Konrad Dębiński
 */
public class CSVParser implements Parsable{

    public String parse(File file) throws IOException{
        List<Map<String,String>> data = new ArrayList<>();

        String line;

        //try with resources is not allowed at level 5 bblabla
        BufferedReader br = new BufferedReader(new FileReader(file));
        String[] labels = {};
        //reading first line for labels
        if((line = br.readLine()) != null){
             labels = line.split(";");
        }


        //next lines
        while ((line = br.readLine()) != null) {

            Map<String,String> singleRowData = new LinkedHashMap<>();
            // use comma as separator
            String[] rowData = line.split(";");

            for(int i = 0; i < labels.length;i++){
                singleRowData.put(labels[i],rowData[i]);
            }

            data.add(singleRowData);


        }


        String json = new Gson().toJson(data);
        
        return json;
    }
    
}
