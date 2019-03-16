package model.codingwolves;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import view.codingwolves.Main;
import view.codingwolves.MaintenanceWindow;

/**
 * This class will be a model class with methods that will addFiles, removeFiles, and updateCheckSum
 * 
 * @author David Alvarez, 2/23/19
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

public class FileModel {
	static String fileStatus;
	//Using an observable list to be able to monitor when elements change
	public static final ObservableList<Files> files = FXCollections.observableArrayList();
	
	/**
	 * This method will add a file to the list files with a unique file id,
	 * the path, when it was lastModified, and the status of the file.
	 * 
	 * @param fileName The file to be added to the list
	 */
	public void addFile(String fileName) {
		long fileId = Main.nextFileID;
		Main.nextFileID += 1L;
		File file = new File(fileName);
		long fileLastModified = file.lastModified();
		try 
		{
			MessageDigest md5Digest = MessageDigest.getInstance("MD5");
			String checkSum = getFileChecksum(md5Digest, file);
			fileStatus = "Indexed"; //This is just to test
			files.add(new Files(fileId, fileName, fileLastModified, checkSum, fileStatus));
		}
		catch(NoSuchAlgorithmException | IOException e) 
		{
			e.printStackTrace();
		}
		
		MaintenanceWindow.table.setItems(files);
		
		int numFiles = files.size();
		String str = Integer.toString(numFiles);
		Main.numOfFilesIndexed.setText(str);
		MaintenanceWindow.numOfFilesIndexed.setText(str);
	}
	/**
	 * This method will remove the specified file from the data list and
	 * update the columns in the maintenance view.
	 * 
	 * @param fileId The identifier for the file to be removed
	 */
	public void removeFile(long fileId) {
		//Loop will iterate through list and remove elements with the current fileId
		for (Iterator<Files> iterator = files.iterator(); iterator.hasNext();)
		{
			Files currentFile = iterator.next();
			if (currentFile.getFileId() == fileId)
			{
					iterator.remove();
			}
		}
		
		int numFiles = files.size();
		String str = Integer.toString(numFiles);
		Main.numOfFilesIndexed.setText(str);
		MaintenanceWindow.numOfFilesIndexed.setText(str);
	}
	
	/**
	 * This method will accept a fileId and check if it matches with a file in the list
	 * if it does it will then make a new checksum for that file.
	 * 
	 * @param fileId The file identifier
	 */
	public void updateFileCheckSum(long fileId) {
		for (Iterator<Files> iterat = files.iterator(); iterat.hasNext();)
		{
			Files currentFile = iterat.next();
			if (currentFile.getFileId() == fileId) {
				try 
				{
					MessageDigest md5Digest = MessageDigest.getInstance("MD5");
					File file = new File(currentFile.getFileName());
					String checkSum = getFileChecksum(md5Digest, file);
					currentFile.setCheckSum(checkSum);
				}
				catch(NoSuchAlgorithmException | IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * This method will accept a fileName and check if the file already exists in the
	 * list.
	 * 
	 * @param fileName The file to be checked
	 * @return Either true or false
	 */
	public boolean fileExists(String fileName) {
		for(Iterator<Files> it = files.iterator(); it.hasNext();)
		{
			Files currentFile = it.next();
			if (currentFile.getFileName() == (fileName))
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * This method will save the list of files to Json formatted file
	 * 
	 */
	public void saveIndexToFile() {
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
			try (Writer writer = new FileWriter(fileIndex)) {
			    Gson gson = new GsonBuilder().setPrettyPrinting().create();
			    JsonObject obj = new JsonObject();
			    obj.addProperty("CurrentFileId", Main.nextFileID);
			    String json1 = gson.toJson(obj);
			    String json2 = gson.toJson(files);
			    writer.write(json1);
			    writer.write(json2);
			    writer.flush();
			    writer.close();
			}
			catch (Exception e)
		    {
		      e.printStackTrace();
		      Platform.exit();
		    }
	}
	/*
	 * Method getFileChecksum taken from 
	 * https://howtodoinjava.com/java/io/how-to-generate-sha-or-md5-file-checksum-hash-in-java/
	 * by L. Gupta
	 */
	public String getFileChecksum(MessageDigest digest, File file) throws IOException
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
