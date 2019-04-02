package controller.codingwolves;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.codingwolves.FileModel;
import model.codingwolves.FilePosition;
import model.codingwolves.Files;
import view.codingwolves.Main;
import view.codingwolves.MaintenanceWindow;
// JAS added for updateIndex() and removeFromInvIndex()
import model.codingwolves.IndexModel;

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
 * David Alvarez
 * Reubin George
 * Erin Hochstetler
 * Jason Sysak
 *
 * WORK IN PROGRESS
 * 
 */

public class FileIndex {
	static FileModel model = new FileModel();
	static String fileName;
	
	/**
	 * This method will search through all the current list of files indexed and search if both words are in the files
	 * and show which files contain those words.
	 * 
	 * @param searchField The phrase to be searched for
	 * @param searchResult The Text to be set in the UI
	 */
	public static void andSearch(String searchField, Text searchResult) {
		
		//
		// WORK IN PROGRESS.
		// 
		Set<FilePosition> filesContainingWords = new HashSet<FilePosition>();
		StringBuilder resultBuilder = new StringBuilder("Files that Contain the Words: " + searchField + ": \n");
		// making a Set of all fileID contained in Files:
		// then we can make this simpler by using the
		// collections retainAll method
		// Note -- there may be other/better ways to make this Set...
		Set<Long> fileIDSet = new HashSet<Long>();
		for (Files currentFile : FileModel.files) {
			fileIDSet.add(currentFile.getFileId());
		}
		// process user input to String[] array
		String[] words = searchField.toLowerCase().split("[^a-zA-Z0-9-]+");
		int x = words.length;
		int y = 0;
		while (y < x) {
			// Load a TreeSet with all Sets from mainIndex matching words[y]
			try {
			filesContainingWords = (Set<FilePosition>) IndexModel.mainIndex.get(words[y].toLowerCase());
			}
			catch (NullPointerException e) {
				resultBuilder.append("No files Match the specified words.");
				searchResult.setText(resultBuilder.toString());
				return;
			}
			if (filesContainingWords == null) {
				break;
			}
			// Another Set to contain just the fileID:
			// fileIDContainingWord is the set of fileID's that contain each word in words[]
			// this will be used for retainAll on the original fileIDSet of all fileIDs
			Set<Long> fileIDContainingWord = new HashSet<Long>();
			for (FilePosition filepos : filesContainingWords) {
				fileIDContainingWord.add(filepos.fileID);
			}
			// retain in fileIDSet only those fileID contained in fileIDContainingWord
			fileIDSet.retainAll(fileIDContainingWord);
			++y;
		}
		
		// Now output results (setText)
		
		if (fileIDSet.size() > 0) {
			for (Long fileID : fileIDSet) {
				for (Files currFile : FileModel.files) {
					if (currFile.getFileId() == fileID) {
						String result = currFile.getFileName();
						resultBuilder.append(result + "\n");
					}
				}
			}
		}
		
		else {
			resultBuilder.append("No matches found.");
		}	
		searchResult.setText(resultBuilder.toString());
		
	}
	/**
	 * This method will search through all the current list of files indexed and search for either of the two words in the files
	 * and show which files contain either or.
	 * 
	 */
	public static void orSearch(String searchField) {
		
		
	}
	/**
	 * This method will search through all the current list of files indexed and search for the exact phrase or sentence in the files
	 * and show which files contain it.
	 * 
	 * @param searchField The phrase to be searched for
	 * @param searchResult The Text to be set in the UI
	 */
	public static void phraseSearch(String searchField, Text searchResult) {
		StringBuilder resultBuilder = new StringBuilder("Files that Contain the Phrase:\n");
		Set<FilePosition> filesContainingPhrase = new TreeSet();
		
		String[] wordsToSearch = searchField.toLowerCase().split("[^a-z0-9-]+");
		//This exception was thrown when the user entered a word that wasn't in the files
		//so this try catch was made.
		try {
			filesContainingPhrase.addAll((Collection)IndexModel.mainIndex.get(wordsToSearch[0]));
		}
		catch (NullPointerException e) {
			resultBuilder.append("No files Match the specified phrase");
			searchResult.setText(resultBuilder.toString());
			return;
		}
		//Copying array elements so that I can start at the second element in the array
		wordsToSearch = Arrays.copyOfRange(wordsToSearch, 1, wordsToSearch.length);
		
		for (int i = 0; i < wordsToSearch.length ; i++)
		{
			String currentWord = wordsToSearch[i];
			if (filesContainingPhrase.size() == 0) {
				break;
			}
			Set<FilePosition> currentPositions = (Set)IndexModel.mainIndex.get(currentWord);
			if (currentPositions == null) {
				filesContainingPhrase.clear();
				break;
			}
			Set<FilePosition> phrase = new TreeSet();
			for (FilePosition currentWordInPhrase : filesContainingPhrase) {
				FilePosition nextWordInPhrase = new FilePosition(currentWordInPhrase.fileID, 
						currentWordInPhrase.wordposition + 1);
				if (currentPositions.contains(nextWordInPhrase)) {
					phrase.add(nextWordInPhrase);
				}
			}
			filesContainingPhrase = phrase;
		}
		if(filesContainingPhrase.size() > 0) {
			//Looping over the remaining files in the set for the file id so I know which files
			//contain the phrase
			for (FilePosition fp : filesContainingPhrase) {
				long fileId = fp.fileID;
				for (Files currentFile : FileModel.files)
				{
					if (currentFile.getFileId() == fileId)
					{
						String result = currentFile.getFileName();
						resultBuilder.append(result + "\n");
					}
				}
			}
		}
		else
		{
			resultBuilder.append("No files Match the specified phrase");
		}
		searchResult.setText(resultBuilder.toString());
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
		// JAS Note -- IndexModel.addTOInvIndex() gets called in
		// FileModel.addFile(). Is not needed here.
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
						//Just to test
						System.out.println("CheckSum Updated");
						// JAS added
						IndexModel.removeFromInvIndex(fileId);
						IndexModel.addToInvIndex(currentFile.getFileName(), fileId);

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
			// JAS Note
			// IndexModel.removeFromInvIndex() gets called in 
			// FileModel.removeFile()
		}
		model.saveIndexToFile();
	}
	/**
	 * This method will initialize the index with the Json file 
	 */
	public static void initializeIndex() {
		String userDir = System.getProperty("user.home");
		File fileIndex = new File(userDir + File.separator + "SearchEngine.json");
		if (!fileIndex.exists()) {
			try {
				fileIndex.createNewFile();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		try 
		{
			JsonReader jsonReader = new JsonReader(new FileReader(fileIndex));
			Gson gson = new Gson();
			if (fileIndex.length() != 0L) {
				JsonObject js = gson.fromJson(jsonReader, JsonObject.class);
				Files[] indexFiles = gson.fromJson(jsonReader, Files[].class);
				jsonReader.close();
				long currentFileId = js.get("CurrentFileId").getAsLong();
				//Read the currentFileId from the file and initialize it to nextFileID so that a file id won't be reused
				Main.nextFileID = currentFileId;
				FileModel.files.addAll(indexFiles);
			}
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
