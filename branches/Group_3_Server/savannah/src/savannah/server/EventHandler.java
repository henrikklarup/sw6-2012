package savannah.server;

//JDOM imports
import java.util.*;

import org.jdom.*;
import org.jdom.xpath.*;


public class EventHandler implements Runnable {
	private ArrayList<String> queries;
	public EventHandler()
	{
		queries = new ArrayList<String>();
	}
	public void start() {
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

	//TODO Stub, Not done
	public void CommitHandler(CommitEvent e)
	{
	try{
			Document xml = e.getEventContent();
			queries = buildQueries(xml);
		}
	catch (JDOMException f)
		{f.printStackTrace();}

	}
	
	private ArrayList<String> buildQueries(Document xml) throws JDOMException
	{
		ArrayList<String> out = new ArrayList<String>();
		
		/**
		 * Build table:AuthUser queries
		 * AuthUser - Attributes: certificate, idUser
		 */
		//Get all entries
		XPath xp = XPath.newInstance("sw6ml/*/*");
		List<Object> nodes = xp.selectNodes(xml);
		for(Object e : nodes)
		{
			Element entry = (Element)e;
			//Retrieve the table name
			String table = entry.getParentElement().getName();
			//Retrieve the crud type
			String type = entry.getAttributeValue("action");
			//Retrieve all children of the current Entry
			List<Element> vals = entry.getChildren();
			String[] str;
			if(type.equals("update"))
			{
				str = new String[vals.size()+4];
			}
			else
			{
				str = new String[vals.size()+2];
			}
			str[1] = table;
			str[0] = type;
			int i = 2;
			for(Element s: vals)
			{
				if(type.equals("update"))
				{
					str[i] = s.getName();
					i++;
				}
				str[i] = addApos(s.getText(),s.getAttributeValue("type"));
				i++;
			}		
			out.add(buildQuery(str));		
		}
		return out;
	}
	
	private String buildQuery(String[] str)
	{	
		/*
		 * These are the different crud types, create,delete and update, The way this is implemented
		 * will require certain preconditions on how the xml is formed.
		 * Current implementation only supports simple create, delete and update queries.
		 */

		//Create require all tags or some values will be placed wrong in the sql query.
		if(str[0].equals("create"))
		{
			StringBuilder builder = new StringBuilder();
			String tmp = "";
			for(int i = 2;i < str.length;i++)
			{
				tmp = str[i];
				builder.append(tmp);
				if(tmp != str[str.length-1])
				{
					builder.append(",");
				}
			}
			return "INSERT INTO " + str[1] + " values("+ builder.toString() +");";
		}
		//Delete will only need 1 tag, the unique identifier of the table.
		else if(str[0].equals("delete"))
		{
			return "DELETE FROM " + str[1] + " where idUsers=" + str[2] + ";";
		}
		//Update will require two tags, the tag that is being updated, and the unique identifier of the table.
		else if(str[0].equals("update"))
		{	
			//FIXME ehm..database design is rather inconsistent, this will fail if the unique identifier is not the 2nd tag
			//FIXME solution, probably make the xml less restrictive and allow an unordered sequence and simple set the order
			//FIXME correct in the savannah event builder API.
			return "UPDATE " + str[1] +" SET " + str[2] +"="+str[3]+" WHERE " + str[4] + "="+str[5]+";";
		}
		//This return should never be reachable, a JDOMException should occur if the xml document is not well formed.
		return "";	
	}
	
	private String addApos(String str,String type)
	{
		//Dirty implementation
		if(type.equals("string"))
		{
			return "'"+str+"'";
		}
		else { return str ;}
	}
	//TODO Stub, implement later.
	private void RequestHandler(RequestEvent e)
	{
		
	}
	
	public ArrayList<String> getQueries()
	{
		return queries;
	}
	
	@Override
	public void run() {
		start();
	}
}