/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class RequestHandler {

    private static String STATIC_IP = "127.0.0.1";
    private static int PORT_NO = 5005;

    private Socket socket;
    private DataInputStream dis;
    private PrintStream ps;

    public RequestHandler() {
    }

    public static void setSTATIC_IP(String STATIC_IP) {
        RequestHandler.STATIC_IP = STATIC_IP;
    }

    public static String getSTATIC_IP() {
        return STATIC_IP;
    }

    synchronized public JsonObject makeRequest(JsonObject request) {
        JsonObject response = null;
        try {
            socket = new Socket(STATIC_IP, PORT_NO);
            //time out in 0.5 min
            socket.setSoTimeout(1000 * 20);
            dis = new DataInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream());

            ps.println(request);
            String read = dis.readLine();
            System.out.println("RequestHandler-Readline= " + read);
            JsonReader jsonReader = Json.createReader(new StringReader(read));
            response = jsonReader.readObject();
            jsonReader.close();
            ps.println("JavaTODO_ClientFINISH");
            ps.close();
            dis.close();
            socket.close();
        } catch (java.net.ConnectException ce) {
            showAlert();
        } catch (SocketTimeoutException ste) {
            System.out.println("socket time out");
            showTimeoutAlert();
        } catch (IOException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            //{"type":"EXption"}

        }

        return response;
    }

    private void showAlert() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("couldn't connect...");
                ButtonType retry = new ButtonType("Retry?", ButtonBar.ButtonData.OK_DONE);
                ButtonType exit = new ButtonType("exit", ButtonBar.ButtonData.CANCEL_CLOSE);
                ButtonType ignore = new ButtonType("Ignore", ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(AlertType.WARNING,
                        "We are having a hard time connecting to the server!", retry, exit, ignore);
                alert.setTitle("Couldn't connect to the server");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == retry) {
                    System.out.println("retry");
                } else if (result.get() == exit) {
                    System.out.println("exit");
                    Platform.exit();
                    System.exit(0);
                } else {
                    //do nothing
                }
            }
        });
    }

    private void showTimeoutAlert() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("timed out...");
                ButtonType exit = new ButtonType("exit", ButtonBar.ButtonData.CANCEL_CLOSE);
                ButtonType ignore = new ButtonType("Ignore", ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(AlertType.WARNING,
                        "The request is taking so long..", exit, ignore);
                alert.setTitle("request timed out");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == exit) {
                    System.out.println("exit");
                    Platform.exit();
                    System.exit(0);
                } else {
                    //do nothing
                }
            }
        });
    }
}
