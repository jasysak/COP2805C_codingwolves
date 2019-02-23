/**
 * 
 */
package view.codingwolves;

/**
 * @author jasysak
 * 
 * FilePosition object to use in Map<String, SortedSet<FilePosition>>
 * for the inverted index 
 * 
 * WORK IN PROGRESS
 *
 */
public class FilePosition implements Comparable<FilePosition>{

	final long fileID;
	final int wordposition;
	//final String fileHash;
	
	@Override
	public int compareTo(FilePosition o) {
		// TODO Auto-generated method stub
		// 
		// 
		//
		
		return 0;
	}
	
	// constructor
	
	public FilePosition(long fileID, int wordposition) {
		this.fileID = fileID;
		this.wordposition = wordposition;
	}

	/*
	 * public FilePosition(String fileHash, int position) {
		// TODO Auto-generated constructor stub
		this.fileHash = fileHash;
		this.position = position;
	* }
	*/
	@Override
	public String toString() {
	    return "fileID = " + this.fileID + " position = " + this.wordposition;
	}
}
