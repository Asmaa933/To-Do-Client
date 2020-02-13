/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import model.*;

/**
 *
 * @author remon
 */
public class JsonUtil {

    /**
     * takes an email and converts it to JSON
     * @param email to be converted to JSON
     * @return the JSON object from the email
     */
    public static JsonObject createJsonEmail(String email) {
        JsonObject obj = Json.createObjectBuilder()
                .add(JsonConst.TYPE, JsonConst.TYPE_EMAIL_SIGNIN_REQUEST)
                .add(JsonConst.EMAIL, email)
                .build();
        return obj;
    }
    
    public static int convertFromJsonId(JsonObject obj){
        return obj.getInt(JsonConst.ID);
    }
    
    /**
     * takes an password and converts it to JSON
     * @param password to be converted to JSON
     * @return the JSON object from the password
     */
    public static JsonObject createJsonPassword(String password, int id) {
        JsonObject obj = Json.createObjectBuilder()
                .add(JsonConst.TYPE, JsonConst.TYPE_PASSWORD_SIGNIN_REQUEST)
                .add(JsonConst.PASSWORD, password)
                .add(JsonConst.ID, id)
                .build();
        return obj;
    }
    
    // ٍchange name to -- parseBoolean //ToJsonObject  // as using in Sign up
    public static boolean convertFromJsonPasswordResponse(JsonObject obj){
        return obj.getBoolean(JsonConst.TYPE_PASSWORD_SIGNIN_RESPONSE); //TypeParseBoolean
    }
    
    public static JsonObject convertToJsonUser(UserModel user){
        JsonObject obj = Json.createObjectBuilder()
                .add(JsonConst.TYPE, JsonConst.TYPE_SIGNUP_REQUEST)
                .add("id", user.getId())
                .add("name", user.getName())
                .add("email", user.getEmail())
                .add("password", user.getPassword())
                .build();
        return obj;
    }
    
    public static JsonObject fromId(String type, int id) {
        JsonObject obj = Json.createObjectBuilder()
                .add(JsonConst.TYPE, type)
                .add(JsonConst.ID, id)
                .build();
        return obj;
    }
    
    public static List<UserModel> toUsersList(JsonObject obj) {
        List<UserModel> users = new ArrayList<>();
        JsonArray jsonArray = obj.getJsonArray("array");
        if (jsonArray != null) {
            
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jObj = (JsonObject) jsonArray.get(i);
                
                int id = jObj.getInt("id");
                String name = jObj.getString("name");
                String email = jObj.getString("email");
                String status = jObj.getString("online_status");
                UserModel user = new UserModel(id, name, email, status);
                users.add(user);
            }
        }
        
        return users;
    }
    
    public static JsonObject convertToJsonUser(String type, UserModel user) {
        JsonObject obj = Json.createObjectBuilder()
                .add(JsonConst.TYPE, type)
                .add("id", user.getId())
                .add("name", user.getName())
                .add("email", user.getEmail())
                .add("password", user.getPassword())
                .build();
        return obj;
    }
    
    public static JsonObject fromCollaborator(String type, CollaboratorModel collaborator) {
        JsonObject obj = Json.createObjectBuilder()
                .add(JsonConst.TYPE, type)
                .add(JsonConst.USER_ID, collaborator.getUser_id())
                .add(JsonConst.LIST_ID, collaborator.getList_id())
                .build();
        return obj;
    }
    
     public static JsonObject fromList(String type, ListModel list){
        JsonObject obj = Json.createObjectBuilder()
                .add(JsonConst.TYPE, type)
                .add("list_id", list.getList_id())
                .add("title", list.getTitle())
                .add("color", list.getColor())
                .add("create_date", list.getCreate_date().toString())
                .add("user_id",list.getUser().getId())
                .build();
        return obj;
    }
    
    public static JsonObject fromTask(TaskModel task) {
        JsonObject obj = Json.createObjectBuilder()
                .add(JsonConst.TYPE, JsonConst.TYPE_ADD_TASK_REQUEST)
                .add("title", task.getTitle())
                .add("description", task.getDescription())
                .add("task_status", task.getTask_status())
                .add("deadline", task.getDeadline().toString())
                .add("list_id", task.getList_id())
                .add("user_id", task.getUser_id())
                .add("assign_date", task.getAssign_date().toString())
                .add("assign_status", task.getAssign_status())
                .build();
        
        return obj;
    }
    
    public static JsonObject fromComment(CommentModel comment) {
        JsonObject obj = Json.createObjectBuilder()
                .add(JsonConst.TYPE, JsonConst.TYPE_ADD_COMMENT_REQUEST)
                .add("task_id", comment.getTask_id())
                .add("user_id", comment.getUser_id())
                .add("comment_text", comment.getComment_text())
                .add("comment_date", comment.getComment_date().toString())
                .build();
        return obj;
    }
    
    public static JsonObject fromTaskId(int taskid) {
        JsonObject obj = Json.createObjectBuilder()
                .add(JsonConst.TYPE, JsonConst.TYPE_COMMENT_LIST_REQUEST)
                .add(JsonConst.ID, taskid)
                .build();
        return obj;
    }
    
    public static List<CommentModel> fromJsonCommentsList(JsonObject obj) {
        List<CommentModel> comments = new ArrayList<>();
        JsonArray jsonArray = obj.getJsonArray("array");
        if (jsonArray != null) {
            
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jObj = (JsonObject) jsonArray.get(i);
                
                String name = jObj.getString("name");
                Timestamp date = Timestamp.valueOf(jObj.getString("comment_date"));
                String commentText = jObj.getString("comment_text");
                CommentModel comment = new CommentModel(commentText, date, name);
                comments.add(comment);
            }
        }
        
        return comments;        
    }
        public static JsonObject updateFromTask(TaskModel task) {
        JsonObject obj = Json.createObjectBuilder()
                .add(JsonConst.TYPE, JsonConst.TYPE_UPDATE_TASK_REQUEST)
                .add("task_id",task.getTask_id())
                .add("title", task.getTitle())
                .add("description", task.getDescription())
                .add("task_status", task.getTask_status())
                .add("deadline", task.getDeadline().toString())
                .add("list_id", task.getList_id())
                .add("user_id", task.getUser_id())
                .add("assign_date", task.getAssign_date().toString())
                .add("assign_status", task.getAssign_status())
                .build();
        
        return obj;
    }
 public static JsonObject fromBoolean(boolean b,int id) {
        JsonObject obj = Json.createObjectBuilder()
                .add(JsonConst.TYPE, JsonConst.TYPE_CHANGE_ONLINE_STATUS)
                .add("status", b)
                .add(JsonConst.ID, id)
                .build();
        return obj;
    }
  public static ArrayList<ListModel> toListOfListModels(JsonObject obj) {
        ArrayList<ListModel> lists = new ArrayList<ListModel>();
        JsonArray jsonArray = obj.getJsonArray("array");
        
        if (jsonArray != null) {
            
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jObj = (JsonObject) jsonArray.get(i);
                
                ListModel list = new ListModel();
                list.setList_id(jObj.getInt("id"));
                list.setTitle(jObj.getString("title"));
                list.setColor(jObj.getString("color"));
                String s =jObj.getString("createDate");
                list.setCreate_date(Timestamp.valueOf(s));
                list.getUser().setId(jObj.getInt("userid"));
                list.getUser().setName(jObj.getString("username"));
                list.getUser().setEmail(jObj.getString("useremail"));
                list.getUser().setOnline_status(jObj.getString("userstates"));
                lists.add(list);
            }
        }
        
        return lists;        
    }
    
}
