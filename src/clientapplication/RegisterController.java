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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.json.JsonObject;
import model.UserModel;
import network.JsonUtil;
import network.RequestHandler;

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
    private ProgressIndicator progressIndicator;
    @FXML
    private Button signUpBtn;
    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private void signUpPressed(ActionEvent event) {
        //validate goto home page or errrors
        // check if register before or not
        if (validateInputs()) {
            signUpBtn.setDisable(true);
            signUpBtn.setText("");
            progressIndicator.setVisible(true);
            logInHyperlink.setVisible(false);
            new Thread(() -> {
                UserModel user = new UserModel(nameTextField.getText(), emailTextField.getText(), passwordTextField.getText());
                JsonObject jsonObject = JsonUtil.convertToJsonUser(user);
                JsonObject response = new RequestHandler().makeRequest(jsonObject);
                boolean addFlag = JsonUtil.convertFromJsonPasswordResponse(response);
                System.out.println("aaaaa" + addFlag);
                Platform.runLater(() -> {
                         signUpBtn.setDisable(false);
            signUpBtn.setText("Sign Up");
       
            progressIndicator.setVisible(false);
                    if (addFlag == true) {
                        Parent loginScene;
                        try {
                            loginScene = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
                            Scene tableViewScene = new Scene(loginScene);
                            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            window.setScene(tableViewScene);
                            window.show();
                        } catch (IOException ex) {
                            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        emailErrorLabel.setText("This email already registered");
                                    logInHyperlink.setVisible(true);

                        // Aller Signup Again  // And Existes Email
                    }
                });

            }).start();
        }
    }

    /**
     *
     * @param event close application
     */
    @FXML
    private void closeButtonPressed(ActionEvent event) {

        Platform.exit(); 
        System.exit(0);
    }

    /**
     *
     * @param event go to login scene
     */
    @FXML
    private void loginPressed(ActionEvent event) {
        Parent loginScene;
        try {
            loginScene = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
            Scene tableViewScene = new Scene(loginScene);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * validate inputs
     *
     * @return true if all fields are right
     */
    private boolean validateInputs() {
        boolean validateFlag = true; 

        if (nameTextField.getText().trim().isEmpty()) {
            nameErrorLabel.setText("Enter name");
            validateFlag = false; 

        } else if (!RegixMethods.isValidName(nameTextField.getText())) {
            nameErrorLabel.setText("Invalid Name");
            validateFlag = false;
        } else {
            nameErrorLabel.setText(""); 
        }

        if (passwordTextField.getText().trim().isEmpty()) { 
            passErrorLabel.setText("Enter password");
            validateFlag = false; 

        } else if (!RegixMethods.isValidPassword(passwordTextField.getText())) {
            passErrorLabel.setText("Password should be mix of letters,numbers and symbols");
            validateFlag = false;
        } else {
            passErrorLabel.setText("");
        }

        if (emailTextField.getText().isEmpty()) {
            emailErrorLabel.setText("Enter email");
            validateFlag = false; 

        } else if (!RegixMethods.isValidEmail(emailTextField.getText())) {
            emailErrorLabel.setText("Invalid email");
            validateFlag = false;
        } else {
            emailErrorLabel.setText("");  
        }

        if (confirmPassTextField.getText().isEmpty()) {
            confirmPassErrorLabel.setText("Enter confirm password");
            validateFlag = false; 

        } else if (!passwordTextField.getText().equals(confirmPassTextField.getText())) {
            confirmPassErrorLabel.setText("Password not matches");
            validateFlag = false;
        } else {
            confirmPassErrorLabel.setText(""); 
        }

        return validateFlag;
    }

}
