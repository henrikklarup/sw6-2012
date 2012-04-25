package savannah.io;

import java.util.List;
import java.util.ArrayList;

import java.lang.StringBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

/**
 * This class represents a package to send to the IOHandler.
 * TransmissionPackage format:
 * Format: TYPE[int]MXML[length]="---data---"FILE[length,name.extension]="---data---"FILE...
 * @author Sir.Thundar
 *
 */
public class TransmissionPackage {
	private List<File> files	= null;
	private String xml			= null;
	private int cr 				= -1;
	private int size 			= -1; 

	/**
	 * Creates a TransmissionPackage from the specified arguments.
	 * @param cr - the specified enum representing transmission type
	 * @param XML - the specified String representing the XML
	 * @param files - the specified Files to send
	 */
	public TransmissionPackage(CRUD cr, String XML, File... files) {
		this.files = new ArrayList<File>();
		this.cr = cr.getValue();
		this.xml = XML;
		this.addFiles(files);
	}
	
	/**
	 * Creates a TransmissionPackage from the specified arguments.
	 * @param cr - the specified enum representing transmission type
	 * @param XML - the specified String representing the XML
	 * @param f - the specified File to send
	 */
	public TransmissionPackage(CRUD cr, String XML, File f) {
		this.files = new ArrayList<File>();
		this.cr = cr.getValue();
		this.xml = XML;
		this.files.add(f);
	}
	
	/**
	 * Creates a TransmissionPackage from the specified arguments.
	 * @param cr - the specified enum representing transmission type
	 * @param XML - the specified String representing the XML
	 */
	public TransmissionPackage(CRUD cr, String XML) {
		this.files = new ArrayList<File>();
		this.cr = cr.getValue();
		this.xml = XML;
	}

	private final void addFiles(File... files) {
		if (files != null) {
			for (File f : files) {
				this.files.add(f);
			}
		}
	}

	/**
	 * Generates a package to send to the IOHandler.
	 * This method may take some time to calculate, because it
	 * reads all the data from the Files to send.
	 * @return - a StringBuilder representing the entire package
	 */
	public StringBuilder getPackage() {
		//StringBuilder to contain the package to send
		StringBuilder builder = new StringBuilder();
		//Adding the data to the StringBuilder
		this.getCRUD(this.cr, builder);
		this.getXML(this.xml, builder);

		for (int i = 0; i < this.files.size(); i++) {
			this.getFile(this.files.get(i), builder);
		}
		//Setting the size of the package
		this.size = builder.length();
		return builder;
	}
	private void getCRUD(int cr, StringBuilder builder) {
		try {
			//Exception handling 
			if (cr == -1) {
				throw new IllegalArgumentException("cr: Cannot be less than zero !");
			}
			if (builder == null) {
				throw new NullPointerException("builder: Cannot be null !");
			}
			//Adding the data
			builder.append("TYPE[" + cr + "]");
		}	catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}	catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}
	}
	private void getXML(String XML, StringBuilder builder) {
		try {
			//Exception handling 
			if (XML.equals("") == true) {
				throw new IllegalArgumentException("XML: Cannot be null !");
			}
			if (builder == null) {
				throw new NullPointerException("builder: Cannot be null !");
			}
			//Adding the data
			builder.append("MXML[" + XML.length() + "]=\"");
			builder.append(XML);
			builder.append("\"");
		}	catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}	catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}
	}
	private void getFile(File f, StringBuilder builder) {
		FileModule fm = new FileModule(4096);
		try {
			//Exception handling 
			if (f == null) {
				throw new NullPointerException("f: Cannot be null !");
			}
			if (f.exists() == false) {
				throw new IOException("f: Cannot find the File !");
			}
			if (builder == null) {
				throw new NullPointerException("builder: Cannot be null !");
			}
			//InputStream to get the File data
			InputStream is = new FileInputStream(f);

			//Adding the data
			builder.append("FILE[" + f.length() + "," + f.getName() + "]=\"");
			fm.readByteToChar(is, builder);
			builder.append("\"");
		}	catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}	catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * This method approximates the size of the transmission.
	 * This method will return -1, unless you call getPackage() first, it
	 * then returns the number of bytes in the package.
	 * @return - the approximate size of the transmission
	 */
	public int approximateTransmissionSize() {
		return this.size;
	}
}
