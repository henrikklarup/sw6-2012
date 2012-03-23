package savannah.io;

import java.net.Socket;
import java.net.UnknownHostException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TestClient {
	//Field variables
	private Socket socket;
	private DataInputStream reader;
	private DataOutputStream writer;
	
	/**
	 * Creates a TestClient that uses the specified name and port to
	 * create Socket connection and sends the specified message.
	 * @param name - the specified name
	 * @param port - the specified port
	 * @param message - the specified message
	 */
	public TestClient(String name, int port, String message) {
		//Initiating the Socket
		try {
			this.socket = new Socket(name,port);
		}	catch (UnknownHostException e) {
			System.out.println("Could not connect on port: " + port + " Error: " + e.getMessage());
		}	catch (IOException e) {
			System.out.println("Could not connect the Socket - " + "Error: " + e.getMessage());
		}
		//Sending the message and looking for transmissions
		work(message);
	}
	
	/**
	 * This method combines the write method and the read method. Then specified message 
	 * is sent using the write method.
	 * @param message - the specified message
	 */
	private void work(String message) {
		//Write the message to the Socket
		write(message);
		//Look for transmissions
		read();
	}
	
	/**
	 * Writes the specified message to the connected Socket.
	 * @param message - the specified message
	 */
	private void write(String message) {
		System.out.println("Writing to the Server");
		try {
			//Initiate the DataOutputStream and write to it
			this.writer = new DataOutputStream(this.socket.getOutputStream());
			this.writer.writeUTF(message);
			//Clean-up the Stream
			this.writer.flush();
			
		}	catch (IOException e) {
			System.out.println("Could not create the writer !");
		}
		System.out.println("Writing complete !");
	}
	
	/**
	 * This method starts listening for incoming transmission from the Socket.
	 */
	private void read() {
		System.out.println("Starting to read -_-' ");
		try {
			//Initiate the DataInputStream
			this.reader = new DataInputStream(this.socket.getInputStream());
			//Read transmissions from the DataInputStream
			
			while (true) {
				String temp = this.reader.readUTF();
				System.out.println("Reading message -_-'");
				System.out.println("Message: " + temp);
			}
		}	catch (IOException e) {
			System.out.println("Could not create the reader !");
		}
	}
	
	/**
	 * Standard main method.
	 * @param args - the specified args
	 */
	public static void main(String[] args) {
		//Message to send through the Socket
		String temp = "This is my test String !";
		//Create an instance of TestClient
		new TestClient("localhost", 50000, temp);
	}

}

