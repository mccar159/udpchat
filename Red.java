/**
*	UDP Client Program
*	Connects to a UDP Server
*	Receives a line of input from the keyboard and sends it to the server
*	Receives a response from the server and displays it.
*
*	@author: Adrienne Bergh
@	version: 2.1
*/

import java.io.*;
import java.net.*;

class Red {
	
	public static void main(String args[]) throws Exception {

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket = new DatagramSocket();

		InetAddress IPAddress = InetAddress.getByName("localhost"); //server IP (Johnny test server)
		System.out.println("Enter hello to enter a chat..");
		while(true){
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];

			String sentence = inFromUser.readLine();
			sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);

			clientSocket.send(sendPacket);

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);

			String response = new String(receivePacket.getData());
            response = response.trim();
            		
			if (response.equals("100")) {
				System.out.println("You are the first to arrive. Please wait for second user.");
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				response = new String(receivePacket.getData());
				if (response.trim().equals("200")) {
					System.out.println("Second user has connected. Send message.");
					sentence = inFromUser.readLine();
					sendData = sentence.getBytes();
					sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
					clientSocket.send(sendPacket);
				}
				else {
					System.out.println("Error receiving connection from second user.");
					clientSocket.close();
					break;
				}
			}
			else if (response.equals("200")) {
				System.out.println("You are the second to arrive. Please wait for first user's message.");
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				response = new String(receivePacket.getData());
				System.out.println(response.trim());
			}
			else if (response.equals("300")) {
				System.out.println("Message sent successfully. Send another? (Type yes or no)");
				String newSentence = inFromUser.readLine();
				if (!(newSentence.equals("yes"))) {
					receivePacket = new DatagramPacket(receiveData, receiveData.length);
					clientSocket.receive(receivePacket);
					response = new String(receivePacket.getData());
					System.out.println(response.trim());
				}
			}
			else if(response.equals("400")) {
				System.out.println("Second user has left. Goodbye.");
				clientSocket.close();
				break;
			}
			else {
				System.out.println(response.trim());
			}	
      
			if (sentence.equals("Goodbye")){
				clientSocket.close();
				break;
			}
      	}
	}
}
