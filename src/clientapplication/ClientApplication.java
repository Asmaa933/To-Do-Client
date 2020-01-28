package clientapplication;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 
import help.JSONHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.simple.JSONObject;

/**
 *
 * @author esma
 */
public class ClientApplication extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene scene = new Scene(root);
      stage.initStyle(StageStyle.UNDECORATED);

        stage.setScene(scene);
        stage.show();
        
//      JSONObject a =  JSONHelper.getJSON("signin", "email", "remonger@gmail.com");
//      ClientConnector conn = new ClientConnector();
//      conn.init();
//      conn.sendMessage(a);
//        System.out.println(a);
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
