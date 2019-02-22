/**
 * 
 */
package view.codingwolves;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.json.Json;
import javax.json.JsonObject;

import org.apache.commons.collections4.MapUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



//import javax.json.*;

/**
 * @author jasysak
 *
 */
public class ParseFile {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws IOException 
	 */
	/*
	 *  This is the basic inverted index model: Map<Word_In_File, SortedSet<FileID, WordPosition>>
	 *  where <FileID, WordPosition> will be an object of type FilePosition - see FilePosition.class
	 *  So an example for the word "The" may be: 
	 *  
	 *  The; 1, 11; 1, 22; 2, 5; 2, 31; 3, 10 ... etc.
	 *  
	 *  this creates the basis of the inverted index, where each file is parsed into the Map (or removed
	 *  from the Map in the case of file removal).
	 *  
	 */ 

	static Map<String, SortedSet<FilePosition>> mainIndex = new HashMap<String, SortedSet<FilePosition>>();
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		
		// this will need to be updated based on where you have the "filename" file located
		String filepath = "./src/main/resources/"; 	
		// temporarily hard-set filename -- this will be user entry
		String filename = "Test_Sample2.txt";
		//Create MD5 checksum for file
		MessageDigest md5Digest = MessageDigest.getInstance("MD5");
		//Get & print checksum
		String checksum = getFileChecksum(md5Digest, new File(filepath + filename));
		System.out.println(checksum);
		//
		// main file read and mainIndex construction begins here
		//
		Scanner s = new Scanner(new File(filepath + filename));
			
		long fileID = 1; // placeholder fileID until fileID generation is decided
		// position will contain the current word position in the file as it's read
		int position = 0;
		
		// I've left the ArrayList in place, but may not be needed moving forward
		ArrayList<String> list = new ArrayList<String>();
		
		// loop to read file contents
		while (s.hasNext()){	
		// word to lowercase and strip special chars	
			String word = s.next().toLowerCase();
			word = word.replaceAll("[^a-zA-Z0-9]", "");
			// if no length, it wasn't a valid word to begin with
			if (word.length() == 0)
				continue;
			// add to ArrayList
			list.add(word);
			// create a new fileposition object "fp" that contains the fileID (long), word position (int)
			FilePosition fp = new FilePosition(fileID, position);
			// if the mainIndex does not contain "word" then we add it
			if (!mainIndex.containsKey(word)) {
				// having some trouble here...code may need work
				TreeSet<FilePosition> ts = new TreeSet<FilePosition>();
				ts.add(fp);
				mainIndex.put(word, ts);
			}
			// if the mainIndex already contains word, then we add only the fp object
			// at the existingWordPosition
			// this may not be working as expected...
			else {
				Set existingWordPosition = mainIndex.get(word);
				existingWordPosition.add(fp);
			}
			++position;
		} // end while loop
		s.close();
		
		// Print the map using Apache commons collection MapUtils	
		MapUtils.verbosePrint(System.out, "Inverted Index", mainIndex);
		
		// create & print json formatted string from ArrayList(list)
		String json = new Gson().toJson(list);
		System.out.println(json);
		
		// now create the "full" json object and pretty print with gson
		JsonObject value = Json.createObjectBuilder()
				.add("checksum", checksum)
				.add("filename", filename)
				.add("Parsed Data", json)
				.add("path", filepath)
				.build();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json_obj = gson.toJson(value);
		System.out.println(json_obj);
		
	}
	
	/*
	 * Method getFileChecksum taken from 
	 * https://howtodoinjava.com/java/io/how-to-generate-sha-or-md5-file-checksum-hash-in-java/
	 * by L. Gupta
	 */
	private static String getFileChecksum(MessageDigest digest, File file) throws IOException
	{
	    //Get file input stream for reading the file content
	    FileInputStream fis = new FileInputStream(file);
	     
	    //Create byte array to read data in chunks
	    byte[] byteArray = new byte[1024];
	    int bytesCount = 0;
	      
	    //Read file data and update in message digest
	    while ((bytesCount = fis.read(byteArray)) != -1) {
	        digest.update(byteArray, 0, bytesCount);
	    };
	     
	    //close the stream; We don't need it now.
	    fis.close();
	     
	    //Get the hash's bytes
	    byte[] bytes = digest.digest();
	     
	    //This bytes[] has bytes in decimal format;
	    //Convert it to hexadecimal format
	    StringBuilder sb = new StringBuilder();
	    for(int i=0; i< bytes.length ;i++) {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	     
	    //return complete hash
	   return sb.toString();
	}
    		
}
