package client;

import interfaces.ListenerIF;
import java.io.IOException;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Thread {

    // FIELDS
    Socket socket;
    private int port;
    private InetAddress serverAddress;
    private Scanner input;
    private PrintWriter output;
    List<ListenerIF> listeners = new ArrayList();
    String username;
    
    // METHODS
    public void connect(String address, int port, String username) throws UnknownHostException, IOException {

        this.port = port;
        serverAddress = InetAddress.getByName(address);
        socket = new Socket(serverAddress, port);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);  //Set to true, to get auto flush behaviour
        this.username = username;
        start();
    }

    public void send(String msg) throws IOException {
        if (socket.isOutputShutdown()) {
            throw new IOException("Outbound socket is closed");
        }
        output.println(msg);
    }

    public void stopClient() throws IOException {
        output.println("CLOSE#");
        socket.shutdownOutput();
    }

    public void registerEchoListener(ListenerIF l) {
        listeners.add(l);
    }

    public void unRegisterEchoListener(ListenerIF l) {
        listeners.remove(l);
    }

    private void notifyListeners(String msg) {

        for (ListenerIF listener : listeners) {
            listener.messageArrived(msg);
        }
    }
    
   public String getUsername() {
       return username;
   }

    
    
    @Override
    // LOOP
    public void run() {
        String msg = input.nextLine();
        while (!msg.equals("CLOSE#")) {
 
            //ONLINE
            //SENDTO
            //BROADCAST
            //CONNECT?
            
            notifyListeners(msg);
            
            msg = input.nextLine(); // blocking call - waiting for msg.
        
        }
        try {
            socket.close();
            input.close();
            input = null;
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
