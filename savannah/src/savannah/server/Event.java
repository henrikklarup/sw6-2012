package savannah.server;

import java.net.Socket;

public interface Event {
	public Socket socket = null;
	
	public Socket getEventsocket();
	
}
