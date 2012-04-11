package savannah.server;

import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom.Document;
import org.jdom.JDOMException;

import savannah.io.IOHandler;

public class CommitHandler {
	private ArrayList<String> queries;
	private QueryBuilder qbuilder;
	private QueryHandler qHandler;
	private IOHandler ihandler;
	public CommitHandler() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		qbuilder = new QueryBuilder();
		queries= new ArrayList<String>();
		qHandler = new QueryHandler();
		ihandler = new IOHandler();
	}
	
	public void HandleIt(CommitEvent e)
	{
	try{
			Document xml = e.getEventContent();
		
			queries = qbuilder.buildQueries(xml);
			
			//TODO Run all queries and respond to sender, success or not
			int failed = 0;
			
			for(String s: queries)
			{
				 int result = qHandler.SendCommit(s);
				 if(result == 0)
				 {
					 failed++;
				 }
			}
			if(e.getEventsocket() != null)
			{
				ihandler.respond(e.getEventsocket(), "message to sender");
			}
			
			
			//TODO Send result
			
			failed = 0;
				
			
		}
	catch (JDOMException f)
		{ f.printStackTrace(); }
	}

	public ArrayList<String> getQueries()
	{
		return queries;
	}
}
