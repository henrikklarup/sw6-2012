package savannah.io;

import java.net.Socket;

import java.io.DataOutputStream;

public class ConnectionIO {
	private DataOutputStream inputStream;
	private Socket socket;
	
	public ConnectionIO(Socket _socket, DataOutputStream _outputStream) {
		this.socket = _socket;
		this.inputStream = _outputStream;
	}
	
	public DataOutputStream getOutputStream() {
		return this.inputStream;
	}
	public Socket getSocket() {
		return this.socket;
	}
	
}