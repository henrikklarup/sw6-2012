package savannah.io;

import savannah.io.DOMinator.DOMinator;
import savannah.server.*;

import java.net.Socket;

import java.io.InputStream;
import java.io.DataInputStream;
import java.io.IOException;

import java.lang.Thread;

import org.jdom.JDOMException;

public class CommunicationThread extends Thread {
	private IOHandler ioHandler;
	private Socket socket;
	private TransmissionHandler handle = null;
	private String folder;
	private int bufferSize;
	
	public CommunicationThread(IOHandler _IOHandler, Socket _socket, String _folder, int _bufferSize) {
		this.ioHandler = _IOHandler;
		this.socket = _socket;
		this.folder = _folder;
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
					this.ioHandler.displayMessage("Connecting from: " + this.socket);
					handle = new TransmissionHandler(inputStream, this.bufferSize, this.folder);
					//TransmissionHandler now has a DEBUG statement implemented ...
					
					DOMinator domI = new DOMinator();
					//CommitEvent
					if (handle.CR() == 1) {
						this.ioHandler.displayMessage("CommitEvent created by: " + this.socket);
						CommitEvent comEvt = new CommitEvent(domI.Dominate(handle.XML()), this.socket, handle.anyFiles());
						EventQueue.getInstance().add(comEvt);
					}
					//RequestEvent
					else if (handle.CR() == 2) {
						this.ioHandler.displayMessage("RequestEvent created by: " + this.socket)
						RequestEvent reqEvt = new RequestEvent(handle.XML(), this.socket);
						EventQueue.getInstance().add(reqEvt);
					}
					hasRead = true;
				}
//				handle = new TransmissionHandler(inputStream, this.bufferSize);
//				this.ioHandler.displayMessage(message);
//				
//				this.ioHandler.sendToConncted(message);
			}
		}	catch (IOException e) {
			System.out.println("CommunicationThread: Could not initiate inputStream for the connection");
		} 	catch (JDOMException e) {
			System.err.println("CommunicationThread: Could not DOMinate");
		}	finally {
			this.ioHandler.removeConnection(this.socket);
		}
	}
	
}

