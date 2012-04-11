package savannah.device;

import savannah.io.TransmissionPackage;
import savannah.io.TransmissionHandler;

import java.net.Socket;
import java.net.UnknownHostException;

import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.IOException;

import java.lang.StringBuilder;
import java.lang.IllegalStateException;

/**
 * I don't know if this class should implement Runnable....
 * @author Sir.Thundar
 *
 */
public class Connection {
	//Field Variables
	private volatile int state			= -1;
	private Socket socket				= null;
	private String folderPath			= "";
	
	/**
	 * 
	 * @throws UnknownHostException - if the host cannot be found
	 * @throws IOException - if there cannot be established a connection to the host
	 */
	public Connection(String folderPath) throws UnknownHostException, IOException {
		this.state = 0;
		this.folderPath = folderPath;
		this.socket = new Socket(ConnectionConfiguration.HOSTNAME, ConnectionConfiguration.PORT);
	}
	
	private synchronized void stateAvailable() {
		this.state = 0;
	}
	
	private synchronized void stateUnavailable() {
		this.state = 1;
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
	
	public synchronized boolean available() {
		//Return true if stm holds - else false
		return (this.state == 0) ? true : false;
	}
	
	public synchronized String Commit(TransmissionPackage tp) throws IllegalStateException, IOException {
		StringBuilder responds = new StringBuilder();
		if (this.state == 0) {
			this.stateUnavailable();
			OutputStream writer = new DataOutputStream(this.socket.getOutputStream());
			StringBuilder sb = tp.getPackage();
			
			//Converting StringBuilder to an array of bytes
			char[] buf = new char[sb.length()];
			sb.getChars(0, sb.length(), buf, 0);
			
			//Sending the data to the Socket and cleaning up.
			writer.write(charsToBytes(buf));
			writer.flush();
			writer.close();
			
			//Waiting for Server responds
			InputStream reader = new DataInputStream(this.socket.getInputStream());
			while (reader.available() <= 0) {
				//Dummy loop so we don't "skip" any data...
			}
			byte[] data = new byte[ConnectionConfiguration.BUFFERSIZE];
			int len;
			while ((len = reader.read(data)) > 0) {
				responds.append(this.bytesToChars(data));
			}
			
			this.stateAvailable();
		}	else {
//			this.state is something other than 0, then it becomes an IllegalState
			throw new IllegalStateException();
		}
		return responds.toString();
	}
	
	public synchronized TransmissionHandler Request(TransmissionPackage tp) throws IllegalStateException, IOException {
		TransmissionHandler th = null;
		if (this.state == 0) {
			this.stateUnavailable();
			OutputStream writer = new DataOutputStream(this.socket.getOutputStream());
			StringBuilder sb = tp.getPackage();
			
			//Converting StringBuilder to an array of bytes
			char[] buf = new char[sb.length()];
			sb.getChars(0, sb.length(), buf, 0);
			
			//Sending the data to the Socket and cleaning up.
			writer.write(charsToBytes(buf));
			writer.flush();
			writer.close();
			
			//Waiting for Server responds
			InputStream reader = new DataInputStream(this.socket.getInputStream());
			while (reader.available() <= 0) {
				//Dummy loop so we don't "skip" any data...
			}
			th = new TransmissionHandler(reader, ConnectionConfiguration.BUFFERSIZE, this.folderPath);
			
			this.stateAvailable();
		}	else {
//			this.state is something other than 0, then it becomes an IllegalState
			throw new IllegalStateException();
		}
		return th;
	}
}
