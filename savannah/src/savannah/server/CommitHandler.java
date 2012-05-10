package savannah.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import org.jdom.Document;
import org.jdom.JDOMException;

import savannah.io.CRUD;
import savannah.io.Configuration;
import savannah.io.IOHandler;
import savannah.io.LogFile;
/**
 * 
 * @author Martin Fjordvald
 *
 */
public class CommitHandler {
	private ArrayList<String> queries;
	private QueryBuilder qbuilder;
	private QueryHandler qHandler;
	/**
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public CommitHandler() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		qbuilder = new QueryBuilder();
		queries= new ArrayList<String>();
		qHandler = new QueryHandler();
	}
	/**
	 * Primary handler method which builds queries, processes them and returns some kind of result
	 * @param e A CommitEvent object to be processed.
	 */
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
				if(e.getFileList() == null)
				{
					log.makeLogEntry(e.getEventsocket(), CRUD.COMMIT.toString(), true);
				}
				else
				{
					log.makeLogEntry(e.getEventsocket(), CRUD.COMMIT.toString(), true, e.getFileList());
				}
				
				IOHandler.getInstance().respond(e.getEventsocket(), CRUD.COMMIT, queries.size() + " received, of which " + failed + " failed");
			}
			
			failed = 0;
		}
	catch (JDOMException f)
		{ f.printStackTrace(); }
	}
	
	/**
	 * Strictly for testing purposes, do not use this, unless you really want to.
	 * @return ArrayList<String> of the queries, if they have been produced
	 */
	public ArrayList<String> getQueries()
	{
		return queries;
	}
}
