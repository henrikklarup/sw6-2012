package savannah.server;

import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom.Document;
import org.jdom.JDOMException;

import savannah.io.CRUD;
import savannah.io.Configuration;
import savannah.io.IOHandler;
import savannah.io.LogFile;

public class CommitHandler {
	private ArrayList<String> queries;
	private QueryBuilder qbuilder;
	private QueryHandler qHandler;
	
	public CommitHandler() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		qbuilder = new QueryBuilder();
		queries= new ArrayList<String>();
		qHandler = new QueryHandler();
	}
	
	public void HandleIt(CommitEvent e)
	{
	try{
			LogFile log = new LogFile(Configuration.LOGFILEPATH);
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
				log.makeLogEntry(e.getEventsocket(), CRUD.COMMIT.toString(), true);
				IOHandler.getInstance().respond(e.getEventsocket(), queries.size() + " received, of which "+failed);
			}
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
