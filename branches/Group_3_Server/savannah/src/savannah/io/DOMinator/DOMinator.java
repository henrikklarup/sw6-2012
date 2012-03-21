package savannah.io.DOMinator;

// JDOM imports
import org.jdom.*;
import org.jdom.input.SAXBuilder;

// Input/Output imports
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Socket;
import java.io.BufferedReader;

public class DOMinator {
	
	private SAXBuilder sax;
	private BufferedReader reader;
	private String xml;
	
	public DOMinator(Socket s) throws Exception
	{
		reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		sax = new SAXBuilder();	
	}
	
	public Document Dominate(Socket s) throws Exception
	{
		xml = reader.readLine();
		return sax.build(new StringReader(xml));
	}
}
