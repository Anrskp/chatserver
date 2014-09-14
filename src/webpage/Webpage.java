/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Webpage;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Emilos
 */
public class Webpage
{
    
    static int port = 8080;
    static String ip = "127.0.0.1";
    public static void main(String[] args) throws Exception {
    if(args.length == 2){
      port = Integer.parseInt(args[0]);
      ip = args[1];
    }
    HttpServer server = HttpServer.create(new InetSocketAddress(ip,port), 0);
    server.createContext("/Chatserver", new RequestHandler());
    server.createContext("/ChatOnline", new ChatonlineRequestHandler());
    server.createContext("/Chatlogfile", new ChatlogfileRequestHandler());
    
    
    server.setExecutor(null); // Use the default executor
    server.start();
    System.out.println("Server started, listening on port: "+port);
  }
    
    
    
    public void run()
    {
        try
        {
            main(null);
        } catch (Exception ex)
        {
            Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    static class RequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html>\n");
  
        sb.append("<head>\n");
        sb.append("<meta charset='UTF-8'>\n");
        sb.append("<title>Chatserver</title>\n");
        sb.append("</head>\n");
        
        sb.append("<body>\n");  
        
       sb.append("<meta charset='UTF-8'>\n");
        
        sb.append("<h1>Velkommen til Chatserver v. 1.0</h1>\n");
        sb.append("<p>Test test test test</p>"); 

        

        
        sb.append("<table border='1px'>");
        sb.append("<thead>");
        sb.append("<tr><th colspan='3'>Klik på billedet for at komme til siden(Indfør bedre teskt)</th>");
        sb.append("</thead>");
        
        sb.append("<tbody>");
        sb.append("<tr>");
        sb.append("<td> <a href=\"https://github.com/Anrskp/ca1ASE\"> <img src='http://www.wakanda.org/sites/default/files/blog/blog-github.png'/></a> </th>");
        sb.append("<td> <a href=\"http://localhost:8080/Chatlogfile\"> <img src='http://cdn.flaticon.com/png/256/28811.png'/></a> </th>");
        sb.append("<td> <a href=\"http://localhost:8080/ChatOnline\"> <img src='http://cdn.techgyd.com/live-chat.png'/></a> </th>");
        sb.append("</tr>");
        sb.append("</tbody>");
        
        sb.append("</body>\n");
            
        sb.append("</html>\n");
        String response = sb.toString();
        int length = sb.toString().getBytes("UTF8").length;
        
        
        he.sendResponseHeaders(200, length);
      Headers h = he.getResponseHeaders();
      h.add("Content-type", "text/html");
      try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
        pw.print(response); 
      }
    }
    }
    
    static class ChatonlineRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException
    {
        
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
            sb.append("<html>\n");
        //        sb.append("<meta charset='UTF-8'>\n");  
                sb.append("<head>\n");
                    sb.append("<meta charset='UTF-8'>\n");
                    sb.append("<title>Chat users online</title>\n");
                sb.append("</head>\n");

                sb.append("<body>\n");  

                    sb.append("<meta charset='UTF-8'>\n");

                    sb.append("<h1>Chat users online test</h1>\n");
                    sb.append("<p>Chatters online = ?</p>");
                    
                    sb.append("<a href=\"http://localhost:8080/Chatserver\">Retur til forsiden.</a>");

                sb.append("</body>\n");

            sb.append("</html>\n");
        String response = sb.toString();
        int length = sb.toString().getBytes("UTF8").length;
        
        
        he.sendResponseHeaders(200, length);
      Headers h = he.getResponseHeaders();
      h.add("Content-type", "text/html");
      try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
        pw.print(response); 
      }
    }
    }
    
    static class ChatlogfileRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException
    {
        String ChatPath = "chatLog.txt"; 
        Scanner scan = new Scanner(new File(ChatPath));
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("<!DOCTYPE html>\n");
            sb.append("<html>\n");
//        sb.append("<meta charset='UTF-8'>\n");  
            sb.append("<head>\n");
                sb.append("<meta charset='UTF-8'>\n");
                sb.append("<title>Chatlog File</title>\n");
            sb.append("</head>\n");

            sb.append("<body>\n");  

                sb.append("<meta charset='UTF-8'>\n");

                sb.append("<h1>Chatlog file Test</h1>\n");
                sb.append("<p>Test test test test</p>"); 
                
                sb.append("<table border='1px'>");
                    sb.append("<thead>");
                        sb.append("<tr><th colspan='2'>Chat Logfile</th>");
                            sb.append("<tr>");
                              sb.append("<th>Date/Class</th>");
                              sb.append("<th>Message</th>");
                            sb.append("</tr>");
                           
                              
                            
                        
                    sb.append("</thead>");

                    sb.append("<tbody>");
                       while (scan.hasNext())
                            {
                              sb.append("<tr><td>");
                              sb.append(scan.nextLine());
                              sb.append("</td><td>");
                              sb.append(scan.nextLine());
                              sb.append("</td><tr>");
                            }
                       sb.append("</tr>");

                       scan.close();

                    sb.append("</tbody>");
                    
                sb.append("<a href=\"http://localhost:8080/Chatserver\">Retur til forsiden.</a>");

            sb.append("</body>\n");
            
        sb.append("</html>\n");
        String response = sb.toString();
        int length = sb.toString().getBytes("UTF8").length;
        
        
        he.sendResponseHeaders(200, length);
      Headers h = he.getResponseHeaders();
      h.add("Content-type", "text/html");
      try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
        pw.print(response); 
      }
    }
    }
    
}
