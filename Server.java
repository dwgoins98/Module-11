package chapter33;

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Server extends Application {
	
	public static boolean primeChecker(int num) {
    	
    	num = num / 2;
    	
    	boolean prime = true;
    	
    	if (num == 0 || num == 1 ) {
    		prime = false;
    	} else {
    		
    		for (int i = 2; i < num; i++) {
    			
    			if(num % i == 0) {
    				
    				prime = false;
    				break;
    				
    			}
    		}
    		
    	}
    	
    	return prime;
    	
    }

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    // Text area for displaying contents
    TextArea ta = new TextArea();

    // Create a scene and place it in the stage
    Scene scene = new Scene(new ScrollPane(ta), 450, 200);
    primaryStage.setTitle("Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    

    
    new Thread( () -> {
      try {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8000);
        Platform.runLater(() ->
          ta.appendText("Server started at " + new Date() + '\n'));
  
        // Listen for a connection request
        Socket socket = serverSocket.accept();
  
        // Create data input and output streams
        DataInputStream inputFromClient = new DataInputStream(
          socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(
          socket.getOutputStream());
  
        while (true) {
          // Receive number from the client
          int number = inputFromClient.readInt();
  
          if(primeChecker(number)) {
        	  int answer = 1;
          } else {
        	  int answer = 0;
          }
          
          // Send area back to the client
          outputToClient.writeInt(answer);
  
          Platform.runLater(() -> {
            ta.appendText("Number received from client: " 
              + number + '\n');
            
            if(primeChecker(number)) {
            ta.appendText(Number + " is prime \n"); 
            } else {
            	ta.appendText(Number + " is NOT prime \n");
            }
          });
        }
      }
      catch(IOException ex) {
        ex.printStackTrace();
      }
    }).start();
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
