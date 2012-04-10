package savannah.io;

import java.net.Socket;

import java.io.InputStream;
import java.io.DataInputStream;
import java.io.IOException;

import java.lang.Thread;

public class CommunicationThread extends Thread {
	private IOHandler ioHandler;
	private Socket socket;
	private TransmissionHandler handle = null;
	private String folder;
	private int bufferSize;
	
	public CommunicationThread(IOHandler _IOHandler, Socket _socket, String _folder, int _bufferSize) {
		this.ioHandler = _IOHandler;
		this.socket = _socket;
		this.folder = folder;
		this.bufferSize = _bufferSize;
		start();
	}
	
	public void run() {
		try {
			InputStream inputStream = new DataInputStream(this.socket.getInputStream());
			boolean hasRead = false;
			
			while (this.isAlive() == true) {
//				String message = inputStream.readUTF();
				if (hasRead == false) {
					handle = new TransmissionHandler(inputStream, this.bufferSize, this.folder);
					//TransmissionHandler now has a DEBUG statement implemented ...
					hasRead = true;
				}
//				handle = new TransmissionHandler(inputStream, this.bufferSize);
//				this.ioHandler.displayMessage(message);
//				
//				this.ioHandler.sendToConncted(message);
			}
		}	catch (IOException e) {
			System.out.println("Could not initiate inputStream for the connection");
		}	finally {
			this.ioHandler.removeConnection(this.socket);
		}
	}
	
}

