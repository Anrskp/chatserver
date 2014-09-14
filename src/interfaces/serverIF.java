package interfaces;
import server.*;

public interface serverIF {
   
    public void stopServer();

    public void broadCast(String msg);

    public void sendTo(String username, String msg);
    
    public void addHandler(ClientHandler ch);
    
    public void removeHandler(ClientHandler ch);
    
    public void runServer(String logFile, String ip, int port);
    
}
