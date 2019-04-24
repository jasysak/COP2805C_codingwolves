package com.codingwolves.FileParser;

/**
 * This class represents is how the rest of the Java application on the server side gets its
 * information. The raw data from the client side is set into this class where the file's
 * name, MD5 hash (checksum), pathname and the data inside each file is stored.
 * @author Reubin George
 * @version 1.0
 * @since 04/19/2019
 */

public class FileTemplate {
    private String FileName;
    private String CheckSum;
    private String Path;
    private String [] ParsedData;

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
