package savannah.io;

import java.net.Socket;
import java.io.DataOutputStream;

public class ConnectionIO {
	//Field variables
	private DataOutputStream inputStream;
	private Socket socket;
	
	/**
	 * Creates a ConnectionIO that wraps a specified Socket and a specified DataOutputStream.
	 * @param _socket - the specified Socket
	 * @param _outputStream - the specified DataOutputStream
	 */
	public ConnectionIO(Socket _socket, DataOutputStream _outputStream) {
		this.socket = _socket;
		this.inputStream = _outputStream;
	}
	
	/**
	 * Gets DataOutputStream of the ConnectionIO.
	 * @return - the ConnectionIO's DataOutputStream
	 */
	public DataOutputStream getOutputStream() {
		return this.inputStream;
	}
	
	/**
	 * Gets the Socket of the ConnectionIO.
	 * @return - the ConnectionIO's Socket
	 */
	public Socket getSocket() {
		return this.socket;
	}
	
}