package savannah.io;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Hashtable;
import java.util.Random;

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
				Thread comThread = new CommunicationThread(con, this.folder);
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
	private String messageToString(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			sb.append((char)b[i]);
		}
		return sb.toString();
	}
	private byte[] stringBuilderToBytes(StringBuilder sb) {
		char[] buf = new char[sb.length()];
		sb.getChars(0, sb.length(), buf, 0);
		return this.messageToBytes(buf);
	}
	private int sizeOf(int in) {
		return new String("" + in).length();
	}
	private int sizeOf(long in) {
		return new String("" + in).length();
	}
	private String xmlLength(int in) {
		String result = "";
		int maxIntSize = 10;
		for (int i = 0; i < (maxIntSize - this.sizeOf(in)); i++) {
			result += "0";
		}
		result += in;
		return result;
	}
	private String fileLength(long in) {
		String result = "";
		int maxLongSize = 19;
		for (int i = 0; i < (maxLongSize - this.sizeOf(in)); i++) {
			result += "0";
		}
		result += in;
		return result;
	}
	private String fileName(String fileName) {
		StringBuilder sb = new StringBuilder();
		int maxFileNameSize = 256;
		for (int i = 0; i < (maxFileNameSize - fileName.length()); i++) {
			sb.append(" ");
		}
		sb.append(fileName);
		return sb.toString();
	}
	private String pingLength(int in) {
		String result = "";
		int maxPingSize = 4;
		for (int i = 0; i < (maxPingSize - this.sizeOf(in)); i++) {
			result+= "0";
		}
		result += in;
		return result;
	}
	
	private void sendPackage(OutputStream writer, int pingSize) {
		StringBuilder sb = new StringBuilder();
		this.makeCRUD(CRUD.PING, sb);
		this.makePing(sb, pingSize);
		
		try {
			writer.write(this.stringBuilderToBytes(sb));
			writer.flush();
		}	catch (IOException e) {
			System.err.println("sendPackage: Could not write CRUD and PING !");
		}
		
	}
	private void sendPackage(OutputStream writer, CRUD cr, String data) {
		StringBuilder sb = new StringBuilder();
		this.makeCRUD(cr, sb);
		this.makeXML(data, false, sb);
		this.makeACCEPT(sb);
		
		try {
			writer.write(this.stringBuilderToBytes(sb));
			writer.flush();
		}	catch (IOException e) {
			System.err.println("sendPackage: Could not write CRUD, XML, ACCEPT !");
		}
	}
	private void sendPackage(OutputStream writer, CRUD cr, String data, File f) {
		StringBuilder sb = new StringBuilder();
		this.makeCRUD(cr, sb);
		this.makeXML(data, true, sb);
		try {
			writer.write(this.stringBuilderToBytes(sb));
		}	catch (IOException e) {
			System.err.println("sendPackage: Could not write CRUD and XML !");
		}
		try {
			this.sendFILE(writer, f, false);
			writer.flush();
		}	catch (FileNotFoundException e) {
			System.err.println("File not found: " + f.getName() + ", path: " + f.getAbsolutePath());
		}	catch (IOException e) {
			System.err.println("sendPackage: Could not write or flush for function - sendFile !");
		}
		
		sb = null;
		sb = new StringBuilder();
		this.makeACCEPT(sb);
		try {
			writer.write(this.stringBuilderToBytes(sb));
			writer.flush();
		}	catch (IOException e) {
			System.err.println("sendPackage: Could not write ACCEPT !");
		}
	}
	private void sendPackage(OutputStream writer, CRUD cr, String data, File... files) {
		StringBuilder sb = new StringBuilder();
		this.makeCRUD(cr, sb);
		this.makeXML(data, true, sb);
		
		try {
			writer.write(this.stringBuilderToBytes(sb));
		}	catch (IOException e) {
			System.err.println("sendPackage: Could not write CRUD and XML !");
		}
		
		for (int i = 0; i < files.length; i++) {
			if ( (i + 1 ) >= files.length ) {
				//Last file is set to false -> no more files are comming
				try {
					this.sendFILE(writer, files[i], false);
					writer.flush();
				}	catch (FileNotFoundException e) {
					System.err.println("File not found: " + files[i].getName() + ", path: " + files[i].getAbsolutePath());
				}	catch (IOException e) {
					System.err.println("sendPackage: Could not write or flush for function - sendFile !");
				}
			}	else {
				try {
					this.sendFILE(writer, files[i], true);
					writer.flush(); 
				}	catch (FileNotFoundException e) {
					System.err.println("File not found: " + files[i].getName() + ", path: " + files[i].getAbsolutePath());
				}	catch (IOException e) {
					System.err.println("sendPackage: Could not write or flush for function - sendFile !");
				}
			}
		}
		
		sb = null;
		sb = new StringBuilder();
		this.makeACCEPT(sb);
		try {
			writer.write(this.stringBuilderToBytes(sb));
			writer.flush();
		}	catch (IOException e) {
			System.err.println("sendPackage: Could not write ACCEPT !");
		}
	}
	
	private void makePing(StringBuilder builder, int pingSize) {
		try {
			if (builder == null) {
				throw new NullPointerException("builder: Cannot be null !");
			}
			
			int packageOffset = 20;
			int randomBytes = -1;

			if (pingSize + packageOffset <= 4096) {
				if (pingSize - packageOffset >= 1) {
					randomBytes = pingSize;
				}	else {
					randomBytes = 12;
				}
			}	else {
				randomBytes = 12;
			}

			builder.append("PING[" + this.pingLength(randomBytes) + "]=\"");
			Random rand = new Random();
			byte[] buf = new byte[randomBytes];
			rand.nextBytes(buf);
			builder.append(this.messageToString(buf));
			builder.append("\"");

		}	catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}
	}
	private void makeCRUD(CRUD cr, StringBuilder builder) {
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
	private void makeXML(String data, boolean anyFiles, StringBuilder builder) {
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
			builder.append("MXML[" + this.xmlLength(data.length()) + "," + files + "]=\"");
			builder.append(data);
			builder.append("\"");
		}	catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}	catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}
	}
	private void makeACCEPT(StringBuilder builder) {
		try {
			if (builder == null) {
				throw new NullPointerException("builder: Cannot be null !");
			}
			builder.append("[ACCEPT]");
		}	catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}
	}
	private void sendFILE(OutputStream os, File f, boolean moreFiles) throws FileNotFoundException, IOException {
		InputStream is = new FileInputStream(f);
		StringBuilder sb = new StringBuilder();

		//Writing "header-START"
		int fileFlag = (moreFiles == true) ? 1 : 0;
		sb.append("FILE[" + this.fileLength(f.length()) + "," + this.fileName(f.getName()) + "," + fileFlag + "]=\"");
		os.write(this.stringBuilderToBytes(sb));

		//Writing content
		long bufCalc = f.length();
		System.out.println("bufCalc = " + bufCalc);
		byte[] buf = new byte[this.bufferSize];
		int len;
		while (bufCalc > 0) {
			if (bufCalc > this.bufferSize) {
				buf = new byte[this.bufferSize];
				len = is.read(buf);
				os.write(buf);
				bufCalc -= len;
			}
			else {
				buf = new byte[(int)bufCalc];
				len = is.read(buf);
				os.write(buf);
				bufCalc = 0;
			}
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
		try {
			this.sendPackage(new DataOutputStream(socket.getOutputStream()), CRUD.COMMIT, data);
		}	catch (IOException e) {
			System.err.println("commitResponds: Could not establish an OutputStream !");
		}
	}
	private void requestResponds(Socket socket, String data) {
		try {
			this.sendPackage(new DataOutputStream(socket.getOutputStream()), CRUD.REQUEST, data);
		}	catch (IOException e) {
			System.err.println("requestResponds: Could not establish an OutputStream !");
		}
	}
	private void requestResponds(Socket socket, String data, File f) {
		try {
			this.sendPackage(new DataOutputStream(socket.getOutputStream()), CRUD.REQUEST, data, f);
		}	catch (IOException e) {
			System.err.println("requestResponds: Could not establish an OutputStream !");
		}
	}
	private void requestResponds(Socket socket, String data, File... files) {
		try {
			this.sendPackage(new DataOutputStream(socket.getOutputStream()), CRUD.REQUEST, data, files);
		}	catch (IOException e) {
			System.err.println("requestResponds: Could not establish an OutputStream !");
		}
	}
	private void pingResponds(Socket socket, int pingSize) {
		try {
			this.sendPackage(new DataOutputStream(socket.getOutputStream()), pingSize);
		}	catch (IOException e) {
			System.err.println("pingResponds: Could not establish an OutputStream !");
		}
	}
	private void errorResponds(Socket socket, String data) {
		try {
			this.sendPackage(new DataOutputStream(socket.getOutputStream()), CRUD.ERROR, data);
		}	catch (IOException e) {
			System.err.println("errorResponds: Could not establish an OutputStream !");
		}
	}
	public void respond(Socket socket, int pingSize) {
		/* CRUD.PING */
		this.pingResponds(socket, pingSize);
	}
	public void respond(Socket socket, CRUD cr, String data) {
		switch(cr.getValue()) {
		case 1:		/*  CRUD.COMMIT */
			this.commitResponds(socket, data);
			break;
		case 2:		/* CRUD.REQUEST */
			this.requestResponds(socket, data);
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
		default:	/* CRUD.ERROR */
			this.errorResponds(socket, data);
			throw new IllegalArgumentException("CRUD.ERROR cannot be resolved to an action !");
		}
	}

	/*
	public synchronized void logIt(Socket socket, String performedAction) {
		LogFile lf = new LogFile(Configuration.LOGFILEPATH);
		lf.logIt(socket, performedAction);
	}
	public synchronized void logIt(List<File> list) {
		LogFile lf = new LogFile(Configuration.LOGFILEPATH);
		lf.logIt(list);
	}
	public synchronized void logIt(boolean completed) {
		LogFile lf = new LogFile(Configuration.LOGFILEPATH);
		lf.logIt(completed);
	}
	*/
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

