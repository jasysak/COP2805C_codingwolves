/**
 * @author Jason Sysak
 * @version 1.0.0 
 * 
 * Feb. 25, 2019
 * 
 * For COP2805C Group Project -- Project #4
 * 
 * codingwolves team
 * Members:
 * David A.
 * Reubin G.
 * Erin H.
 * Jason S.
 *
 */
package view.codingwolves;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.json.Json;
import javax.json.JsonObject;

import org.apache.commons.collections4.MapUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ParseFile {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws IOException 
	 *
	 *
	 *  This is the basic inverted index model: Map<Word_In_File, SortedSet<FileID, WordPosition>>
	 *  where <FileID, WordPosition> will be an object of type FilePosition - see FilePosition.class
	 *  So an example for the word "The" may be: 
	 *  
	 *  The; 1, 11; 1, 22; 2, 5; 2, 31; 3, 10 ... etc.
	 *  
	 *  this creates the basis of the inverted index, where each file is parsed into the Map (or removed
	 *  from the Map in the case of file removal).
	 *  
	 *  WORK IN PROGRESS
	 */ 

	static Map<String, SortedSet<FilePosition>> mainIndex = new HashMap<String, SortedSet<FilePosition>>();
	
	// main method "test driver" -- this classes methods will eventually
	// be called by others and the main method removed.
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
		// try the W. Pollock algo
		checksum = md5Digest(filepath + filename);
		System.out.println(checksum);
		
		// simple call to method 
		// for now it returns the ArrayList list
		// only to build the JSON data below
		ArrayList<String> list = addToIndex(filepath, filename);
		
		// create & print json formatted string from ArrayList(list)
		String json = new Gson().toJson(list);
		//System.out.println(json);
				
		// now create the "full" json object and pretty print with gson
		JsonObject value = Json.createObjectBuilder()
			.add("filename", filename)
			.add("checksum", checksum)
			.add("path", filepath)
			.add("Parsed Data", json)
			.build();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json_obj = gson.toJson(value);
		System.out.println("\nJSON Data: ");
		System.out.println(json_obj);
		
		// write to test file
		try {
			FileWriter writer = new FileWriter("./testfile.json");
			writer.write(json_obj);
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
			
	} // end main
	
	// method addToIndex 
	// main file read and mainIndex construction begins here
	//
	static ArrayList<String> addToIndex(String filepath, String filename) throws FileNotFoundException {
		Scanner s = new Scanner(new File(filepath + filename));
		// placeholder fileID until fileID generation is decided
		long fileID = 1001;
		// position will contain the current word position in the file as it's read
		int position = 0;
		
		// I've left the ArrayList in place, but may not be needed moving forward
		// it is only used for creating the JSON data, and placed here within the
		// addToIndex method since it uses the scanner while loop below to read the
		// file contents into the ArrayList. Note this method must return therefore
		// return the ArrayList to main.
		ArrayList<String> list = new ArrayList<String>();
		
		// loop to read file contents
		while (s.hasNext()){	
		// word to lowercase and strip special chars	
			String word = s.next().toLowerCase();
			word = word.replaceAll("[^a-zA-Z0-9]", "");
			// if no length after replace, it wasn't a valid word to begin with
			if (word.length() == 0)
				continue;
			// add to ArrayList
			list.add(word);
			// create a new fileposition object "fp" that contains the fileID (long), current word position (int)
			FilePosition fp = new FilePosition(fileID, position);
			// if the mainIndex does not contain "word" then we add the word along with a new
			// TreeSet containing a single object fp
			if (!mainIndex.containsKey(word)) {
				// Add new fp if word doesn't exist yet in mainIndex
				SortedSet<FilePosition> newWordPosition = new TreeSet<FilePosition>();
				newWordPosition.add(fp);
				mainIndex.put(word, newWordPosition);
			}
			// if the mainIndex already contains word, then we add the fp object
			// to the existingWordPosition Set (value) corresponding to word (key)
			else {
				SortedSet<FilePosition> existingWordPosition = mainIndex.get(word);
				existingWordPosition.add(fp);
				// some output to verify:
				System.out.print("KEY = " + String.format(("%-15s"), word) + ": ");
				Iterator<FilePosition> it = existingWordPosition.iterator();
			    System.out.print("VALUES = ");
				while(it.hasNext()){
			     		System.out.print(it.next() + ", ");
			     }
			    System.out.println();
			}
			++position;
		} // end while loop
		s.close();
		
		// Print the map using Apache commons collection MapUtils	
		// note that only one fp object is printed from each set
		// for each word in the mainIndex Map (due to suspected bug in the
		// else/"add fp to existing word" code section above).
		// MapUtils.verbosePrint(System.out, "Inverted Index", mainIndex);
		// MapUtils.debugPrint(System.out, "DEBUG Print", mainIndex);
		
		return list; // temporary return of list for JSON building
		
	} // end addToIndex
	
	// A few stub methods we will probably need
	
	static void removeDocumentFromIndex () {
		// TODO stub method
		// called when document is to be entirely removed from mainIndex
		// may also need to remove it from disk storage too! 
	}
	
	static void updateIndex() {
		// TODO stub method
		// called when "Update Index" button is pressed from Maintenance window
	}
	
	static void loadIndexFromStorage () {
		// TODO stub method
		// called at startup to access disk storage of index and load it into memory/mainIndex Map
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
	//
	// testing new MD5 algo.
	// by Wayne Pollock, Tampa FL USA.
	// from http://wpollock.com/AJava/FileKitSrc.htm
	//
	   public static String md5Digest ( String file ) throws IOException
	   {
	      byte [] buffer = new byte[1024];
	      MessageDigest md = null;
	      FileInputStream fis = new FileInputStream(file);

	      try
	      {   md = MessageDigest.getInstance( "MD5" );
	      } catch ( NoSuchAlgorithmException nsae )
	      {   System.out.println( nsae ); System.exit( 1 );  }

	      for ( ; ; )  // Do forever
	      {  int numOfBytesRead = fis.read( buffer );
	         if ( numOfBytesRead < 1 )
	            break;
	         md.update( buffer, 0, numOfBytesRead );
	      }
	      fis.close();
	      return hexString( md.digest() );
	   }

	   private static String hexString ( byte [] bytes )
	   {
	      // Converts an array of bytes to a String of hex digits,
	      // with a space between groups of four digits.
	      // Note that the MD5 digest is always 128 bits = 16 bytes,
	      // so there will be 5 * 16 / 2 = 40 (32 hex digits plus
	      // 7 spaces = 39 characters, which is close enough).

		  final char [] hexDigit =
			      { '0', '1', '2', '3', '4', '5', '6', '7',
			        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		   
	      StringBuffer s = new StringBuffer( 5 * bytes.length / 2 );

	      for ( int i = 0; i < bytes.length; ++i )
	      {  int d1 = ( bytes[i] & 0x0000000F );
	         int d2 = ( bytes[i] & 0x000000F0 ) >>> 4;

	         // Output a space seperator after every 4th hex digit:
	         //if ( i % 2 == 0 && i != 0 )
	         //   s.append( ' ' );

	         s.append( hexDigit[d2] );
	         s.append( hexDigit[d1] );
	      }

	      return s.toString();
	   }

    		
}
