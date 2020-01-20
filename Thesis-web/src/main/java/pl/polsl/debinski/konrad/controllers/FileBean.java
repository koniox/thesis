/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.debinski.konrad.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import pl.polsl.debinski.konrad.pojo.parser.Parsable;
import pl.polsl.debinski.konrad.pojo.parser.ParserFactory;

/**
 *
 * @author Konrad Dębiński
 * @version 1.0
 */
@ManagedBean(name = "fileBean", eager = true)
@ViewScoped
public class FileBean implements Serializable{
    /**
     * file uploaded by primefaces component
     */
    private UploadedFile file;
    /**
     * data of uploaded file after parsing
     */
    private Map<String,String> fileData;
    /**
     * name of the file
     */
    private String fileName;
    
    /**
     * Handles uploading file 
     * @param event upload of file 
     * @throws IOException
     * @throws Exception 
     */
    public void handleFileUpload(FileUploadEvent event) throws IOException, Exception{
        this.file = event.getFile();
        fileName = FilenameUtils.removeExtension(file.getFileName());
        InputStream in = file.getInputstream();
        File myFile = new File(fileName);
        FileUtils.copyInputStreamToFile(in, myFile);
      
        ParserFactory parserFactory = new ParserFactory();

        Parsable parser = parserFactory.createParser(FilenameUtils.getExtension(file.getFileName()));

        fileData = parser.parse(myFile);

        if (file != null) {
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }      
    }
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
     
    public Map<String, String> getFileData() {
        return fileData;
    }

    public void setFileData(Map<String, String> fileData) {
        this.fileData = fileData;
    }
    
    
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

  
}
