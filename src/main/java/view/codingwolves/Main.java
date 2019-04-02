package view.codingwolves;

import java.io.File;
import java.io.IOException;

import javax.naming.OperationNotSupportedException;

import controller.codingwolves.FileIndex;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.codingwolves.FileModel;
import model.codingwolves.IndexModel;
import model.codingwolves.Searches;
// JAS added for initial load of II JSON
// Not needed yet, until loadIndexFromStorage()
// is functioning.
// import model.codingwolves.IndexModel;

/**
 * The purpose of this class is to make the interface for the main window of the Search Engine project. 
 * This interface will allow the user to search for files, go to an administrator window and look at
 * information about the application and who made it.
 * 
 * @author David Alvarez, 2/9/19
 * @version 1.0
 * 
 * For COP2805C Group Project
 * 
 * codingwolves team
 * Members:
 * David Alvarez
 * Reubin George
 * Erin Hochstetler
 * Jason Sysak
 * 
 */

public class Main extends Application{
	public static long nextFileID;
	private final String iconPath = "/monitor.png";
	private final Button searchBtn = new Button();
	private final Button maintenanceBtn = new Button();
	private final Button aboutBtn = new Button();
	private TextField searchField;
	public Text searchResult = new Text();
	public static Label numOfFilesIndexed;
	RadioButton andButton;
	RadioButton orButton;
	RadioButton phraseButton;
	Stage maintStage = new Stage();
	MaintenanceWindow maint = new MaintenanceWindow();
	private Searches selectedSearch = Searches.AND;
	
