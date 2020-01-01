/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.pojo.parser;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Konrad Dębiński
 */
public class CSVParser implements Parsable{

    public Map<String,String> parse(File file) throws IOException{
        Map<String,String> parsedMap = new LinkedHashMap<>();
        List<Map<String,String>> data = new ArrayList<>();

        String line;
       
        
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
        String[] labels = {};
        //reading first line for labels
        if((line = br.readLine()) != null){
             labels = line.split(";",-1);
        }


        //next lines
        while ((line = br.readLine()) != null) {

            Map<String,String> singleRowData = new LinkedHashMap<>();
            // use comma as separator
            String[] rowData = line.split(";",-1);
            
            
            
            for(int i = 0; i < labels.length;i++){
                singleRowData.put(labels[i],rowData[i]);
            }

            data.add(singleRowData);


        }
        
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<labels.length;i++){
            sb.append(labels[i]);
            if(i != labels.length-1)
                sb.append(",");
        }


        String json = new Gson().toJson(data);
        
        parsedMap.put("labels", sb.toString());
        parsedMap.put("json", json);
        
        return parsedMap;
    }
    
}
