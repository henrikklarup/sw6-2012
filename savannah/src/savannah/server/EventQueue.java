package savannah.server;
import savannah.server.*;

//jdom imports
import java.util.LinkedList;
import java.util.Queue;
import org.jdom.*;


public class EventQueue{
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
	public synchronized void add(Event e)
	{
		eque.add(e);
	}
	
	public synchronized Event remove()
	{
		return eque.remove();
	}
	
	public boolean isEmpty()
	{
		return eque.isEmpty();
	}
}
