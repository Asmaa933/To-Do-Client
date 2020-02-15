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

/**
 *
 * @author remon
 */
public class ClientConnector {

    private static final String STATIC_IP = "127.0.0.1";
    private static final int PORT_NO = 5005;

    private static Socket socket;
    private static DataInputStream dis;
    private static PrintStream ps;
    
    private static Thread myThread;

    /**
     * startConnection connection with the server
     */
    public static void startConnection() {
        try {
            socket = new Socket(STATIC_IP, PORT_NO);
            dis = new DataInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();  
        }
        
        myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {                    
                    try {   
                        //checkMyConnection();
                        String replyMsg = dis.readLine();
                        //Use Massage 
                        System.out.println("Client Message From Server (replyMsg)= " + replyMsg);
                    } catch (IOException ex) {
                       ex.printStackTrace();
                    }
                }
            }
        });
        myThread.start();
    }

    /**
     * sends a message to the server
     *
     * @param msg to be sent to the server
     */
    public static void sendMessage(String msg) {
        ps.println(msg);
    }
    
    /* public static void sendMessage(JSONObject msg) {
        ps.println(msg);
    }*/

    /**
     * close connection and release resources
     */
    public static void closeConnection() {
        try {
            ps.println("JavaTODO_ClientFINISH");
            ps.close();
            dis.close();
            socket.close();
            myThread.stop();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
}