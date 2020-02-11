/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.ListModel;

/**
 *
 * @author ghost
 */
public class HomeController implements Initializable {
    
    private Label label;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void friend(ActionEvent event) throws IOException {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("ListView.fxml"));
        
        Parent root = loader.load();
            
        ListViewController listController = loader.getController();
        
        
        ListModel list = new ListModel();
        list.setList_id(2);
        
       // list.setTitle("ZeynabNote");
                     //list.setColor("0xff8080ff");
        list.getUser().setName("zeynab");
        list.getUser().setId(1);
                //listController.OwnerNameText.setText("Zeynab Note");
        listController.setList(list);
                 //Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
     Stage stage = new Stage();
                 Scene scene = new Scene(root);
                  //stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Add List");
        stage.show();
    }
    
}
