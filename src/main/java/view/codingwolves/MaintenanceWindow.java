/**
 * 
 */
package view.codingwolves;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author David Alvarez
 *
 */
class MaintenanceWindow extends Application {
	private final String iconPath = "/monitor.png";
	
	@Override
    public void start(Stage secondaryStage) 
	{
		BorderPane border = new BorderPane();
		
		Scene scene = new Scene(border, 800, 600);
		secondaryStage.setTitle("Maintenance");
		secondaryStage.getIcons().add(new Image(iconPath));
		secondaryStage.setScene(scene);
	}
}
