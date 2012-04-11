package savannah.server;

import java.sql.SQLException;
import java.util.ArrayList;

import org.jdom.Document;
import org.jdom.JDOMException;

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
