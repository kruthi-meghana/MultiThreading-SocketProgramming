
import java.io.*;
import java.net.*;
import java.util.*;


/*
This is a ServerSideChat class a part of multi-client chat room application

@author: Kruthi Meghana Anumandla
*/

public class ServerSideChat {
    
    private int port;
    private Set<String> clientNames = new HashSet<>();
    private Set<UserThread> clientThreads = new HashSet<>();
    
    public ServerSideChat(int port) {
        this.port = port;
    }
    
    public void serverListener() {
        
        try(ServerSocket serverSocket = new ServerSocket(port)) {
        
            System.out.println("Server is active!");
            
            while (true) {
                /*Waiting for the client socket to connect .accpet() waits until there is a client connection*/
                
                Socket socket = serverSocket.accept();
                
                UserThread client = new UserThread(socket, this);
                clientThreads.add(client);
                
                /* Starting the client thread*/
                client.clientListener();
            }
            
        } catch (IOException exp) {
            System.out.println("Error in establishing server"+exp.getMessage());
        }
    }
    
    /* This method shall broadcast the message to all existing client except sender*/
    void broadcast(String message, UserThread sender) {
        for(UserThread broadClient : clientThreads) {
            if(broadClient != sender) {
                broadClient.sendMessage(message);
            }
        }
    }
    
    /* we need to update the clientNames after updating the clientThreads: Add & Remove*/
    void addClient(String clientName) {
        clientNames.add(clientName);
    }
    
    
    void removeClient(String clientName, UserThread client) {
        clientNames.remove(clientName);
        clientThreads.remove(client);
    }
    
    Set<String> liveClientNames() {
        return this.clientNames;
    }

    
    
    public static void main(String[] args) {
        
        if(args.length < 1) {
            System.out.println("There was problem in setting up the server");
            System.exit(0);
        }
        
        ServerSideChat server = new ServerSideChat(Integer.parseInt(args[0]));
        server.serverListener();
    }
}
