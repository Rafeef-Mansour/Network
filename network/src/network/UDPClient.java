package network;

import java.io.*;
import java.net.*;

public class UDPClient {
	
	

	
	    public static void main(String args[]) throws Exception {
	    	
	           DatagramSocket clientSocket = new DatagramSocket();
	            InetAddress IPAddress = InetAddress.getByName("255.255.255.255"); // Broadcast address
	            
	            byte[] sendData;
	            
	            String studentName = "rafeef mansour"; // Replace with your own name
	            
	            while (true) {
	                sendData = studentName.getBytes();
	                
	                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8855);
	                
	                clientSocket.send(sendPacket);
	                
	                System.out.println("Sent message: " + studentName);
	                
	                Thread.sleep(2000); // Wait for 2 seconds before sending the next message
	            }
	    	
	    	
	    
	    }
	    
}
