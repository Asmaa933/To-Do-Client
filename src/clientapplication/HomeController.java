/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapplication;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.json.JsonObject;
import model.*;
import network.*;

/**
 * FXML Controller class
 *
 * @author ghost
 */
public class HomeController implements Initializable {

    //FXML
    @FXML
    private Button notificationButton;
    @FXML
    private Button makeNewListButton;
    @FXML
    private ListView<TaskModel> toDoList;
    @FXML
    private ListView<TaskModel> inProgressList;
    @FXML
    private Button makeNewTaskButton;
    @FXML
    private ListView<TaskModel> doneList;
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
    private Tab friendTap;
    @FXML
    private Tab friendRequestTap;
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
    private ListView<TaskModel> taskRequestList;
    @FXML
    private AnchorPane listAnchor;
    @FXML
    private Tab btnMyLists;
    @FXML
    private ListView<String> listOfMyLists;
    @FXML
    private Tab btnCollaborationLists;
    @FXML
    private ListView<String> listOfCollaborationLists;

    //Observable lists
    private ObservableList<String> friendRequestObservable;
    private ObservableList<String> FriendListObservable;
    private ObservableList<TaskModel> toDoListObservable;
    private ObservableList<TaskModel> inProgressListObservable;
    private ObservableList<TaskModel> doneListObservable;
    private ObservableList<String> notificationObservable = FXCollections.observableArrayList("zeynab", "esma", "mazen", "remon", "ahmed");
    private ObservableList<TaskModel> taskRequestObservable = FXCollections.observableArrayList();
    private ObservableList<String> listOfMyListsObservable;
    private ObservableList<String> btnCollaborationListsObservable;

    //Lists
    private ArrayList<ListModel> userLists;
    private ArrayList<ListModel> userCollaborateLists;
    private List<TaskModel> tasks;
    private ArrayList<TaskModel> toDoTasks;
    private ArrayList<TaskModel> inProgressTasks;
    private ArrayList<TaskModel> doneTasks;
    private List<TaskModel> taskRequests;
    private List<UserModel> listOfAllFriends;
    private List<UserModel> listOfAllFriendsRequest;

    private int loginUserID;
    private boolean friendFlag = false;
    private boolean notificationFlag = false;
    private boolean taskRequestFlag = false;
    private int listID;

    public void setLoginUserID(int loginUserID) {
        this.loginUserID = loginUserID;
        setLists(loginUserID);
    }

