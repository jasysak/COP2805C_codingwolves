/**
 * This class will make the Maintenance window of the Search Engine project. This window will allow the user
 * to view the files indexed, add files to the index, delete files from the index, and to update the index 
 * files if any there were any changes to the files.
 */
package view.codingwolves;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author David Alvarez
 *
 */
class MaintenanceWindow extends Application {
	private final String iconPath = "/monitor.png";
	private final Button addFile = new Button();
	private final Button updateIndexFiles = new Button();
	private final Button removeSelectedFiles = new Button();
	static int numOfFilesIndexed = 0;
	final static String VERSION_NUMBER = "1.0";
	
	@Override
    public void start(Stage secondaryStage) 
	{
		BorderPane border = new BorderPane();
		TableView indexTable = addTableView();
		HBox hboxT = addHBoxT();
		HBox hboxBL1 = addHBoxBL1();
		HBox hboxBL2 = addHboxBL2();
		VBox vboxB = new VBox(hboxBL1, hboxBL2);
		border.setCenter(indexTable);
		border.setTop(hboxT);
		border.setBottom(vboxB);
		
		Scene scene = new Scene(border, 800, 600);
		secondaryStage.setTitle("Maintenance");
		secondaryStage.getIcons().add(new Image(iconPath));
		secondaryStage.setScene(scene);
	}
	/**
	 * This will make the table that will hold the files indexed and the status of those files
	 * @return The TableView
	 */
	private TableView addTableView()
	{
		TableView table = new TableView();
		
		table.setEditable(false);
		TableColumn fileNameCol = new TableColumn("File Name");
		fileNameCol.setPrefWidth(400);
		TableColumn statusCol = new TableColumn("Status");
		statusCol.setPrefWidth(400);
		
		table.getColumns().addAll(fileNameCol, statusCol);
		return table;
	}
	/**
	 * This will make the HBox for the top of the Interface
	 * @return The HBox
	 */
	private HBox addHBoxT()
	{
		HBox hbox = new HBox();
		
		Text title = new Text("Search Engine - Index Maitenance");
		title.setFont(Font.font("SansSerif", FontWeight.BOLD, 30));
		
		hbox.getChildren().add(title);
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(10, 0, 10, 0));
		return hbox;
	}
	/**
	 * This will make the first HBox in the bottom interface
	 * @return The HBox
	 */
	private HBox addHBoxBL1()
	{
		HBox hboxL1 = new HBox();
		
		addFile.setText("Add File");
		addFile.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		addFile.setPrefSize(100, 20);
		
		updateIndexFiles.setText("Update Indexed Files");
		updateIndexFiles.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		updateIndexFiles.setPrefSize(150, 20);
		
		removeSelectedFiles.setText("Remove Selected Files");
		removeSelectedFiles.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		removeSelectedFiles.setPrefSize(150, 20);
		
		hboxL1.getChildren().addAll(addFile, updateIndexFiles, removeSelectedFiles);
		hboxL1.setAlignment(Pos.CENTER);
		hboxL1.setSpacing(100);
		hboxL1.setPadding(new Insets(10, 0, 10, 0));
		return hboxL1;
	}
	/**
	 * This will make the contents of the second HBox in the bottom interface
	 * @return The HBox
	 */
	private HBox addHboxBL2()
	{
		HBox hboxL2 = new HBox();
		
		String s1 = "Number of Files Indexed: " + numOfFilesIndexed;
		Label numOfFiles = new Label(s1);
		numOfFiles.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		
		String s2 = "Search Engine version " + VERSION_NUMBER;
		Label seVersion = new Label(s2);
		seVersion.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		
		hboxL2.getChildren().addAll(numOfFiles, seVersion);
		hboxL2.setAlignment(Pos.CENTER);
		hboxL2.setSpacing(350);
		hboxL2.setPadding(new Insets(10, 0, 10, 0));
		return hboxL2;
	}
}
