package com.codingwolves.web.model;

/**
 * This class is used for direct injection of the client's data for usage by the classes
 * on the server side. The injection happens using Jackson Framework's RequestBody
 * annotation
 * @author Reubin George
 * @since 04/19/2019
 * @version 1.0
 */
public class AjaxData {
    private String Code;
    private int filesProcessed;
    private int filesParsed;
    private String[] fileName;
    private String[] checkSum;
    private String[] path;
    private String[] parsedData;

    /*
    public String getCode(){
        return this.Code;
    }
    public void setCode(String Code){
        this.Code=Code;
    }

    public int getFilesProcessed (){
        return this.filesProcessed;
    }
    public void setFileProcessed(int filesProcessed){
        this.filesProcessed=filesProcessed;
    }
    */
    public int getFilesParsed(){return  this.filesParsed;}
    public void  setFilesParsed(int filesParsed){this.filesParsed=filesParsed;}

    public String[] getFileName(){return  this.fileName;}
    public void  setFileName(String[] fileName){this.fileName = fileName;}

    public String[] getCheckSum(){return this.checkSum;}
    public void setCheckSum(String[] checkSum){this.checkSum = checkSum;}

    public String[] getPath(){return this.path;}
    public void setPath(String[] path){this.path = path;}

    public String[] getParsedData(){return this.parsedData;}
    public void setParsedData(String[] parsedData){this.parsedData = parsedData;}

    @Override
    public String toString(){
        return Code;
    }

}
