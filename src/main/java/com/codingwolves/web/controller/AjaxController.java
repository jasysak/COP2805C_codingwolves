package com.codingwolves.web.controller;

import com.codingwolves.Persistence.Handler.ModelFile;
import com.codingwolves.Persistence.Handler.PersistentData;
import com.codingwolves.web.model.AjaxData;
import com.codingwolves.web.model.AjaxReceiver;
import com.codingwolves.FileParser.FileTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import com.codingwolves.web.jsonview.Views;
import com.codingwolves.FileParser.*;
import org.springframework.web.bind.annotation.*;

import static com.codingwolves.InvertedIndex.InvertedIndexGenerator.*;
import static com.codingwolves.Persistence.Service.ServiceImplementer.databaseInitializer;
import java.util.*;

@RestController
public class AjaxController {
    List<FileTemplate>Files = new ArrayList<>();

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/performMagic")
    public AjaxReceiver getClientData(@RequestBody AjaxData clientData){

        try {

            FileCreator.generateFileList(clientData,Files);
            //postingsList(Files);
            PersistentData persistentData = new PersistentData();
            persistentData.addFile(Files);
            //persistentData.columnNames;
        }
        catch (Exception e){
            System.out.println("Error found");
            e.printStackTrace();
        }
        List<ModelFile>persistentFiles = new PersistentData().listAllFiles();
        List<String>fileNames = new ArrayList<>();
        List<String >checksum = new ArrayList<>();
        AjaxReceiver result;
        for(ModelFile individualFile: persistentFiles){
            fileNames.add(individualFile.getFileName());
            checksum.add(individualFile.getChecksum());
        }
        result = new AjaxReceiver(fileNames,checksum,"Persistent Data");
        return result;
    }
    @JsonView(Views.Public.class)
    @RequestMapping(value = "/persistantData")
    public AjaxReceiver getPersistantData(){
        AjaxReceiver result = new AjaxReceiver();
        return result;
    }
    @JsonView(Views.Public.class)
    @RequestMapping(value ="/initializer")
    public AjaxReceiver initializeDatabase(){
        String dbStatus = databaseInitializer();
        List<ModelFile>persistantFiles;
        List<String>fileNames = new ArrayList<>();
        List<String >checksum = new ArrayList<>();
        persistantFiles = (new PersistentData()).listAllFiles();
        for(ModelFile individualFile: persistantFiles){
            fileNames.add(individualFile.getFileName());
            checksum.add(individualFile.getChecksum());
        }
        AjaxReceiver result = new AjaxReceiver(fileNames,checksum,dbStatus);
        return result;
    }
    @JsonView(Views.Public.class)
    @RequestMapping(value = "/delete")
    public AjaxReceiver deleteFile(@RequestBody String fileName){
        AjaxReceiver result = new AjaxReceiver();
        PersistentData persistentData = new PersistentData();
        result.setActionPerformed(persistentData.deleteFile(fileName));
        return result;
    }
}