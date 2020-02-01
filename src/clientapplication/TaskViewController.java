/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapplication;

import java.net.URL;
import static java.util.Collections.list;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author esma
 */
public class TaskViewController implements Initializable {

    @FXML
    private Label titleLabel;
    @FXML
    private Label ownerLabel;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker deadlineDatePicker;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TextArea commentTextArea;
    @FXML
    private ListView<String> commentsListView;
    
    ObservableList<String> commentslist = FXCollections.observableArrayList("zeynab","esma","mazen","remon","ahmed");

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    commentsListView.setItems(commentslist);
      
        commentsListView.setCellFactory(param-> new Cell());

    }    

    @FXML
    private void editButtonPressed(ActionEvent event) {
    }

    @FXML
    private void deleteButtonPressed(ActionEvent event) {
    }

    @FXML
    private void addCommentPressed(ActionEvent event) {
    }

    @FXML
    private void cancelButtonPressed(ActionEvent event) {
    }

    @FXML
    private void doneButtonPressed(ActionEvent event) {
    }
    static class Cell extends ListCell<String>{
       VBox vbox =new VBox();
        HBox hbox = new HBox();
               Pane pane =new Pane();

       Label userName =new Label();
       Label date =new Label();
     
        TextArea commentsArea =new TextArea();
        
        
       
     
        
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty); 
            setText(null);
            setGraphic(null);
            
            if (item != null && !empty) {
                userName.setText(item);
               
                //setGraphic(image);
                setGraphic(vbox);
            }
        }

        public Cell() {
            super();
           hbox.getChildren().addAll(userName,pane,date);
            hbox.setHgrow(pane, Priority.ALWAYS);

            vbox.getChildren().addAll(hbox,commentsArea);
            vbox.setVgrow(pane, Priority.ALWAYS);
            commentsArea.setPrefHeight(50);
            commentsArea.setPrefWidth(50);
            commentsArea.setText("hello");
            date.setText("12/12/2012");

        }
        
        

   }
    
}
