package com.codingwolves.web.model;

import com.codingwolves.web.jsonview.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
/**
 * This class provides the means with which the JAVA app gives persistent data
 * information to the client using Jackson Framework. This injection happens using {@link com.codingwolves.web.jsonview.Views}
 * @author Reubin George
 * @since 04/19/2019
 * @version 1.0
 */
public class AjaxReceiver {
    @JsonView(Views.Public.class)
    private List<String> fileNames;
    @JsonView(Views.Public.class)
    private List<String> checksum;
    @JsonView(Views.Public.class)
    private String actionPerformed;

    /**
     * Default constructor with its set of mutator and accessors
     */
    public AjaxReceiver(){}

    /**
     * Non-default constructor that accepts all the fields required for this class
     * and doesn't need any mutator methods.
     * @param fileNames Names of all files provided by the user
     * @param checksum MD5 hashes of all the files
     * @param actionPerformed Message showing action that the server performed
     */
    @NonNull
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

}
