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
import help.RegixMethods;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginController implements Initializable {

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
    private void handleButtonAction(ActionEvent event) {
        
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
