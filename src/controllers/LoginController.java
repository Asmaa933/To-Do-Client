/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/////////////////Email Constraints
///////////////Password Constraints
// 8-16 characters password with at least one digit, at least one
// lowercase letter at least one uppercase letter, at least one
// special character with no whitespaces
package controllers;
import clientapplication.*;
import help.RegixMethods;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class LoginController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label invalidLabel;
    @FXML
    private Button nextButton;
    @FXML
    private Hyperlink createNewAccountButton;
    @FXML
    private TextField emailText;
    @FXML
    private Label emailPasswordLabel;
    @FXML
    private PasswordField passwordText;
    @FXML
    private Hyperlink notYouButton;
    
    boolean flagFalidEmail = false;
    
    
    
  
   
   /**
    * 
    * @param event   
    */
 @FXML   
    private void nextButtonPressed(ActionEvent event) {
        String email = emailText.getText();
        if (flagFalidEmail == false) 
        {
            if (RegixMethods.isValidEmail(email)) {
                System.out.print(" Email Valid \n");
                //if (emailQuery(email));
                emailPasswordLabel.setText("Password");
                emailText.setVisible(false);
                passwordText.setVisible(true);
                flagFalidEmail = true;
                invalidLabel.setText("");
            } else {
                invalidLabel.setText(" Email Not Found");
            }            
        } else {
            String password = passwordText.getText();
            if (RegixMethods.isValidPassword(password)) {
                passwordText.setText("");
                invalidLabel.setText("");
                // passwordQuery(password);
            } else {
                invalidLabel.setText(" Password Not Valid ");
            }
           
        }
    }
    @FXML 
        private void newAccountPressed(ActionEvent event) {
     
    
        }
            
       
       
            
            
        

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
