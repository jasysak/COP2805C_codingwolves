/**
 * 
 */
package model.codingwolves;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import view.codingwolves.Main;
import view.codingwolves.MaintenanceWindow;

/**
 * @author David Alvarez, 2/23/19
 *
 */
public class FileModel {
	private String fileName;
	static List<String> listOfFiles = new ArrayList();
	
	public void addFile(String fileName) {
		File file = new File(fileName);
		long fileLastModified = file.lastModified();
		listOfFiles.add(fileName);
		
		int numFiles = listOfFiles.size();
		String str = Integer.toString(numFiles);
		Main.numOfFilesIndexed.setText(str);
		MaintenanceWindow.numOfFilesIndexed.setText(str);
	}
	public void removeFile() {
		
	}
	public void updateFileLastModified() {
		
	}
}
