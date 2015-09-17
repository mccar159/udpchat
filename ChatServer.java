/**
*	UDP Server Program
*	Listens on a UDP port
*	Receives messages from each client and transmit the messages to the other client
*	Displays the messages from both clients on the ChatServer screen
*	"Goodbye" message will terminate the session
*
*	@author: Connor McCarl
@	version: 2.0
*/

import java.io.*;
import java.net.*;

class UDPServer {
	
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
		
		
		
      byte[] receiveData = new byte[1024];
      byte[] sendData  = new byte[1024];
	  
      while(true)
        {
          DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);	 
          serverSocket.receive(receivePacket);
		  
		  String sentence = null;
		  
		  		  
		  if(red == null || blue == null){
			if(red != receivePacket.getAddress() && red != null) {
				//second client - store IP address
				blueIP = receivePacket.getAddress();
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
			  sentence = "100";
			  sendData = sentence.getBytes();
			  
			  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, redIP, redPort);
			  serverSocket.send(sendPacket);
			}
		  } else{ //both clients are active and chatting
			  sentence = new String(receivePacket.getData());
			  sendData = sentence.getBytes();
			
				if(sentence.equals("Goodbye")){
					//send 400 code to both
					//print end conversation on server screen
					
				}
				//check who chatted
				if(receivePacket.getAddress == redIP){
					//print message on server screen Red:
					//send 300 code to red
					//send message to blue
				} else{
					//print message on server screen Blue:
					//send 300 code to blue
					//send message to red
				}
		}
		
		  }
		  
		  
          
		  

         

          

          

    }
}
