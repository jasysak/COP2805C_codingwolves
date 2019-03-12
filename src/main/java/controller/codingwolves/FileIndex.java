package controller.codingwolves;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
 * also contains methods pertaining to the inverted index: build,
 * update, edit, file removal, etc.
 * 
 * @author David Alvarez, 2/24/19
 * @author Jason Sysak
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
	static Map<String, SortedSet<FilePosition>> mainIndex = new HashMap<String, SortedSet<FilePosition>>();
	
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
		addToIndex(fileName);
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
	
	static void addToIndex(String filename) throws FileNotFoundException, IOException {
		
		Scanner s = new Scanner(new File(filename));
		
		// placeholder fileID
		long fileID = 0;
		
		// position will contain the current word position in the file as it's read
		int position = 0;
	
		while (s.hasNext()){	
			String word = s.next().toLowerCase();
			word = word.replaceAll("[^a-zA-Z0-9]", "");
			if (word.length() == 0)
				continue;
			FilePosition fp = new FilePosition(fileID, position);
			if (!mainIndex.containsKey(word)) {
				SortedSet<FilePosition> newWordPosition = new TreeSet<FilePosition>();
				newWordPosition.add(fp);
				mainIndex.put(word, newWordPosition);
			}

			else {
				SortedSet<FilePosition> existingWordPosition = mainIndex.get(word);
				existingWordPosition.add(fp);
				// some output to verify:
				//System.out.print("KEY = " + String.format(("%-15s"), word) + ": ");
				//Iterator<FilePosition> it = existingWordPosition.iterator();
			    //System.out.print("VALUES = ");
				//while(it.hasNext()){
			    // 		System.out.print(it.next() + ", ");
			    // }
			    //System.out.println();
			}
			++position;
		} // end while loop
		s.close();
		
		// Print map using Apache commons collection MapUtils
		// MapUtils.verbosePrint(System.out, "Inverted Index", mainIndex);
		// MapUtils.debugPrint(System.out, "DEBUG Print", mainIndex);
		
		return;
		
	} // end addToIndex
	
	static void saveIndexToStorage (String iFilename) {
		// I left setPrettyPrinting so the JSON output is nicer to
		// read. This can be removed whenever it's no longer needed.
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json_obj = gson.toJson(mainIndex);
		System.out.println("\nSaved JSON Data to " + iFilename + ":");
		System.out.println(json_obj);
			
		try {
			FileWriter writer = new FileWriter(iFilename);
			writer.write(json_obj);
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}	
	}
	static void removeDocumentFromIndex () {
		// TODO stub method
		// called when document is to be entirely removed from mainIndex
		// Note that it will remain in the saved index JSON file until 
		// program exit or manual Update Index (see below) is selected
	}
	
	static void updateIndex() {
		// TODO stub method
		// called when "Update Index" button is pressed from Maintenance window
	}
	
	static void loadIndexFromStorage () {
		// TODO stub method
		// called at startup to access disk storage of index and load it into memory/mainIndex Map
	}
	
	
	// main method added for TEST ONLY (this will be removed after bugs etc. are gone)
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		
		// placeholder filename 
		String filename = "./src/main/resources/Test_Sample2.txt";
		String indexFilename = "./src/main/resources/testfile.json";
		
		addToIndex(filename);
		saveIndexToStorage(indexFilename);
		
	} // end TEST main method
}
