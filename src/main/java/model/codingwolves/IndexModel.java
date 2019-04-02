package model.codingwolves;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.apache.commons.collections4.MapUtils;

/**
 * @author Jason Sysak
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
 * This class contains various methods that add, remove, edit, and update
 * the inverted index (II). The II will be stored in memory as a Map and will
 * be saved to disk as JSON formatted text using GSON libraries.
 * 
 */

public class IndexModel {
	
	public static Map<String, SortedSet<FilePosition>> mainIndex = new HashMap<String, SortedSet<FilePosition>>();
	static final String indexFilename = System.getProperty("user.home") + File.separator + "invIndex.json";
//	static FileModel model = new FileModel();
	
//	final static String indexFilename = "invIndex.json";

	// placeholder fileID
	// static long fileID;
	
	public static void addToInvIndex(String filename, long fileID) throws FileNotFoundException, IOException {
	
		Scanner s = new Scanner(new File(filename));
		
		// position will contain the current word position in the file as it's read
		int position = 0;
	
		while (s.hasNext()) {	
			String word = s.next().toLowerCase();
			word = word.replaceAll("[^a-zA-Z0-9-]+", "");
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
		MapUtils.debugPrint(System.out, "addToInvIndex DEBUG Print", mainIndex);
		
		// temporary save here until save on exit is implemented
		saveIndexToStorage();
		
		return;
		
	} // end addToInvIndex
	
	public static void saveIndexToStorage () throws IOException {
		// I left setPrettyPrinting so the JSON output is nicer to
		// read. This can be removed whenever it's no longer needed.
		
		File file = new File(indexFilename);
		if (!file.exists()) {
			file.createNewFile();
			}
		
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
	
	public static void removeFromInvIndex (long fileID) {
		//
		// NOTE work in progress
		// This code currently throws ClassCastException
		// the logic may also be flawed
		//
		Set<String> keySet = new TreeSet<String>(mainIndex.keySet());
		for (String  word : keySet) {
			SortedSet<FilePosition> valueSet = mainIndex.get(word);
			Iterator<FilePosition> it = valueSet.iterator();
			FilePosition fp = (FilePosition)it.next();
			while (fp != null) {	
				if (fp.fileID != fileID) continue;
				it.remove();
			}
			// if valueSet is empty, also remove the word
			if (valueSet.size() == 0) {
				mainIndex.remove(word);
			}
		}
	
	} // end removeFromInvIndex
	
	public static void updateIndex() throws FileNotFoundException, IOException {
		// As written, this is a "hard" full reset of the II.
		// Another, perhaps better way, would be to remove+(re)add only those
		// files flagged as needing an update.
		// called when "Update Index" button is pressed from Maintenance window
		mainIndex.clear();	
		// code below not entirely working yet.
		Iterator<Files> it = FileModel.files.iterator();
		while (it.hasNext()) {
			System.out.println("Updating File: " + ((Files) FileModel.files).getFileName() + "\n");
			addToInvIndex(((Files) FileModel.files).getFileName(), ((Files) FileModel.files).getFileId());
		}
		System.out.println("Reset Completed. Results:\n");
		MapUtils.debugPrint(System.out, "updateIndex DEBUG Print", mainIndex);
	} // end updateIndex
	
	public static void loadIndexFromStorage () throws IOException {
		
		// called at startup to access disk storage of index and load it into memory/mainIndex Map
		mainIndex.clear(); 	// cleanup just in case
		File indexFile = new File(indexFilename);
		InputStream is = new FileInputStream(indexFile);
		JsonReader jsonString = new JsonReader(new InputStreamReader(is, "UTF-8"));
		Gson gson = new Gson();
		Type mapType = new TypeToken<Map<String, Set<FilePosition>>>(){}.getType();
		mainIndex = gson.fromJson(jsonString, mapType);
		is.close();		
		MapUtils.debugPrint(System.out, "loadIndexFromStorage DEBUG Print", mainIndex);
		return;	
		
	} // end loadIndexFromStorage
	
	// main method added for TEST ONLY (this will be removed after bugs etc. are gone)
/*
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		
		// placeholder filename 
		String filename = "C:\\Users\\Jason\\Documents\\eclipse-workspace\\Search-Engine.git\\src\\main\\resources\\Test_Sample2.txt";
		fileID = 0;
		// TEST addToInvIndex
		addToInvIndex(filename, fileID);
		
		// TEST saveIndexToStorage
		saveIndexToStorage();
		
		// clear() is for testing of load
		// mainIndex.clear();
		// TEST loadIndexFromStorage
		mainIndex = loadIndexFromStorage();
		MapUtils.debugPrint(System.out, "LOADED DEBUG Print", mainIndex);
		// TEST updateIndex
		// updateIndex();
		// MapUtils.debugPrint(System.out, "DEBUG Print", mainIndex);
		
		// TEST removeFromInvIndex
		// removeFromInvIndex(fileID);
		// MapUtils.debugPrint(System.out, "DEBUG Print", mainIndex);
			
	} // end TEST main method
*/
} // end class
