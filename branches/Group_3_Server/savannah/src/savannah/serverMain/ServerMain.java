package savannah.serverMain;

import savannah.io.IOHandler;
import savannah.server.*;

public class ServerMain {
	
	public static void main(String[] args) {
		new EventHandler().run();
		new IOHandler(ServerConfiguration.PORT, ServerConfiguration.FOLDERPATH, ServerConfiguration.BUFFERSIZE);
	}

}
