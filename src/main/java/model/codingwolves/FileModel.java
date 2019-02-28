/**
 * 
 */
package model.codingwolves;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import view.codingwolves.Main;
import view.codingwolves.MaintenanceWindow;

/**
 * @author David Alvarez, 2/23/19
 *
 */
public class FileModel {
	private String fileName;
	//Using an observable list to be able to monitor when elements change
	private final ObservableList<Files> files = FXCollections.observableArrayList();
	
	/**
	 * This method will add a file to the list files with a unique file id,
	 * the path, when it was lastModified, and the status of the file.
	 * 
	 * @param fileName The file to be added to the list
	 */
	public void addFile(String fileName) {
		long fileId = Main.nextFileID;
		Main.nextFileID += 1L;
		File file = new File(fileName);
		long fileLastModified = file.lastModified();
		String fileStatus = "Indexed"; //This is just to test
		files.add(new Files(fileId, fileName, fileLastModified, fileStatus));
		
		//Add full path name to the fileName column
		MaintenanceWindow.fileNameCol.setCellValueFactory(new PropertyValueFactory<Files, String>("fileName"));
		//Add the status of the file to the status column
		MaintenanceWindow.statusCol.setCellValueFactory(new PropertyValueFactory<Files, String>("fileStatus"));
		MaintenanceWindow.table.setItems(files);
		
		int numFiles = files.size();
		String str = Integer.toString(numFiles);
		Main.numOfFilesIndexed.setText(str);
		MaintenanceWindow.numOfFilesIndexed.setText(str);
	}
	/**
	 * This method will remove the specified file from the data list and
	 * update the columns in the maintenance view.
	 * 
	 * @param fileId The identifier for the file to be removed
	 */
	public void removeFile(long fileId) {
		//Loop will iterate through list and remove elements with the current fileId
		for (Iterator<Files> iterator = files.iterator(); iterator.hasNext();)
		{
			Files currentFile = iterator.next();
			if (currentFile.getFileId() == fileId)
			{
					iterator.remove();
			}
		}
	}
	public void updateFileLastModified() {
		
	}
}
