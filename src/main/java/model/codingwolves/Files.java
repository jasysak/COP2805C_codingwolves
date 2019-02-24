/**
 * 
 */
package model.codingwolves;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author David Alvarez, 2/24/19
 *
 */
public class Files {
	private SimpleStringProperty fileName;
	private SimpleStringProperty fileStatus;
	
	public Files(String fileName, String fileStatus) {
		this.fileName = new SimpleStringProperty(fileName);
		this.fileStatus = new SimpleStringProperty(fileStatus);
	}
	public String getFileName() {
		return fileName.get();
	}
	public void setFileName(String filename) {
		fileName.set(filename);
	}
	public String getFileStatus() {
		return fileStatus.get();
	}
	public void setFileStatus(String filestatus) {
		fileStatus.set(filestatus);
	}
}
