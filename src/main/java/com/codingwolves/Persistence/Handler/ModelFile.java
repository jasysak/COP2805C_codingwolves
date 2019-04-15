package com.codingwolves.Persistence.Handler;

public class ModelFile {
    private String fileName;
    private String checksum;
    private String filePath;

    public ModelFile(){
        super();
    }

    public ModelFile(String fileName, String checksum, String filePath){
        super();
        this.fileName =fileName;
        this.checksum = checksum;
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
