package savannah.server;
import savannah.server.*;

//jdom imports
import java.util.LinkedList;
import java.util.Queue;
import org.jdom.*;


public class EventQueue implements Runnable {
	private static EventQueue _que = null;
	
	private EventQueue() { }
	private Queue<Event> eque = new LinkedList<Event>();
	
	public static synchronized EventQueue getInstance()
	{
		if (_que == null)
		{
			_que = new EventQueue();
		}
		return _que;
	}
	
	//TODO Add documents to the event queue.
	private void add(Event e)
	{
		eque.add(e);
	}
	
	private Event remove()
	{
		return eque.remove();
	}
	
	//TODO NOI - stubs, consider refactor to seperate classs?
	public void EventHandler() {
		while(eque.isEmpty()) {
			while(!eque.isEmpty()) {
				Event e = remove();
				if (e.getClass().equals(savannah.server.CommitEvent.class))
				{
					//Then call commit handler
				}
				else if(e.getClass().equals(savannah.server.RequestEvent.class))
				{
					//Then call Request handler
				}
			}
		}
	}

	@Override
	public void run() {
		EventHandler();
	}
}
