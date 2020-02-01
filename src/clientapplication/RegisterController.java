package clientapplication;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import help.RegixMethods;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author AhmedWagdy
 */
public class RegisterController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private PasswordField confirmPassTextField;
    @FXML
    private Hyperlink logInHyperlink;

    @FXML
    private Label nameErrorLabel;

    @FXML
    private Label emailErrorLabel;

    @FXML
    private Label passErrorLabel;

    @FXML
    private Label confirmPassErrorLabel;

    @FXML
    private Button signUpButton;
    
        private int x = 0;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @FXML
    private void signUpPressed(ActionEvent event) {
        //validate goto home page or errrors
        // check if register before or not
        System.out.println(x);
       if(validateInputs()){
        //sign up data in database
        if (true /* sign up success*/)
        {
        ((Node) event.getSource()).getScene().getWindow().hide();
        FXMLLoader fxload = new FXMLLoader(getClass().getResource("HomeView.fxml"));
        Parent root;
        try {
            root = (Parent) fxload.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
       }
    }

    /**
     *
     * @param event close application
     */
    @FXML
    private void closeButtonPressed(ActionEvent event) {
        Platform.exit();

    }

    /**
     *
     * @param event go to login scene
     */
    @FXML
    private void loginPressed(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();

        FXMLLoader fxload = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        Parent root;
        try {
            root = (Parent) fxload.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

        /**
         * validate textFields
         */
    }
/**
 * validate inputs 
 * @return true if all fields are right
 */
    private boolean validateInputs() {
        boolean validateFlag = false;
        if (nameTextField.getText().isEmpty()) {
            nameErrorLabel.setText("Enter name");

        } else if (!RegixMethods.isValidName(nameTextField.getText())) {
            nameErrorLabel.setText("Invalid Name");
            validateFlag = false;
        } else {
            nameErrorLabel.setText("");
        }

        if (passwordTextField.getText().isEmpty()) {
            passErrorLabel.setText("Enter password");

        } else if (!RegixMethods.isValidPassword(passwordTextField.getText())) {
            passErrorLabel.setText("Password should be mix of letters,numbers and symbols");
            validateFlag = false;
        } else {
            passErrorLabel.setVisible(false);

        }
        if (emailTextField.getText().isEmpty()) {
            emailErrorLabel.setText("Enter email");

        } else if (!RegixMethods.isValidEmail(emailTextField.getText())) {
            emailErrorLabel.setText("Invalid email");
            validateFlag = false;
        } else {
            emailErrorLabel.setVisible(false);

        }
        if (confirmPassTextField.getText().isEmpty()) {
            confirmPassErrorLabel.setText("Enter confirm password");

        } else if (!passwordTextField.getText().equals(confirmPassTextField.getText())) {
            confirmPassErrorLabel.setText("Password not matches");
            validateFlag = false;
        } else {
            confirmPassErrorLabel.setText("");

        }
        return validateFlag;

    }

}
