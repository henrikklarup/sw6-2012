package savannah.test;

import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;

import savannah.io.DOMinator.DOMinator;

public class DOMinatorTest {

	
	public static void main(String[] args) throws JDOMException, IOException {
		DOMinator dom = new DOMinator();
		Document d = dom.Dominate(xml);
		System.out.println(d.toString());

	}

	static String xml = "<?xml version=\"1.0\"?><root><body>ninja</body></root>";
}
