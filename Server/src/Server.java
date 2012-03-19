import java.lang.Thread;
import java.lang.Exception;
import java.io.IOException;

import java.net.Socket;
import java.net.ServerSocket;
import java.util.Enumeration;
import java.util.Hashtable;

import java.io.DataOutputStream;


public class Server {
	private ServerSocket serverSocket;
	private Hashtable connectionStreams = new Hashtable();

	/**
	*
	*
	*/
	public Server(int port) throws IOException {
		//We listen on port for any connections
		listen(port);
	}
	
	/**
	*
	*
	*/
	private void listen(int port) throws IOException {
		//Create the ServerSocket
		this.serverSocket = new ServerSocket(port);
		System.out.println(Message.ServerListen(port));

		//Accepting connections
		while(true) {
			//Get the next connection
			Socket connection = this.serverSocket.accept();
			System.out.println(Message.ServerConnection(this.serverSocket));
			
			//Creating an OutputStream for the connection
			//and saving it for later
			DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
			connectionStreams.put(connection, outputStream);

			//Creating a separate Thread for the connection
			new ServerThread(this, connection);
		}
	}

	/**
	*
	*
	*/
	Enumeration getDataOutputStreams() {
		return this.connectionStreams.elements();
	}

	/**
	*
	*
	*/
	public void sendToAll(String message) {
		//Synchronized because another thread might be
		//calling removeConnection() and this would screw up things
		synchronized(this.connectionStreams) {
			//Iterate over each connected client
			for(Enumeration e = getDataOutputStreams(); e.hasMoreElements(); ) {
				//Retrieve the OutputStream
				DataOutputStream outputStream = (DataOutputStream)e.nextElement();

				//Send the message to the "network"
				try {
					outputStream.writeUTF(message);
				}	catch (IOException ie) {
					System.out.println(Message.ServerIOExceptionMessage(ie));
				}
			}
		}

	}

	/**
	*
	*
	*/
	public void removeConnection(Socket s) {
		//Synchronize so we don't mess up the threads
		synchronized(this.connectionStreams) {
			System.out.println(Message.ServerDisconnection(s));

			//Remove the connecction from the Hashtable
			this.connectionStreams.remove(s);

			//Making sure the connection is closed
			try {
				s.close();
			}	catch (IOException ie) {
				System.out.println(Message.ServerIOExceptionSocket(s));
				ie.printStackTrace();
			}
			
		}
	}


	/**
	*
	*
	*/
	//Main method for running the Server
	//Usage: java Server <port>
	public static void main(String[] args) throws Exception {
		
		//Get the port # from the command line
		int port = Integer.parseInt( args[0] );

		//Create a Server Obj - begins automatically accepting connections
		new Server ( port );
	}

}