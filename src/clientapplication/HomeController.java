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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
    private ObservableList<String> notificationObservable;
    private ObservableList<TaskModel> taskRequestObservable;
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
    private List<String> listOfNotifications;

    //variables
    private int loginUserID;
    private boolean friendFlag = false;
    private boolean notificationFlag = false;
    private boolean taskRequestFlag = false;
    private int listID;
    private boolean isCollaborator = false;
    @FXML
    private Button graphButton;
    @FXML
    private TabPane listsTapPane;
    @FXML
    private Pane pane2;
    @FXML
    private Pane pane1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeNewTaskButton.setDisable(true);
        notificationAnchor.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                notificationAnchor.setVisible(false);
            }
        });

        taskRequestAnchor.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                taskRequestAnchor.setVisible(false);
            }
        });

        friendAnchor.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                friendAnchor.setVisible(false);
            }
        });

    }

    //FXML Functions
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
            Scene scene = new Scene(root);
            stage.getIcons().add(new Image("/images/logo1.png"));
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
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
            TaskViewController taskController = (TaskViewController) fxload.getController();
            TaskModel task = new TaskModel();
            task.setList_id(listID);
            taskController.setFromLastView(true, task, loginUserID, isCollaborator);
            Scene scene = new Scene(root);
            stage.getIcons().add(new Image("/images/logo1.png"));
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
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
    private void mylistsItemClicked(MouseEvent event) {
        isCollaborator = false;
        makeNewTaskButton.setDisable(false);
        int selectedindex = listOfMyLists.getSelectionModel().getSelectedIndex();
        listID = userLists.get(selectedindex).getList_id();
        String color = userLists.get(selectedindex).getColor();
        color = "#" + color.substring(2, 8);
        pane1.setStyle("-fx-background-color:" + color + ";");
        pane2.setStyle("-fx-background-color:" + color + ";");
        updateTasksLists(listID);

    }

    @FXML
    private void myCollobaratelistsItemClicked(MouseEvent event) {
        isCollaborator = true;
        makeNewTaskButton.setDisable(true);
        int selectedindex = listOfCollaborationLists.getSelectionModel().getSelectedIndex();
        listID = userCollaborateLists.get(selectedindex).getList_id();
        String color = userLists.get(selectedindex).getColor();
        color = "#" + color.substring(2, 8);
        pane1.setStyle("-fx-background-color:" + color + ";");
        pane2.setStyle("-fx-background-color:" + color + ";");
        updateTasksLists(listID);
    }

    @FXML
    private void addFriendPressed(ActionEvent event) {
        if (addFriendText.getText().trim().isEmpty()) {

        } else {
            String recieverEmail = addFriendText.getText().trim();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    JsonObject addFriendRequest = JsonUtil.toJsonAddFriend(loginUserID, recieverEmail);
                    JsonObject addFriendResponse = new RequestHandler().makeRequest(addFriendRequest);
                    boolean isRequestSent = JsonUtil.toBoolean(addFriendResponse);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (!isRequestSent) {
                                addFriendText.setText("");
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Can't add friend!");
                                alert.setTitle("Can't add friend!");
                                alert.setContentText("the email you are trying to add either doesn't exsist or is already in your friend list");
                                alert.showAndWait();
                            } else {
                                addFriendText.setText("");
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Friend Request is on the way!");
                                alert.setTitle("Friend Request is on the way!");
                                alert.setContentText("your friend has recived the friend request. pending acceptance...");
                                alert.showAndWait();
                            }
                        }
                    });

                }
            }).start();
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
        taskRequests = new ArrayList<>();
        taskRequestObservable = FXCollections.observableArrayList();
        if (taskRequestFlag == false) {
            taskRequestAnchor.setVisible(true);
            taskRequestFlag = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    JsonObject request = JsonUtil.getTaskRequests(loginUserID);
                    JsonObject response = new RequestHandler().makeRequest(request);
                    taskRequests = JsonUtil.fromJsonTaskRequests(response);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            taskRequestObservable.addAll(taskRequests);
                            taskRequestList.setItems(taskRequestObservable);
                            taskRequestList.setCellFactory(param -> new CellTaskRequest());
                        }
                    });
                }
            }).start();

        } else {
            taskRequestAnchor.setVisible(false);
            taskRequestFlag = false;
        }
    }

    @FXML
    private void notificationPressed(ActionEvent event) {
        setNotifications();
        if (notificationFlag == false) {
            notificationAnchor.setVisible(true);
            notificationFlag = true;
        } else {
            notificationAnchor.setVisible(false);
            notificationFlag = false;
        }
    }

    @FXML
    private void logOutPressed(ActionEvent event) {
        //make user offline
        JsonObject request = JsonUtil.fromBoolean(false, loginUserID);
        new RequestHandler().makeRequest(request);
        Parent root;
        try {
            FXMLLoader fxload = new FXMLLoader(getClass().getResource("LoginView.fxml"));
            root = (Parent) fxload.load();
            LoginController login = (LoginController) fxload.getController();
            login.setId(-1);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            window.getIcons().add(new Image("/images/logo1.png"));
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            window.setScene(scene);
            window.hide();
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void closeButtonPressed(ActionEvent event) {
        JsonObject request = JsonUtil.fromBoolean(false, loginUserID);
        new RequestHandler().makeRequest(request);
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void btnChartPressed(ActionEvent event) {
        
JsonObject request = JsonUtil.fromStats(loginUserID);
        JsonObject response = new RequestHandler().makeRequest(request);
        int allLists = response.getInt("all_lists");
        int allTasks = response.getInt("all_tasks");
        int todoTasks = response.getInt("todo_tasks");
        int inprogressTasks = response.getInt("in_progress_tasks");
        int doneTasks = response.getInt("done_tasks");
        ClientChart.setInputs(allLists, allTasks, todoTasks, inprogressTasks, doneTasks);

        Stage stageOld = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        try {
            ClientChart clientChart = new ClientChart();
            clientChart.start(window);
        } catch (Exception ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //setters
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

    private void updateTasksLists(int listID) {
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

    private void setAllFriends() {
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

    private void setAllFriendsRequest() {
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

    private void setNotifications() {
        notificationObservable = FXCollections.observableArrayList();
        notificationList.setItems(notificationObservable);
        notificationList.setCellFactory(param -> new CellNotification());

        new Thread(() -> {
            //get all notifiaction
            JsonObject jsonObject = JsonUtil.fromId(JsonConst.TYPE_GET_NOTIFICATION, loginUserID);
            JsonObject response = new RequestHandler().makeRequest(jsonObject);
            listOfNotifications = JsonUtil.toNotificationAsString(response);

            Platform.runLater(() -> {
                for (String notif : listOfNotifications) {
                    notificationObservable.add(notif);
                }
            });
        }).start();
    }

    //Cell classes
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
            friendRequestName.setId("lbl2");
            accept.setId("save1");
            reject.setId("save");
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

                if (getListView().equals(listOfMyLists)) {
                    String color = userLists.get(getIndex()).getColor();
                    color = "#" + color.substring(2, 8);
                    edit.setStyle("-fx-background-color:" + color + ";");
                } else if (getListView().equals(listOfCollaborationLists)) {
                    String color = userLists.get(getIndex()).getColor();
                    color = "#" + color.substring(2, 8);
                    edit.setStyle("-fx-background-color:" + color + ";");
                }
                setGraphic(hbox);

            }
        }

        public CellLists() {
            super();
            hbox.getChildren().addAll(listNameLabel, pane, edit);
            hbox.setHgrow(pane, Priority.ALWAYS);
            edit.setId("nextBtn");
            listNameLabel.setId("lbl2");

            edit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListView.fxml"));
                        Parent root = loader.load();
                        ListViewController listController = loader.getController();

                        if (getListView().equals(listOfMyLists)) {
                            listController.setList(userLists.get(getIndex()));
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.setResizable(false);
                            stage.setScene(scene);
                            stage.setTitle("Edit List");
                                    stage.getIcons().add(new Image("/images/logo1.png"));

                            stage.showAndWait();
                           
                            setLists(loginUserID);

                        } else {
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
            textArea.setMaxWidth(198);
            textArea.setMaxHeight(70);
            textArea.setWrapText(true);
            textArea.setStyle("-fx-font-weight:bold;");
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
                hbox.getChildren().clear();
                hbox.getChildren().addAll(friendNameLabel, pane, image);
                setGraphic(hbox);
            }
        }

        public Cellfriend() {
            super();

            //hbox.getChildren().addAll(friendNameLabel, pane);
            hbox.setHgrow(pane, Priority.ALWAYS);
            friendNameLabel.setId("lbl2");

        }

    }

    class CellTask extends ListCell<TaskModel> {

        VBox vbox = new VBox();
        HBox hbox1 = new HBox();
        Label taskNameLabel = new Label();
        Button delete = new Button("Delete");
        HBox hbox2 = new HBox();
        Label assignToLabel = new Label("Assign To: ");
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
                assignTotext.setText(item.getUser_name() + "     (" + item.getAssign_status() + ")");
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
            taskNameLabel.setId("lbl1");
            assignToLabel.setId("lbl3");
            assignTotext.setId("lbl");
            deadlineLabel.setId("lbl3");
            deadlinetext.setId("lbl");
            delete.setId("save");

            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (!isCollaborator) {
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
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Access Denied");
                        alert.setContentText("You haven't permission to delete task");
                        alert.showAndWait();

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
                        taskControl.setFromLastView(false, task, loginUserID, isCollaborator);
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

        private int currentTaskId;
        VBox vbox = new VBox();
        HBox hbox1 = new HBox();
        Label taskNameLabel = new Label("Task                 : ");

        Label taskNametext = new Label();
        HBox hbox2 = new HBox();
        Button reject = new Button("Reject");
        Button accept = new Button("Accept");
        Label assignToLabel = new Label("Assign Date     : ");
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
                taskNametext.setText(item.getTitle());
                this.currentTaskId = item.getTask_id();
                assignTotext.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(item.getAssign_date()));
                deadlinetext.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(item.getDeadline()));
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
            taskNameLabel.setId("lbl3");
            taskNametext.setId("lbl");
            assignToLabel.setId("lbl3");
            assignTotext.setId("lbl");
            deadlineLabel.setId("lbl3");
            deadlinetext.setId("lbl");
            accept.setId("save1");
            reject.setId("save");
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
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JsonObject jRejectRequest = JsonUtil.fromId(JsonConst.TYPE_REJECT_TASK_REQUEST, currentTaskId);
                            JsonObject jRejectResponse = new RequestHandler().makeRequest(jRejectRequest);
                        }
                    }).start();
                    getListView().getItems().remove(getItem());
                }
            });
            accept.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //accept friend request
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JsonObject jAcceptRequest = JsonUtil.fromId(JsonConst.TYPE_ACCEPT_TASK_REQUEST, currentTaskId);
                            JsonObject jAcceptResponse = new RequestHandler().makeRequest(jAcceptRequest);
                        }
                    }).start();
                    getListView().getItems().remove(getItem());

                }
            });

        }
    }

}
