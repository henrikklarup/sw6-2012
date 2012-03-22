package savannah.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//Client
public class TestingPurposeClient implements Runnable
{
	static PrintWriter out;
	static BufferedReader in;
	
	static FileInputStream reader;
	
	static String msg = "";
	
	public static void client()
	{	
		try {
		File file = new File("/home/martin/Documents/eadocs/sw6_example.xml");
		reader = new FileInputStream(file);
		
		in = new BufferedReader(new InputStreamReader(reader));
		
		while(in.ready())
		{
			msg = msg + in.readLine() + System.getProperty("line.separator");
			System.out.println(msg);
		}
		Socket socket = new Socket("127.0.0.1",50000);
		
		out = new PrintWriter(socket.getOutputStream(),true);
		//in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		out.println(msg);
		
		System.out.println("hopefully send a msg");
		}
		catch (Exception e)
		{ e.printStackTrace();}
	}

	public static void main(String args[])
	{   
		client();
	}
	@Override
	public void run() {
		try { client(); }
		catch (Exception e)
		{e.printStackTrace();}
	}
}

