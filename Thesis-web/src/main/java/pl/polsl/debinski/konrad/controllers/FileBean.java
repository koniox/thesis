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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import pl.polsl.debinski.konrad.beans.ResourceBean;
import pl.polsl.debinski.konrad.pojo.Resource;
import pl.polsl.debinski.konrad.pojo.parser.Parsable;
import pl.polsl.debinski.konrad.pojo.parser.Parser;
import pl.polsl.debinski.konrad.pojo.parser.ParserFactory;

/**
 *
 * @author Konrad Dębiński
 * @version 1.0
 */
@ManagedBean
@RequestScoped
public class FileBean implements Serializable{
    private UploadedFile file;
//    private Resource resource;
//    private ParserFactory parserFactory;
    @EJB
    ResourceBean resourceBean;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public void handleFileUpload(FileUploadEvent event) throws IOException {
          this.file = event.getFile();
          InputStream in = file.getInputstream();
          File myFile = new File(file.getFileName());
          FileUtils.copyInputStreamToFile(in, myFile);
        try {
            ParserFactory parserFactory = new ParserFactory();
            
            Parser parser = parserFactory.createParser(FilenameUtils.getExtension(file.getFileName()));
            Resource resource = new Resource();
            
            resource.setJsonDataString(parser.getJson(myFile));
            resource.setTitle("testing");
            resource.setLabels("testing");
            resourceBean.createOrUpdateResource(resource);
        } catch (Exception ex) {
            Logger.getLogger(FileBean.class.getName()).log(Level.SEVERE, null, ex);
        }
          
          if (file != null) {
              FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
              FacesContext.getCurrentInstance().addMessage(null, message);
          }
          else{
              FacesMessage message = new FacesMessage("Jd", " is uploaded.");
              FacesContext.getCurrentInstance().addMessage(null, message);
          }
          
    }
}
