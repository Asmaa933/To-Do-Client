/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapplication;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import models.ListModel;

/**
 *
 * @author ghost //....Don't forget to add constructor to ListModel has title, color & date.... 
 */
public class ListViewController implements Initializable {
    
    @FXML
    private TextField nameText;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField dateText;
    @FXML
    private Button addButton;
    @FXML
    private ListView<String> listFriend;
    @FXML
    private Button saveButton;
    @FXML
    private Label OwnerNameLabel;
    @FXML
    private Label AddListLabel;
    @FXML
    private Button deleteButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox selectCombobox;
    
    ObservableList<String> selectedFriends;
    //ObservableList<String> selectedFriends = FXCollections.observableArrayList("zeynab","esma","mazen","remon","ahmed");
    ListModel newList;
   
   static class Cell extends ListCell<String>{
       
       HBox hbox =new HBox();
      
//       Image profile  = new Image("F:\\\\AssignmentsOfJava\\\\lab9\\\\ListToDo\\\\src\\\\listtodo\\\\profile.png");
//
//       ImageView image = new ImageView(profile);
       
       Label userName =new Label();
       Pane pane =new Pane();
       Button delete=new Button("Delete");
       
       public Cell() {
            super();
            hbox.getChildren().addAll(userName,pane,delete);
            hbox.setHgrow(pane, Priority.ALWAYS);
            
            delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Delete Friend");
                alert.setHeaderText("Do you want to delete this Friend");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                     getListView().getItems().remove(getItem());
                }
               
            }
        });
        }
       
          @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
            setText(null);
            setGraphic(null);
            
            if (item != null && !empty) {
                userName.setText(item);
                setGraphic(hbox);
            }
        }

   }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Add date & time In Run Time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        System.out.println(dtf.format(now));  
        dateText.setText(dtf.format(now));
        
    //    deleteButton.setVisible(true);
        
//Add Freinds in run time in comboBox At run Time
        ////////////Query of getting user's Friend in a lis of kind ObservableList<String> to match with the comboBox
        /////selectCombobox.setItems(list From Query);

    }
            
    
    
    @FXML
    private void addButtonPressed(ActionEvent event) {

        selectedFriends=(ObservableList<String>) selectCombobox.getSelectionModel().getSelectedItem();
        listFriend.setItems(selectedFriends);
        listFriend.setCellFactory(param-> new Cell());
        ////Query for take the selectedFriends to database with considered the owner
    }
    
    @FXML
    private void saveButtonPressed(ActionEvent event) {

      String hex1 = "#" + Integer.toHexString(colorPicker.getValue().hashCode());
      
      newList=new ListModel(nameText.getText(), hex1,Timestamp.valueOf(dateText.getText()));
      
      ////Query for take the list data from obj newList to database with considered the owner   
        
    }

    @FXML
    private void CancelButtonPressed(ActionEvent event) {
       //.........hide this Scene..........

       ((Node) event.getSource()).getScene().getWindow().hide();
    }
    
    

    @FXML
    private void deleteButtonPressed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Delete List");
    alert.setHeaderText("Do you want to delete this List");
   // alert.setContentText("Careful with the next step!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ...Function Query to delete the List
        }
    }

}
