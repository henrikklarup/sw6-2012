package savannah.io;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.DataOutputStream;
import java.io.IOException;

import java.util.ArrayList;

public class IOHandler {
	//Field variables
	private ServerSocket serverSocket;
	private ArrayList<ConnectionIO> connections = new ArrayList<ConnectionIO>();
	private String folder;
	private int bufferSize;
	
	/**
	 * Creates a IOHandler that uses the specified port on which it accepts connections.
	 * @param port - the specified port
	 */
	
	public IOHandler()
	{
		
	}
	public IOHandler(int port, String folder, int bufferSize) {
		this.folder = folder;
		this.bufferSize = bufferSize;
		listen(port);
	}

	/**
	 * Listens for connections on the specified port.
	 * @param port - the specified port
	 */
	private void listen(int port) {
		try {
			//Initiates a ServerSocket
			System.out.println("Initiating serversocket !");
			this.serverSocket = new ServerSocket(port);
			System.out.println("Initiation complete");
			
		}	catch (IOException io) {
			System.out.println("Could not create ServerSocket -_-");
		}
		System.out.println("Starting to listen !");
		//Loop that continues to look & accept connections
		//on the ServerSocket
		while (true) {
			try {
				//Making a connections
				Socket con = this.serverSocket.accept();
				System.out.println("Connection accepted - Socket: " + con);

				//Saving the connections and a corresponding
				//DataOutputStream for later use
				this.connections.add(new ConnectionIO(con, new DataOutputStream(con.getOutputStream())));
				//Starts a new Thread used for communication
				new CommunicationThread(this, con, this.folder, this.bufferSize);
			}	catch (IOException e) {
				System.out.println("Could not accept connection !");
			}
		}
	}
	
	/**
	 * Prints the specified message to the IOHandler's console.
	 * @param message - the specified message
	 */
	public void displayMessage(String message) {
		System.out.println("Message: " + message);
	}
	/*
	public void sendToConncted(String message, Socket socket) {
		synchronized (this.connections) {
			for (ConnectionIO elem : this.connections) {
				if ( (elem.getSocket() == socket) == false) {
					DataOutputStream temp = elem.getOutputStream();

					try {
						temp.writeUTF(message);
					}	catch (IOException e) {
						System.out.println("Could not sendToConnected ! Error: " + e.getMessage());
					}
				}
			}
		}
	}
	 */
	
	/**
	 * Sends the specified message to all the connected Sockets.
	 * @param message - the specified message
	 */
	public void sendToConncted(String message) {
		//Synchronizing so it is not possible to
		//remove a connection, while sending it a
		//message
		synchronized (this.connections) {
			System.out.println("Number of connections: " + this.amountOfConnection());
			//Finding a connection and sending the message to it
			for (ConnectionIO elem : this.connections) {
				DataOutputStream temp = elem.getOutputStream();
				try {
					//Writing the message and cleaning up
					temp.writeUTF(message);
					temp.flush();
				}	catch (IOException e) {
					System.out.println("Could not sendToConnected ! Error: " + e.getMessage());
				}
			}
		}
	}
	
	
	public void respond(Socket socket, String message) {
		
		synchronized (this.connections) {
			DataOutputStream temp = this.findConnectionStream(socket);
			try {
				temp.writeUTF(message);
				temp.flush();
			}	catch (IOException io) {
				System.out.println("Could not send respond to Socket! Error: " + io.getMessage());
			}
		}
		
	}
	
	private DataOutputStream findConnectionStream(Socket socket) {
		DataOutputStream temp = null;
		
		for (ConnectionIO elem : this.connections) {
			if (elem.getSocket() == socket) {
				temp = elem.getOutputStream();
				break;	//No need to iterate to much
			}
		}
		if (temp == null) {
			throw new NullPointerException("Could not find the Socket");
		}
		else {
			return temp;
		}
		
	}

	/**
	 * Checks if the specified Socket is in the list of connections.
	 * @param socket - the specified Socket
	 * @return true, if the Socket exists, otherwise false
	 */
	private boolean isInConnections(Socket socket) {
		//Running through the elements 
		//and checking the element with the Socket
		for (ConnectionIO elem : this.connections) {
			if (elem.getSocket() == socket) {
				return true;
			}
		}
		//else: return false
		return false;
	}
	
	/**
	 * Finds the index of the specified Socket.
	 * @param socket - the specified Socket
	 * @return the index of the Socket, otherwise 0
	 */
	private int findConnectionIndex(Socket socket) {
		int index = 0;
		//Running through the elements 
		//and checking the element with the Socket
		for (int i = 0; i < this.connections.size(); i++) {
			if (this.connections.get(i).getSocket() == socket) {
				index = i;
			}
		}
		//Returning the index of the Socket
		return index;
	}
	
	/**
	 * Gets the amount of connections that are connected to the IOHandler
	 * @return the amount of connected connections
	 */
	private int amountOfConnection() {
		return this.connections.size();
	}

	/**
	 * Removes a Socket connection based on the specified
	 * @param socket - the specified Socket
	 */
	public void removeConnection(Socket socket) {
		//Synchronizing so it is not possible to
		//send a message, while removing a connection
		synchronized (this.connections) {
			System.out.println("Disconnecting Socket: " + socket);

			//Safety check
			if (this.isInConnections(socket) == false) {
				throw new IllegalArgumentException("The connection did not exist !");
			}
			else {
				//Removing the connection
				this.connections.remove(findConnectionIndex(socket));
			}

			try {
				//Closing the connection
				socket.close();
			}	catch (IOException io) {
				System.out.println("Could not close Socket: " + socket);
			}	
		}
	}

//	public static void main(String[] args) {
//		//"Hard-Codedededede" port value
//		int port = 50000;
//		int bufferSize = 4096;
//		String folder = "C:\\newfolder";
//		
//		//Starting the IOHandler
//		new IOHandler(port, folder, bufferSize);
//	}




}

