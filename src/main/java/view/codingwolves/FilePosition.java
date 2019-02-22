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
	final int position;
	//final String FileHash;
	
	@Override
	public int compareTo(FilePosition o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	// constructor
	
	public FilePosition(long fileID, int position) {
		this.fileID = fileID;
		this.position = position;
	}

	/*
	 * public FilePosition(String FileHash, int position) {
		// TODO Auto-generated constructor stub
		this.FileHash = FileHash;
		this.position = position;
	* }
	*/
	

}
