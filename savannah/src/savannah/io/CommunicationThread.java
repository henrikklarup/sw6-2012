package savannah.io;

import savannah.device.Connection;
import savannah.io.DOMinator.DOMinator;
import savannah.server.*;

import java.net.Socket;

import java.io.InputStream;
import java.io.DataInputStream;
import java.io.IOException;

import java.lang.Thread;

import org.jdom.JDOMException;

public class CommunicationThread extends Thread {
	//	private IOHandler ioHandler;
	private Socket socket;
	private TransmissionHandler handle = null;
	private String folder;
	private int bufferSize;
	private volatile boolean lock = false;

	public CommunicationThread(Socket _socket, String _folder, int _bufferSize) {
		//		this.ioHandler = IOHandler.getInstance();
		this.socket = _socket;
		this.folder = _folder;
		this.bufferSize = _bufferSize;
		//		start();
	}

	public final synchronized void lockEngage() {
		this.lock = true;
	}
	public final synchronized void lockDisengage() {
		this.lock = false;
	}
	
	public void run() {
		//		this.ioHandler = IOHandler.getInstance();
		try {
			InputStream inputStream = new DataInputStream(this.socket.getInputStream());

			IOHandler.getInstance().displayMessage("Connecting from: " + this.socket);
			handle = new TransmissionHandler(this.socket, this.folder);
			IOHandler.getInstance().logIt(this.socket, handle.CR().toString());
			//TransmissionHandler now has a DEBUG statement implemented ...

			//CommitEvent
			if (handle.CR() == CRUD.COMMIT) {
				DOMinator domI = new DOMinator();
				IOHandler.getInstance().displayMessage("CommitEvent created by: " + this.socket);
				CommitEvent comEvt = new CommitEvent(domI.Dominate(handle.XML()), this.socket, this);
				EventQueue.getInstance().add(comEvt);
				while (lock == true) {
					//Do nothing and keep the thread alive
				}
				IOHandler.getInstance().logIt(true);
			}
			//RequestEvent
			else if (handle.CR() == CRUD.REQUEST) {
				IOHandler.getInstance().displayMessage("RequestEvent created by: " + this.socket);
				RequestEvent reqEvt = new RequestEvent(handle.XML(), this.socket, this);
				EventQueue.getInstance().add(reqEvt);
//				IOHandler.getInstance().respond(this.socket, CRUD.REQUEST, "I so hope that this is going to work !!!");
				while (lock == true) {
					//Do nothing and keep the thread alive
				}
				IOHandler.getInstance().logIt(true);
			}
			//Ping
			else if (handle.CR() == CRUD.PING) {
				IOHandler.getInstance().displayMessage("Ping sent from: " + this.socket);
				IOHandler.getInstance().respond(this.socket, handle.XML());
				IOHandler.getInstance().logIt(true);
			}
			else if (handle.CR() == CRUD.ERROR){
				IOHandler.getInstance().displayMessage("TransmissionHandler could not resolve to a known package");
				IOHandler.getInstance().logIt(false);
			}
			else {
				IOHandler.getInstance().displayMessage("TransmissionHandler could not resolve to a known package");
			}
		}	catch (IOException e) {
			System.out.println("CommunicationThread: Could not initiate inputStream for the connection");
		} 	catch (JDOMException e) {
			System.err.println("CommunicationThread: Could not DOMinate");
		}	finally {
			IOHandler.getInstance().removeConnection(this.socket);
		}
	}

}

