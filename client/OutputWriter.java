import java.io.*;
import java.net.*;
import java.util.*;


/*

This is a outPutWritter class is a thread created for each client thread

whose responsibility is to write any message from the server and display it.


@author: Kruthi Meghana Anumandla
*/

public class OutputWriter extends Thread{
    
    private PrintWriter writer;
    private Socket socket;
    private ClientSideChat client;
    
    
    public OutputWriter(Socket socket, ClientSideChat client) {
        
        this.socket = socket;
        this.client = client;
	
	    try {
	        OutputStream outputText = socket.getOutputStream();
            	writer = new PrintWriter(outputText, true);
	    } catch (IOException exp) {
	        System.out.println("Error in reading"+exp.getMessage());
	    }
        
    }
    
    public void run() {
        
        Console console = System.console();
        
        String userName = console.readLine("Enter your user name:\n");
        client.setClientName(userName);
        System.out.println("Hi,"+userName+"! Welcome to chat room. you can leave the room anytime by entering 'exit'\n");
        writer.println(userName);
        
        
        
        String msg = "";
        while(!msg.equals("exit")) {
            msg = console.readLine( userName + ": ");
            writer.println(msg);
        }

        try {
            socket.close();
        } catch (IOException ex) {
 
            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }
       
}
