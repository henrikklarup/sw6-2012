package savannah.test;

import java.io.File;
import java.io.IOException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import savannah.device.*;
import savannah.io.TransmissionHandler;

public class TestingTransmissionHandler {
	
	private static final String CLIENTFOLDER = "C:\\tempClient";
	
	public static void main(String[] args) {

//		for (int i = 0; i < 10; i++) {
//			if ( (i + 1) >= 10 ) {
//				System.out.println("i = " + i);
//			}	else {
//				System.out.println("lower: " + i);
//			}
//		}
		
		
		
		try {
			Connection con = new Connection(TestingTransmissionHandler.CLIENTFOLDER);
		
//			TransmissionHandler th = con.sendRequest("Anal !");
//			System.out.println("Any file: " + th.getAnyFiles());
//			for (File f: th.getFILES()) {
//				System.out.println(f.getName());
//				System.out.println(f.getAbsolutePath());
//			}
			System.out.println(con.sendCommit("My test String", new File("C:\\tempClient\\sadPanda.jpg"), new File("C:\\tempClient\\bendover-sml.jpg")));
			
			//The long "empty" part of the received transmission is because of the bufferSize in the Connection.java
			//Maybe it should receive a TransmissionHandler instead ?
			
			//Debugging path:
			//D:\MyDocuments\SW6\SW62012\branches\Group_3_Server\savannah\bin>
			
			//Run option:
			//java -classpath . savannah.serverMain.ServerMain
			//java -classpath . savannah.test.TestingTransmissionHandler
			
//			System.out.println(con.sendCommit("Anal"));
		}	catch (IOException e) {
			System.err.println("con: " + e);
		}
		
		
		
		

		//"FILE\\[[0-9]*,[0-9]*\\]"
//		Pattern pat = Pattern.compile(",[0-1]\\]");
//		String input = "FILE[000012345,   hehh.jpg,1]=\"";
//		Matcher man = pat.matcher(input);
//		
//		if (man.find() == true) {
//			System.out.println("Found: " + man.group());
//			System.out.println("Start index ? = " + man.start());
//			System.out.println("End index ? = " + man.end());
//			System.out.println(input.subSequence(man.start(), man.end()));
//			System.out.println("SubString test: " + (input.substring(man.start()+1, man.end()-1)));
//		}
//		else {
//			System.err.println("Could not match expression");
//		}
//		
//		System.out.println("---------------------------------");
//		String in = "                     hello.jpg";
//		String result = "";
//		int index = -1;
//		for (int i = 0; i < in.length(); i++) {
//			if (in.charAt(i) != ' ') {
//				index = i;
//				break;
//			}
//		}
//		for (int i = index; i  < in.length(); i++) {
//			result += in.charAt(i);
//		}
//		System.out.println("Result: ---" + result + "---");

	}

}
