/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.pojo.parser;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
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
 * @version 1.0
 */
public class JSONParser implements Parsable{

   
    public Map<String, String> parse(File file) throws IOException{
        Map<String,String> parsedMap = new LinkedHashMap<>();
        List<Map<String,String>> data = new ArrayList<>();

        JsonReader reader = new JsonReader(new FileReader(file));

        reader.beginArray();
        while (reader.hasNext()){
            data.add(readElement(reader));
        }
        reader.endArray();

        reader.close();
        
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for(String key: data.get(0).keySet()){
            sb.append(key);
            if(i++ != data.get(0).size() - 1){
                sb.append(",");
            }
        }


       
        String json = new Gson().toJson(data);
        
        parsedMap.put("labels", sb.toString());
        parsedMap.put("json", json);
        
        return parsedMap;

    }
    private Map<String, String> readElement(JsonReader reader) throws IOException {
        Map<String,String> element = new LinkedHashMap<>();
        reader.beginObject();
        while (reader.hasNext()){
            String key = reader.nextName();
            String value = (reader.peek() == JsonToken.NULL) ? "" : reader.nextString();
            if(value.equals(""))
                reader.skipValue();
            element.put(key,value);
        }
        reader.endObject();
        return element;

    }
    
}
