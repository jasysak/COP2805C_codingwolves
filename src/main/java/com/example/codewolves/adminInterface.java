package com.example.codewolves;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
 
 
/**
 * Sample application that shows examples of the different layout panes
 * provided by the JavaFX layout API.
 * The resulting UI is for demonstration purposes only and is not interactive.
 */
 
 
public class adminInterface extends Application {
    
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(adminInterface.class, args);
    }
    
    @Override
    public void start(Stage stage) {
 
 
// Use a border pane as the root for scene
        BorderPane border = new BorderPane();
        

        HBox hbox = addHBox();
        border.setTop(hbox);
        
// Add a list view for center region  
        ListView<String> listview = addListView();
        border.setCenter(listview);
        AnchorPane anchorpane = addAnchorPane();
        border.setBottom(anchorpane);
        
        Scene scene = new Scene(border);
        stage.setScene(scene);
        stage.setTitle("Search Engine Sample Admin Interface");
        stage.show();
    }
 
/*
 * Creates an HBox with text entry box and search button for the top 
 * row in the top region.
 * Need to add 3 toggles for AND, OR, Exact Match on second row
 */
    
    private HBox addHBox() {
 
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);   // Gap between nodes
 
        Label label01 = new Label("Search Terms:");
 
        TextField textField01 = new TextField();
        
        Button buttonSearch = new Button("Search");
        buttonSearch.setPrefSize(80, 20);
        
        final ToggleGroup group = new ToggleGroup();

        RadioButton rb1 = new RadioButton("AND");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);

        RadioButton rb2 = new RadioButton("OR");
        rb2.setToggleGroup(group);
         
        RadioButton rb3 = new RadioButton("Match Exact");
        rb3.setToggleGroup(group);
        
        hbox.getChildren().addAll(label01, textField01, buttonSearch, rb1, rb2, rb3);
        
        return hbox;
    }
    

/* create a list view for main window view (center)
 * 
 * 
 */
    private ListView<String> addListView() {
    	
    	ListView<String> list = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList (
            "Item 1", "Item 2", "Item 3", "Item 4");
        list.setItems(items);
        list.setPrefWidth(500);
        list.setPrefHeight(700);

        return list;
    }
    
 
/*
 * Creates an anchor pane using the provided grid and an HBox with buttons
 * 
 * @param grid Grid to anchor to the top of the anchor pane
 */
    private AnchorPane addAnchorPane() {
 
        AnchorPane anchorpane = new AnchorPane();
        
        Button buttonEdit = new Button("Edit Item");
        Button buttonDelete = new Button("Delete Item");
        Button buttonSave = new Button("Save to Disk");
        Button buttonAdmin = new Button("User View");
        Button buttonExit = new Button("Exit");
 
        HBox hb = new HBox();
        hb.setPadding(new Insets(10, 10, 10, 10));
        hb.setSpacing(10);
        hb.getChildren().addAll(buttonEdit, buttonDelete, buttonSave, buttonAdmin, buttonExit);
 
        anchorpane.getChildren().addAll(hb);
        
        AnchorPane.setBottomAnchor(hb, 5.0);
        AnchorPane.setRightAnchor(hb, 5.0);
 
        return anchorpane;
    }
}
