/**
 * 
 */
package view.codingwolves;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

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
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {

		String filepath = "./src/main/resources/"; // this will need to be updated...
		
		// temporarily hard-set filename -- this will be user entry
		String filename = "Test_Sample2.txt";

		//Create checksum for this file
		File file = new File(filepath + filename);
		 
		//Use MD5 algorithm
		MessageDigest md5Digest = MessageDigest.getInstance("MD5");
		 
		//Get the checksum
		String checksum = getFileChecksum(md5Digest, file);
		 
		//see checksum
		System.out.println(checksum);
		
		Scanner s = new Scanner(new File(filepath + filename));
		
		ArrayList<String> list = new ArrayList<String>();
		while (s.hasNext()){
		    list.add(s.next());
		    
		}
		s.close();
		
		// replace all non-alphanumeric values and print list
		for(int i = 0; i < list.size(); i++) {
			String strTemp = list.get(i); 
			list.set(i, strTemp.replaceAll("[^a-zA-Z0-9]", ""));
			System.out.println(list.get(i));
		}
		
		// TODO generate JSON array(s) from filename, checksum, filepath, ArrayList
		//
		// Work in progress...
		//
		
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
	    for(int i=0; i< bytes.length ;i++)
	    {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	     
	    //return complete hash
	   return sb.toString();
	}
    		
}
