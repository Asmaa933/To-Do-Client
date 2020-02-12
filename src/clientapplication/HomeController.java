/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapplication;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;

/**
 * FXML Controller class
 *
 * @author ghost
 */
public class HomeController implements Initializable {

    @FXML
    private Button notificationButton;
    @FXML
    private Button makeNewListButton;
    @FXML
    private ListView<String> toDoList;
    @FXML
    private ListView<String> doingList;
    @FXML
    private Button makeNewTaskButton;
    @FXML
    private ListView<String> doneList;
    @FXML
    private ListView<String> friendList;
    @FXML
    private ListView<String> friendRequestList;
    @FXML
    private TextField addFriendText;
    @FXML
    private Button addFriendButton;

    @FXML
    private Button logOutButton;

    @FXML
    private ListView<String> listsList;

    @FXML
    private Tab friendTap;

    @FXML
    private Tab friendRequestTap;

    ObservableList<String> friendRequestObservable = FXCollections.observableArrayList("zeynab", "esma", "mazen", "remon", "ahmed");
    ObservableList<String> listListsObservable = FXCollections.observableArrayList("zeynab", "esma", "mazen", "remon", "ahmed");
    ObservableList<String> FriendListObservable = FXCollections.observableArrayList("zeynab", "esma", "mazen", "remon", "ahmed");
    ObservableList<String> toDoListObservable = FXCollections.observableArrayList("zeynab", "esma", "mazen", "remon", "ahmed");
    ObservableList<String> toDoListObservable2 = FXCollections.observableArrayList("zeynab", "esma", "mazen", "remon", "ahmed");
    ObservableList<String> notificationObservable = FXCollections.observableArrayList("zeynab", "esma", "mazen", "remon", "ahmed");
    ObservableList<String> taskRequestObservable = FXCollections.observableArrayList("zeynab", "esma", "mazen", "remon", "ahmed");

    @FXML
    private Button friendButton;
    @FXML
    private AnchorPane friendAnchor;
    @FXML
    private Button taskRequestButton;
    @FXML
    private AnchorPane notificationAnchor;
    @FXML
    private ListView<String> notificationList;
    @FXML
    private AnchorPane taskRequestAnchor;
    @FXML
    private ListView<String> taskRequestList;

    boolean friendFlag = false;
    boolean notificationFlag = false;
    boolean taskRequestFlag = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listsList.setItems(listListsObservable);
        listsList.setCellFactory(param -> new CellLists());

        friendList.setItems(FriendListObservable);
        friendList.setCellFactory(param -> new Cellfriend());

        friendRequestList.setItems(friendRequestObservable);
        friendRequestList.setCellFactory(param -> new CellFriendRequest());

        toDoList.setItems(toDoListObservable);
        toDoList.setCellFactory(param -> new CellToDO());

        notificationList.setItems(notificationObservable);
        notificationList.setCellFactory(param -> new CellNotification());

