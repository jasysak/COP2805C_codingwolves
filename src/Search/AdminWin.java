package Search;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.*;

public class AdminWin extends Application 
{
	Scene AdmWin;
	static Stage adminStage;
	FileChooser SelectFile; 
	
	@Override
	public void start (Stage adminStage) throws Exception
	{
			//Main Layout
		BorderPane AdminWin = new BorderPane();
			
			//Top Pane
		VBox TopArea = LabelArea();
		AdminWin.setTop(TopArea);
			
			//Center Pane
		TableView<String> CenterArea = FileList();
		AdminWin.setCenter (CenterArea);
			
			//Bottom Pane
		HBox BottomArea = FileArea();
		AdminWin.setBottom(BottomArea);
			
			//Stage/Scene configurations
		AdmWin = new Scene(AdminWin, 600, 600);
		adminStage.setScene(AdmWin);
		adminStage.setTitle("Search Engine");
		adminStage.show();
	}
	public static VBox LabelArea()
	{
			//VBox configuration
		VBox LabelArea = new VBox();
		LabelArea.setPadding(new Insets(15,12,15,12));
		LabelArea.setSpacing(10);
		LabelArea.setAlignment(Pos.CENTER);
		LabelArea.setStyle("-fx-background-color: DARKGRAY;");
		
			//Window title
		Text WinTitle = new Text("Coding Wolves Administators");
		WinTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		
			//VBox exports
		LabelArea.getChildren().addAll(WinTitle);
		return LabelArea;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static  TableView<String> FileList()
	{
		TableView table = new TableView();
		table.setEditable(false);
		TableColumn filepath = new TableColumn("File");
		filepath.setPrefWidth(300);
		TableColumn index = new TableColumn ("Index");
		index.setPrefWidth(300);
		table.getColumns().addAll(filepath, index);
		table.getItems().add(new File("file"));
		
		return table;
	}
	public static HBox FileArea()
	{
			//HBox configuration
		HBox FileArea = new HBox();
		FileArea.setPadding(new Insets(15,12,15,12));
		FileArea.setSpacing(10);
		FileArea.setAlignment(Pos.BOTTOM_LEFT);
		FileArea.setStyle("-fx-background-color: DARKGRAY;");
		
			//File Buttons
        Button AddFile = new Button("Add File");
        FileChooser SelectFile = new FileChooser();
        AddFile.setOnAction(e -> 
        	{ 
        		SelectFile.setInitialDirectory(new File(System.getProperty("user.home")));
        		SelectFile.getExtensionFilters().addAll
        			(
        			new FileChooser.ExtensionFilter("Text Files", "*.txt"),
        			new FileChooser.ExtensionFilter("HTML Files","*.html")
        			);
        		File file = SelectFile.showOpenDialog(adminStage);
        		if(file != null)
        		
        		return;		
        	});
		
        Button UpdateFile = new Button("Update File");
        
		Button RemoveFile = new Button("Remove File");

			//HBox exports
		FileArea.getChildren().addAll(AddFile, UpdateFile, RemoveFile);
		return FileArea;
	}
}
