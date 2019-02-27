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
	
	// Object constructor
	
	public FilePosition(long fileID, int wordposition) {
		this.fileID = fileID;
		this.wordposition = wordposition;
	}
	
	// Override methods
	
	@Override
	public int compareTo(FilePosition o) {
		if ( o == null )
			throw new IllegalArgumentException( "Argument must not be null." );
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

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
 
		// this instance check
		if (this == o) {
			return true;
		}
		//
		// TODO work in progress
		//
		return false;
		
	}
	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + (int)fileID;
		result = 37 * result + wordposition;
		return result;
	}
	
	@Override
	public String toString() {
	    return "fileID = " + this.fileID + " position = " + this.wordposition;
	}
	
	
	
}
