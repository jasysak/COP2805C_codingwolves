/**
 * The purpose of this class is to make the interface for the main window of the Search Engine project. This interface
 * will allow the user to search for files, go to an administrator window and look at information about the application
 * and who made it.
 * Written by David Alvarez, 2/9/19
 */
package view.codingwolves;

import javafx.application.Application;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * @author David Alvarez
 *
 */
public class Main extends Application {
	private final String iconPath = "/monitor.png";
	private final Button search = new Button();
	private final Button maintenance = new Button();
	private final Button about = new Button();
	static final String FILE_ENCODING = "UTF8";
	static final String LAST_DIRECTORY = "LastvisitedDir";
	public static String result;
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
    public void start(Stage stage) 
	{
		BorderPane border = new BorderPane();
		HBox hboxT = addHBoxT();
		VBox vboxT = addVBoxT(hboxT);
		TextFlow textFlow = addTextFlowC(result);
		border.setTop(vboxT);
		border.setCenter(textFlow);
		
		Scene mainScene = new Scene(border, 1000, 700);
		stage.setScene(mainScene);
		stage.setTitle("SearchEngine");
		stage.getIcons().add(new Image(iconPath));
		stage.show();
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
		
		RadioButton orButton = new RadioButton("Any _of the Search Terms");
		orButton.setFont(Font.font("SansSerif", FontWeight.BLACK, 12));
		orButton.setMnemonicParsing(true);
		orButton.setToggleGroup(searchTypes);
		
		RadioButton phraseButton = new RadioButton("Exact _Phrase");
		phraseButton.setFont(Font.font("SansSerif", FontWeight.BLACK, 12));
		phraseButton.setMnemonicParsing(true);
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
		search.setPrefSize(100, 20);
		textField.setPromptText("Enter a partial phrase or the full phrase found in the indexed files.");
		textField.setPrefSize(500, 20);
		
		hbox.getChildren().addAll(label1, textField, search);
		hbox.setSpacing(30);
		hbox.setPadding(new Insets(20, 0, 0, 0));
		return hbox;
	}
	private TextFlow addTextFlowC(String result)
	{
		TextFlow textFlow = new TextFlow();
		
		Text searchResult = new Text(result);
		return textFlow;
	}
}
