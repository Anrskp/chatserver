package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler extends Thread {

    Scanner input;
    PrintWriter writer;
    Socket socket;
    Server server;
    String username;

    public ClientHandler(Socket socket, Server server) throws IOException {
        input = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
        this.socket = socket;
        this.server = server;
    }

    void send(String msg) {
        writer.println(msg);
    }

    @Override
    public void run() {
        try {
            server.addHandler(this);
            String message = input.nextLine(); //IMPORTANT blocking call
            Logger.getLogger(Server.class.getName()).log(Level.INFO, String.format("Received the message: %1$S ", message));

            String[] messageparts = message.split("#");
 
            while (!messageparts[0].equals("CLOSE")) {
                
                
                if (messageparts[0].equals("ONLINE")) {
                    username = messageparts[1];
                    //serverkald der returnere opdatere listen over onlineusers.
                }

                if (messageparts[0].equals("SEND") && (!messageparts[1].equals("*"))) {
                    // SEND MSG TO
                }
                
                if (messageparts[0].equals("SEND") && (messageparts[1].equals("*"))) {
                    server.broadCast(message);
                }

                //TEST 
                server.broadCast(message);

                Logger.getLogger(Server.class.getName()).log(Level.INFO, String.format("Received the message: %1$S ", message.toUpperCase()));
                message = input.nextLine(); //IMPORTANT blocking call - waiting for new msg.
            }
        } catch (NoSuchElementException ste) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, "Socket timed out", ste);
        }
        try {
            input.close();
            writer.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Logger.getLogger(Server.class.getName()).log(Level.INFO, "Closed a Connection");
            server.removeHandler(this);
            // server kald der opdatere onlineliste.
        }
    }
}