        taskRequestList.setItems(taskRequestObservable);
        taskRequestList.setCellFactory(param -> new CellTaskRequest());

    }

    @FXML
    private void makeNewListPressed(ActionEvent event) {
          FXMLLoader fxload = new FXMLLoader(getClass().getResource("ListView.fxml"));
        Parent root;
        try {
            root = (Parent) fxload.load();
            Stage stage = new Stage();
        ListViewController listController = fxload.getController();
            ListModel list = new ListModel();
            //change
            
        list.setList_id(2);
        list.getUser().setName("zeynab");
        list.getUser().setId(1);
        listController.setList(list);

            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
         stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Add List");
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    @FXML
    private void makeNewTaskPressed(ActionEvent event) {
        FXMLLoader fxload = new FXMLLoader(getClass().getResource("TaskView.fxml"));
        Parent root;
        try {
            root = (Parent) fxload.load();
            Stage stage = new Stage();
            TaskViewController reg = (TaskViewController) fxload.getController();
            TaskModel task = new TaskModel();
            reg.setFromLastView(true, task);
            stage.setScene(new Scene(root));
                    stage.setTitle("Add Task");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void addFriendPressed(ActionEvent event) {
    }

    @FXML
    private void logOutPressed(ActionEvent event) {

    }

    @FXML
    private void friendButtonPressed(ActionEvent event) {
        if (friendFlag == false) {
            friendAnchor.setVisible(true);
            friendFlag = true;
        } else {
            friendAnchor.setVisible(false);
            friendFlag = false;
        }
    }

    @FXML
    private void taskRequestPressed(ActionEvent event) {
        if (taskRequestFlag == false) {
            taskRequestAnchor.setVisible(true);
            taskRequestFlag = true;
        } else {
            taskRequestAnchor.setVisible(false);
            taskRequestFlag = false;
        }
    }

    @FXML
    private void notificationPressed(ActionEvent event) {
        if (notificationFlag == false) {
            notificationAnchor.setVisible(true);
            notificationFlag = true;
        } else {
            notificationAnchor.setVisible(false);
            notificationFlag = false;
        }
    }

    static class CellFriendRequest extends ListCell<String> {

        HBox hbox = new HBox();
        Label friendRequestName = new Label();
        Pane pane = new Pane();
        Button reject = new Button("Reject");
        Button accept = new Button("Accept");

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                friendRequestName.setText(item);
                setGraphic(hbox);
            }
        }

        public CellFriendRequest() {
            super();
            hbox.getChildren().addAll(friendRequestName, pane, accept, reject);
            hbox.setHgrow(pane, Priority.ALWAYS);
            reject.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    getListView().getItems().remove(getItem());
                }
            });
            accept.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //accept friend request 
                }
            });
        }

    }

    static class CellLists extends ListCell<String> {

        HBox hbox = new HBox();
        Label listNameLabel = new Label();
        Pane pane = new Pane();
        Button edit = new Button("Edit");

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                listNameLabel.setText(item);
                setGraphic(hbox);
            }
        }

        public CellLists() {
            super();
            hbox.getChildren().addAll(listNameLabel, pane, edit);
            hbox.setHgrow(pane, Priority.ALWAYS);
            edit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //go to list reject scene 
                }
            });
        }

    }

    static class CellNotification extends ListCell<String> {

        HBox hbox = new HBox();
        TextArea textArea = new TextArea();

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                textArea.setText(item);
                setGraphic(hbox);
            }
        }

        public CellNotification() {
            super();
            hbox.getChildren().addAll(textArea);
            textArea.setMaxWidth(223);
            textArea.setMaxHeight(70);
            textArea.setEditable(false);
        }
    }

    static class CellTaskRequest extends ListCell<String> {

        HBox hbox = new HBox();
        TextArea textArea = new TextArea();

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                textArea.setText(item);
                setGraphic(hbox);
            }
        }

        public CellTaskRequest() {
            super();
            hbox.getChildren().addAll(textArea);
            textArea.setMaxWidth(223);
            textArea.setMaxHeight(70);
            textArea.setEditable(false);
        }
    }

    static class Cellfriend extends ListCell<String> {

        HBox hbox = new HBox();
        Label friendNameLabel = new Label();
        Pane pane = new Pane();

        Image profile = new Image(getClass().getResource("/images/profile.png").toExternalForm());
        //Image profile = new Image(getClass().getResourceAsStream("profile.png").toString());
        ImageView image = new ImageView(profile);

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                friendNameLabel.setText(item);
                setGraphic(hbox);
            }
        }

        public Cellfriend() {
            super();
            hbox.getChildren().addAll(friendNameLabel, pane, image);
            hbox.setHgrow(pane, Priority.ALWAYS);

        }

    }

    static class CellToDO extends ListCell<String> {

        VBox vbox = new VBox();
        HBox hbox1 = new HBox();
        Label taskNameLabel = new Label();
        Button delete = new Button("Delete");
        HBox hbox2 = new HBox();
        Label assignToLabel = new Label("Assign To:        ");
        Label assignTotext = new Label();
        HBox hbox3 = new HBox();
        Label deadlineLabel = new Label("Deadline Time: ");
        Label deadlinetext = new Label();
        HBox hbox4 = new HBox();
        Hyperlink readMore = new Hyperlink("Read More");
        HBox hbox5 = new HBox();
        CheckBox move = new CheckBox("Move To The Next");
        Pane pane = new Pane();
        Pane pane2 = new Pane();
        Pane pane3 = new Pane();
        Pane pane4 = new Pane();
        Pane pane5 = new Pane();

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                assignTotext.setText(item);
                setGraphic(vbox);
            }
        }

        public CellToDO() {
            super();
            hbox1.getChildren().addAll(taskNameLabel, pane3, delete);
            hbox2.getChildren().addAll(assignToLabel, assignTotext);
            hbox3.getChildren().addAll(deadlineLabel, deadlinetext);
            hbox4.getChildren().addAll(pane4, readMore, pane);
            hbox5.getChildren().addAll(pane2, move);
            vbox.getChildren().addAll(hbox1, hbox2, hbox3, hbox4, hbox5, pane5);
            hbox4.setHgrow(pane4, Priority.ALWAYS);
            hbox5.setHgrow(pane2, Priority.ALWAYS);
            hbox1.setHgrow(pane3, Priority.ALWAYS);
            hbox4.setHgrow(pane, Priority.ALWAYS);
            vbox.setVgrow(pane5, Priority.ALWAYS);
            taskNameLabel.setText("Title");
            deadlinetext.setText("9/2/2020");
            VBox.setMargin(vbox, new Insets(10, 10, 10, 10));
            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //go to list reject scene 
                }
            });
            readMore.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FXMLLoader fxload = new FXMLLoader(getClass().getResource("TaskView.fxml"));
                    Parent root;
                    try {
                        root = (Parent) fxload.load();
                        Stage stage = new Stage();
                        TaskViewController taskControl = (TaskViewController) fxload.getController();
                        //from db 
                        TaskModel task = new TaskModel(1, "Asmaa", "test", "todo", new Timestamp(2020, 12, 12, 12, 00, 00, 00), 1, 1, new Timestamp(2020, 12, 15, 12, 00, 00, 00), "offline");

                        taskControl.setFromLastView(false, task);
                        stage.setScene(new Scene(root));
                                            stage.setTitle("Task Details");

                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.initModality(Modality.APPLICATION_MODAL);

                        stage.showAndWait();
                    } catch (IOException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
        }
    }

}
