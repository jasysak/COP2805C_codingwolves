package com.codingwolves.Persistence.Handler;

/**
 * The ModelFile class is essentially a more efficient version of {@link com.codingwolves.FileParser.FileTemplate}.
 * This class contains the accessor and mutator methods for the file name, checksum and filepath ONLY.
 * The class has no information about the data within each file uploaded by the client.
 * @author Reubin George
 * @version 1.0
 * @since 04/19/2019
 */
public class ModelFile {
    private String fileName;
    private String checksum;
    private String filePath;

    /**
     * Default constructor for the class
     */

    public ModelFile(){
        super();
    }

    /**
     * Non-default constructor which sets the field without the need for any mutator.
     * @param fileName Name of the file
     * @param checksum MD5 hash of the file
     * @param filePath Path name of the file. 
     */
    public ModelFile(String fileName, String checksum, String filePath){
        super();
        this.fileName =fileName;
        this.checksum = checksum;
        this.filePath = filePath;
    }

    /**
     * Accessor that returns the file name of the object
     * @return the name of the file
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Mutator that sets a private field
     * @param fileName Name of the file
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Accessor method that returns a MD5 hash of a file
     * @return a MD5 hash of the file (also called checksum)
     */
    public String getChecksum() {
        return checksum;
    }

    /**
     * Mutator that sets the checksum for a file
     * @param checksum MD5 hash
     */
    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    /**
     * Accessor that returns the pathname of the file
     * @return the pathname of the file
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Mutator that sets the pathname of the file object
     * @param filePath pathname of the file
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
