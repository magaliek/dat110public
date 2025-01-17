package no.hvl.dat110.udpexample.server;

import java.io.*;
import java.net.*;

import no.hvl.dat110.udpexample.system.Configuration;

public class UDPEchoServer {

	private DatagramSocket serverSocket;

	public UDPEchoServer(int serverport) throws SocketException {

		serverSocket = new DatagramSocket(serverport);
	}

	public void process() {

		byte[] recvbuf = new byte[Configuration.MAXTEXTLEN];

		DatagramPacket request = new DatagramPacket(recvbuf, recvbuf.length);

		try {

			serverSocket.receive(request);
			
			String intext = new String(request.getData());
			
			System.out.println("SERVER RECEIVED: " + intext);

			String outtext = intext.toUpperCase();
			
			byte[] msg = outtext.getBytes();
		
			InetAddress ipaddress = request.getAddress();
			int port = request.getPort();

			DatagramPacket response = new DatagramPacket(msg, msg.length, ipaddress, port);

			System.out.println("SERVER SENDING:  " + outtext);

			serverSocket.send(response);

		} catch (IOException ex) {
			
			System.out.println("UDPServer: " + ex.getMessage());
			ex.printStackTrace();
			
		} 
	}

	public void stop() {

		if (serverSocket != null) {
			serverSocket.close();
		}

	}
}
