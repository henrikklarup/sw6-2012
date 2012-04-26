package savannah.server;

import java.net.Socket;

import org.jdom.*;

import savannah.io.CommunicationThread;

public class RequestEvent implements Event {
	
	private String event;
	private Socket socket;
	public CommunicationThread com1 = null;
	public RequestEvent(String s,Socket sock,CommunicationThread com)
	{
		this.event = s;
		this.socket = sock;
		if(!(com.equals(null)))
		{
			this.com1 = com;
			com1.lockEngage();
		}
		
	}
	
	public String getEventContent()
	{
		return event;
	}

	@Override
	public Socket getEventsocket() {
		return socket;
	}

	@Override
	public Event getEventType() {
		return this;
	}

	@Override
	public CommunicationThread getCom() {
		return com1;
	}
	

}
