package savannah.test;

import java.awt.EventQueue;
import java.io.File;
import java.util.ArrayList;

import org.jdom.Document;

import savannah.io.DOMinator.DOMinator;
import savannah.server.CommitEvent;
import savannah.server.EventHandler;

public class EventHandlerTest {
		private static EventHandler handler = new EventHandler();
		
		public static void main(String[] args) throws Exception
		{
			File f = new File("/home/martin/Documents/eadocs/sw6_example.xml");
			DOMinator dom = new DOMinator();
			Document d = dom.Dominate(f);
			
			CommitEvent com = new CommitEvent(d,null,false);
			handler.CommitHandler(com);
			ArrayList<String> q = handler.getQueries();
			for(String s: q)
			{
				System.out.println(s);
			}
			System.out.println("done");
		}
}
