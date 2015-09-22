/**
*	UDP Client Program
*	Connects to a UDP Server
*	Receives a line of input from the keyboard and sends it to the server
*	Receives a response from the server and displays it.
*
*	@author: John Green
@	version: 2.1
*/

import java.io.*;
import java.net.*;

class Blue {
	
    public static void main(String args[]) throws Exception{

      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

      DatagramSocket clientSocket = new DatagramSocket();

      int port = 9876;

      InetAddress IPAddress = InetAddress.getByName("192.168.1.84");

	  System.out.println("Client started...");
	  boolean connected = false;
      while (true){
        byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		String sentence = "";
		//Establish connection if needed
		if (connected){
			System.out.println("Please enter a message or press enter to receive messages: ");
      		sentence = inFromUser.readLine();
      			if (sentence.equals("Goodbye")){
					clientSocket.close();	
					break;
				}
      	}else{
      		sendData = "Creating Connection".getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			clientSocket.send(sendPacket);
			connected = true;
      	}
		//If wanted you can send something
		if (!(sentence.equals(""))){
			sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			clientSocket.send(sendPacket);
			while (true){
				System.out.print("Send another message: [y/n]: ");
		      	sentence = inFromUser.readLine();
		      	if (sentence.equals("y")){
		      		System.out.println("Please enter a message: ");
      				sentence = inFromUser.readLine();
      				sendData = sentence.getBytes();
					sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					clientSocket.send(sendPacket);
		      	}else{
		      		break;
		      	}
			}
		//Send nothing and listen for chat from the other party
		//But do nothing
		}
		
		
		while (true){
			//this cleans the byte stream

			//receive messages until further notice
			//if they are not server messages but just text then you can recieve as many as possible
			try{
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.setSoTimeout(1000);
      			clientSocket.receive(receivePacket);
				//The data received from the server
		        receiveData = new byte[1024];
			    String message = new String(receivePacket.getData());
				message = message.trim();
				if (message.equals("300")) {
					//wait for response from other member
					// receivePacket = new DatagramPacket(receiveData, receiveData.length);
		   			// clientSocket.receive(receivePacket);
		   			// message = new String(receivePacket.getData());
					// message = message.trim();
				//you are the second person to join the chat
				}else if (message.equals("200")){
					System.out.println("Please wait for a message");
					receivePacket = new DatagramPacket(receiveData, receiveData.length);
					clientSocket.receive(receivePacket); 
					//The data received from the server
					message = new String(receivePacket.getData());
					System.out.println(message.trim());
				//you are the first person to join the chat
				}else if (message.equals("100")){
					System.out.println("You are the first one to enter the chat.");
					System.out.println("Please wait for a chat partner.");
					receivePacket = new DatagramPacket(receiveData, receiveData.length);
					clientSocket.setSoTimeout(0);
					clientSocket.receive(receivePacket);
					message = new String(receivePacket.getData());
					message = message.trim();
					if (message.equals("200")){
						System.out.println("Partner connected...");
					}else{
						System.out.println("Unknown error. Exiting..");
						System.exit(0);
					}
				}else if (message.equals("400")){
					System.out.println("Other user has left chat. Goodbye!");
					clientSocket.close();
					break;
				}else{
					System.out.println(message);
				}
				if (sentence.equals("Goodbye")){
					clientSocket.close();	
					break;
				}
				clientSocket.setSoTimeout(0);
			}catch(SocketTimeoutException e){
				break;
			}catch (Exception e){
				System.out.println(e.getMessage());
			}
		}
      }
    }
}
