package savannah.io;

import java.net.Socket;

import java.io.DataInputStream;
import java.io.IOException;

import java.lang.Thread;

public class CommunicationThread extends Thread {
	private IOHandler ioHandler;
	private Socket socket;
	
	public CommunicationThread(IOHandler _IOHandler, Socket _socket) {
		this.ioHandler = _IOHandler;
		this.socket = _socket;
		start();
	}
	
	public void run() {
		try {
			DataInputStream inputStream = new DataInputStream(this.socket.getInputStream());
			
			while (this.isAlive() == true) {
				String message = inputStream.readUTF();
				this.ioHandler.displayMessage(message);
				
				this.ioHandler.sendToConncted(message, this.socket);
			}
		}	catch (IOException e) {
			System.out.println("Could not initiate inputStream for the connection");
		}	finally {
			this.ioHandler.removeConnection(this.socket);
		}
	}
	
}

