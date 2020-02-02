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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.json.JsonObject;
import network.JsonUtil;
import network.RequestHandler;

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

    private int id = -1;
    ActionEvent eventSource = null;

    /**
     *
     * @param event go to password if email was valid
     */
    @FXML
    private void nextButtonPressed(ActionEvent event) {
        eventSource = event;

        if (flagEmail == false) {
            validateEmail();
        } else {
            validatePassword();
        }
    }

    /**
     *
     * @param event go to register scene
     */
    @FXML
    private void newAccountPressed(ActionEvent event) {
        Parent tableViewParent;
        try {
            tableViewParent = FXMLLoader.load(getClass().getResource("RegisterView.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
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

    private void validateEmail() {
        if (emailText.getText().isEmpty()) {
            invalidLabel.setText("Enter email");
        } else if (RegixMethods.isValidEmail(emailText.getText())) {

            new Thread(() -> {
                JsonObject jsonObject = JsonUtil.createJsonEmail(emailText.getText());
                JsonObject response = new RequestHandler().makeRequest(jsonObject);
                System.out.println(response);
                id = JsonUtil.convertFromJsonId(response);
                
                Platform.runLater(() -> {
                    if (id != -1) {
                        emailText.setVisible(false);
                        passwordText.setVisible(true);
                        notYouHyperLink.setVisible(true);
                        
                        flagEmail = true;
                        invalidLabel.setText("");
                        passwordText.setText("");
                    } else {
                        invalidLabel.setText("Could't find your email");
                    }
                });
  
            }).start();

        } else {
            Platform.runLater(() -> {
                invalidLabel.setText("Enter valid email");
            });   
        }
        
    }

    
    private void validatePassword() {
        if (passwordText.getText().isEmpty()) {
            invalidLabel.setText("Enter password");

        } else if (RegixMethods.isValidPassword(passwordText.getText())) {

            new Thread(() -> {
                JsonObject jsonObject = JsonUtil.createJsonPassword(passwordText.getText(), id);
                JsonObject response = new RequestHandler().makeRequest(jsonObject);
                boolean passflag = JsonUtil.convertFromJsonPasswordResponse(response);

                if (passflag == true) {

                    Platform.runLater(() -> {

                        passwordText.setText("");
                        invalidLabel.setText("");

                        //   go to home page
                        //  pass id to home
                        ((Node) eventSource.getSource()).getScene().getWindow().hide();
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

                    });

                } else {
                    Platform.runLater(() -> {
                        invalidLabel.setText("Wrong password");
                    });
                }
            }).start();

        } else {
            Platform.runLater(() -> {
                invalidLabel.setText("Enter valid password");
            });
        }

    }

}
