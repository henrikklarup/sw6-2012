package savannah.server;

//JDOM imports
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.xpath.*;


public class EventHandler implements Runnable {
	public void EventHandler() {
		EventQueue eque = EventQueue.getInstance();
		while(eque.isEmpty()) {
			while(!eque.isEmpty()) {
				Event e = eque.remove();
				if (e.getClass().equals(savannah.server.CommitEvent.class))
				{
					CommitHandler((CommitEvent)e);
				}
				else if(e.getClass().equals(savannah.server.RequestEvent.class))
				{
					RequestHandler((RequestEvent)e);
				}
			}
		}
	}

	//TODO Stub, implement later
	public void CommitHandler(CommitEvent e)
	{
	try{
		Document xml = e.getEventContent();
		XPath xp = XPath.newInstance("sw6ml/Department/Entry");
		
		}
	catch (JDOMException f)
	{f.printStackTrace();}
	}
	
	//TODO Stub, implement later.
	public void RequestHandler(RequestEvent e)
	{
		
	}
	
	@Override
	public void run() {
		EventHandler();
	}
}
