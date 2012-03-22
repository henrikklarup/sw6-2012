package savannah.io;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.DataOutputStream;
import java.io.IOException;

import java.util.ArrayList;

public class IOHandler {
	private ServerSocket serverSocket;
	private ArrayList<ConnectionIO> connections = new ArrayList<ConnectionIO>();

	public IOHandler(int port) {
		listen(port);
	}

	private void listen(int port) {
		try {
			System.out.println("Initiating serversocket !");
			this.serverSocket = new ServerSocket(port);
			System.out.println("Initiation complete");
		}	catch (IOException io) {
			System.out.println("Could not create ServerSocket -_-");
		}
		System.err.println("Starting to listen !");
		while (true) {
			try {
				Socket con = this.serverSocket.accept();
				System.out.println("Connection accepted - Socket: " + con);

				this.connections.add(new ConnectionIO(con, new DataOutputStream(con.getOutputStream())));
				new CommunicationThread(this, con);

			}	catch (IOException e) {
				System.out.println("Could not accept connection !");
			}
		}
	}

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
	public void sendToConncted(String message, Socket socket) {
		synchronized (this.connections) {
			System.out.println("Number of connections: " + this.amountOfConnection());
			for (ConnectionIO elem : this.connections) {
				DataOutputStream temp = elem.getOutputStream();

				try {
					temp.writeUTF(message);
//					temp.writeChars(message);
				}	catch (IOException e) {
					System.out.println("Could not sendToConnected ! Error: " + e.getMessage());
				}
			}
		}
	}

	private boolean isInConnections(Socket socket) {
		for (ConnectionIO elem : this.connections) {
			if (elem.getSocket() == socket) {
				return true;
			}
		}
		return false;
	}
	private int findConnectionIndex(Socket socket) {
		int index = 0;
		for (int i = 0; i < this.connections.size(); i++) {
			if (this.connections.get(i).getSocket() == socket) {
				index = i;
			}
		}
		return index;
	}
	private int amountOfConnection() {
		return this.connections.size();
	}

	public void removeConnection(Socket socket) {
		synchronized (this.connections) {
			System.out.println("Disconnecting Socket: " + socket);

			if (this.isInConnections(socket) == false) {
				throw new IllegalArgumentException("The connection did not exist !");
			}
			else {
				this.connections.remove(findConnectionIndex(socket));
			}

			try {
				socket.close();
			}	catch (IOException io) {
				System.out.println("Could not close Socket: " + socket);
			}	
		}
	}

	public static void main(String[] args) {
		int port = 50000;
		new IOHandler(port);
	}




}

