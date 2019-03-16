package controller.codingwolves;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.codingwolves.FileModel;
import model.codingwolves.Files;
import view.codingwolves.Main;
import view.codingwolves.MaintenanceWindow;

/**
 * This class is the controller and will implement the classes in the model class as well as
 * add files to the index, remove files from the index, update files in the index, AND search,
 * OR search, PHRASE search, and to save index to file.
 * 
 * @author David Alvarez, 2/24/19
 * 
 * For COP2805C Group Project
 * 
 * codingwolves team
 * Members:
 * David A.
 * Reubin G.
 * Erin H.
 * Jason S.
 *
 * WORK IN PROGRESS
 * 
 */

public class FileIndex {
	static FileModel model = new FileModel();
	static String fileName;
	
	/**
	 * This method will search through all the current list of files indexed and search for the two words in the files
	 * and show which files contain those words.
	 * 
	 */
	public static void andSearch() {
		
	}
	/**
	 * 
	 */
	public static void orSearch() {
		
	}
	/**
	 * 
	 */
	public static void phraseSearch() {
		
	}
	/**
	 * This method will allow the user to choose a file using their operating systems file selector
	 * and will then add the file selected to the observable list of files.
	 * 
	 */
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
		if (model.fileExists(fileName)) {
			return;
		}
		model.addFile(fileName);
		model.saveIndexToFile();
	}
	/**
	 * This method will update the current selected file from the GUI and compare the current checksum
	 * to the checksum of the file in memory and if different update the current checksum.
	 * 
	 */
	public static void updateFilesInIndex()	{
		Files selectedItem = MaintenanceWindow.table.getSelectionModel().getSelectedItem();
		if (selectedItem == null) {
			return;
		}
		for (Iterator<Files> iterator = FileModel.files.iterator(); iterator.hasNext();)
		{
			Files currentFile = iterator.next();
			File file = new File(currentFile.getFileName());
			try
			{
				MessageDigest md5Digest = MessageDigest.getInstance("MD5");
				String fileCheckSum = model.getFileChecksum(md5Digest, file);
				if (selectedItem.getFileId() == currentFile.getFileId())
				{
					if (!selectedItem.getCheckSum().equals(fileCheckSum) 
						&& currentFile.getFileName() == selectedItem.getFileName())
					{
						long fileId = currentFile.getFileId();
						model.updateFileCheckSum(fileId);
					}
					else if (selectedItem.getCheckSum().equals(fileCheckSum) 
							&& currentFile.getFileName() == selectedItem.getFileName())
					{
						ImageView icon = new ImageView("/monitor.png");
						icon.setFitWidth(48);
						icon.setFitHeight(48);
						Alert noChange = new Alert(AlertType.INFORMATION);
						noChange.setTitle("No Change Detected");
						noChange.setContentText("No Change Detected.");
						noChange.getDialogPane().setGraphic(icon);
						noChange.showAndWait();
						return;
					}
				}
			}
			catch(NoSuchAlgorithmException | IOException e) 
			{
				e.printStackTrace();
				Platform.exit();
			}
		}
		model.saveIndexToFile();
	}
	/**
	 * This method will get the current selected file and remove the file from the tableview and the observable list of
	 * files then update the Json file.
	 * 
	 */
	public static void removeFilesFromIndex() {
		Files selectedItem = MaintenanceWindow.table.getSelectionModel().getSelectedItem();
		if (selectedItem == null) {
			return;
		}
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
		model.saveIndexToFile();
	}
	/**
	 * This method will initialize the index with the Json file 
	 */
	public static void initializeIndex() {
		String userDir = System.getProperty("user.home");
		File fileIndex = new File(userDir + File.separator + "SearchEngine.json");
		try 
		{
			JsonReader jsonReader = new JsonReader(new FileReader(fileIndex));
			Gson gson = new Gson();
			JsonObject js = gson.fromJson(jsonReader, JsonObject.class);
			Files[] indexFiles = gson.fromJson(jsonReader, Files[].class);
			jsonReader.close();
			long currentFileId = js.get("CurrentFileId").getAsLong();
			//Read the currentFileId from the file and initialize it to nextFileID so that a file id won't be reused
			Main.nextFileID = currentFileId;
			FileModel.files.addAll(indexFiles);
			
			//Add full path name to the fileName column
			MaintenanceWindow.fileNameCol.setCellValueFactory(new PropertyValueFactory<Files, String>("fileName"));
			//Add the status of the file to the status column
			MaintenanceWindow.statusCol.setCellValueFactory(new PropertyValueFactory<Files, String>("fileStatus"));
			MaintenanceWindow.table.setItems(FileModel.files);
		} 
		catch (JsonIOException | JsonSyntaxException | IOException e) 
		{
			e.printStackTrace();
		}
	}
}
