
import java.io.*;
import java.net.*;
import java.util.*;


/*

This is a ClientSideChat class a part of multi-client chat room application

This file is run in the client system for every client thread there shall be

two other threads (InputReader, OutputWriter) running simultaneously 

which shall manage the reading and writing to the client socket.


@author: Kruthi Meghana Anumandla
*/

public class ClientSideChat {
    
    private String IPAddr;
    private String clientName;
    private int port;
    
    
    public ClientSideChat(String IPAddr, int port) {
        
        this.IPAddr = IPAddr;
        this.port = port;
        
    }
    
    public void clientSetup() {
        
        try {
        
            Socket socket = new Socket(this.IPAddr, this.port);
            
            System.out.println("Connection is successfully established");
            
            new InputReader(socket, this).start();
            new OutputWriter(socket, this).start();
            
        }catch(UnknownHostException unkExp) {
            System.out.println("Error in establishing connection:"+unkExp.getMessage());
        }catch (IOException exp) {
            System.out.println("I/O error while connecting to the host:"+exp.getMessage());
        }
        
        
    }
    
    void setClientName(String clientName) {
        this.clientName = clientName;
    }
    
    String getClientName(String clientName) {
        return this.clientName;
    }
    
    
    public static void main(String[] args) {
        
        if(args.length < 2)
        	return;
        ClientSideChat client = new ClientSideChat(args[0], Integer.parseInt(args[1]));
        client.clientSetup();
        
    }
    
}
