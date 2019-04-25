package com.codingwolves.web.controller;

import com.codingwolves.Persistence.Handler.ModelFile;
import com.codingwolves.Persistence.Handler.PersistentData;
import com.codingwolves.web.model.AjaxData;
import com.codingwolves.web.model.AjaxReceiver;
import com.codingwolves.FileParser.FileTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import com.codingwolves.web.jsonview.Views;
import com.codingwolves.FileParser.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.web.bind.annotation.*;
import static com.codingwolves.Persistence.Service.ServiceImplementer.*;
import java.util.*;

/**
 * The AjaxController class handles data from the client side and distributes the requests
 * to the various classes on the server side. These actions range from created inverted
 * indices to simply viewing the files in persistent storage. This class implements the
 * Spring Framework's Rest Controller
 * @author Reubin George
 * @since 04/19/2019
 * @version 1.0
 */
@RestController
public class AjaxController {
    /**
     * This methods accepts file information from the client and sends the this information
     * to various class that disseminates the information.
     * @param clientData the parameter uses Checker framework's NonNull and Jackson's RequestBody
     *                   annotation to get the data from the client side
     * @return Message showing the actions performed by the methods.
     */
    @JsonView(Views.Public.class)
    @RequestMapping(value = "/performMagic")
    public AjaxReceiver getClientData(@RequestBody @NonNull AjaxData clientData){
        AjaxReceiver result;
        List<FileTemplate>Files = new ArrayList<>();
        try {

            FileCreator.generateFileList(clientData,Files);
            PersistentData persistentData = new PersistentData();
            persistentData.addFile(Files);
        }
        catch (Exception e){
            System.out.println("Error found");
            e.printStackTrace();
        }

        List<ModelFile>persistentFiles = new PersistentData().listAllFiles();
        List<String> fileNames = new ArrayList<>();
        List<String> checksum = new ArrayList<>();

        for(ModelFile individualFile: persistentFiles){
            fileNames.add(individualFile.getFileName());
            checksum.add(individualFile.getChecksum());
        }
        result = new AjaxReceiver(fileNames,checksum,"Persistent Data");
        return result;
    }

    /**
     * This method check and creates database and tables if necessary every time the
     * main page is called.
     * @return A message to if database or tables are created
     */

    @JsonView(Views.Public.class)
    @RequestMapping(value ="/initializer")
    public AjaxReceiver initializeDatabase(){
        String dbStatus = databaseInitializer();
        List<ModelFile>persistentFiles;
        List<String>fileNames = new ArrayList<>();
        List<String >checksum = new ArrayList<>();
        persistentFiles = (new PersistentData()).listAllFiles();
        for(ModelFile individualFile: persistentFiles){
            fileNames.add(individualFile.getFileName());
            checksum.add(individualFile.getChecksum());
        }
        AjaxReceiver result = new AjaxReceiver(fileNames,checksum,dbStatus);
        return result;
    }

    /**
     * This method is used to delete a file from persistent storage
     * @param fileName Client provides this string
     * @return A message that displays the actions performed
     */
    @JsonView(Views.Public.class)
    @RequestMapping(value = "/delete")
    public AjaxReceiver deleteFile(@RequestBody String fileName){
        AjaxReceiver result = new AjaxReceiver();
        PersistentData persistentData = new PersistentData();
        result.setActionPerformed(persistentData.deleteFile(fileName));
        return result;
    }
}