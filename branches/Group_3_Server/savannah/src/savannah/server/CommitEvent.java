package savannah.server;

import java.net.Socket;
import org.jdom.Document;

public class CommitEvent implements Event {
	private Document event;
	private Socket socket;
	public CommitEvent(Document d,Socket s)
	{
		this.event = d;
		this.socket = s;
	}
	
	public Document getEventContent()
	{
		return event;
	}

	@Override
	public Socket getEventsocket() {
		return socket;
	}
	
}
