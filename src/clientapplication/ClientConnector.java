/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapplication;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author remon
 */
public class ClientConnector {

    public static final String STATIC_IP = "127.0.0.1";
    public static final int PORT_NO = 5005;

    private Socket socket;
    private DataInputStream dis;
    private PrintStream ps;

    /**
     * init connection with the server
     */
    public void init() {
        try {
            socket = new Socket(STATIC_IP, PORT_NO);
            dis = new DataInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * sends a message to the server
     *
     * @param msg to be sent to the server
     */
    public void sendMessage(String msg) {
        ps.println(msg);
    }
    
     public void sendMessage(JSONObject msg) {
        ps.println(msg);
    }

    /**
     * close connection and release resources
     */
    public void closeConnection() {
        try {
            dis.close();
            ps.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
