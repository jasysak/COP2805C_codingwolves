/**
 * @author Jason Sysak
 * @version 1.0.0 
 * 
 * Feb. 28, 2019
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
 * FileListItem.java (name subject to change)
 * 
 * PROTOTYPE Java class defining the FileListItem object to be used
 * in the [FileList] list of files which are indexed
 *
 * WORK IN PROGRESS
 * 
 */
package view.codingwolves;

/**
 * @author jay
 *
 */
public class FileListItem implements Comparable<FileListItem> {

	public final long fileID;
	public final String filename;
	public final long lastModifiedTime;
	public final String md5Hash;
	public final String filepath;
	
	public FileListItem (long fileID, String filename, long lastModifiedTime, String md5Hash, String filepath) {
		this.fileID = fileID;
		this.filename = filename;
		this.lastModifiedTime = lastModifiedTime;
		this.md5Hash = md5Hash;
		this.filepath = filepath;
	}
	
	@Override
	public int hashCode() {
		// TODO stub method
		return 0;
				
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		// TODO stub method
		return true;
	}
	
	@Override
	public String toString() {
		return ("filename = " + this.filename + " path = " + this.filepath);
	}

	@Override
	public int compareTo(FileListItem o) {
		// TODO Auto-generated method stub
		return (int)(this.fileID - o.fileID);
	}
	
	
}
