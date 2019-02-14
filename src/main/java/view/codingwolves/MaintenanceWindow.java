/**
 * This class will make the Maintenance window of the Search Engine project. This window will allow the user
 * to view the files indexed, add files to the index, delete files from the index, and to update the index 
 * files if any there were any changes to the files.
 */
package view.codingwolves;

import javax.naming.OperationNotSupportedException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
 * @author David Alvarez, 2/12/19
 *
 */
class MaintenanceWindow extends Application {
	private final String iconPath = "/monitor.png";
	private final Button addFileBtn = new Button();
	private final Button updateIndexedFilesBtn = new Button();
	private final Button removeSelectedFilesBtn = new Button();
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
		
		addFileBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
				try {
					addFile();
				} catch (OperationNotSupportedException e1) {
					System.out.println(e1);
				}
			}
		});
		updateIndexedFilesBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
				try {
					updateIndexedFiles();
				} catch (OperationNotSupportedException e1) {
					System.out.println(e1);
				}
			}
		});
		removeSelectedFilesBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
				try {
					removeSelectedFiles();
				} catch (OperationNotSupportedException e1) {
					System.out.println(e1);
				}
			}
		});
	}
	/**
	 * This will make the table that will hold the files indexed and the status of those files
	 * @return The TableView for the center of the interface
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
	 * @return The HBox for the top of the interface
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
	 * @return The first layer HBox in the bottom of the interface
	 */
	private HBox addHBoxBL1()
	{
		HBox hboxL1 = new HBox();
		
		addFileBtn.setText("Add File");
		addFileBtn.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		addFileBtn.setPrefSize(100, 20);
		
		updateIndexedFilesBtn.setText("Update Indexed Files");
		updateIndexedFilesBtn.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		updateIndexedFilesBtn.setPrefSize(150, 20);
		
		removeSelectedFilesBtn.setText("Remove Selected Files");
		removeSelectedFilesBtn.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		removeSelectedFilesBtn.setPrefSize(150, 20);
		
		hboxL1.getChildren().addAll(addFileBtn, updateIndexedFilesBtn, removeSelectedFilesBtn);
		hboxL1.setAlignment(Pos.CENTER);
		hboxL1.setSpacing(100);
		hboxL1.setPadding(new Insets(10, 0, 10, 0));
		return hboxL1;
	}
	/**
	 * This will make the contents of the second HBox in the bottom interface
	 * @return The second layer HBox in the bottom of the interface
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
	/**
	 * Stub method to test addFile button EventHandler
	 * @throws OperationNotSupportedException
	 */
	private void addFile() throws OperationNotSupportedException
	{
		throw new OperationNotSupportedException("Function not yet Implemented");
	}
	/**
	 * Stub method to test updateIndexedFiles button EventHandler
	 * @throws OperationNotSupportedException
	 */
	private void updateIndexedFiles() throws OperationNotSupportedException
	{
		throw new OperationNotSupportedException("Function not yet Implemented");
	}
	/**
	 * Stub method to test removeSelectedFiles button EventHandler
	 * @throws OperationNotSupportedException
	 */
	private void removeSelectedFiles() throws OperationNotSupportedException
	{
		throw new OperationNotSupportedException("Function not yet Implemented");
	}
}
