package com.codingwolves.FileParser;


public class FileTemplate {
    String FileName;
    String CheckSum;
    String Path;
    String [] ParsedData;
    public FileTemplate(){ }

    public FileTemplate(String FileName, String CheckSum, String Path, String[] ParsedData){
        super();
        this.FileName = FileName;
        this.CheckSum = CheckSum;
        this.Path = Path;
        this.ParsedData = ParsedData;
    }

    public String getFileName(){return this.FileName;}
    public void setFileName(String FileName){this.FileName = FileName;}

    public String getCheckSum(){return this.CheckSum;}
    public void setCheckSum(String CheckSum){this.CheckSum = CheckSum;}

    public String getPath(){return this.Path;}
    public void setPath(String Path){this.Path=Path;}

    public String[] getParsedData(){return this.ParsedData;}
    public void setParsedData(String[] ParsedData){this.ParsedData=ParsedData;}

}
