package savannah.serverMain;

import savannah.io.IOHandler;
import savannah.server.*;

public class ServerMain {
	
	public static void main(String[] args) {
		Thread t1 = new Thread(IOHandler.getInstance());
		Thread t2 = new Thread(new EventHandler());
		t1.start();
		t2.start();
		
	}

}
