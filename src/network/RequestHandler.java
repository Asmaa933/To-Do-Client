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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;


/**
 *
 * @author Mazen Mohamed
 */
public class RequestHandler {
    private static String STATIC_IP = "127.0.0.1";
    private static int PORT_NO = 5005;

    private  Socket socket;
    private  DataInputStream dis;
    private  PrintStream ps;

    public RequestHandler() {
    }
    
    synchronized public  JsonObject makeRequest(JsonObject request)
    {   
        JsonObject response=null;
        try {
            socket = new Socket(STATIC_IP, PORT_NO);
            dis = new DataInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream());
            
            ps.println(request);
            String read = dis.readLine();
            JsonReader jsonReader = Json.createReader(new StringReader(read));
            response = jsonReader.readObject();
            jsonReader.close();
            ps.println("JavaTODO_ClientFINISH");
            ps.close();
            dis.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
             //{"type":"EXption"}

        } 

        return response;
    }
    
    
}
