package savannah.serverMain;

import savannah.io.IOHandler;
import savannah.server.*;

public class ServerMain {
	
	public static void main(String[] args) {
		new EventHandler().run();
		IOHandler.getInstance().run();
	}

}
