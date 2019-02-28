/**
 * 
 */
package model.codingwolves;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author David Alvarez, 2/24/19
 *
 */
public class Files {
	private SimpleLongProperty fileId;
	private SimpleStringProperty fileName;
	private SimpleLongProperty fileLastModified;
	private SimpleStringProperty fileStatus;
	
	public Files(long fileId, String fileName, long fileLastModified, String fileStatus) {
		this.fileId = new SimpleLongProperty(fileId);
		this.fileName = new SimpleStringProperty(fileName);
		this.fileLastModified = new SimpleLongProperty(fileLastModified);
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
	public String getFileStatus() {
		return fileStatus.get();
	}
	public void setFileStatus(String filestatus) {
		fileStatus.set(filestatus);
	}
}
