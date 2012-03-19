import java.lang.Thread;
import java.io.IOException;
import java.io.EOFException;

import java.net.Socket;

import java.io.DataInputStream;

public class ServerThread extends Thread {
	//Server that created this Thread
	private Server server;
	//The Socket connected to our Client
	private Socket socket;

	/**
	*
	*
	*/
	public ServerThread(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
		//Start the Thread
		start();
	}

	/**
	*
	*
	*/
	public void run() {
		try {
			//Create a DataInputStream for the cummonication
			//The Client uses DataOutputStream to write to us
			DataInputStream inputStream = new DataInputStream(this.socket.getInputStream());

			while(true) {
				//Get the next message from a Client
				String message = inputStream.readUTF();
				System.out.println(Message.ServerThreadSending(message));

				//Send the message to all the connected Clients
				this.server.sendToAll(message);
			}
		}	catch (EOFException eof) {
			System.out.println(Message.ServerThreadEOFException(eof));
		}	catch (IOException ie) {
			System.out.println(Message.ServerIOExceptionMessage(ie));
			ie.printStackTrace();
		}	finally {
			//The connection is closed somehow
			//The server deals with it by removing it
			this.server.removeConnection(this.socket);
		}
	}




}


