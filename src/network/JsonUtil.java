/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import javax.json.Json;
import javax.json.JsonObject;
import model.UserModel;

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
    
}