    private void setLists(int userID) {
        listOfMyListsObservable = FXCollections.observableArrayList();
        btnCollaborationListsObservable = FXCollections.observableArrayList();

        //UserList
        JsonObject requestAllLists = JsonUtil.fromId(JsonConst.TYPE_SELECT_ALL_LIST, userID);
        JsonObject responseAllLists = new RequestHandler().makeRequest(requestAllLists);
        userLists = JsonUtil.toListOfListModels(responseAllLists);

        for (ListModel list : userLists) {
            listOfMyListsObservable.add(list.getTitle());
        }

        //UserCollabortorList
        JsonObject requestAllCollaborateLists = JsonUtil.fromId(JsonConst.TYPE_SELECT_ALL_COLLABORATOR_LIST, userID);
        JsonObject responseAllCollaborateLists = new RequestHandler().makeRequest(requestAllCollaborateLists);
        userCollaborateLists = JsonUtil.toListOfListModels(responseAllCollaborateLists);

        for (ListModel list : userCollaborateLists) {
            btnCollaborationListsObservable.add(list.getTitle());
        }
        listOfMyLists.setItems(listOfMyListsObservable);
        listOfMyLists.setCellFactory(param -> new CellLists());

        listOfCollaborationLists.setItems(btnCollaborationListsObservable);
        listOfCollaborationLists.setCellFactory(param -> new CellLists());

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeNewTaskButton.setDisable(true);

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

            list.getUser().setId(loginUserID);
            JsonObject jsonObject = JsonUtil.fromId(JsonConst.TYPE_SELECT_UESRMODEL, loginUserID);
            JsonObject response = new RequestHandler().makeRequest(jsonObject);
            UserModel userModel = JsonUtil.toUserModel(response);

            list.setUser(userModel);
            listController.setList(list);

            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Add List");

            stage.showAndWait();
            setLists(loginUserID);

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
            task.setList_id(listID);
            reg.setFromLastView(true, task, loginUserID);
            stage.setScene(new Scene(root));
            stage.setTitle("Add Task");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            Platform.runLater(() -> {
                updateTasksLists(listID);
            });
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void addFriendPressed(ActionEvent event) {
    }

    @FXML
    private void logOutPressed(ActionEvent event) {
        //make user offline
        JsonObject request = JsonUtil.fromBoolean(false, loginUserID);
        JsonObject response2 = new RequestHandler().makeRequest(request);
        Parent root;
        try {
            FXMLLoader fxload = new FXMLLoader(getClass().getResource("LoginView.fxml"));
            root = (Parent) fxload.load();
            LoginController login = (LoginController) fxload.getController();
            login.setId(-1);

            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
            window.hide();
            window.show();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void friendButtonPressed(ActionEvent event) {
        if (friendFlag == false) {
            setAllFriends();
            setAllFriendsRequest();
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
            JsonObject request = JsonUtil.getTaskRequests(loginUserID);
            JsonObject response = new RequestHandler().makeRequest(request);
            taskRequests = new ArrayList<>();
            taskRequests = JsonUtil.fromJsonTaskRequests(response);
            taskRequestObservable.addAll(taskRequests);

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

    @FXML
    private void mylistsItemClicked(MouseEvent event) {
        makeNewTaskButton.setDisable(false);
        int selectedindex = listOfMyLists.getSelectionModel().getSelectedIndex();
        listID = userLists.get(selectedindex).getList_id();
        updateTasksLists(listID);
    }

    @FXML
    private void myCollobaratelistsItemClicked(MouseEvent event) {
        makeNewTaskButton.setDisable(false);
        int selectedindex = listOfCollaborationLists.getSelectionModel().getSelectedIndex();
        listID = userCollaborateLists.get(selectedindex).getList_id();
        updateTasksLists(listID);
    }

    public void updateTasksLists(int listID) {
        tasks = new ArrayList<>();
        toDoTasks = new ArrayList<>();
        inProgressTasks = new ArrayList<>();
        doneTasks = new ArrayList<>();
        toDoListObservable = FXCollections.observableArrayList();
        inProgressListObservable = FXCollections.observableArrayList();
        doneListObservable = FXCollections.observableArrayList();
        new Thread(new Runnable() {
            @Override
            public void run() {
                JsonObject request = JsonUtil.getTasks(listID);
                JsonObject response = new RequestHandler().makeRequest(request);
                tasks = JsonUtil.fromJsonTasksList(response);
                for (TaskModel task : tasks) {
                    switch (task.getTask_status()) {
                        case TaskModel.TASK_STATUS.TODO:
                            toDoTasks.add(task);
                            break;
                        case TaskModel.TASK_STATUS.INPROGRESS:
                            inProgressTasks.add(task);
                            break;
                        case TaskModel.TASK_STATUS.DONE:
                            doneTasks.add(task);
                            break;
                    }
                }
                Platform.runLater(() -> {

                    toDoListObservable.setAll(toDoTasks);
                    toDoList.setItems(toDoListObservable);
                    toDoList.setCellFactory(param -> new CellTask());

                    inProgressListObservable.setAll(inProgressTasks);
                    inProgressList.setItems(inProgressListObservable);
                    inProgressList.setCellFactory(param -> new CellTask());

                    doneListObservable.setAll(doneTasks);
                    doneList.setItems(doneListObservable);
                    doneList.setCellFactory(param -> new CellTask());

                });
            }
        }).start();
    }

    class CellFriendRequest extends ListCell<String> {

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
                    updateFriendRequest(TeammateModel.TEAMMATE_STATUS.REJECTED);

                }
            });
            accept.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    updateFriendRequest(TeammateModel.TEAMMATE_STATUS.ACCEPTED);
                }
            });
        }

        private void updateFriendRequest(String type) {
            int uset2_ID = listOfAllFriendsRequest.get(getIndex()).getId();
            TeammateModel teammateModel = new TeammateModel(loginUserID, uset2_ID, type);

            accept.setDisable(true);
            reject.setDisable(true);

            new Thread(() -> {
                JsonObject jsonObject = JsonUtil.fromTeammateModel(teammateModel, JsonConst.TYPE_FRIENDS_REQUEST_UPDATE);
                JsonObject response = new RequestHandler().makeRequest(jsonObject);
                boolean changeflag = JsonUtil.convertFromJsonPasswordResponse(response);

                Platform.runLater(() -> {
                    if (!changeflag) {
                        accept.setDisable(false);
                        reject.setDisable(false);
                    } else {
                        listOfAllFriendsRequest.remove(getIndex());
                        friendRequestObservable.remove(getIndex());
                        setAllFriends();
                    }
                });
            }).start();
        }

    }

    class CellLists extends ListCell<String> {

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
                    // ((Node) event.getSource()).getScene().getWindow().hide();

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListView.fxml"));
                        Parent root = loader.load();
                        ListViewController listController = loader.getController();

                        if (getListView().equals(listOfMyLists)) {
                            listController.setList(userLists.get(getIndex()));
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setResizable(false);
                            stage.setScene(scene);
                            stage.setTitle("Edit List");
                            stage.showAndWait();
                            setLists(loginUserID);

                        } else {
                            //listController.setList(userCollaborateLists.get(getIndex()));
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setTitle("Access Denied");
                            alert.setContentText("You haven't permission to edit");
                            alert.showAndWait();
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }

    }

    class CellNotification extends ListCell<String> {

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

    class Cellfriend extends ListCell<String> {

        HBox hbox = new HBox();
        Label friendNameLabel = new Label();
        Pane pane = new Pane();

        Image profile = new Image(getClass().getResource("/images/profile.png").toExternalForm());
        ImageView image = new ImageView(profile);

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                friendNameLabel.setText(item);
                if (listOfAllFriends.get(getIndex()).getOnline_status().equals(UserModel.ONLINE_STATUS.ONLINE)) {
                    image = new ImageView(new Image(getClass().getResource("/images/online.png").toExternalForm()));
                } else if (listOfAllFriends.get(getIndex()).getOnline_status().equals(UserModel.ONLINE_STATUS.OFFLINE)) {
                    image = new ImageView(new Image(getClass().getResource("/images/offline.png").toExternalForm()));
                }
                hbox.getChildren().add(image);
                setGraphic(hbox);
            }
        }

        public Cellfriend() {
            super();
            hbox.getChildren().addAll(friendNameLabel, pane);
            hbox.setHgrow(pane, Priority.ALWAYS);

        }

    }

    class CellTask extends ListCell<TaskModel> {

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
        Pane pane = new Pane();
        Pane pane2 = new Pane();
        Pane pane3 = new Pane();
        Pane pane4 = new Pane();
        private TaskModel selectedTask;

        @Override
        protected void updateItem(TaskModel item, boolean empty) {
            selectedTask = item;
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                taskNameLabel.setText(item.getTitle());
                assignTotext.setText(item.getUser_name());
                deadlineLabel.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(item.getDeadline()));
                setGraphic(vbox);
            }
        }

        public CellTask() {
            super();
            hbox1.getChildren().addAll(taskNameLabel, pane3, delete);
            hbox2.getChildren().addAll(assignToLabel, assignTotext);
            hbox3.getChildren().addAll(deadlineLabel, deadlinetext);
            hbox4.getChildren().addAll(pane4, readMore, pane);
            vbox.getChildren().addAll(hbox1, hbox2, hbox3, hbox4);
            hbox4.setHgrow(pane4, Priority.ALWAYS);
            hbox1.setHgrow(pane3, Priority.ALWAYS);
            hbox4.setHgrow(pane, Priority.ALWAYS);
            VBox.setMargin(vbox, new Insets(10, 10, 10, 10));

            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete List");
                    alert.setHeaderText(null);
                    alert.setContentText("Do you want to delete this Task ");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println(selectedTask.getTask_id());
                                JsonObject request = JsonUtil.fromTaskId(selectedTask.getTask_id(), JsonConst.TYPE_DELETE_TASK_REQUEST);
                                new RequestHandler().makeRequest(request);
                                Platform.runLater(() -> {
                                    updateTasksLists(listID);
                                });
                            }
                        }).start();
                    } else if (result.get() == ButtonType.CANCEL) {
                        alert.close();
                    }

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
                        TaskModel task = selectedTask;
                        taskControl.setFromLastView(false, task, loginUserID);
                        stage.setScene(new Scene(root));
                        stage.setTitle("Task Details");

                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.initModality(Modality.APPLICATION_MODAL);

                        stage.showAndWait();
                        Platform.runLater(() -> {
                            updateTasksLists(listID);
                        });
                    } catch (IOException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
        }
    }

    class CellTaskRequest extends ListCell<TaskModel> {

        VBox vbox = new VBox();
        HBox hbox1 = new HBox();
        Label taskNameLabel = new Label("Task                 :");
        Label taskNametext = new Label();
        HBox hbox2 = new HBox();
        Button reject = new Button("Reject");
        Button accept = new Button("Accept");
        Label assignToLabel = new Label("Assign Date     :          ");
        Label assignTotext = new Label();
        HBox hbox3 = new HBox();
        Label deadlineLabel = new Label("Deadline Time : ");
        Label deadlinetext = new Label();
        HBox hbox4 = new HBox();
        Pane pane = new Pane();
        Pane pane2 = new Pane();
        Pane pane3 = new Pane();
        Pane pane4 = new Pane();
        Pane pane5 = new Pane();
        Pane pane6 = new Pane();
        Pane pane7 = new Pane();
        HBox hbox5 = new HBox();

        @Override
        protected void updateItem(TaskModel item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                assignTotext.setText(item.getTitle());
                setGraphic(vbox);
            }
        }

        public CellTaskRequest() {
            super();
            hbox1.getChildren().addAll(taskNameLabel, taskNametext, pane7);
            hbox2.getChildren().addAll(assignToLabel, assignTotext);
            hbox3.getChildren().addAll(deadlineLabel, deadlinetext);
            hbox4.getChildren().addAll(pane2, accept, pane, reject, pane4);
            hbox5.getChildren().addAll(pane3);
            vbox.getChildren().addAll(hbox1, hbox2, hbox3, hbox4, pane5);
            deadlinetext.setText("9/2/2020");
            taskNametext.setText("TO DO");

            hbox1.setHgrow(pane7, Priority.ALWAYS);
            hbox4.setHgrow(pane4, Priority.ALWAYS);
            hbox4.setHgrow(pane, Priority.ALWAYS);
            hbox4.setHgrow(pane2, Priority.ALWAYS);
            hbox5.setHgrow(pane3, Priority.ALWAYS);
            vbox.setVgrow(pane5, Priority.ALWAYS);
            VBox.setMargin(vbox, new Insets(10, 10, 10, 10));
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

    public void setAllFriends() {
        FriendListObservable = FXCollections.observableArrayList();
        friendList.setItems(FriendListObservable);
        friendList.setCellFactory(param -> new Cellfriend());

        new Thread(() -> {
            // get friend
            JsonObject jsonObject = JsonUtil.fromId(JsonConst.TYPE_GET_ALL_FRIENDS, loginUserID);
            JsonObject response = new RequestHandler().makeRequest(jsonObject);
            listOfAllFriends = JsonUtil.toUsersList(response);

            Platform.runLater(() -> {
                for (UserModel firend : listOfAllFriends) {
                    FriendListObservable.add(firend.getName());
                }
            });
        }).start();
    }

    public void setAllFriendsRequest() {
        friendRequestObservable = FXCollections.observableArrayList();
        friendRequestList.setItems(friendRequestObservable);
        friendRequestList.setCellFactory(param -> new CellFriendRequest());

        new Thread(() -> {
            //get all friend request
            JsonObject jsonObject = JsonUtil.fromId(JsonConst.TYPE_GET_ALL_FRIENDS_REQUEST, loginUserID);
            JsonObject response = new RequestHandler().makeRequest(jsonObject);
            listOfAllFriendsRequest = JsonUtil.toUsersList(response);

            Platform.runLater(() -> {
                for (UserModel firendrequest : listOfAllFriendsRequest) {
                    friendRequestObservable.add(firendrequest.getName());
                }
            });
        }).start();
    }

}
