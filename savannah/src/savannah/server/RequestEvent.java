package savannah.server;

import java.net.Socket;

import org.jdom.*;

public class RequestEvent implements Event {
	
	private String event;
	private Socket socket;
	public RequestEvent(String s,Socket sock)
	{
		this.event = s;
		this.socket = sock;
	}
	
	public String getEventContent()
	{
		return event;
	}

	@Override
	public Socket getEventsocket() {
		return socket;
	}

}
