import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class Client extends Panel implements Runnable {
	//Components for the visual display of the chat window
	private TextField textField = new TextField();
	private TextArea textArea = new TextArea();

	//The Socket connecting us to the Server
	private Socket socket;

	//Communication Streams - comes from the Socket
	private DataOutputStream outputStream;
	private DataInputStream inputStream;

	/**
	*
	*
	*/
	public Client(String host, int port) {
		//Screen layout
		setLayout(new BorderLayout());
		add("North", this.textField);
		add("Center", this.textArea);

		//Press enter to sent the message
		this.textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processMessage(e.getActionCommand());
			}
		});

		//Connect to the Server
		try {
			//Initiate the connection
			this.socket = new Socket(host, port);
			System.out.println(Message.ClientConnection(this.socket));

			//Connecting the Streams to the Socket
			this.inputStream = new DataInputStream(this.socket.getInputStream());
			this.outputStream = new DataOutputStream(this.socket.getOutputStream());

			//Background Thread for receiving messages
			new Thread(this).start();
		}	catch(IOException ie) {
			System.out.println(Message.ClientIOException(ie));
		}
	}

	/**
	*
	*
	*/
	private void processMessage(String message) {
		try {
			//Send message to the Server
			this.outputStream.writeUTF(message);

			//Clear the GUI
			this.textField.setText("");
		}	catch (IOException ie) {
			System.out.println(Message.ClientIOException(ie));
		}
	}

	/**
	*
	*
	*/
	public void run() {
		try {
			//Receive the messages
			while(true) {
				//Get the next message
				String message = this.inputStream.readUTF();
				//Print the message in the textArea
				this.textArea.append(message + "\n");
			}
		}	catch (IOException ie) {
			System.out.println(Message.ClientIOException(ie));
		}
	}

}