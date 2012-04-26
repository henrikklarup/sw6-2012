package savannah.io;


import java.net.ServerSocket;
import java.net.Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Hashtable;

public class IOHandler implements Runnable {
	//Field variables
	private static IOHandler _instance = null;
	private ServerSocket serverSocket;
	private Hashtable<Socket, DataOutputStream> connections = new Hashtable<Socket, DataOutputStream>(100);
	private String folder;
	private int bufferSize;

	@Override
	public void run() {
		listen();
	}

	private IOHandler(String folder, int bufferSize) {
		this.folder = folder;
		this.bufferSize = bufferSize;
	}
	public static synchronized IOHandler getInstance() {
		if (_instance == null) {
			_instance = new IOHandler(Configuration.FOLDERPATH, Configuration.BUFFERSIZE);
		}
		return _instance;
	}

	/**
	 * Listens for connections on the specified port.
	 * @param port - the specified port
	 */
	private void listen() {
		try {
			//Initiates a ServerSocket
			System.out.println("Initiating serversocket !");
			this.serverSocket = new ServerSocket(Configuration.PORT);
			System.out.println("Initiation complete");

		}	catch (IOException io) {
			System.err.println("Could not create ServerSocket -_-");
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
				this.connections.put(con, new DataOutputStream(con.getOutputStream()));
				//				this.connections.add(new ConnectionIO(con, new DataOutputStream(con.getOutputStream())));
				//Starts a new Thread used for communication
				Thread comThread = new CommunicationThread(con, this.folder, this.bufferSize);
				comThread.start();
				
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
	private byte[] messageToBytes(String message) {
		byte[] buf = new byte[message.length()];
		for (int i = 0; i < message.length(); i++) {
			buf[i] = (byte)message.charAt(i);
		}
		return buf;
	}
	private byte[] messageToBytes(char[] c) {
		byte[] buf = new byte[c.length];
		for (int i = 0; i < c.length; i++) {
			buf[i] = (byte)c[i];
		}
		return buf;
	}
	private byte[] stringBuilderToBytes(StringBuilder sb) {
		char[] buf = new char[sb.length()];
		sb.getChars(0, sb.length(), buf, 0);
		return this.messageToBytes(buf);
	}

	private void createCRUD(CRUD cr, StringBuilder builder) {
		try {
			//Exception handling 
			if (cr.getValue() == -1 || cr == CRUD.ERROR) {
				if (cr.getValue() == -1) {
					throw new IllegalArgumentException("cr: Cannot be less than zero !");
				}	else if (cr == CRUD.ERROR) {
					throw new IllegalArgumentException("cr: Cannot is set CRUD.ERROR !");
				}
			}
			if (builder == null) {
				throw new NullPointerException("builder: Cannot be null !");
			}
			//Adding the data
			builder.append("TYPE[" + cr.getValue() + "]");
		}	catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}	catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}
	}
	private void createDATA(String data, boolean anyFiles, StringBuilder builder) {
		try {
			//Exception handling 
			if (data.equals("") == true) {
				throw new IllegalArgumentException("data: Cannot be null !");
			}
			if (builder == null) {
				throw new NullPointerException("builder: Cannot be null !");
			}
			int files = (anyFiles == true) ? 1 : 0;
			//Adding the data
			builder.append("MXML[" + data.length() + "," + files + "]=\"");
			builder.append(data);
			builder.append("\"");
		}	catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}	catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}
	}
	private void createFILE(OutputStream os, File f) throws FileNotFoundException, IOException {
		InputStream is = new FileInputStream(f);
		StringBuilder sb = new StringBuilder();
		byte[] buf = new byte[this.bufferSize];
		@SuppressWarnings("unused")
		int len;

		//Writing "header-START"
		sb.append("FILE[" + f.length() + "," + f.getName() + "]=\"");
		os.write(this.stringBuilderToBytes(sb));
		//Writing content
		while ((len = is.read(buf)) > 0) {
			os.write(buf);
		}
		//Writing "header-END"
		sb = null;
		sb = new StringBuilder();
		sb.append("\"");
		os.write(this.stringBuilderToBytes(sb));

		//The cleanup crew... Put this stuff in the finally clause
		is.close();
	}
	private void commitResponds(Socket socket, String data) {
		DataOutputStream temp = this.getDataOutputStream(socket);
		StringBuilder sb = new StringBuilder();
		this.createCRUD(CRUD.COMMIT, sb);
		this.createDATA(data, false, sb);
		try {
			temp.write(this.messageToBytes(sb.toString()));
			temp.flush();
		}	catch (IOException e) {
			System.err.println("Could not commitResponds - write !");
		}
	}
	private void requestResponds(Socket socket, String data) {
		DataOutputStream temp = this.getDataOutputStream(socket);
		StringBuilder sb = new StringBuilder();
		this.createCRUD(CRUD.REQUEST, sb);
		this.createDATA(data, false, sb);
		try {
			temp.write(this.stringBuilderToBytes(sb));
			temp.flush();
		}	catch (IOException e) {
			System.err.println("Could not requestResponds - write - CRUD and data !");
		}
	}
	private void requestResponds(Socket socket, String data, File f) {
		DataOutputStream temp = this.getDataOutputStream(socket);
		StringBuilder sb = new StringBuilder();
		this.createCRUD(CRUD.REQUEST, sb);
		this.createDATA(data, true, sb);

		try {
			temp.write(this.stringBuilderToBytes(sb));
			temp.flush();
		}	catch(IOException e) {
			System.err.println("Could not requestResponds - write - CRUD and data !");
		}

		try {
			this.createFILE(temp, f);
		}	catch (FileNotFoundException e) {
			System.err.println("Could not requestResponds - write - FileNotFound: " + f.getName() + " !");
		}	catch (IOException e) {
			System.err.println("Could not requestResponds - write - File !");
		}

	}
	private void requestResponds(Socket socket, String data, File... files) {
		DataOutputStream temp = this.getDataOutputStream(socket);
		StringBuilder sb = new StringBuilder();
		this.createCRUD(CRUD.REQUEST, sb);
		this.createDATA(data, true, sb);

		try {
			temp.write(this.stringBuilderToBytes(sb));
			temp.flush();
		}	catch(IOException e) {
			System.err.println("Could not requestResponds - write - CRUD and data !");
		}
		
		for (File f : files) {
			try {
				this.createFILE(temp, f);
			}	catch (FileNotFoundException e) {
				System.err.println("Could not requestResponds - write - FileNotFound: " + f.getName() + " !");
			}	catch (IOException e) {
				System.err.println("Could not requestResponds - write - File !");
			}
		}
	}
	private void pingResponds(Socket socket, String data) {
		DataOutputStream temp = this.getDataOutputStream(socket);
		StringBuilder sb = new StringBuilder();
		this.createCRUD(CRUD.PING, sb);
		this.createDATA(data, false, sb);
		try {
			temp.write(this.messageToBytes(sb.toString()));
			temp.flush();
		}	catch (IOException e) {
			System.err.println("Could not pingResponds - write !");
		}
	}
	private void errorResponds(Socket socket, String data) {
		DataOutputStream temp = this.getDataOutputStream(socket);
		StringBuilder sb = new StringBuilder();
		this.createCRUD(CRUD.ERROR, sb);
		this.createDATA(data, false, sb);
		try {
			temp.write(this.messageToBytes(sb.toString()));
			temp.flush();
		}	catch (IOException e) {
			System.err.println("Could not pingResponds - write !");
		}
	}
	public void respond(Socket socket, CRUD cr, String data) {
		switch(cr.getValue()) {
		case 1:		/*  CRUD.COMMIT */
			this.commitResponds(socket, data);
			break;
		case 2:		/* CRUD.REQUEST */
			this.requestResponds(socket, data);
			break;
		case 3:		/* CRUD.PING */
			this.pingResponds(socket, data);
			break;
		default:	/* CRUD.ERROR */
			this.errorResponds(socket, data);
			throw new IllegalArgumentException("CRUD.ERROR cannot be resolved to an action !");
		}
	}
	public void respond(Socket socket, CRUD cr, String data, File f) {
		switch(cr.getValue()) {
		case 1:		/*  CRUD.COMMIT */
			this.commitResponds(socket, data);
			break;
		case 2:		/* CRUD.REQUEST */
			this.requestResponds(socket, data, f);
			break;
		case 3:		/* CRUD.PING */
			this.pingResponds(socket, data);
			break;
		default:	/* CRUD.ERROR */
			this.errorResponds(socket, data);
			throw new IllegalArgumentException("CRUD.ERROR cannot be resolved to an action !");
		}
	}
	public void respond(Socket socket, CRUD cr, String data, File... files) {
		switch(cr.getValue()) {
		case 1:		/*  CRUD.COMMIT */
			this.commitResponds(socket, data);
			break;
		case 2:		/* CRUD.REQUEST */
			this.requestResponds(socket, data, files);
			break;
		case 3:		/* CRUD.PING */
			this.pingResponds(socket, data);
			break;
		default:	/* CRUD.ERROR */
			this.errorResponds(socket, data);
			throw new IllegalArgumentException("CRUD.ERROR cannot be resolved to an action !");
		}
	}
	
	public synchronized void logIt(Socket socket, String performedAction) {
		LogFile lf = new LogFile(Configuration.LOGFILEPATH);
		lf.logIt(socket, performedAction);
	}
	public synchronized void logIt(boolean completed) {
		LogFile lf = new LogFile(Configuration.LOGFILEPATH);
		lf.logIt(completed);
	}

	public void respond(Socket socket, String message) {
		synchronized (this.connections) {
			DataOutputStream temp = this.getDataOutputStream(socket);
			try {
				temp.write(this.messageToBytes(message));
				temp.flush();
			}	catch (IOException io) {
				System.err.println("Could not send respond to Socket ! Error: " + io.getMessage());
			}
		}
	}

	private DataOutputStream getDataOutputStream(Socket socket) {
		DataOutputStream temp = this.connections.get(socket);

		if (temp == null) {
			throw new NullPointerException("Could not find the Socket");
		}	else {
			return temp;
		}
	}
	/**
	 * Checks if the specified Socket is in the list of connections.
	 * @param socket - the specified Socket
	 * @return true, if the Socket exists, otherwise false
	 */
	private boolean isConnected(Socket socket) {
		return this.connections.containsKey(socket);
	}
	/**
	 * Removes a Socket connection based on the specified
	 * @param socket - the specified Socket
	 */
	public void removeConnection(Socket socket) {
		synchronized (this.connections) {
			System.out.println("Disconnecting Socket: " + socket);

			//Safety check
//			if (this.isConnected(socket)) {
//				throw new NullPointerException("The connection did not exist !");
//			}	else {
//				this.connections.remove(socket);
//			}
			this.connections.remove(socket);

			try {
				//Closing the connection
				socket.close();
			}	catch (IOException e) {
				System.err.println("Could not close Socket: " + socket);
			}
		}
	}

}

