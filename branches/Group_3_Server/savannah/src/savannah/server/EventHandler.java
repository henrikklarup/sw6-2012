package savannah.server;

import java.sql.SQLException;

public class EventHandler implements Runnable {
	private CommitHandler cHandler;
	private RequestHandler rHandler;
	public EventHandler()
	{
		try {
			cHandler = new CommitHandler();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rHandler = new RequestHandler();
	}
	public void start() {
		
		EventQueue eque = EventQueue.getInstance();
		while(eque.isEmpty()) {
			while(!eque.isEmpty()) {
				Event e = eque.remove();
				if (e.getClass().equals(savannah.server.CommitEvent.class))
				{
					cHandler.HandleIt((CommitEvent)e);
				}
				else if(e.getClass().equals(savannah.server.RequestEvent.class))
				{
					rHandler.HandleIt((RequestEvent)e);
				}
			}
		}	
	}


	@Override
	public void run() {
		start();
	}
}