/**
 * 
 */
package controller.codingwolves;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
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
	public static void addFileToIndex() throws NoSuchAlgorithmException, IOException {
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
		if (model.fileExists(fileName)) {
			return;
		}
		model.addFile(fileName);
	}
	public static void updateFilesInIndex()	{
		for (Iterator<Files> iterator = FileModel.files.iterator(); iterator.hasNext();)
		{
			Files currentFile = iterator.next();
			//String 
		}
	}
	public static void removeFilesFromIndex() {
		Files selectedItem = MaintenanceWindow.table.getSelectionModel().getSelectedItem();
		long fileId = selectedItem.getFileId();
		MaintenanceWindow.table.getItems().remove(selectedItem);
		model.removeFile(fileId);
	}
}
