package view.codingwolves;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.naming.OperationNotSupportedException;

import controller.codingwolves.FileIndex;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.codingwolves.FileModel;
import model.codingwolves.Files;

/**
 * This class will make the Maintenance window of the Search Engine project. This window will allow the user
 * to view the files indexed, add files to the index, delete files from the index, and to update the index 
 * files if any there were any changes to the files.
 * 
 * @author David Alvarez, 2/12/19
 *
 */
public class MaintenanceWindow extends Application {
	private final String iconPath = "/monitor.png";
	private final Button addFileBtn = new Button();
	private final Button updateIndexedFilesBtn = new Button();
	private final Button removeSelectedFilesBtn = new Button();
	public static final Label numOfFilesIndexed = new Label ("0");
	final static String VERSION_NUMBER = "1.0";
	public static TableView<Files> table = new TableView<Files>();
	public static TableColumn<Files, String> fileNameCol = new TableColumn<Files, String>("File Name");;
	public static TableColumn<Files, String> statusCol = new TableColumn<Files, String>("Status");;
	
	@Override
    public void start(Stage secondaryStage) 
	{
		BorderPane border = new BorderPane();
		HBox hboxT = addHBoxT();
		HBox hboxBL1 = addHBoxBL1();
		HBox hboxBL2 = addHboxBL2();
		VBox vboxB = new VBox(hboxBL1, hboxBL2);
		border.setCenter(addTableView());
		border.setTop(hboxT);
		border.setBottom(vboxB);
		
		Scene scene = new Scene(border, 800, 600);
		secondaryStage.setTitle("Maintenance");
		secondaryStage.getIcons().add(new Image(iconPath));
		secondaryStage.setScene(scene);
		secondaryStage.setResizable(false);
		
		addFileBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
					FileIndex.addFileToIndex();
			}
		});
		updateIndexedFilesBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
				FileIndex.updateFilesInIndex();
			}
		});
		removeSelectedFilesBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
				FileIndex.removeFilesFromIndex();
			}
		});
	}
	/**
	 * This will make the table that will hold the files indexed and the status of those files
	 * @return The TableView for the center of the interface
	 */
	private TableView<Files> addTableView()
	{	
		table.setEditable(false);
		//fileNameCol = new TableColumn<Files, String>("File Name");
		fileNameCol.setPrefWidth(400);
		//statusCol = new TableColumn<Files, String>("Status");
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
		
		Text title = new Text("Search Engine - Index Maintenance");
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
		GridPane grid =  new GridPane();
		HBox hboxL3 = new HBox();
		
		Label numOfFiles = new Label("Number of Files Indexed:");
		numOfFiles.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		int numFiles = FileModel.files.size();
		String str = Integer.toString(numFiles);
		numOfFilesIndexed.setText(str);
		numOfFilesIndexed.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		
		String s2 = "Search Engine version " + VERSION_NUMBER;
		Label seVersion = new Label(s2);
		seVersion.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		
		hboxL2.getChildren().addAll(numOfFiles, grid);
		hboxL2.setAlignment(Pos.CENTER);
		hboxL2.setSpacing(350);
		hboxL2.setPadding(new Insets(10, 0, 10, 0));
		hboxL3.getChildren().addAll(numOfFiles, numOfFilesIndexed);
		hboxL3.setSpacing(5);
		grid.add(hboxL3, 0, 0);
		grid.add(seVersion, 5, 0);
		grid.setHgap(70);
		return hboxL2;
	}
}
