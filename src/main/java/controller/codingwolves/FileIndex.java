/**
 * 
 */
package controller.codingwolves;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.codingwolves.FileModel;
import model.codingwolves.Files;
import view.codingwolves.MaintenanceWindow;

/**
 * @author David Alvarez, 2/24/19
 *
 */
public class FileIndex {
	static FileModel model = new FileModel();
	static String fileName;
	
	public static void andSearch() {
		
	}
	public static void orSearch() {
		
	}
	public static void phraseSearch() {
		
	}
	public static void addFileToIndex() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose a file to add to the Index");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("Text Files", "*.txt"),
			new FileChooser.ExtensionFilter("HTML Files", "*.html")
		);
		File selectedFile = fileChooser.showOpenDialog(new Stage());
		if (selectedFile == null) {
			return;
		}
		else if (selectedFile != null) {
			try {
				fileName = selectedFile.getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//if (fileName)
		model.addFile(fileName);
	}
	public static void updateFilesInIndex()	{
		
	}
	public static void removeFilesFromIndex() {
		Files selectedItem = MaintenanceWindow.table.getSelectionModel().getSelectedItem();
		long fileId = selectedItem.getFileId();
		MaintenanceWindow.table.getItems().remove(selectedItem);
		model.removeFile(fileId);
	}
}
