package Search;

import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.*;


public class Search3 extends Application 
{
	public static void main(String[] args) {launch(args);}
	
	@Override
	public void start (Stage stage) throws Exception
	{
			//Main Layout
		BorderPane SearchWin = new BorderPane();
			//Top Pane
		VBox TopArea = SearchArea();
		SearchWin.setTop(TopArea);
			//Center Pane
		ListView<String> List = SearchList();
		SearchWin.setCenter (List);
			//Bottom Pane
		HBox BottomArea = AdminArea();
		SearchWin.setBottom(BottomArea);
			//Stage/Scene configurations
		Scene SchWin = new Scene(SearchWin, 600, 600);
		stage.setScene(SchWin);
		stage.setTitle("Search Engine");
		stage.show();
	}
	public VBox SearchArea()
	{
			//VBox configuration
		VBox SearchArea = new VBox();
		SearchArea.setPadding(new Insets(15,12,15,12));
		SearchArea.setSpacing(10);
		SearchArea.setAlignment(Pos.CENTER);
		SearchArea.setStyle("-fx-background-color: DARKGRAY;");
		
			//Window title
		Text WinTitle = new Text("Coding Wolves Search Engine");
		WinTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		
			//Search Line
		HBox SearchLine = new HBox();
		SearchLine.setPadding(new Insets(15,12,15,12));
		SearchLine.setSpacing(10);
		SearchLine.setAlignment(Pos.CENTER);
		SearchLine.setStyle("-dx-background-colr: DARKGRAY;");
			//Search Label
		Label Input = new Label("Search Input");
		Input.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
			//Search box
		TextField SearchBox = new TextField();
		SearchBox.setPromptText("Type here");
			//Search Button
		Button Search = new Button("Search");
		
		SearchLine.getChildren().addAll(Input, SearchBox, Search);
		
			//Option Line
		HBox OptionLine = new HBox();
		OptionLine.setPadding(new Insets(15,12,15,12));
		OptionLine.setSpacing(10);
		OptionLine.setAlignment(Pos.CENTER);
		OptionLine.setStyle("-dx-background-colr: DARKGRAY;");
			//Options
		RadioButton option1 = new RadioButton("Search All");
		option1.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		RadioButton option2 = new RadioButton("Search Any");
		option2.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		RadioButton option3 = new RadioButton("Search Exact");
		option3.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		
		OptionLine.getChildren().addAll(option1, option2, option3);
			
			//VBox exports
		SearchArea.getChildren().addAll(WinTitle, SearchLine, OptionLine);
		return SearchArea;
	}
	public ListView<String> SearchList()
	{
		ListView<String> SearchList = new ListView<String>();
		ObservableList<String> items = FXCollections.<String>observableArrayList("test");
		SearchList.setItems(items);
		return SearchList;
	}
	public HBox AdminArea()
	{
			//HBox configuration
		HBox AdminArea = new HBox();
		AdminArea.setPadding(new Insets(15,12,15,12));
		AdminArea.setSpacing(10);
		AdminArea.setAlignment(Pos.BOTTOM_LEFT);
		AdminArea.setStyle("-fx-background-color: DARKGRAY;");
		
			//Administrators Button
		Button Administrators = new Button("Administrators");
		Administrators.setOnAction(e -> { 
		Stage adminStage = new Stage(); 
		AdminWin aw = new AdminWin(); 
		try { aw.start( adminStage ); } 
		catch (Exception ex) {} 
		adminStage.show(); 
		} ); 
		
			//HBox exports
		AdminArea.getChildren().addAll(Administrators);
		return AdminArea;
	}
}
