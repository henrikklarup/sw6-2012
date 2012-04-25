package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.Random;

public class ServerHelper {

	public int getStatus() {
		Random r = new Random();
        int result = r.nextInt(3);
        
        return result;
	}
}
