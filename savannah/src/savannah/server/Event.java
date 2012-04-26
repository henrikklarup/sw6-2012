package savannah.server;

import java.net.Socket;

import savannah.io.CommunicationThread;

public interface Event {
	public Socket socket = null;
	public CommunicationThread com = null;
	
	public Socket getEventsocket();
	public Event getEventType();
	public CommunicationThread getCom();
	
}
