package savannah.io;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import java.net.Socket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogFile {
	//Field variables
	private String path	= "";
	
	public LogFile(String filePath) {
		this.path = filePath;
	}
	
	public void logIt(Socket socket, String performedAction) {
		FileWriter write = null;
		PrintWriter print = null;
		DateFormat dateFormat = new SimpleDateFormat("[dd-MM-yyyy - HH:mm:ss]");
		try {
			write = new FileWriter(this.path, true);
			print = new PrintWriter(write);
			
			//Writing messages to the log file
//			print.printf(dateFormat.format(Calendar.getInstance().getTime()) + "\t Connecting from: %s \t --- \t Performed Action: %s --- \t %s %n", socket, performedAction, complete);
			print.printf(dateFormat.format(Calendar.getInstance().getTime()) + "\t Connecting from: %s %n", socket);
			print.printf("\t Performed Action: %s %n", performedAction);
//			print.printf("\t Completed: %s %n %n", complete);
			//Flushing
			print.flush();
			write.flush();
			//Closing
			print.close();
			write.close();
		} 	catch (IOException e) {
			System.err.println("Could not find file: " + this.path + " !");
		}
	}
	public void logIt(boolean completed) {
		FileWriter write = null;
		PrintWriter print = null;
		String complete = (completed == true) ? "SUCCESS" : "FAILED";
		try {
			write = new FileWriter(this.path, true);
			print = new PrintWriter(write);
			
			//Writing messages to the log file
			print.printf("\t Completed: %s %n %n", complete);
			//Flushing
			print.flush();
			write.flush();
			//Closing
			print.close();
			write.close();
		} 	catch (IOException e) {
			System.err.println("Could not find file: " + this.path + " !");
		}
	}	
	
	
}