	public static void main(String[] args) {
		launch(args);

	}
	@Override
    public void start(Stage primaryStage) 
	{
		FileIndex.initializeIndex();
		// JAS added
		// Why is throws IOException or a try..catch needed here?
		try {
			IndexModel.loadIndexFromStorage();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("No Inverted Index JSON file found. Assuming first run of program.");
			// e1.printStackTrace();
		}

		
		BorderPane border = new BorderPane();
		HBox hboxT = addHBoxT();
		VBox vboxT = addVBoxT(hboxT);
		TextFlow textFlow = addTextFlowC();
		HBox hboxB = addHBoxB();
		border.setTop(vboxT);
		border.setCenter(textFlow);
		border.setBottom(hboxB);
		
		Scene mainScene = new Scene(border, 1000, 700);
		primaryStage.setScene(mainScene);
		primaryStage.setTitle("SearchEngine");
		primaryStage.getIcons().add(new Image(iconPath));
		primaryStage.show();
		primaryStage.setResizable(false);
		maint.start(maintStage);

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	        @Override
	        public void handle(WindowEvent event) {
	        	// JAS added
	        	// Save InvIndex.json file
	        	System.out.println("Search Engine is closing");
	    	    try {
	    			IndexModel.saveIndexToStorage();
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}	
	            System.exit(0);
	        }
		});
		searchBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
				switch(selectedSearch) {
				case AND:
					FileIndex.andSearch(Main.this.searchField.getText(), Main.this.searchResult);
					break;
				case OR:
					FileIndex.orSearch(Main.this.searchField.getText());
					break;
				case PHRASE:
					FileIndex.phraseSearch(Main.this.searchField.getText(), Main.this.searchResult);
					break;
				}
			}
		});
		maintenanceBtn.setOnAction(new EventHandler<ActionEvent>()
		{
		    public void handle(ActionEvent e) {
				maintStage.show();
				maintStage.isAlwaysOnTop();
		    }
		});
		
		aboutBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
				File indexFile = new File(System.getProperty("user.home") + File.separator + "SearchEngine.json");
				File wordIndexFile = new File(System.getProperty("user.home") + File.separator + "invIndex.json");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("About");
				alert.setHeaderText(null);
				alert.setContentText("Search Engine 1.0\n\nWritten by David Alvarez, Jason Sysak, Reubin George, Erin Hochstetler, Tampa Florida USA\n"
						+ "FileList Index file: " + indexFile + ",\nsize: " + indexFile.length() + " bytes\nWordList Index file: "
						+ wordIndexFile + ",\nsize: " + wordIndexFile.length() + " bytes");
				alert.initOwner(primaryStage);
				alert.showAndWait();
			}
		});
		andButton.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
				RadioButton button = (RadioButton)e.getSource();
		        button.setSelected(true);
		        
		        Main.this.selectedSearch = Searches.AND;
			}
		});
		orButton.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
				RadioButton button = (RadioButton)e.getSource();
		        button.setSelected(true);
		        
		        Main.this.selectedSearch = Searches.OR;
			}
		});
		phraseButton.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
				RadioButton button = (RadioButton)e.getSource();
		        button.setSelected(true);
		        
		        Main.this.selectedSearch = Searches.PHRASE;
			}
		});
	}
	
	/**
	 * Make the VBox layout for the top of interface and embed HBox into it
	 * @param hbox
	 * @return	vbox layout for the top of the interface
	 */
	private VBox addVBoxT(HBox hboxT)
	{
		VBox vbox = new VBox();
		HBox hbL2 = new HBox();
		
		Text title = new Text("Search Engine");
		title.setFont(Font.font("SansSerif", 40));
		
		final ToggleGroup searchTypes = new ToggleGroup();
		
		this.andButton = new RadioButton("_All of the Search Terms");
		andButton.setFont(Font.font("SansSerif", FontWeight.BLACK, 12));
		andButton.setMnemonicParsing(true);
		andButton.setToggleGroup(searchTypes);
		andButton.setSelected(true);
		
		this.orButton = new RadioButton("Any _of the Search Terms");
		orButton.setFont(Font.font("SansSerif", FontWeight.BLACK, 12));
		orButton.setMnemonicParsing(true);
		orButton.setToggleGroup(searchTypes);
		
		this.phraseButton = new RadioButton("Exact _Phrase");
		phraseButton.setFont(Font.font("SansSerif", FontWeight.BLACK, 12));
		phraseButton.setMnemonicParsing(true);
		phraseButton.setToggleGroup(searchTypes);
		
		hbL2.getChildren().addAll(andButton, orButton, phraseButton);
		vbox.getChildren().addAll(title, hboxT, hbL2);
		vbox.setAlignment(Pos.CENTER);
		hboxT.setAlignment(Pos.CENTER);
		hbL2.setAlignment(Pos.CENTER);
		hbL2.setPadding(new Insets(10, 0, 10, 0));
		hbL2.setSpacing(20);
		return vbox;
	}
	/**
	 * Make the HBox layout for the top of interface
	 * @return	hbox layout for the top of the interface
	 */
	private HBox addHBoxT()
	{
		HBox hboxT = new HBox();
		
		Label label1 = new Label("Search Terms: ");
		label1.setFont(Font.font("SansSerif", FontWeight.BOLD, 15));
		
		this.searchField = new TextField();
		this.searchField.setPromptText("Enter a word or phrase to search for");
		this.searchField.setPrefSize(500, 20);
		searchBtn.setText("Search");
		searchBtn.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		searchBtn.setPrefSize(100, 20);
		
		hboxT.getChildren().addAll(label1, this.searchField, searchBtn);
		hboxT.setSpacing(30);
		hboxT.setPadding(new Insets(20, 0, 0, 0));
		return hboxT;
	}
	/**
	 * Will make the TextFlow layout that will contain the results of the search
	 * @param result The result of the search request
	 * @return the TextFlow layout for the center of the interface
	 */
	private TextFlow addTextFlowC()
	{
		TextFlow textFlow = new TextFlow();
		
		searchResult.setFont(Font.font("SansSerif", FontWeight.BOLD, 14));
		textFlow.getChildren().add(searchResult);
		textFlow.setPadding(new Insets(10, 10, 10, 10));
		
		return textFlow;
	}
	/**
	 * Make the HBox layout for the bottom of the interface
	 * @return the hbox for the bottom of the interface
	 */
	private HBox addHBoxB()
	{
		HBox hboxB = new HBox();
		HBox hboxL2 = new HBox();
		
		maintenanceBtn.setText("Maintenance");
		maintenanceBtn.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		maintenanceBtn.setPrefSize(120, 20);
		
		Label numOfFiles = new Label("Number of Files Indexed:");
		numOfFiles.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		numOfFilesIndexed = new Label("0");
		int numFiles = FileModel.files.size();
		String str = Integer.toString(numFiles);
		numOfFilesIndexed.setText(str);
		numOfFilesIndexed.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		
		aboutBtn.setText("About");
		aboutBtn.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		aboutBtn.setPrefSize(100, 20);
		
		hboxL2.getChildren().addAll(numOfFiles, numOfFilesIndexed);
		hboxL2.setSpacing(5);
		hboxB.getChildren().addAll(maintenanceBtn, hboxL2, aboutBtn);
		hboxB.setAlignment(Pos.CENTER);
		hboxB.setSpacing(260);
		hboxB.setPadding(new Insets(10, 0, 10, 0));
		return hboxB;
	}
}