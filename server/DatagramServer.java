import java.io.*;
import java.net.*;

public class DatagramServer{
	private static final int PORT = 5000;
	private static DatagramSocket datagramSocket;
	private static DatagramPacket inPacket, outPacket;
	private static byte[] buffer;
	
	public static void main(String[] args){
		System.out.println("Opening port...");
		try{
			datagramSocket = new DatagramSocket(PORT);
		}catch(SocketException exception){
			System.out.println("Unable to open port!");
			System.exit(1);
		}
		handleClient();
	}
	private static void handleClient(){
		try{
			String messageIn, messageOut;
			int numberMessages = 0;
			InetAddress clientAddress = null;
			int clientPort;
			do{
				buffer = new byte[256];
				inPacket = new DatagramPacket(buffer, buffer.length);
				datagramSocket.receive(inPacket);
				clientAddress = inPacket.getAddress();
				clientPort = inPacket.getPort();
				messageIn = new String(inPacket.getData(),
							0, inPacket.getLength());
				System.out.println("Message received.");
				numberMessages++;
				
				messageOut = "Message "+ numberMessages + ": "+messageIn;
				
				outPacket = new DatagramPacket(messageOut.getBytes(), messageOut.length(), clientAddress
							, clientPort);
				datagramSocket.send(outPacket);
			}while(true);
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
}