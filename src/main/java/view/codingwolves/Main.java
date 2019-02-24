/**
 * The purpose of this class is to make the interface for the main window of the Search Engine project. This interface
 * will allow the user to search for files, go to an administrator window and look at information about the application
 * and who made it.
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
/**
 * @author David Alvarez, 2/9/19
 * @version 1.0
 */
public class Main extends Application{
	private final String iconPath = "/monitor.png";
	private final Button searchBtn = new Button();
	private final Button maintenanceBtn = new Button();
	private final Button aboutBtn = new Button();
	private TextField searchField;
	static String result;
	public static Label numOfFilesIndexed;
	
	public static void main(String[] args) {
		launch(args);

	}
	@Override
    public void start(Stage primaryStage) 
	{
		BorderPane border = new BorderPane();
		HBox hboxT = addHBoxT();
		VBox vboxT = addVBoxT(hboxT);
		TextFlow textFlow = addTextFlowC(result);
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

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	        @Override
	        public void handle(WindowEvent event) {
	            System.exit(0);
	        }
		});
		searchBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
				try {
					searchIndexedFiles();
				} catch (OperationNotSupportedException e1) {
					System.out.println(e1);
				}
			}
		});
		maintenanceBtn.setOnAction(new EventHandler<ActionEvent>()
		{
		    public void handle(ActionEvent e) {
		    	Stage maintStage = new Stage();
		    	MaintenanceWindow maint = new MaintenanceWindow();
				maint.start(maintStage);
				maintStage.show();
				maintStage.isAlwaysOnTop();
		    }
		});
		
		aboutBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
				try {
					aboutInformation();
				} catch (OperationNotSupportedException e1) {
					System.out.println(e1);
				}
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
		
		RadioButton andButton = new RadioButton("_All of the Search Terms");
		andButton.setFont(Font.font("SansSerif", FontWeight.BLACK, 12));
		andButton.setMnemonicParsing(true);
		andButton.setToggleGroup(searchTypes);
		andButton.setOnAction(onAction);
		andButton.setSelected(true);
		
		RadioButton orButton = new RadioButton("Any _of the Search Terms");
		orButton.setFont(Font.font("SansSerif", FontWeight.BLACK, 12));
		orButton.setMnemonicParsing(true);
		orButton.setOnAction(onAction);
		orButton.setToggleGroup(searchTypes);
		
		RadioButton phraseButton = new RadioButton("Exact _Phrase");
		phraseButton.setFont(Font.font("SansSerif", FontWeight.BLACK, 12));
		phraseButton.setMnemonicParsing(true);
		phraseButton.setOnAction(onAction);
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
	private TextFlow addTextFlowC(String result)
	{
		TextFlow textFlow = new TextFlow();
		
		Text searchResult = new Text(result);
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
		maintenanceBtn.setOnAction(onAction);
		
		Label numOfFiles = new Label("Number of Files Indexed:");
		numOfFiles.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		numOfFilesIndexed = new Label("0");
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
	//Will select the radio button that is chosen when using the Mnemonic key
	EventHandler<ActionEvent> onAction = new EventHandler<ActionEvent>()
	{
	    @Override
	    public void handle(ActionEvent e)
	    {
	        RadioButton button = (RadioButton)e.getSource();
	        button.setSelected(true);
	    }
	};
	/**
	 * Stub method to test search button EventHandler
	 * @throws OperationNotSupportedException
	 */
	private void searchIndexedFiles() throws OperationNotSupportedException
	{
		throw new OperationNotSupportedException("Function not yet Implemented");
	}
	/**
	 * Stub method to test about button EventHandler
	 * @throws OperationNotSupportedException
	 */
	private void aboutInformation()	throws OperationNotSupportedException
	{
		throw new OperationNotSupportedException("Function not yet Implemented");
	}
}