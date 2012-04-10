package savannah.io;

import java.net.Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class TestClient {
	private Socket socket;
	private DataInputStream reader;
	private DataOutputStream writer;
	
	public TestClient(String name, int port, String message) throws IOException {
		this.socket = new Socket(name,port);
		work(message);
	}
	
	private void work(String message) {
		write(message);
		read();
	}
	
	private void write(String message) {
		System.out.println("Writing to the Server");
		try {
			this.writer = new DataOutputStream(this.socket.getOutputStream());
			this.writer.writeUTF(message);
//			this.writer.writeChars(message);
			this.writer.flush();
			
		}	catch (IOException e) {
			System.out.println("Could not create the writer !");
		}
		System.out.println("Writing complete !");
	}
	private void read() {
		System.out.println("Starting to read -_-' ");
		try {
			this.reader = new DataInputStream(this.socket.getInputStream());
			while (true) {
				String temp = this.reader.readUTF();
				System.out.println("Reading message -_-'");
				System.out.println("Message: " + temp);
			}
			
		}	catch (IOException e) {
			System.out.println("Could not create the reader !");
		}
	}
	
	public static void main(String[] args) {
		String temp = "This is my test String !";
		try {
			new TestClient("localhost", 50000, temp);
		}	catch (IOException e) {
			System.out.println("Could not create a Client !");
		}
	}

}

