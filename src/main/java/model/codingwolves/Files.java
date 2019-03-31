package model.codingwolves;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author David Alvarez, 2/24/19
 * 
 * For COP2805C Group Project
 * 
 * codingwolves team
 * Members:
 * David Alvarez
 * Reubin George
 * Erin Hochstetler
 * Jason Sysak
 *
 * WORK IN PROGRESS
 * 
 * Object class to define Files objects contained in the List
 * of files stored/that have been indexed in the inverted index.
 * 
 */

public class Files {
	private SimpleLongProperty fileId;
	private SimpleStringProperty fileName;
	private SimpleLongProperty fileLastModified;
	private SimpleStringProperty checkSum;
	private SimpleStringProperty fileStatus;
	
	public Files(long fileId, String fileName, long fileLastModified, String checkSum, String fileStatus) {
		this.fileId = new SimpleLongProperty(fileId);
		this.fileName = new SimpleStringProperty(fileName);
		this.fileLastModified = new SimpleLongProperty(fileLastModified);
		this.checkSum = new SimpleStringProperty(checkSum);
		this.fileStatus = new SimpleStringProperty(fileStatus);
	}
	public long getFileId() {
		return fileId.get();
	}
	public void setFileId(long fileid) {
		fileId.set(fileid);
	}
	public String getFileName() {
		return fileName.get();
	}
	public void setFileName(String filename) {
		fileName.set(filename);
	}
	public long getLastModified() {
		return fileLastModified.get();
	}
	public void setLastModified(long lastmodified) {
		fileLastModified.set(lastmodified);
	}
	public String getCheckSum() {
		return checkSum.get();
	}
	public void setCheckSum(String checksum) {
		checkSum.set(checksum);
	}
	public String getFileStatus() {
		return fileStatus.get();
	}
	public void setFileStatus(String filestatus) {
		fileStatus.set(filestatus);
	}
}
