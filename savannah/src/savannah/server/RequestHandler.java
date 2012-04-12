package savannah.server;

public class RequestHandler {
	
	public RequestHandler()
	{
		qbuilder = new QueryBuilder();
	}
	private QueryBuilder qbuilder;
	
	public void HandleIt(RequestEvent e)
	{
		String s = e.getEventContent();
		qbuilder.buildQueries(s);
	}
}
