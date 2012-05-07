package savannah.io;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import java.net.Socket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

public class LogFile {
	//Field variables
	private String path	= "";

	public LogFile(String filePath) {
		this.path = filePath;
	}

	public synchronized void makeLogEntry(Socket socket, String performedAction, boolean completed) {
		FileWriter write = null;
		PrintWriter print = null;
		DateFormat dateFormat = new SimpleDateFormat("[dd-MM-yyyy - HH:mm:ss]");
		String complete = (completed == true) ? "SUCCESS" : "FAILED";
		try {
			write = new FileWriter(this.path, true);
			print = new PrintWriter(write);
			
			//Writing messages to the log file
			print.printf(dateFormat.format(Calendar.getInstance().getTime()) + "\t Connecting from: %s %n", socket);
			print.printf("\tPerformed Action: %s%n", performedAction);
			print.printf("\tCompleted: %s%n%n", complete);
			
			//Flushing
			print.flush();
			write.flush();
			//Closing
			print.close();
			write.close();
			
		}	catch (IOException e) {
			System.err.println("Could not find file: " + this.path + " !");
		}
	}
	public synchronized void makeLogEntry(Socket socket, String performedAction, boolean completed, List<File> files) {
		FileWriter write = null;
		PrintWriter print = null;
		DateFormat dateFormat = new SimpleDateFormat("[dd-MM-yyyy - HH:mm:ss]");
		String complete = (completed == true) ? "SUCCESS" : "FAILED";
		
		try {
			write = new FileWriter(this.path, true);
			print = new PrintWriter(write);
			
			//Writing messages to the log file
			print.printf(dateFormat.format(Calendar.getInstance().getTime()) + "\t Connecting from: %s %n", socket);
			print.printf("\tPerformed Action: %s%n", performedAction);
			print.printf("\tCompleted: %s%n%n", complete);
			
			//Flushing
			print.flush();
			write.flush();

		}	catch (IOException e) {
			System.err.println("Could not find file: " + this.path + " !");
		}
		
		for (File f: files) {
			try {
				//Writing messages to the log file
				print.printf("\t\tPath: %s\t---\tName: %s\t---\tSize: %d bytes%n", f.getAbsolutePath(), f.getName(), f.length());
				
				//Flushing
				print.flush();
				write.flush();
				
			}	catch (IOException e) {
				System.err.println("Could not find file: " + this.path + " !");
			}
		}
		
		try {
			//Closing
			print.close();
			write.close();
			
		}	catch (IOException e) {
			System.err.println("Could not find file: " + this.path + " !");
		}
		
	}
	
	private synchronized void logIt(Socket socket, String performedAction) {
		FileWriter write = null;
		PrintWriter print = null;
		DateFormat dateFormat = new SimpleDateFormat("[dd-MM-yyyy - HH:mm:ss]");
		try {
			write = new FileWriter(this.path, true);
			print = new PrintWriter(write);

			//Writing messages to the log file
			print.printf(dateFormat.format(Calendar.getInstance().getTime()) + "\t Connecting from: %s %n", socket);
			print.printf("\tPerformed Action: %s%n", performedAction);

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
	private synchronized void logIt(boolean completed) {
		FileWriter write = null;
		PrintWriter print = null;
		String complete = (completed == true) ? "SUCCESS" : "FAILED";
		try {
			write = new FileWriter(this.path, true);
			print = new PrintWriter(write);

			//Writing messages to the log file
			print.printf("\tCompleted: %s%n%n", complete);
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
	private synchronized void logIt(List<File> files) {
		FileWriter write = null;
		PrintWriter print = null;
		for (File f: files) {
			try {
				write = new FileWriter(this.path, true);
				print = new PrintWriter(write);

				//Writing messages to the log file
				print.printf("\t\tPath: %s\t---\tName: %s\t---\tSize: %d bytes%n", f.getAbsolutePath(), f.getName(), f.length());
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

}
