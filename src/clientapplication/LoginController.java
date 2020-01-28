package clientapplication;

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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {

    @FXML
    private Label invalidLabel;

    @FXML
    private TextField emailText;

    @FXML
    private PasswordField passwordText;
    @FXML
    private Hyperlink notYouHyperLink;

    private boolean flagEmail = false;

    /**
     *
     * @param event go to password if email was valid
     */
    @FXML
    private void nextButtonPressed(ActionEvent event) {
        if (flagEmail == false) {
        validateEmail();
        } else {
            if (validatePassword()) {
//          go to home page 
//          pass id to home
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
     * @param event go to register scene
     */
    @FXML
    private void newAccountPressed(ActionEvent event) {

        FXMLLoader fxload = new FXMLLoader(getClass().getResource("RegisterView.fxml"));
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
     * @param event back to email to change it
     */
    @FXML
    private void notYouPressed(ActionEvent event) {
        emailText.setVisible(true);
        notYouHyperLink.setVisible(false);
        passwordText.setVisible(false);
        invalidLabel.setText("");
        flagEmail = false;

    }

    private boolean validateEmail() {
        boolean checkEmail = false;
        if (emailText.getText().isEmpty()) {
            invalidLabel.setText("Enter email");

        } else if (true/*replace with query in db*/){//query email;
            emailText.setVisible(false);
            passwordText.setVisible(true);
            notYouHyperLink.setVisible(true);
            

            flagEmail = true;
            invalidLabel.setText("");
            passwordText.setText("");
            checkEmail = true;
        } else {
            invalidLabel.setText("Could't find your email");
        }
        return checkEmail;
    }

    private boolean validatePassword() {
        boolean checkPassword = false;
        if (passwordText.getText().isEmpty()) {
            invalidLabel.setText("Enter password");

        } else if (true /* query on password in db*/) {
            passwordText.setText("");
            invalidLabel.setText("");
            checkPassword = true;
        } else {
            invalidLabel.setText("Wrong password");
        }
        return checkPassword;
    }
}
