package dk.aau.cs.giraf.oasis.lib.controllers;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;

import dk.aau.cs.giraf.savannah.serverConnection.Connection;
import dk.aau.cs.giraf.savannah.serverConnection.TransmissionHandler;


/**
 * Server helper
 * @author Admin
 *
 */
public class ServerHelper {

	/**
	 * Get status
	 * @return Random
	 */
	public int getStatus() {
		Random r = new Random();
        int result = r.nextInt(3);
        
        return result;
	}
	
	public String syncUserDataByCertificate(String certificate) {
//		int rows = 0;
		String xml = "";
		
		
		Connection con;
		TransmissionHandler th = null;
		try {
			con = new Connection("/mnt/sdcard/Pictures");
			th = con.sendRequest("profiles=" + certificate + ",1&");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		xml = th.getXML();
			
		
		return xml;
	}
}
