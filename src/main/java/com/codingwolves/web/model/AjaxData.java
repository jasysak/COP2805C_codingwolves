package com.codingwolves.web.model;

public class AjaxData {
    String Code;
    int filesProcessed;
    int filesParsed;
    String[] fileName;
    String[] checkSum;
    String[] path;
    String[] parsedData;

    /**
     *
     * @return
     */
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
