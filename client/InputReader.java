
import java.io.*;
import java.net.*;


/*

This is a InputReader class is a thread created for each client thread

whose responsibility is to read any message from the server and display it.


@author: Kruthi Meghana Anumandla
*/

public class InputReader extends Thread{
    
    private BufferedReader reader;
    private Socket socket;
    private ClientSideChat client;
    
    
    public InputReader(Socket socket, ClientSideChat client) {
        
        this.socket = socket;
        this.client = client;
	
	try {
	    InputStream inputText = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputText));
	} catch (IOException exp) {
	    System.out.println("Error in reading"+exp.getMessage());
	}        
    }
    
    public void run() {
        while(true) {
            try {
            	 String messageReceived = reader.readLine();
                System.out.println("\n"+messageReceived);
            } catch(IOException exp) {
            	String errorMsg = exp.getMessage();
            	if(errorMsg.equals("Socket closed")) {
                	errorMsg = "Bye!We hope you have a nice day!";
                } else {
                	errorMsg = "Error this one:" + exp.getMessage();
                }
                System.out.println(errorMsg);
                break;
            }   
        }
    }
       
}
