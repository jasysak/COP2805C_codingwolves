package controller.codingwolves;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.codingwolves.FileModel;
import model.codingwolves.Files;
import view.codingwolves.MaintenanceWindow;

/**
 * This class is the controller and will implement the classes in the model class as well as
 * add files to the index, remove files from the index, update files in the index, AND search,
 * OR search, PHRASE search, and to save index to file.
 *
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
	//Still a work in progress
	public static void updateFilesInIndex()	{
		Files selectedItem = MaintenanceWindow.table.getSelectionModel().getSelectedItem();
		for (Iterator<Files> iterator = FileModel.files.iterator(); iterator.hasNext();)
		{
			Files currentFile = iterator.next();
			if (currentFile.getFileName() == selectedItem.getFileName() 
					&& currentFile.getCheckSum() == selectedItem.getCheckSum())
			{
				
			}
		}
	}
	public static void removeFilesFromIndex() {
		Files selectedItem = MaintenanceWindow.table.getSelectionModel().getSelectedItem();
		String name = selectedItem.getFileName();
		ImageView icon = new ImageView("/monitor.png");
		icon.setFitWidth(48);
		icon.setFitHeight(48);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete File?");
		alert.setContentText("Delete " + name + " ?");
		alert.getDialogPane().setGraphic(icon);
		alert.showAndWait();
		
		if (alert.getResult() == ButtonType.OK)
		{
			long fileId = selectedItem.getFileId();
			MaintenanceWindow.table.getItems().remove(selectedItem);
			model.removeFile(fileId);
		}
	}
}
