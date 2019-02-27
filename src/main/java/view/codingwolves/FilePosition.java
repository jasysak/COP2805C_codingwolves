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
		if (this.fileID == o.fileID)
			// this will return the position offset
			// between the positions of matched word
			// in a set. That is, if the this.wordposition
			// and o.wordposition are in fact different
			// instances of the same word, the return
			// value will be a positive, non-zero int
		    return (this.wordposition - o.wordposition);
		else
			// this will return the fileID offset between
			// two different files. Also a positive, non-zero
			// int for different files in the index.
			return ((int)(this.fileID - o.fileID));
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
