package network;

import java.io.*;
import java.net.*;

public class WebServer {
	

	
	
		public static void main(String[] args) {
			int serverPort = 9977;
			try {
				ServerSocket serverSocket = new ServerSocket(serverPort);
				System.out.println("The server is ready to receive");

				while (true) {
					Socket connectionSocket = serverSocket.accept();
					BufferedReader inFromClient = new BufferedReader(
							new InputStreamReader(connectionSocket.getInputStream()));
					DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

					String sentence = inFromClient.readLine();
					System.out.println(connectionSocket.getInetAddress());
					System.out.println(sentence);

					if (sentence != null) {
						// Extract the requested file name from the HTTP request
						String[] request = sentence.split(" ");
						String fileName = request[1].substring(1); // Remove the leading '/'

						// Check the file extension and determine the appropriate Content-Type
						String contentType;
						String filePath;
						if (fileName.equals("main_en.html") && request[1].startsWith("/en")) {
							contentType = "text/html";
							filePath = "main_en.html";
						} else if (fileName.endsWith(".html")) {
							contentType = "text/html";
							filePath = fileName;
						} else if (fileName.endsWith(".css")) {
							contentType = "text/css";
							filePath = fileName;
						} else if (fileName.endsWith(".png")) {
							contentType = "image/png";
							filePath = fileName;
						} else if (fileName.endsWith(".jpg")) {
							contentType = "image/jpg";
							filePath = fileName;
						} else if (fileName.equals("yt") && request[1].startsWith("/yt")) {
							outToClient.writeBytes("HTTP/1.1 307 Temporary Redirect\r\n");
							outToClient.writeBytes("Location: https://www.youtube.com\r\n");
							connectionSocket.close();
							continue;
						} else if (fileName.equals("so") && request[1].startsWith("/so")) {
							outToClient.writeBytes("HTTP/1.1 307 Temporary Redirect\r\n");
							outToClient.writeBytes("Location: https://stackoverflow.com\r\n");
							connectionSocket.close();
							continue;
						} else if (fileName.equals("rt") && request[1].startsWith("/rt")) {
							outToClient.writeBytes("HTTP/1.1 307 Temporary Redirect\r\n");
							outToClient.writeBytes("Location: https://www.ritaj.com\r\n");
							connectionSocket.close();
							continue;
						} else {
							// If the file doesn't exist or the request is wrong, return a 404 Not Found
							// response
							String errorResponse = "HTTP/1.1 404 Not Found\r\n"
									+ "Content-Type: text/html; charset=utf-8\r\n\r\n" + "<!DOCTYPE html>\n" + "<html>\n"
									+ "<head>\n" + "<title>Error 404</title>\n" + "</head>\n" + "<body>\n"
									+ "<h1>The file is not found</h1>\n" + "<p><strong>Your names and IDs</strong></p>\n"
									+ "<p>Client IP: " + connectionSocket.getInetAddress() + "</p>\n" + "<p>Client Port: "
									+ connectionSocket.getPort() + "</p>\n" + "</body>\n" + "</html>\n";
							outToClient.writeBytes(errorResponse);
							connectionSocket.close();
							continue;
						}

			 			// Open and send the requested file
						File file = new File(filePath);
						FileInputStream fileInputStream = new FileInputStream(file);
						byte[] fileData = new byte[(int) file.length()];
						fileInputStream.read(fileData);

						String response = "HTTP/1.1 200 OK\r\n" + "Content-Type: " + contentType + "\r\n"
								+ "Content-Length: " + fileData.length + "\r\n\r\n";
						byte[] responseBytes = response.getBytes();
						byte[] combined = new byte[responseBytes.length + fileData.length];
						System.arraycopy(responseBytes, 0, combined, 0, responseBytes.length);
						System.arraycopy(fileData, 0, combined, responseBytes.length, fileData.length);

						outToClient.write(combined);
						connectionSocket.close();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



