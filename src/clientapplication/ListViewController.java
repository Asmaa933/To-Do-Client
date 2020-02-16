/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapplication;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.json.JsonObject;
import model.*;
import network.*;

/**
 *
 * @author ghost color & date....
 */
public class ListViewController implements Initializable {

    @FXML
    private TextField titleText;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField dateText;
    @FXML
    private Button addButton;
    @FXML
    private ListView<String> collaborateListView;
    @FXML
    private Button saveButton;
    @FXML
    private TextField OwnerNameText;
    @FXML
    private Button deleteButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<String> teammatesCombobox;
     @FXML
    private ProgressIndicator ProgressIndicator;
    @FXML
    private ProgressIndicator ProgressIndicator2;

    ObservableList<String> teammatesList = FXCollections.observableArrayList();
    ObservableList<String> collaborateList = FXCollections.observableArrayList();

    private ListModel list;
    private List<UserModel> teammates;
    private List<UserModel> collaborators;
    private boolean editFlag;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        teammates = new ArrayList<>(); 
        collaborators = new ArrayList<>();  
    }

    public void setList(ListModel listModel) {
        this.list = listModel;

        if (list.getList_id() == -1) {
            editFlag = false;
            deleteButton.setVisible(false);
        } else {
            editFlag = true;
        }

        dateText.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(list.getCreate_date()));
        OwnerNameText.setText(list.getUser().getName());
        titleText.setText(list.getTitle());
        colorPicker.setValue(Color.web(list.getColor()));

        new Thread(() -> {
            // get teammates
            JsonObject jsonObject = JsonUtil.fromId(JsonConst.TYPE_GET_ALL_FRIENDS, list.getUser().getId());
            JsonObject response = new RequestHandler().makeRequest(jsonObject);
            teammates = JsonUtil.toUsersList(response);

            if (editFlag == true) {
                // get collaborator
                jsonObject = JsonUtil.fromId(JsonConst.TYPE_COLLABORATOR_LIST, list.getList_id());
                response = new RequestHandler().makeRequest(jsonObject);
                collaborators = JsonUtil.toUsersList(response);
            }
            Platform.runLater(() -> {
                // set teammates
                for (UserModel teammate : teammates) {
                    teammatesList.add(teammate.getName());
                }
                teammatesCombobox.setItems(teammatesList);

                if (editFlag == true) {
                    //set collaborator
                    for (UserModel collaborator : collaborators) {
                        collaborateList.add(collaborator.getName());
                    }
                    collaborateListView.setItems(collaborateList);
                    collaborateListView.setCellFactory(param -> new Cell());
                }

            });
        }).start();

    }
     @FXML 
    private void addButtonPressed(ActionEvent event) {

        boolean selectedFlag = false;
        int selectedIndex = teammatesCombobox.getSelectionModel().getSelectedIndex();

        if (list.getList_id() != -1) {
            if (selectedIndex != -1) {
                ProgressIndicator2.setVisible(true);
                UserModel selectedUser = teammates.get(selectedIndex);
                for (UserModel collaborator : collaborators) {
                    if (collaborator.getId() == selectedUser.getId()) {
                        selectedFlag = true;

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Add Collaborator");
                        alert.setContentText("This teammate is already added in this list !!");
                        alert.showAndWait();
                    }
                }
                if (selectedFlag == false) {
                    new Thread(() -> {
                        CollaboratorModel collaboratorModel = new CollaboratorModel(list.getList_id(), selectedUser.getId());
                        JsonObject jsonObject = JsonUtil.fromCollaborator(JsonConst.TYPE_ADD_COLLABORATOR, collaboratorModel);
                        JsonObject response = new RequestHandler().makeRequest(jsonObject);
                        boolean insertFlag = JsonUtil.convertFromJsonPasswordResponse(response); // change name of method    
                        if (insertFlag) {
                            Platform.runLater(() -> {
                                collaborators.add(selectedUser);
                                collaborateList.add(selectedUser.getName());
                                collaborateListView.setItems(collaborateList);
                                collaborateListView.setCellFactory(param -> new Cell());
                            });
                        }
                        //else// alert show wrong in insert in DB

                        Platform.runLater(() -> {
                            ProgressIndicator2.setVisible(false);
                            addButton.setDisable(false);
                        });
                    }).start();
                }else{
                    ProgressIndicator2.setVisible(false);
                }
            }
            
        } else {
            ProgressIndicator2.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Add Collaborator");
            alert.setContentText("Please save the list first, then add Collaborators ");
            alert.showAndWait();
        }
    }
    @FXML
    private void saveButtonPressed(ActionEvent event) {
        saveButton.setDisable(true);
        
        if (!titleText.getText().trim().isEmpty()) {
            ProgressIndicator.setVisible(true);
            list.setTitle(titleText.getText());
            list.setColor(colorPicker.getValue().toString());

            new Thread(() -> {
                if (editFlag == false) {
                    JsonObject jsonObject = JsonUtil.fromList(JsonConst.TYPE_INSERT_LIST, list);
                    JsonObject response = new RequestHandler().makeRequest(jsonObject);
                    list.setList_id(JsonUtil.convertFromJsonId(response));
                } else {
                    JsonObject jsonObject = JsonUtil.fromList(JsonConst.TYPE_UPDATE_LIST, list);
                    JsonObject response = new RequestHandler().makeRequest(jsonObject);
                    int id = JsonUtil.convertFromJsonId(response);
                    if (list.getList_id() != id || id == -1) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Update List");
                        alert.setContentText("List didn't updated, please try again ");
                        alert.showAndWait();
                    }
                }
                Platform.runLater(() -> {
                    ProgressIndicator.setVisible(false);
                    saveButton.setDisable(false);
                });
            }).start();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Save List");
            alert.setContentText("Please enter list name ");
            alert.showAndWait();
        }
    }

    @FXML
    private void CancelButtonPressed(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void deleteButtonPressed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete List");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to delete this List !? this will delete the List with it's tasks and your collaborator can't find Them ? ");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ...Function Query to delete the List
        } else if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }
    class Cell extends ListCell<String> {

        HBox hbox = new HBox();
        Label userName = new Label();
        Pane pane = new Pane();
        Button delete = new Button("Delete");

        public Cell() {
            super();
            hbox.getChildren().addAll(userName, pane, delete);
            hbox.setHgrow(pane, Priority.ALWAYS);

            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Delete Collaborator");
                    alert.setContentText("Do you want to delete this Collaborator");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        int cellIndex = getIndex();
                        CollaboratorModel collaborator = new CollaboratorModel(list.getList_id(), collaborators.get(cellIndex).getId());
                        new Thread(() -> {
                            JsonObject jsonObject = JsonUtil.fromCollaborator(JsonConst.TYPE_REMOVE_COLLABORATOR, collaborator);
                            JsonObject response = new RequestHandler().makeRequest(jsonObject);
                            boolean deleteFlag = JsonUtil.convertFromJsonPasswordResponse(response); // change name of method
                            if (deleteFlag) {
                                Platform.runLater(() -> {
                                    collaborators.remove(cellIndex);
                                    collaborateList.remove(cellIndex);
                                    collaborateListView.refresh(); 
                                });
                            }
                        }).start();
                    } else if (result.get() == ButtonType.CANCEL) {
                        alert.close();
                    }
                }
            });
        }
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty); 
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                userName.setText(item);
                setGraphic(hbox);
            }
        }
    }

   

}
