package savannah.server;

import java.io.File;
import java.net.Socket;
import java.util.ArrayList;

import org.jdom.Document;

public class CommitEvent implements Event {
	
	private Document event;
	private Socket socket;
	public ArrayList<File> files = null;
	
	public CommitEvent(Document d,Socket s,Boolean any_files)
	{
		this.event = d;
		this.socket = s;
		if(any_files) {
			this.files = new ArrayList<File>();
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
}
