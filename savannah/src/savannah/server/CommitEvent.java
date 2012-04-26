package savannah.server;

import java.io.File;
import java.net.Socket;
import java.util.ArrayList;

import org.jdom.Document;

import savannah.io.CommunicationThread;

public class CommitEvent implements Event {
	
	private Document event;
	private Socket socket;
	private CommunicationThread com1;
	public ArrayList<File> files = null;
	
	public CommitEvent(Document d,Socket s,CommunicationThread com)
	{
		this.event = d;
		this.socket = s;
		if(!(com.equals(null)))
		{
			this.com1 = com;
		}
	
	}
	
	public Document getEventContent()
	{
		return event;
	}
	
	public void addFile(File e)
	{
		
		if(files == null) {
			this.files = new ArrayList<File>();
		}
		this.files.add(e);
	}
	
	public void removeFile(File e)
	{
		if(files.contains(e))
		{
			files.remove(e);
		}
	}
	
	public ArrayList<File> getFileList()
	{
		return files;
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
