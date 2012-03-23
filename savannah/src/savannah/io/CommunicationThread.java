package savannah.io;

import java.net.Socket;

import java.io.DataInputStream;
import java.io.IOException;

import java.lang.Thread;

public class CommunicationThread extends Thread {
	//Field variables
	private IOHandler ioHandler;
	private Socket socket;
	
	/**
	 * Creates a CommunicationThread from the specified IOHandler and Socket.
	 * @param _IOHandler - the specified IOHandler
	 * @param _socket - the specified Socket
	 */
	public CommunicationThread(IOHandler _IOHandler, Socket _socket) {
		this.ioHandler = _IOHandler;
		this.socket = _socket;
		//Start this Thread
		start();
	}
	
	/**
	 * This method is called when a CommunicationThread is started.
	 */
	public void run() {
		try {
			//Initiate a Stream to use for communication
			DataInputStream inputStream = new DataInputStream(this.socket.getInputStream());
			
			//Keep listening for any incoming messages
			while (this.isAlive() == true) {
				String message = inputStream.readUTF();
				this.ioHandler.displayMessage(message);
				
				//Send the message to all the connected Sockets
				this.ioHandler.sendToConncted(message);
			}
		}	catch (IOException e) {
			System.out.println("Could not initiate inputStream for the connection");
		}	finally {
			//If a Socket connection is no longer responding
			//something it is wrong and it is assumed broken
			//therefore we remove the connection
			this.ioHandler.removeConnection(this.socket);
		}
	}
	
}

