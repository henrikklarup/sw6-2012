package savannah.server;

//JDOM imports
import java.util.*;

import org.jdom.*;
import org.jdom.input.*;
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
		
		XPath xp = XPath.newInstance("sw6ml/AuthUser/Entry");
		List<Object> nodes = xp.selectNodes(xml);
		
		xp = XPath.newInstance("/sw6ml/AuthUser//@action");
		String type = xp.valueOf(xml);
	
		String[] str = new String[2];
		int i = 0;
		for(Object o: nodes)
		{
			xp = XPath.newInstance("/sw6ml/AuthUser/Entry["+ i +" ]/certificate");
			str[0] = xp.valueOf(o);
			xp = XPath.newInstance("/sw6ml/AuthUser/Entry["+ i +" ]/idUser");
			str[1] = xp.valueOf(o);
		    out.add(buildQuery("AuthUsers",type,str));
			i++;
		}
		/**
		 * Build table:
		 */
		return out;
	}
	
	
	private String buildQuery(String table, String type,String[] str)
	{
		StringBuilder builder = new StringBuilder();
		for(String s: str)
		{
			builder.append(s);
			if(s != str[str.length])
			{
				builder.append(",");
			}
		}
		String values = builder.toString();
		if(type.equals("create"))
		{
			return "insert into " + table + " values("+ values +")";
		}
		else if(type.equals("delete"))
		{
			return "delete from  " + table + " where idUsers=" + str[1] + ";";
		}
		else if(type.equals("update"))
		{
			//TODO Fix updates later
		}
		return "";	
	}
	//TODO Stub, implement later.
	private void RequestHandler(RequestEvent e)
	{
		
	}
	
	@Override
	public void run() {
		start();
	}
}