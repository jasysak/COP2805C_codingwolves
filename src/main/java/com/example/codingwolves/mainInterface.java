/**
 * @author Jason Sysak 2/19/19
 * @version 0.0.1
 * 
 * A basic user and admin UI window for COP 2805C Project 3
 * Search Engine written in Java
 * 
 * The UI is for discussion and demonstration only. It is not
 * functional at this time.
 */
package com.example.codingwolves;

import javax.naming.OperationNotSupportedException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
  
public class mainInterface extends Application {
    
	// Add some buttons
	private final Button buttonAdmin = new Button("Admin. View");
    private final Button buttonExit = new Button("Exit");
    private final Button buttonSearch = new Button("Search");
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) 
    {
 
    	// Use a BorderPane as the root for scene     	
        BorderPane border = new BorderPane();
        
        // add HBox at top area for text entry, radio buttons
        HBox hbox = addHBox();
        border.setTop(hbox);
        
        // Add a ListView for center region for listing of search items
        ListView<String> listview = addListView();
        border.setCenter(listview);
        
        // Add an anchor pane at the bottom for Admin & Exit buttons        
        AnchorPane anchorpane = addAnchorPane();
        border.setBottom(anchorpane);
 
        // Setup and draw the scene
        Scene scene = new Scene(border, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Search Engine Sample User Interface");
        primaryStage.show();
        
        // Event handlers
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	        @Override
	        public void handle(WindowEvent event) {
	            System.exit(0);
	        }
        });   
        
        buttonAdmin.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	Stage maintStage = new Stage();
		    	adminInterface maint = new adminInterface();
				maint.start(maintStage);
				maintStage.show();
				maintStage.isAlwaysOnTop();
		    }
		});
        
        buttonExit.setOnAction(new EventHandler<ActionEvent>() {
        	
        	public void handle(ActionEvent event) {
	            System.exit(0);
        	}
        });
        
        /* For Search button - no functionality, so will throw exception
         * Credit to D. Alvarez for this code
         */
        buttonSearch.setOnAction(new EventHandler<ActionEvent>() {
        	
        	public void handle(ActionEvent event) {
	            try {
	            	searchIndexedFiles();
	            }
	            catch (OperationNotSupportedException e1) {
	            	System.out.println(e1);
	            }
        	}
        });
            
    } // end primaryStage scene
 
/*
 * Creates an HBox with text entry box and search button 
 * with 3 radio buttons for AND, OR, Exact Match
 */
    
    private HBox addHBox() {
 
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);   // Gap between nodes
 
        Label label01 = new Label("Search Terms:");
 
        TextField textField01 = new TextField ();
        
        buttonSearch.setPrefSize(80, 20);
        
        final ToggleGroup buttonGroup = new ToggleGroup();

        RadioButton rb1 = new RadioButton("AND");
        rb1.setToggleGroup(buttonGroup);
        rb1.setSelected(true);

        RadioButton rb2 = new RadioButton("OR");
        rb2.setToggleGroup(buttonGroup);
         
        RadioButton rb3 = new RadioButton("Match Exact");
        rb3.setToggleGroup(buttonGroup);
        
        hbox.getChildren().addAll(label01, textField01, buttonSearch, rb1, rb2, rb3);
        
        return hbox;
    } // end addHBox method
    

/* 
 * create a list view for main window (center) to list search items
 */
    private ListView<String> addListView() {
    	
    	ListView<String> list = new ListView<>();
    	// dummy ObservableList - will be populated with database items TBD
        ObservableList<String> items = FXCollections.observableArrayList (
            "Item 1", "Item 2", "Item 3", "Item 4");
        list.setItems(items);
        list.setPrefWidth(500);
        list.setPrefHeight(700);

        return list;
        
    } // end addListView method
    
/*
 * Creates an anchor pane for additional UI elements -
 * Switch to Admin. View and Exit buttons
 */
    private AnchorPane addAnchorPane() {
 
        AnchorPane anchorpane = new AnchorPane();
        
        HBox hb = new HBox();
        hb.setPadding(new Insets(10, 10, 10, 10));
        hb.setSpacing(10);
        hb.getChildren().addAll(buttonAdmin, buttonExit);
 
        anchorpane.getChildren().addAll(hb);
        
        AnchorPane.setBottomAnchor(hb, 5.0);
        AnchorPane.setRightAnchor(hb, 5.0);
 
        return anchorpane;
        
    } // end addAnchorPane method

/**
 *  Method stub for search function -- by D. Alvarez
 * @throws OperationNotSupportedException
 */
    
    private void searchIndexedFiles() throws OperationNotSupportedException
	{
		throw new OperationNotSupportedException("Function not yet Implemented");
	}
    
} // end class mainInterface
