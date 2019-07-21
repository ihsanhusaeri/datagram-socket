import java.io.*;
import java.net.*;
import java.util.*;
public class DatagramClient{
	private static InetAddress inetAddress;
	private static final int PORT = 5000;
	private static DatagramSocket socket;
	private static DatagramPacket inPacket, outPacket;
	private static byte[] buffer;
	
	public static void main(String[] args){
		try{
			inetAddress = InetAddress.getLocalHost();
			
		}catch(UnknownHostException exception){
			System.out.println("Host ID not found!");
			System.exit(1);
		}
		accessServer();
		
	}
	private static void accessServer(){
		try{
			socket = new DatagramSocket();
			Scanner input = new Scanner(System.in);
			String message="";
			String response = "";
			do{
				System.out.print("Enter message: ");
				message = input.nextLine();
				if(!message.equals("CLOSE")){
					outPacket = new DatagramPacket(message.getBytes(),
								message.length(), inetAddress, PORT);
					socket.send(outPacket);
					buffer = new byte[256];
					inPacket = new DatagramPacket(
								buffer, buffer.length
								);
					socket.receive(inPacket);
					response = new String(inPacket.getData(),
										0, inPacket.getLength());
										
					System.out.println("\nSERVER: "+response);
					
				}
			}while(!message.equals("CLOSE"));
		}catch(IOException exception){
			exception.printStackTrace();
		}finally{
			System.out.println("\nClosing connection...");
			socket.close();
		}
	}
}