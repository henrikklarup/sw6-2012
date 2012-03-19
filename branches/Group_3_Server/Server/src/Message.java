import java.net.ServerSocket;
import java.net.Socket;

import java.io.IOException;
import java.io.EOFException;

public class Message {

	public static String ServerStartup() {
		return "Server Startup Complete";
	}
	public static String ServerShutdown() {
		return "Server is going to Shutdown";
	}

	
	public static String ServerListen(int port) {
		return "Listening on port: " + port;
	}
	public static String ServerConnection(ServerSocket serverSocket) {
		return "Connection from socket: " + serverSocket;
	}
	public static String ServerDisconnection(Socket s) {
		return "Disconnecting the socket: " + s;
	}


	public static String ServerCreating() {
		return "Creating something in the database";
	}
	public static String ServerReading() {
		return "Reading something from the database";
	}
	public static String ServerUpdating() {
		return "Updating something in the database";
	}
	public static String ServerDeleting() {
		return "Deleting something in the database";
	}


	public static String ServerIOExceptionSocket(Socket s) {
		return "Error while closing socket: " + s;
	}
	public static String ServerIOExceptionMessage(IOException ie) {
		return "Error while sending the message! IOException: " + ie;
	}


	public static String ServerThreadSending(String message) {
		return "Sending message: " + message;
	}
	public static String ServerThreadEOFException(EOFException eof) {
		return "Error in while retrieving the message ! EOFException: " + eof;
	}


	public static String ClientConnection(Socket s) {
		return "Connected to the Server on socket: " + s;
	}
	public static String ClientDisconnection(Socket s) {
		return "Disconnecting from the Server on socket: " + s;
	}


	public static String ClientIOException(IOException ie) {
		return "Could not connect Streams to the Server ! IOException: " + ie;
	}
}