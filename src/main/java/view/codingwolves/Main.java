/**
 * The purpose of this class is to make the interface for the main window of the Search Engine project. This interface
 * will allow the user to search for files, go to an administrator window and look at information about the application
 * and who made it.
 * Written by David Alvarez, 2/9/19
 */
package view.codingwolves;

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
/**
 * @author David Alvarez
 *
 */
public class Main extends Application{
	private final String iconPath = "/monitor.png";
	private final Button search = new Button();
	private final Button maintenance = new Button();
	private final Button about = new Button();
	static String result;
	static int numOfFilesIndexed = 0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}
	/**
	 * 
	 */
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
		
		maintenance.setOnAction(new EventHandler<ActionEvent>()
		{
		    public void handle(ActionEvent e) {
		    	Stage maintStage = new Stage();
		    	MaintenanceWindow maint = new MaintenanceWindow();
				maint.start(maintStage);
				maintStage.show();
				maintStage.isAlwaysOnTop();
		    }
		});
	}
	/**
	 * Make the VBox layout for the top of interface and embed HBox into it
	 * @param hbox
	 * @return	vbox layout
	 */
	private VBox addVBoxT(HBox hbox)
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
		vbox.getChildren().addAll(title, hbox, hbL2);
		vbox.setAlignment(Pos.CENTER);
		hbox.setAlignment(Pos.CENTER);
		hbL2.setAlignment(Pos.CENTER);
		hbL2.setPadding(new Insets(10, 0, 10, 0));
		hbL2.setSpacing(20);
		return vbox;
	}
	/**
	 * Make the HBox layout for the top of interface
	 * @return	hbox layout
	 */
	private HBox addHBoxT()
	{
		HBox hbox = new HBox();
		
		Label label1 = new Label("Search Terms: ");
		label1.setFont(Font.font("SansSerif", FontWeight.BOLD, 15));
		
		TextField textField = new TextField();
		search.setText("Search");
		search.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		search.setPrefSize(100, 20);
		textField.setPromptText("Enter a partial phrase or the full phrase found in the indexed files.");
		textField.setPrefSize(500, 20);
		
		hbox.getChildren().addAll(label1, textField, search);
		hbox.setSpacing(30);
		hbox.setPadding(new Insets(20, 0, 0, 0));
		return hbox;
	}
	/**
	 * 
	 * @param result
	 * @return
	 */
	private TextFlow addTextFlowC(String result)
	{
		TextFlow textFlow = new TextFlow();
		
		Text searchResult = new Text(result);
		return textFlow;
	}
	/**
	 * Make the HBox layout for the bottom of the interface
	 * @return
	 */
	private HBox addHBoxB()
	{
		HBox hboxB = new HBox();
		
		maintenance.setText("Maintenace");
		maintenance.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		maintenance.setPrefSize(100, 20);
		maintenance.setOnAction(onAction);
		
		String s1 = "Number of Files Indexed: " + Integer.toString(numOfFilesIndexed);
		Label numOfFiles = new Label(s1);
		numOfFiles.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		
		about.setText("About");
		about.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		about.setPrefSize(100, 20);
		
		hboxB.getChildren().addAll(maintenance, numOfFiles, about);
		hboxB.setAlignment(Pos.CENTER);
		hboxB.setSpacing(300);
		hboxB.setPadding(new Insets(10, 0, 10, 0));
		return hboxB;
	}
	//Will select the radio button that is chosen using the Mnemonic key
	EventHandler<ActionEvent> onAction = new EventHandler<ActionEvent>()
	{
	    @Override
	    public void handle(ActionEvent e)
	    {
	        RadioButton button = (RadioButton)e.getSource();
	        button.setSelected(true);
	    }
	};
}