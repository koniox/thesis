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
 * @author debin
 */
public class JSONParser implements Parsable{
    public String parse(File file) throws IOException{
        List<Map<String,String>> data = new ArrayList<>();

        JsonReader reader = new JsonReader(new FileReader(file));

        reader.beginArray();
        while (reader.hasNext()){
            data.add(readElement(reader));
        }
        reader.endArray();

        reader.close();
        
        String json = new Gson().toJson(data);
        
        return json;

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
