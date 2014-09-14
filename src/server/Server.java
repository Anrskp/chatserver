package server;

import interfaces.serverIF;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Utils;

public class Server implements serverIF {

    //FIELDS
    boolean serverRunning = true;
    private static ServerSocket serverSocket;
    private static final Properties properties = Utils.initProperties("server.properties");
    // MAKE HASHMP WITH USERNAME AS KEY INSETAD OF ARRAYLIST!
    private List<ClientHandler> handlers = Collections.synchronizedList(new ArrayList<ClientHandler>());

    public static void main(String[] args) {
        int port = Integer.parseInt(properties.getProperty("port"));
        String ip = properties.getProperty("serverIp");
        String logFile = properties.getProperty("logFile");
        new Server().runServer(logFile, ip, port);
    }

    //SERVER METHODS 
    @Override
    public void stopServer() {
        serverRunning = false;
    }

    @Override
    public void broadCast(String msg) {
        handlers.stream().forEach((handler) -> {
            handler.send(msg);
        });
    }

    public void updateOnlineList(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.   
    }
    
    @Override
    public void sendTo(String username, String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addHandler(ClientHandler ch) {
    handlers.add(ch);
    }

    @Override
    public void removeHandler(ClientHandler ch) {
    handlers.remove(ch);
    }
    
    public int onlineUsers() {
    return handlers.size();
    }

    @Override
    //SERVER LOOP
    public void runServer(String logFile, String ip, int port) {
        Utils.setLogFile(logFile, Server.class.getName());

        Logger.getLogger(Server.class.getName()).log(Level.INFO, "Sever started");
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(ip, port));
            do {
                Socket socket = serverSocket.accept(); //Important Blocking call.
                Logger.getLogger(Server.class.getName()).log(Level.INFO, "Connected to a client");
                new ClientHandler(socket, this).start();
            } while (serverRunning);
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Utils.closeLogger(Server.class.getName());
        }
    }

}
