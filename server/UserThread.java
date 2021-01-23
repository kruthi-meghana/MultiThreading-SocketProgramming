
import java.io.*;
import java.net.*;
import java.util.*;


/*

This is a UserThread class a part of multi-client chat room application

For each client a thread of this class is created in the ServerSideChat file

This thread is responsible for reading and writting the text from input and 

output streams and then communicate it with the server.


@author: Kruthi Meghana Anumandla
*/

public class UserThread extends Thread{
    
    private Socket socket;
    private ServerSideChat server;
    private PrintWriter outputWriter;
    private BufferedReader inputReader;
    
    
    public UserThread(Socket socket, ServerSideChat server) {
        this.socket = socket;
        this.server = server;
    }
    
    public void clientListener() {
    
    	System.out.println("Listening to Client:");
        
        try {
            
            OutputStream outputText = socket.getOutputStream();
            outputWriter = new PrintWriter(outputText, true);
            
            InputStream inputText = socket.getInputStream();
            inputReader = new BufferedReader(new InputStreamReader(inputText));
            
            String clientName = inputReader.readLine();
            
            printLiveClients();
            
            server.addClient(clientName);
            String sMessage = "New client added"+clientName;
            server.broadcast(sMessage, this);
            
            String msg = "";
            
            while(!msg.equals("exit")) {
                msg = inputReader.readLine();
                sMessage = clientName+":  "+msg;
                server.broadcast(sMessage, this);
            
            }
            
            server.removeClient(clientName, this);
            socket.close();
            
            sMessage = clientName + " has exited the chat room";
            server.broadcast(sMessage, this);
            
        } catch (IOException exp) {
            System.out.println("Error in client thread "+exp.getMessage());
        }
    }
    
   
    void sendMessage(String message) {
        outputWriter.println(message);
    }
    
    
    void printLiveClients() {
        if(server.liveClientNames().isEmpty()) {
            outputWriter.println("No users are currently connected \n");
        } else {
            outputWriter.println("Connected users are: \n"+ server.liveClientNames() + "\n");
        }
    }
    
}
