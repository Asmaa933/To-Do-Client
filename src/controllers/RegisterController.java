/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

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

/**
 *
 * @author AhmedWagdy
 */
public class RegisterController implements Initializable {

    @FXML
    private Button closeButton;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    }

}
