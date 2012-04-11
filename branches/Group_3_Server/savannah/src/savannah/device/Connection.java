package savannah.device;

import java.net.Socket;
import java.net.UnknownHostException;

import java.io.IOException;
import java.lang.IllegalStateException;


public class Connection {
	//Field Variables
	private volatile int state			= -1;
	private boolean available 			= true;
	private Socket socket				= null;
	
	/**
	 * 
	 * @throws UnknownHostException - if the host cannot be found
	 * @throws IOException - if there cannot be established a connection to the host
	 */
	public Connection() throws UnknownHostException, IOException {
		this.state = 0;
		this.socket = new Socket(ConnectionConfiguration.HOSTNAME, ConnectionConfiguration.PORT);
		
	}
	
	
	//IllegalStateException ?
	public synchronized void Commit() {
		
	}
	
	public void Request() {
		
	}
}
