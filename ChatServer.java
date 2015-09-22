/**
*	Chat Server Program
*	Listens on a UDP port
*	Receives messages from each client and transmit the messages to the other client
*	Displays the messages from both clients on the ChatServer screen
*	"Goodbye" message will terminate the session
*
*	@author: Connor McCarl
	Partners: Adrienne Bergh, John Green
@	version: 2.0
*/

import java.io.*;
import java.net.*;

class ChatServer{
	
  public static void main(String args[]) throws Exception
    {
    DatagramSocket serverSocket = null;
	int redPort = 0;
	int bluePort = 0;
	  
	try
		{
			serverSocket = new DatagramSocket(9876);
		}
	
	catch(Exception e)
		{
			System.out.println("Failed to open UDP socket");
			System.exit(0);
		}
		
		InetAddress redIP = null;
		InetAddress blueIP = null;
		
		
		
      
	  
      while(true)
        {
          byte[] receiveData = new byte[1024];
      	  byte[] sendData  = new byte[1024];
          DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);	 
          serverSocket.receive(receivePacket);
		  
		  String sentence = null;
		  
		  		  
		  if(redIP == null || blueIP == null){
			if(redIP != receivePacket.getAddress() && redIP != null) {
				//second client - store IP address
				blueIP = receivePacket.getAddress();
				System.out.println("Blue IP Address: " + blueIP.toString());
				bluePort = receivePacket.getPort();
				sentence = "200";
				sendData = sentence.getBytes();
				
				//notify second client there are two people in chat
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, blueIP, bluePort);
				serverSocket.send(sendPacket);
				
				//notify first client that second client has entered
				sendPacket = sendPacket = new DatagramPacket(sendData, sendData.length, redIP, redPort);
				serverSocket.send(sendPacket);
			} else {
				//first client - store IP address or keeps sending 100 code if first client keeps sending stuff
			  redIP = receivePacket.getAddress();
			  redPort = receivePacket.getPort();
		      System.out.println("Red IP Address: " + redIP.toString());

			  sentence = "100";
			  sendData = sentence.getBytes();
			  
			  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, redIP, redPort);
			  serverSocket.send(sendPacket);
			}
		  } else{ //both clients are active and chatting
			  sentence = new String(receivePacket.getData()).trim();
				if(sentence.equals("Goodbye")){
					//notify second client there are two people in chat
					sentence = "400";
					System.out.println("Both clients are exiting.");
					//print end conversation on server screen
					sendData = "400".getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, blueIP, bluePort);
					serverSocket.send(sendPacket);

					sendPacket = sendPacket = new DatagramPacket(sendData, sendData.length, redIP, redPort);
					serverSocket.send(sendPacket);
					System.exit(0);
					//send 400 code to bot				
				}
				//check who chatted
				if(receivePacket.getAddress().equals(redIP)){
					//print message on server screen Red:
     				System.out.println("Red sent: " + sentence);
					//send message to blue
					sendData = sentence.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, blueIP, bluePort);
					serverSocket.send(sendPacket);
					//send 300 code to red
					sendData = "300".getBytes();
					sendPacket = sendPacket = new DatagramPacket(sendData, sendData.length, redIP, redPort);
					serverSocket.send(sendPacket);

				}else{
					//print message on server screen Blue:
					System.out.println("Blue sent: " + sentence);
					//send message to red
					sendData = sentence.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, redIP, redPort);
					serverSocket.send(sendPacket);
					//send 300 code to blue
					sendData = "300".getBytes();
					sendPacket = sendPacket = new DatagramPacket(sendData, sendData.length, blueIP, bluePort);
					serverSocket.send(sendPacket);
				}

				
			}	
		}
	}
}