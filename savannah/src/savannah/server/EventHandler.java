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
		System.out.println("not whileing any true in the eventhandler");
		while(true) {
			System.out.println("whileing some true in eventhandler");
			while(!eque.isEmpty()) {
				System.out.println("Que is not Empty!");
				Event e = eque.remove();
				if (e.getClass().equals(savannah.server.CommitEvent.class))
				{
					cHandler.HandleIt((CommitEvent)e);
					((CommitEvent)e).getCom().lockDisengage();
				}
				else if(e.getClass().equals(savannah.server.RequestEvent.class))
				{
					rHandler.HandleIt((RequestEvent)e);
					((RequestEvent)e).getCom().lockDisengage();
				}
			}
		}	
	}


	@Override
	public void run() {
		System.out.println("Starting the eventhandler");
		start();
	}
}