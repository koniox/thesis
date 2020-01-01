/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.pojo.parser;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author debin
 */
public class XLSXParser implements Parsable{

    

    public Map<String,String> parse(File file) throws IOException{
        Map<String,String> parsedMap = new LinkedHashMap<>();
        List<Map<String,String>> data = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        boolean firstIteration = true;

        FileInputStream fs = null;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XLSXParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        XSSFWorkbook wb = new XSSFWorkbook(fs);
        XSSFSheet sheet = wb.getSheetAt(0);

        //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext())
        {
            XSSFRow row = (XSSFRow) rowIterator.next();


            //For each row, iterate through all the columns
            Iterator<Cell> cellIterator = row.cellIterator();
            Map<String,String> singleRowData = new LinkedHashMap<>();
            int i = 0;
            while (cellIterator.hasNext())
            {
                Cell cell = cellIterator.next();
                if(firstIteration){
                    labels.add(cell.toString());
                }
                else{
                    singleRowData.put(labels.get(i),cell.toString());
                    i++;
                }
            }
            if(!firstIteration)
                data.add(singleRowData);
            firstIteration = false;
        }
        fs.close();
       
        String stringLabels = labels.stream().collect(Collectors.joining(","));
        String json = new Gson().toJson(data);
        
        parsedMap.put("labels", stringLabels);
        parsedMap.put("json", json);
        
        return parsedMap;

    }
    
}
