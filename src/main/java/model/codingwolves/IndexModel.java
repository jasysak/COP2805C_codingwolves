package model.codingwolves;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import org.apache.commons.collections4.MapUtils;
import javafx.application.Platform;

/**
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
 * This class contains vruious methods that add, remove, edit, and update
 * the inverted index (II). The II will be stored in memory as a Map and will
 * be saved to disk as JSON formatted text using GSON libraries.
 * 
 */

public class IndexModel {
	
	public static Map<String, SortedSet<FilePosition>> mainIndex = new HashMap<String, SortedSet<FilePosition>>();
//	final static String indexFilename = System.getProperty("user.home") + File.pathSeparator + "invIndex.json";
	final static String indexFilename = "invIndex.json";

	
	public static void addToInvIndex(String filename) throws FileNotFoundException, IOException {
	
		
		Scanner s = new Scanner(new File(filename));
		
		// placeholder fileID
		long fileID = 0;
		String userDir = System.getProperty("user.home");
		File fileIndex = new File(userDir + File.separator + indexFilename);
		if (!fileIndex.exists()) {
			try {
				fileIndex.createNewFile();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
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
	
	public static void saveIndexToStorage () {
		// I left setPrettyPrinting so the JSON output is nicer to
		// read. This can be removed whenever it's no longer needed.
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json_obj = gson.toJson(mainIndex);
		// System.out.println("\nSaved JSON Data to " + indexFilename);
		// System.out.println(json_obj);
			
		try {
			FileWriter writer = new FileWriter(indexFilename);
			writer.write(json_obj);
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}	
	} // end saveIndexToStorage
	
	public static void removeDocumentFromIndex (String filename) {
		// TODO stub method
		// called when document is to be entirely removed from mainIndex
		// Note that it will remain in the saved index JSON file until 
		// program exit or manual Update Index (see below) is selected
		
	} // end removeDocumentFromIndex
	
	public void updateIndex() throws FileNotFoundException, IOException {
		// TODO stub method
		// called when "Update Index" button is pressed from Maintenance window
		mainIndex.clear();	// dump all contents. *** N.B. if calling method has iterator
							// make sure this clear() is not happening after each iteration!
		Iterator<Files> it = FileModel.files.iterator();
		while (it.hasNext()) {
			System.out.println("Placeholder");
			//addToInvIndex(Files.getFileName()); //
		}
	} // end updateIndex
	
//	This is broken right now. Work in progress
  	public static void loadIndexFromStorage () throws IOException {
		// TODO stub method
		// called at startup to access disk storage of index and load it into memory/mainIndex Map
		mainIndex.clear(); 	// cleanup just in case
		
		try (FileReader reader = new FileReader(indexFilename)) {
			Gson gson = new Gson();
			//Type mapType = new TypeToken<Map<String, SortedSet<FilePosition>>>(){}.getType();  
			mainIndex = gson.fromJson(indexFilename, Map.class);
			reader.close();		
		}
		catch (Exception e)
	    {
	      e.printStackTrace();
	      Platform.exit();
	    }
		
		
	} // end loadIndexFromStorage
  	
	// main method added for TEST ONLY (this will be removed after bugs etc. are gone)
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		
		

		// placeholder filename 
		String filename = "./src/main/resources/Test_Sample2.txt";
			
		addToInvIndex(filename);
		saveIndexToStorage();
		loadIndexFromStorage();
		MapUtils.debugPrint(System.out, "DEBUG Print", mainIndex);
		
		
			
	} // end TEST main method
	
} // end class
