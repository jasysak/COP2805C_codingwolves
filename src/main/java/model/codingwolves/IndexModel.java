package model.codingwolves;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

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
	static long fileID;
	
	public static void addToInvIndex(String filename, long fileID) throws FileNotFoundException, IOException {
	
		Scanner s = new Scanner(new File(filename));
		
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
		MapUtils.debugPrint(System.out, "DEBUG Print", mainIndex);
		
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

		HashSet<String> keySet = new HashSet<String>(mainIndex.keySet());
		for (String  word : keySet) {
			SortedSet<FilePosition> valueSet = mainIndex.get(word);
			Iterator<FilePosition> it = valueSet.iterator();
			while (it.hasNext()) {
				FilePosition fp = (FilePosition)it.next();
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
		// code below not working yet.
		Iterator<Files> it = FileModel.files.iterator();
		while (it.hasNext()) {
			System.out.println("Placeholder");
			addToInvIndex(((Files) FileModel.files).getFileName(), ((Files) FileModel.files).getFileId());
		}
		System.out.println("Reset Completed");
		MapUtils.debugPrint(System.out, "DEBUG Print", mainIndex);
	} // end updateIndex
	
	
	// loadIndexFromStorage is broken right now. Work in progress
	// we may need to parse the file -- see my third comment below
	
	public static void loadIndexFromStorage () throws IOException {
		// TODO stub method
		// called at startup to access disk storage of index and load it into memory/mainIndex Map
		mainIndex.clear(); 	// cleanup just in case
		// String jsonTxt = null;
		
		try (InputStream is = new FileInputStream(indexFilename))		
		// (FileReader reader = new FileReader(indexFilename)) 
		{
			// jsonTxt = IOUtils.toString(is, "UTF-8");
			// Gson gson = new Gson();
			
			// First Try - doesn't work because FilePosition is not a generic type (I think)
			//Type mapType = new TypeToken<Map<String, SortedSet<FilePosition>>>(){}.getType();  
			//mainIndex = gson.fromJson(indexFilename, mapType);
			
			// Next Try - doesn't seem to work. Still working on it...
			// Map<String, SortedSet<FilePosition>> mainIndex = new HashMap<String, SortedSet<FilePosition>>();
		    // mainIndex = (Map<String, SortedSet<FilePosition>>)gson.fromJson(jsonTxt, mainIndex.getClass());
		    
		    // Next option is to write a low-level parser to individually assign elements of the JSON
		    // file to their corresponding Map elements, i.e. <String, Set<FilePosition>>
			
			// Another try with Jackson ObjectMapper
			ObjectMapper mapper = new ObjectMapper();
			HashMap<String, SortedSet<FilePosition>> mainIndex = mapper.readValue(is, HashMap.class);
			 		
			is.close();	
			
			
				
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
		String filename = "C:\\Users\\Jason\\Documents\\eclipse-workspace\\Search-Engine.git\\src\\main\\resources\\Test_Sample2.txt";
		fileID = 0;
		addToInvIndex(filename, fileID);
		
//		saveIndexToStorage();
		
		// clear() is for testing of load
//		mainIndex.clear();
//		loadIndexFromStorage();
//		updateIndex();
//		MapUtils.debugPrint(System.out, "DEBUG Print", mainIndex);
		removeFromInvIndex(fileID);
		MapUtils.debugPrint(System.out, "DEBUG Print", mainIndex);
		
			
	} // end TEST main method
	
} // end class
