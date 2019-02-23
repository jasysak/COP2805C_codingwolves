/**
 * 
 */
package controller.codingwolves;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author David
 *
 */
public class FileIndex {
	
	public static void addFileToIndex() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose a file to add to the Index");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("Text Files", "*.txt"),
			new FileChooser.ExtensionFilter("HTML Files", "*.html")
		);
		File file = fileChooser.showOpenDialog(new Stage());
	}

}
