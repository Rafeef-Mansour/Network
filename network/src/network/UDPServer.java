package network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;	
import java.util.HashMap;
import java.util.Map;

public class UDPServer {
	

	
	
		public static void main(String args[]) throws Exception {
			DatagramSocket serverSocket = new DatagramSocket(8855);

			Map<String, String> clientMessages = new HashMap<>();

			byte[] receiveData = new byte[1024];

			while (true) {
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

				serverSocket.receive(receivePacket);

				String clientIP = receivePacket.getAddress().getHostAddress();
				String clientName = new String(receivePacket.getData()).trim();

				clientMessages.put(clientIP, clientName);

				System.out.println("Received message from " + clientName + " at " + clientIP);
				System.out.println("Current messages:");

				int count = 1;

				for (Map.Entry<String, String> entry : clientMessages.entrySet()) {
					String ip = entry.getKey();
					String name = entry.getValue();
					System.out.println(count + "- Received message from " + name + " at " + ip);
					count++;
				}

				System.out.println("=========================================");

				receiveData = new byte[1024]; // Clear the receive buffer
			}
			
			
			
		}
	}

