package savannah.device;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.DataInputStream;

import java.io.OutputStream;
import java.io.DataOutputStream;

import java.io.File;

import java.util.Random;

import java.net.Socket;
import java.net.UnknownHostException;

import savannah.io.CRUD;
import savannah.io.TransmissionHandler;

public class Connection {
	//Field Variables
	private Socket socket				= null;
	private InputStream reader			= null;
	private OutputStream writer			= null;
	private String folderPath			= "";
	private File folder					= null;
	private int bufferSize 				= -1;

	private static final int PINGSIZE 	= 32;

	//Default for bufferSize is ConnectionConfiguration.BUFFERSIZE
	public Connection(String folderPath, int bufferSize) throws UnknownHostException, IOException {
		if (folderPath.equals("") == true) {
			throw new IllegalArgumentException("folderPath: Cannot be null !");
		}
		if (this.bufferSize <= 0) {
			this.bufferSize = ConnectionConfiguration.BUFFERSIZE;
		}
		else {
			this.bufferSize = bufferSize;
		}
		this.folderPath = folderPath;
		this.socket = new Socket(ConnectionConfiguration.HOSTNAME, ConnectionConfiguration.PORT);
	}
	public Connection(String folderPath) throws UnknownHostException, IOException {
		if (folderPath.equals("") == true) {
			throw new IllegalArgumentException("folderPath: Cannot be null !");
		}
		this.folderPath = folderPath;
		this.bufferSize = ConnectionConfiguration.BUFFERSIZE;
		this.socket = new Socket(ConnectionConfiguration.HOSTNAME, ConnectionConfiguration.PORT);
	}

	
//	private final boolean makeFolder(String folderPath) {
//		File dir = new File(folderPath);
//		boolean success = false;
//		try {
//			if (dir.exists() == false) {
//				success = dir.mkdir();
//				if (success == false) {
//					throw new IOException("folderPath: Could not create folder !");
//				}
//			}	else {
//				this.folder = dir;
//				success = true;
//			}
//		}	catch (IOException e) {
//			System.err.println(e.getMessage());
//		}
//		return success;
//	}
	private byte[] stringBuilderToBytes(StringBuilder sb) {
		char[] buf = new char[sb.length()];
		sb.getChars(0, sb.length(), buf, 0);
		return this.charsToBytes(buf);
	}
	private byte[] charsToBytes(char[] c) {
		byte[] buf = new byte[c.length];
		for (int i = 0; i < c.length; i++) {
			buf[i] = (byte)c[i];
		}
		return buf;
	}
	private char[] bytesToChars(byte[] b) {
		char[] buf = new char[b.length];
		for (int i = 0; i < b.length; i++) {
			buf[i] = (char)b[i];
		}
		return buf;
	}

