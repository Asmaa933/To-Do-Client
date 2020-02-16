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
import java.util.Optional;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Button nextBtn;
    private boolean flagEmail = false;
    private int id = -1;
    ActionEvent eventSource = null;
    @FXML
    private AnchorPane loginPane2;
    @FXML
    private Pane loginPane1;
    @FXML
    private Button closeButton;
    @FXML
    private ImageView imagePassword;
    @FXML
    private ImageView imageLogin;

    public void setId(int id) {
        this.id = id;
    }

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
        Parent registerScene;
        try {
            registerScene = FXMLLoader.load(getClass().getResource("RegisterView.fxml"));
            Scene registerViewScene = new Scene(registerScene);

            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(registerViewScene);
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
        System.exit(0);
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

    @FXML
    private void settingsButtonPressed(ActionEvent event) {
        boolean ipRegixFlag = false;
        TextInputDialog dialog = new TextInputDialog(RequestHandler.getSTATIC_IP());
        dialog.setTitle("Change Server IP");
        dialog.setHeaderText("");
        dialog.setContentText("Enter server ip:");
        do {
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                if (RegixMethods.isValidIP(result.get())) {
                    RequestHandler.setSTATIC_IP(result.get());
                    ipRegixFlag = true;
                    dialog.setHeaderText("");

                } else {
                    dialog.setHeaderText("Enter valid ip");
                    ipRegixFlag = false;

                }

            } else {
                dialog.close();
                ipRegixFlag = true;
            }
        } while (!ipRegixFlag);
    }

    private void validateEmail() {
        if (emailText.getText().isEmpty()) {
            invalidLabel.setText("Enter email");
        } else if (RegixMethods.isValidEmail(emailText.getText())) {
            nextBtn.setText("");
            nextBtn.setDisable(true);
            progressIndicator.setVisible(true);
            new Thread(() -> {
                JsonObject jsonObject = JsonUtil.createJsonEmail(emailText.getText());
                JsonObject response = new RequestHandler().makeRequest(jsonObject);
                id = JsonUtil.convertFromJsonId(response);

                Platform.runLater(() -> {
                    nextBtn.setText("Next");
                    nextBtn.setDisable(false);
                    progressIndicator.setVisible(false);
                    if (id != -1) {
                        emailText.setVisible(false);
                        passwordText.setVisible(true);
                        imagePassword.setVisible(true);
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
            nextBtn.setText("");
            nextBtn.setDisable(true);
            progressIndicator.setVisible(true);
            new Thread(() -> {
                JsonObject jsonObject = JsonUtil.createJsonPassword(passwordText.getText(), id);
                JsonObject response = new RequestHandler().makeRequest(jsonObject);
                boolean passflag = JsonUtil.convertFromJsonPasswordResponse(response);
//change status
                JsonObject request = JsonUtil.fromBoolean(passflag, id);
                JsonObject response2 = new RequestHandler().makeRequest(request);
                if (passflag == true) {

                    Platform.runLater(() -> {
                        passwordText.setText("");
                        invalidLabel.setText("");
                        Parent root;
                        try {
                            FXMLLoader fxload = new FXMLLoader(getClass().getResource("HomeView.fxml"));
                            root = (Parent) fxload.load();
                            HomeController home = (HomeController) fxload.getController();
                            home.setLoginUserID(id);
                            Stage window = (Stage) ((Node) eventSource.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root);
                            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                            window.setScene(scene);
                            window.hide();
                            window.show();
                        } catch (IOException ex) {
                            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                } else {
                    Platform.runLater(() -> {
                        nextBtn.setText("Next");
                        nextBtn.setDisable(false);
                        progressIndicator.setVisible(false);
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
