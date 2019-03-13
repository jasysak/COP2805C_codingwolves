/**
 * 
 */
package model.codingwolves;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.collections4.MapUtils;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 * @author jay
 *
 */
public class IndexModel {
	
	public static Map<String, SortedSet<FilePosition>> mainIndex = new HashMap<String, SortedSet<FilePosition>>();

	public static void addToIndex(String filename) throws FileNotFoundException, IOException {
		
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
	
	public static void saveIndexToStorage (String iFilename) {
		// I left setPrettyPrinting so the JSON output is nicer to
		// read. This can be removed whenever it's no longer needed.
		Gson gson = new GsonBuilder().create();
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
	} // end saveIndexToStorage
	
	public static void removeDocumentFromIndex (String filename) {
		// TODO stub method
		// called when document is to be entirely removed from mainIndex
		// Note that it will remain in the saved index JSON file until 
		// program exit or manual Update Index (see below) is selected
		
	} // end removeDocumentFromIndex
	
	public void updateIndex() {
		// TODO stub method
		// called when "Update Index" button is pressed from Maintenance window
		mainIndex.clear();	// dump all contents. *** N.B. if calling method has iterator
							// make sure this clear() is not happening after each iteration!
		
		
	} // end updateIndex
	
/**	This is broken right now. Work in progress
 * 	public static void loadIndexFromStorage (String iFilename) {
		// TODO stub method
		// called at startup to access disk storage of index and load it into memory/mainIndex Map
		mainIndex.clear(); 	// cleanup just in case
		try (BufferedReader br = new BufferedReader(new FileReader(iFilename))) {
			Gson gson = new Gson();
			Type mapType = new TypeToken<Map<String, SortedSet<FilePosition>>>(){}.getType();  
			Map<String, SortedSet<FilePosition>> mainIndex = gson.fromJson(iFilename, mapType);
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
	} // end loadIndexFromStorage
*/	
	// main method added for TEST ONLY (this will be removed after bugs etc. are gone)
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
			
		// placeholder filename 
		String filename = "./src/main/resources/Test_Sample2.txt";
		String indexFilename = "./src/main/resources/invIndex.json";
			
		addToIndex(filename);
		saveIndexToStorage(indexFilename);
		// loadIndexFromStorage(indexFilename);
		MapUtils.debugPrint(System.out, "DEBUG Print", mainIndex);
		
		
			
	} // end TEST main method
	
} // end class