	private void sendFile(OutputStream os, File f) throws FileNotFoundException, IOException {
		InputStream is = new FileInputStream(f);
		StringBuilder sb = new StringBuilder();
		byte[] buf = new byte[this.bufferSize];
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
	private String bytesToString(char[] c) {
		StringBuilder sb = new StringBuilder();
		sb.append(c);
		return sb.toString();
	}
	//Improve the CRUD check.............................
	private void getCRUD(CRUD cr, StringBuilder builder) {
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
	private void getXML(String XML, boolean anyFiles, StringBuilder builder) {
		try {
			//Exception handling 
			if (XML.equals("") == true) {
				throw new IllegalArgumentException("XML: Cannot be null !");
			}
			if (builder == null) {
				throw new NullPointerException("builder: Cannot be null !");
			}
			int files = (anyFiles == true) ? 1 : 0; 
			//Adding the data
			builder.append("MXML[" + XML.length() + "," + files + "]=\"");
			builder.append(XML);
			builder.append("\"");
		}	catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}	catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}
	}
	private void getACCEPT(StringBuilder builder) {
		try {
			if (builder == null) {
				throw new NullPointerException("builder: Cannot be null !");
			}
			builder.append("[ACCEPT]");
		}	catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}
	}

	public String sendCommit(String xml, File... files) throws IOException {
		this.writer = new DataOutputStream(this.socket.getOutputStream());
		this.reader = new DataInputStream(this.socket.getInputStream());
		StringBuilder sb = new StringBuilder();

		//Writing the first part of the package
		this.getCRUD(CRUD.COMMIT, sb);
		this.getXML(xml, true, sb);
		this.writer.write(this.stringBuilderToBytes(sb));

		//Writing any Files
		for (File f : files) {
			this.sendFile(this.writer, f);
		}
		this.writer.flush();
		
		//Writing acceptance
		this.getACCEPT(sb);
		this.writer.write(this.stringBuilderToBytes(sb));
		this.writer.flush();

		//Re-using "old" Objects
		sb = null;
		sb = new StringBuilder();

		//Waiting for Server confirmation
		byte[] buf = new byte[this.bufferSize];
		int len;
		while ((len = reader.read(buf)) > 0) {
			sb.append(this.bytesToChars(buf));
		}
		
		//Sending acceptance
		this.getACCEPT(sb);
		this.writer.write(this.stringBuilderToBytes(sb));

		//Cleanup crew...
		this.writer.close();
		//this.reader.close();

		return sb.toString();
	}
	public String sendCommit(String xml, File f) throws IOException {
		this.writer = new DataOutputStream(this.socket.getOutputStream());
		this.reader = new DataInputStream(this.socket.getInputStream());
		StringBuilder sb = new StringBuilder();

		//Writing the first part of the package
		this.getCRUD(CRUD.COMMIT, sb);
		this.getXML(xml, true, sb);
		this.writer.write(this.stringBuilderToBytes(sb));

		//Writing the File
		this.sendFile(this.writer, f);
		this.writer.flush();
		
		//Writing acceptance
		this.getACCEPT(sb);
		this.writer.write(this.stringBuilderToBytes(sb));
		this.writer.flush();

		//Re-using "old" Objects
		sb = null;
		sb = new StringBuilder();

		//Waiting for Server confirmation
		byte[] buf = new byte[this.bufferSize];
		int len;
		while ((len = reader.read(buf)) > 0) {
			sb.append(this.bytesToChars(buf));
		}

		//Cleanup crew...
		this.writer.close();
		//this.reader.close();

		return sb.toString();
	}
	public String sendCommit(String xml) throws IOException {
		System.out.println("Init");
		this.writer = new DataOutputStream(this.socket.getOutputStream());
		this.reader = new DataInputStream(this.socket.getInputStream());
		StringBuilder sb = new StringBuilder();
		System.out.println("Init - Done \n");
		
		//Writing the first part of the package
		this.getCRUD(CRUD.COMMIT, sb);
		this.getXML(xml, false, sb);
		this.getACCEPT(sb);
		System.out.println("Sending data !");
		this.writer.write(this.stringBuilderToBytes(sb));
		this.writer.flush();
		System.out.println("Sending data - Done !");

		//Re-using "old" Objects
		sb = null;
		sb = new StringBuilder();

		//Waiting for Server confirmation
		byte[] buf = new byte[this.bufferSize];
		int len;
		while ((len = this.reader.read(buf)) > 0) {
			sb.append(this.bytesToChars(buf));
		}

		//Cleanup crew...
		this.writer.close();
		//this.reader.close();

		return sb.toString();
	}
	//Make the sendPing function receive a TransmissionHandler instead !
	public long sendPing() throws IOException {
		this.writer = new DataOutputStream(this.socket.getOutputStream());
		this.reader = new DataInputStream(this.socket.getInputStream());
		Random rand = new Random();
		StringBuilder sb = new StringBuilder();
		byte[] buf = new byte[Connection.PINGSIZE];
		long timeSend, timeRespond = 0;
		System.out.println("buf - size: " + buf.length);
		//Creating the data package
		this.getCRUD(CRUD.PING, sb);
		rand.nextBytes(buf);
		this.getXML(this.bytesToString(this.bytesToChars(buf)), false, sb);

		//Sending the data to the Socket
		timeSend = System.currentTimeMillis();	//Time start
		this.writer.write(this.stringBuilderToBytes(sb));

		//Re-using the "old" structures
		buf = null;
		buf = new byte[this.bufferSize];
		int len = this.reader.read(buf);
		timeRespond = System.currentTimeMillis();	//Time stop

		//Cleanup crew...
		//this.reader.close();
		this.writer.flush();
		this.writer.close();

		return (timeRespond - timeSend);
	}

	public TransmissionHandler sendRequest(String xml) throws FileNotFoundException, IOException {
		this.writer = new DataOutputStream(this.socket.getOutputStream());
		this.reader = new DataInputStream(this.socket.getInputStream());
		StringBuilder sb = new StringBuilder();

		//Writing the first part of the package
		this.getCRUD(CRUD.REQUEST, sb);
		this.getXML(xml, false, sb);
		this.getACCEPT(sb);
		this.writer.write(this.stringBuilderToBytes(sb));

		//Re-using the "old" Objects
		sb = null;
		sb = new StringBuilder();

		//Waiting for Server confirmation
		TransmissionHandler th = new TransmissionHandler(this.socket, this.folderPath);

		//Cleanup crew...
		//this.reader.close();
		this.writer.flush();
		this.writer.close();

		return th;
	}
	//If this.reader.close() is called, then this will be epic fail !!!
	public InputStream getConnectionInputStream() throws IOException {
		return this.socket.getInputStream();
	}
	public String getFolderPath() {
		return this.folderPath;
	}
	public int getBufferSize() {
		return this.bufferSize;
	}
	public File getFolder() {
		return this.folder;
	}

}
