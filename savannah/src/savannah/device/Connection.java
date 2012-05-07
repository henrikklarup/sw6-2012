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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private String bytesToString(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			sb.append((char)b[i]);
		}
		return sb.toString();
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
	private String pingLength(int in) {
		String result = "";
		int maxPingSize = 4;
		for (int i = 0; i < (maxPingSize - this.sizeOf(in)); i++) {
			result+= "0";
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

	private void sendFILE(OutputStream os, File f, boolean moreFiles) throws FileNotFoundException, IOException {
		InputStream is = new FileInputStream(f);
		StringBuilder sb = new StringBuilder();

		//Writing "header-START"
		int fileFlag = (moreFiles == true) ? 1 : 0;
		sb.append("FILE[" + this.fileLength(f.length()) + "," + this.fileName(f.getName()) + "," + fileFlag + "]=\"");
		os.write(this.stringBuilderToBytes(sb));

		//Writing content
		long bufCalc = f.length();
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
	private String bytesToString(char[] c) {
		StringBuilder sb = new StringBuilder();
		sb.append(c);
		return sb.toString();
	}
	//Improve the CRUD check.............................
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
	private void makeXML(String XML, boolean anyFiles, StringBuilder builder) {
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
			builder.append("MXML[" + this.xmlLength(XML.length()) + "," + files + "]=\"");
			builder.append(XML);
			builder.append("\"");
		}	catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}	catch (NullPointerException e) {
			System.err.println(e.getMessage());
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

			builder.append("PING[" + this.pingLength(pingSize) + "]=\"");
			Random rand = new Random();
			byte[] buf = new byte[randomBytes];
			rand.nextBytes(buf);
			builder.append(this.bytesToString(this.bytesToChars(buf)));
			builder.append("\"");

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

	
	private final CRUD CR(InputStream is) throws IOException {
		/*
			-------------------------
					CRUD
			-------------------------
			crudLength	= 1
			tag-stuff	= 6
			total		= 7
		 */
		is = new DataInputStream(is);
		byte[] buf = new byte[7];
		int len = is.read(buf);
		String data = this.bytesToString(buf);
		
		Pattern expr = Pattern.compile("\\[[0-9]\\]");
		Matcher match = expr.matcher(data);
		if (match.find() != true) {
			throw new IllegalStateException("Could not find CRUD !");
		}
//		String result = data.substring(match.start() + 1, match.end() - 1);

		switch(Integer.parseInt(data.substring(match.start() + 1, match.end() - 1))) {
		case 1:
			return CRUD.COMMIT;
		case 2:
			return CRUD.REQUEST;
		case 3:
			return CRUD.PING;
		default:
			return CRUD.ERROR;
		}
	}
	private void receivePing(InputStream reader) {
		
	}
	
	
	
	private long sendPackage(OutputStream writer, int pingSize) throws IOException {
		StringBuilder sb = new StringBuilder();
		this.makeCRUD(CRUD.PING, sb);
		this.makePing(sb, pingSize);
		long init = -1;
		long end = -1;
		
		init = System.currentTimeMillis();
		writer.write(this.stringBuilderToBytes(sb));
		end = System.currentTimeMillis();
		writer.flush();
		
		return (end - init);
	}
	private void sendPackage(OutputStream writer, CRUD cr, String data) throws IOException {
		StringBuilder sb = new StringBuilder();
		this.makeCRUD(cr, sb);
		this.makeXML(data, false, sb);
		this.makeACCEPT(sb);

		writer.write(this.stringBuilderToBytes(sb));
		writer.flush();
	}
	private void sendPackage(OutputStream writer, CRUD cr, String data, File f) throws IOException {
		StringBuilder sb = new StringBuilder();
		this.makeCRUD(cr, sb);
		this.makeXML(data, true, sb);

		writer.write(this.stringBuilderToBytes(sb));
		this.sendFILE(writer, f, false);
		writer.flush();

		sb = null;
		sb = new StringBuilder();
		this.makeACCEPT(sb);
		writer.write(this.stringBuilderToBytes(sb));
		writer.flush();
	}
	private void sendPackage(OutputStream writer, CRUD cr, String data, File... files) throws IOException {
		StringBuilder sb = new StringBuilder();
		this.makeCRUD(cr, sb);
		this.makeXML(data, true, sb);

		writer.write(this.stringBuilderToBytes(sb));
		for (int i = 0; i < files.length; i++) {
			if ( (i + 1 ) >= files.length ) {
				//Last file is set to false -> no more files are comming
				this.sendFILE(writer, files[i], false);
				writer.flush();
			}	else {
				this.sendFILE(writer, files[i], true);
				writer.flush();
			}
		}

		sb = null;
		sb = new StringBuilder();
		this.makeACCEPT(sb);
		writer.write(this.stringBuilderToBytes(sb));
		writer.flush();
	}


	public String sendCommit(String xml, File... files) throws IOException {
		//Sending the commit event
		this.writer = new DataOutputStream(this.socket.getOutputStream());
		this.sendPackage(this.writer, CRUD.COMMIT, xml, files);

		//Receiving the responds
		TransmissionHandler th = new TransmissionHandler(this.socket, this.folderPath);

		//Cleanup crew...
		this.writer.flush();
		this.writer.close();

		return th.getXML();
	}
	public String sendCommit(String xml, File f) throws IOException {
		//Sending the commit event
		this.writer = new DataOutputStream(this.socket.getOutputStream());
		this.sendPackage(this.writer, CRUD.COMMIT, xml, f);

		//Receiving the responds
		TransmissionHandler th = new TransmissionHandler(this.socket, this.folderPath);

		//Cleanup crew...
		this.writer.flush();
		this.writer.close();

		return th.getXML();
	}
	public String sendCommit(String xml) throws IOException {
		//Sending the commit event
		this.writer = new DataOutputStream(this.socket.getOutputStream());
		this.sendPackage(this.writer, CRUD.COMMIT, xml);

		//Receiving the responds
		TransmissionHandler th = new TransmissionHandler(this.socket, this.folderPath);

		//Cleanup crew...
		this.writer.flush();
		this.writer.close();

		return th.getXML();
	}
	//Make the sendPing function receive a TransmissionHandler instead !
	public long sendPing(int pingSize) throws IOException {
		this.writer = new DataOutputStream(this.socket.getOutputStream());
		this.reader = new DataInputStream(this.socket.getInputStream());
		
		long timeSend, timeRespond = -1;
		timeSend = this.sendPackage(this.writer, pingSize); 		


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
		//Sending the request event
		this.writer = new DataOutputStream(this.socket.getOutputStream());
		this.sendPackage(this.writer, CRUD.REQUEST, xml);

		//Waiting for Server confirmation
		TransmissionHandler th = new TransmissionHandler(this.socket, this.folderPath);

		//Cleanup crew...
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
