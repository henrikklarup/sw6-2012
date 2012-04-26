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
		System.out.println("event added to que");
		eque.add(e);
	}
	
	public synchronized Event remove()
	{
		System.out.println("event removed from que");
		return eque.remove();
	}
	
	public boolean isEmpty()
	{	
		return eque.isEmpty();
	}
}
