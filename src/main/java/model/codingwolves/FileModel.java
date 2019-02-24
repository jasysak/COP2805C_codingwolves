/**
 * 
 */
package model.codingwolves;

import java.io.File;
import java.util.ArrayList;
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
	static List<String> listOfFiles = new ArrayList();
	private final ObservableList<Files> data = FXCollections.observableArrayList();
	
	public void addFile(String fileName) {
		File file = new File(fileName);
		long fileLastModified = file.lastModified();
		listOfFiles.add(fileName);
		String fileStatus = "Indexed"; //This is just to test
		data.add(new Files(fileName, fileStatus));
		MaintenanceWindow.fileNameCol.setCellValueFactory(new PropertyValueFactory<Files, String>("fileName"));
		MaintenanceWindow.statusCol.setCellValueFactory(new PropertyValueFactory<Files, String>("fileStatus"));
		MaintenanceWindow.table.setItems(data);
		
		int numFiles = listOfFiles.size();
		String str = Integer.toString(numFiles);
		Main.numOfFilesIndexed.setText(str);
		MaintenanceWindow.numOfFilesIndexed.setText(str);
	}
	public void removeFile() {
		
	}
	public void updateFileLastModified() {
		
	}
}
