/*
 * A simple TCP client that sends messages to a server and display the message
   from the server.
 * For use in CPSC 441 lectures
 * Instructor: Prof. Mea Wang
 *
 * This program was further manipulated and edited by:
 * Austin Graham
 * UCID : 30035861
 * Cody Clark
 * UCID : 30010560
 */


import java.io.*;
import java.net.*;

import javax.lang.model.util.ElementScanner6;

class TCPClient2 {


    public static void main(String args[]) throws Exception
    {
      String line = "";
        if (args.length != 2)
        {
            System.out.println("Usage: TCPClient <Server IP> <Server Port>");
            System.exit(1);
        }

        // Initialize a client socket connection to the server
        Socket clientSocket = new Socket(args[0], Integer.parseInt(args[1]));

        System.out.println("before Threads running");
        // Initialize user input stream
        /*
        Thread object0 = new Thread(new inThread());
           object0.start();
        Thread object1 = new Thread(new outThread());
           object1.start();
        */
           Runnable parameter0 = new inThread(clientSocket);
        new Thread(parameter0).start();
           Runnable parameter1 = new outThread(clientSocket);
        new Thread(parameter1).start();
        // Close the socket
        //clientSocket.close();
    }
}

class outThread extends Thread{
  private Socket clientSocket;
  public outThread(Socket clientSocket){
    this.clientSocket = clientSocket;
  }
  public void run(){
    System.out.println("outThread running");
    PrintWriter outBuffer = null;
    BufferedReader inFromUser = null;
    try{
     outBuffer = new PrintWriter(clientSocket.getOutputStream(), true);

     String outLine = "";
     inFromUser = new BufferedReader(new InputStreamReader(System.in));
     System.out.println("Please enter a message to be sent to the server ('/quit' to terminate): ");
     System.out.println("Enter /login <username> <password> to login.\n" +
                                   "Or enter /signup <username> <password> to signup for an account.\n");
     while (!outLine.equals("/quit")) {
     // Ask the user for input
     if ((outLine = inFromUser.readLine()) != "") {
        outBuffer.println(outLine);
        outBuffer.flush();
     }
    }
    }
    catch(IOException e){
    }
   };
}

class inThread extends Thread{
  private Socket clientSocket;
  public inThread(Socket clientSocket){
    this.clientSocket = clientSocket;
  }

  public void run(){
  System.out.println("inThread running");
  String inLine = "";
  BufferedReader inBuffer = null;
  try{
  // Initialize input and an output stream for the connection(s)
  inBuffer = new BufferedReader(new
    InputStreamReader(clientSocket.getInputStream()));

    // Loops and asks the user for commands as long as they haven't decided to quit
    while (!inLine.equals("/quit") )
    {
      // System.out.println("woo it gets here");
      // line = inBuffer.readLine();
        // Check to see if there's a response from the server

        if ((inLine = inBuffer.readLine()) != "" )
        {

          inLine = inLine.replace("^","\n");
          System.out.println(inLine);
        }

        // System.out.print("\nCommand: ");

            // System.out.println(line); // An echo from the server, what the server sent in response

        }


        // When the user enters in something not recognized as a command
        //else {
            // System.out.println("I'm sorry but that doesn't look like a recognized command.");
            // System.out.println("Please try again\n");
        //}
    }

  catch( IOException e){
  }
  };
}
