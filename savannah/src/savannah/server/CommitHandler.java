package savannah.server;

import java.util.ArrayList;

import org.jdom.Document;
import org.jdom.JDOMException;

public class CommitHandler {
	private ArrayList<String> queries;
	private QueryBuilder qbuilder;
	public CommitHandler()
	{
		qbuilder = new QueryBuilder();
		queries= new ArrayList<String>();
	}
	
	public void HandleIt(CommitEvent e)
	{
	try{
			Document xml = e.getEventContent();
			queries = qbuilder.buildQueries(xml);
			
			//TODO Run all queries and respond to sender, success or not
		}
	catch (JDOMException f)
		{ f.printStackTrace(); }
	}

	public ArrayList<String> getQueries()
	{
		return queries;
	}
}
