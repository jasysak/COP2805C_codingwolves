package com.codingwolves.web.model;

import com.codingwolves.web.jsonview.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;

public class AjaxReceiver {
    @JsonView(Views.Public.class)
    List<String> fileNames;
    @JsonView(Views.Public.class)
    List<String> checksum;
    @JsonView(Views.Public.class)
    String actionPerformed;

    public AjaxReceiver(){}

    public AjaxReceiver(List<String> fileNames, List<String> checksum, String actionPerformed){
        super();
        this.fileNames = fileNames;
        this.checksum = checksum;
        this.actionPerformed = actionPerformed;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    public List<String> getChecksum() {
        return checksum;
    }

    public void setChecksum(List<String> checksum) {
        this.checksum = checksum;
    }

    public String getActionPerformed() {
        return actionPerformed;
    }

    public void setActionPerformed(String actionPerformed) {
        this.actionPerformed = actionPerformed;
    }

    /* List<String> files;

    public List<String> getFiles (){return files;}
    public void setFiles(List<String>files){this.files=files;}*/
}
