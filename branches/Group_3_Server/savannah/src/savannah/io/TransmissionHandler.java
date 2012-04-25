package savannah.io;

import savannah.device.ConnectionConfiguration;
import savannah.device.Connection;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import java.net.Socket;

public class TransmissionHandler {
	private List<File> files 	= null;
	private File folder			= null;
	private int bufferSize 		= -1;
	private boolean working 	= true;

	private CRUD cr 			= CRUD.ERROR;
	private String xml 			= "";
	private boolean iHaveFile	= false;
	private boolean accepted	= false;
	private ArrayWrapper overflow = null;

 	public TransmissionHandler(Connection con) throws FileNotFoundException, IOException {
		if (con == null) {
			throw new NullPointerException("con: Cannot be null !");
		}	else { 
			this.bufferSize = con.getBufferSize();
			this.files = new ArrayList<File>();
			this.folder = con.getFolder();
			//Invoking the deconstruction !
			this.deconstruct(con.getConnectionInputStream());
		}
	}
	public TransmissionHandler(Socket socket, String folder) throws FileNotFoundException, IOException {
		this.bufferSize = 4096;
		this.files = new ArrayList<File>();
		if (this.makeFolder(folder) == false) {
			throw new IllegalArgumentException("Noooooes !");
		}
		this.deconstruct(socket.getInputStream());
	}
	private final boolean makeFolder(String folderPath) {
		File dir = new File(folderPath);
		boolean success = false;
		try {
			if (dir.exists() == false) {
				success = dir.mkdir();
				if (success == false) {
					throw new IOException("folderPath: Could not create folder !");
				}
			}	else {
				this.folder = dir;
				success = true;
			}
		}	catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return success;
	}
	
	private final void deconstruct(InputStream is) throws FileNotFoundException, IOException {
		this.cr = this.CR(is);
		this.xml = this.XML(is, this.overflow);

		if (iHaveFile == true) {
			while (this.working == true) {
				this.FILE(is);
			}
		}	else {
			this.accepted = this.ACCEPT(is, this.overflow);
		}
	}

	private void moveData(byte[] input, int index, byte[] output) {
		int iter = 0;
		for (int i = index; i < input.length; i++) {
			output[iter] = input[i];
			iter++;
		}
	}
	private XMLWrapper findXMLlength(byte[] buf) {
		String delimClafR = "]";
		String delimComma = ",";
		String length = "";
		String anyFiles = "";

		int storedIndex = -1;
		//int i = 4 should be the  " MXML[ " offset
		for (int i = 5; i < buf.length; i++) {
			if (length.contains(delimComma) == true) {
				storedIndex = i;
				break;
			}
			length += (char)buf[i];
		}
		length = length.substring(0, length.length() -1);
		
		//Setting storedIndex to point at the first part of
		//the name, instead of at a  " , "
//		storedIndex++;
		
		for (int i = storedIndex; i < buf.length; i++) {
			if (anyFiles.contains(delimClafR) == true) {
				storedIndex = i;
				break;
			}
			anyFiles += (char)buf[i];
		}
		//Subtract 1, since this is the delimiter !
		anyFiles = anyFiles.substring(0, anyFiles.length() -1);
		boolean result = (anyFiles.contains("1") == true) ? true : false;
		
		//Setting the storedIndex to point at the index of the first XML char
		storedIndex += 2;
		
		System.out.println("StoredIndex: " + storedIndex);
		System.out.println(Arrays.toString(buf));
		System.out.println(bytesToString(buf));
		System.out.println("Remaining data - in bytes: " + (buf.length - storedIndex));
		return new XMLWrapper(Integer.parseInt(length), storedIndex, (buf.length - storedIndex), result);
	}
	private FileWrapper findFilelength(byte[] buf) {
		String delimComma	= ",";
		String delimClafL	= "]";
		String name			= "";
		String length 		= "";

		int storedIndex = -1;
		for (int i = 5; i < buf.length; i++) {
			if (length.contains(delimComma) == true) {
				storedIndex = i;
				break;
			}
			length += (char)buf[i];
		}
		//Subtract 1, since this is the delimiter !
		length = length.substring(0, length.length() -1);

		//Setting storedIndex to point at the first part of
		//the name, instead of at a  " , "
		storedIndex++;

		for (int i = storedIndex; i < buf.length; i++) {
			if (name.contains(delimClafL)) {
				storedIndex = i;
				break;
			}
			name += (char)buf[i];
		}
		//Subtract 1, since this is the delimiter !
		name = name.substring(0, name.length() -1);
		System.out.println("Name: " + name);
		System.out.println("Length: "+ length);
		return new FileWrapper(Long.parseLong(length), name, storedIndex, (buf.length - storedIndex));
	}
	private String bytesToString(byte[] buf) {
		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < buf.length; i++) {
//			sb.append((char)buf[i]);
//		}
		for (byte b : buf) {
			sb.append((char)b);
		}
		return sb.toString();
	}
	private String bytesToString(byte[] buf, int upTo) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < upTo; i++) {
			sb.append((char)buf[i]);
		}
		return sb.toString();
	}
	private String bytesToString(byte[] buf, int from, int upTo) {
		StringBuilder sb = new StringBuilder();
		for (int i = from; i < upTo; i++) {
			sb.append((char)buf[i]);
		}
		return sb.toString();
	}

	private final CRUD CR(InputStream is) throws IOException {
		//Reading  "TYPE[int]" - the size is 7
		//The index of the int is 6
		byte[] buf = new byte[7];
		//int len = this.reader.read(buf);
		is = new DataInputStream(is);
		int len = is.read(buf);

		//Converting the byte data to int
		//Maybe find a better way that is less crude...
		String temp = "";
		temp += (char)buf[5];	//Maybe it is buf[5] and not buf[6]
		
		switch(Integer.parseInt(temp)) {
		case 1:
			return CRUD.COMMIT;
		case 2:
			return CRUD.REQUEST;
		case 3:
			return CRUD.PING;
		default:
			return CRUD.ERROR;
		}
	}
	private final String XML(InputStream is, ArrayWrapper aw) throws IOException {
		//Reading "MXML[length,int]" - the size is minimum: 9  &  max: 18
		//The maxmimum cells/spaces an int can be is 10
		byte[] buf = new byte[18];
		int lenTemp = is.read(buf);
		XMLWrapper wrap = this.findXMLlength(buf);
		StringBuilder sb = new StringBuilder();
		int bufCalc = -1;

		//If this is the case, then we know that all of the XML data is 
		//in the buffer
		if ( (wrap.getStoredIndex() + wrap.getLength()) < buf.length ) {
			System.out.println("Entered IF !");
			bufCalc = wrap.getLength();
			for (int i = wrap.getStoredIndex(); i < bufCalc + wrap.getStoredIndex(); i++) {
				sb.append((char)buf[i]);

			}
			//Increasing by the length of that which has been processed
			wrap.increaseStoredIndex(wrap.getLength());
			//Increasing by 1, because of the offset at the end of the XML data
			wrap.increaseStoredIndex(1);
			byte[] overflow = new byte[buf.length - wrap.getStoredIndex()];
			int tempJ = 0;
			for (int i = wrap.getStoredIndex(); i < buf.length; i++) {
				overflow[tempJ] = buf[i];
				tempJ ++;
			}
			aw = new ArrayWrapper(overflow);
			System.out.println("Overflow: " + bytesToString(overflow));
			System.out.println("IF - sb: " + sb.toString());
		}
		//If this is the case, then we know that all of the XML data is NOT
		//in the buffer, and we need to read multiple times.
		else {
			System.out.println("Entered ELSE !");
			bufCalc = wrap.getLength();
			//Appended the data left in the buffer
			sb.append(this.bytesToString(buf, wrap.getStoredIndex(), buf.length));
			bufCalc -= (buf.length - wrap.getStoredIndex());

			while (bufCalc > 0) {
				if (bufCalc > this.bufferSize) {
					buf = new byte[this.bufferSize];
					int len = is.read(buf);
					sb.append(this.bytesToString(buf));
					bufCalc -= len;
				}
				else {
					buf = new byte[bufCalc];
					int len = is.read(buf);
					sb.append(this.bytesToString(buf));
					bufCalc = 0;
				}
			}
			//Dumping the unneeded package data
//			int dump = is.read();
			System.out.println("ELSE - sb: " + sb.toString());
		}
		this.iHaveFile = wrap.areThereFiles();
		System.out.println("StringBuilder: " + sb.toString());
		return sb.toString();
	}
	private final void FILE(InputStream is) throws FileNotFoundException, IOException {
		//Reading "FILE[length,name.extension]" - the size is minimum: 9  &  max: 283
		//The maxmimum cells/spaces a long can be is 19
		//The maxmimum cells/spaces a File name can be is 256 - so sayeth I !
		byte[] buf = new byte[283];
		int lenTemp = is.read(buf);
		FileWrapper wrap = this.findFilelength(buf);

		File dst = new File(this.folder.getAbsolutePath() + File.pathSeparator + wrap.getName());
		if (dst.exists() == false) {
			boolean success = dst.createNewFile();
			if (success == false) {
				throw new IllegalArgumentException("Could not create File !");
			}
		}
		OutputStream os = new FileOutputStream(this.folder.getAbsolutePath() + File.pathSeparator + wrap.getName());

		StringBuilder sb = new StringBuilder();
		long bufCalc = -1;
		int dump = -1;
		if (wrap.getRemaining() == 0) {
			//Used the entire buffer, make new buffer and 
			//do a standard read + buffer
			bufCalc = wrap.getLength();
			while (bufCalc > 0) {
				if (bufCalc > ConnectionConfiguration.BUFFERSIZE) {
					buf = new byte[ConnectionConfiguration.BUFFERSIZE];
					int len = is.read(buf);
					os.write(buf);
					bufCalc -= len;
				}
				else {
					buf = new byte[(int)bufCalc];
					int len = is.read(buf);
					os.write(buf);
					bufCalc = 0;
				}
			}
			//Dump the last char... ( " )
			dump = is.read();
		}
		else if ((wrap.getRemaining() - 2) == 0) {
			//Just in case that there is 2 remaining chars ( =" )
			//We don't want to do a lot of work just for these 2 chars
			dump = is.read();
			dump = is.read();

			bufCalc = wrap.getLength();
			while (bufCalc > 0) {
				if (bufCalc > ConnectionConfiguration.BUFFERSIZE) {
					buf = new byte[ConnectionConfiguration.BUFFERSIZE];
					int len = is.read(buf);
					os.write(buf);
					bufCalc -= len;
				}
				else {
					buf = new byte[(int)bufCalc];
					int len = is.read(buf);
					os.write(buf);
					bufCalc = 0;
				}
			}
			//Dump the last char... ( " )
			dump = is.read();
		}
		else {
			//We have remaining data and we need do to some
			//black magic to get this to work.
			byte[] overflow = new byte[wrap.getRemaining() - 2];
			wrap.increaseStoredIndex(2);
			this.moveData(buf, wrap.getStoredIndex(), overflow);
			os.write(overflow);

			if (wrap.getLength() < this.bufferSize) {
				//The xml is small enough to handle easily
				buf = null;
				buf = new byte[(int)wrap.getLength() + 1];

				//Reading the data and appending it to the current data
				lenTemp = is.read(buf);
				os.write(buf, 0, buf.length -1);
				wrap.increaseStoredIndex((int)wrap.getLength() + 1);
			}	else {
				//The xml is very large and we will take it
				//in multiple iterations
				buf = null;
				bufCalc = wrap.getLength();

				while (bufCalc > 0) {
					if (bufCalc > this.bufferSize) {
						buf = new byte[this.bufferSize];
						int len = is.read(buf);
						os.write(buf);
						bufCalc -= len;
					}
					else {
						buf = new byte[(int)bufCalc];
						int len = is.read(buf);
						os.write(buf);
						bufCalc = 0;
					}
				}
				//Dump the last char...  ( " )
				dump = is.read();
			}
		}

		//If the InputStream is not yet empty
		//Then true, because there is still data to process
		//Else false, becuase there is not more data to process
		this.working = (dump > 0) ? true : false;

		this.files.add(dst);
		os.flush();
		os.close();
	}
	public boolean ACCEPT(InputStream is, ArrayWrapper aw) throws IOException {
		//Reading "[ACCEPT] - the size is 7
		//The index of ACCEPT is n+1 to n-1
		StringBuilder sb = new StringBuilder();
		sb.append(this.bytesToString(aw.getData()));
		byte[] buf = new byte[7];
		int len = is.read(buf);
		sb.append(this.bytesToString(buf, 0, buf.length));
		System.out.println("ACCEPT: " + sb.toString());
		return (sb.toString().equals("ACCEPT") == true) ? true : false;
	}

	public CRUD CR() {
		return this.cr;
	}
	public String XML() {
		return this.xml;
	}
	public List<File> FILES() {
		return this.files;
	}
	public boolean anyFiles() {
		return this.files.isEmpty();
	}

	class XMLWrapper {
		private int _length;
		private int _storedIndex;
		private int _remaining;
		private boolean _anyFiles;

		XMLWrapper(int length, int storedIndex, int remaining, boolean anyFiles) {
			this._length = length;
			this._storedIndex = storedIndex;
			this._remaining = remaining;
			this._anyFiles = anyFiles;
		}

		public int getLength() {
			return this._length;
		}
		public int getStoredIndex() {
			return this._storedIndex;
		}
		public int getRemaining() {
			return this._remaining;
		}
		public boolean areThereFiles() {
			return this._anyFiles;
		}
		public void increaseStoredIndex(int increase) {
			this._storedIndex += increase;
		}
	}
	class FileWrapper {
		private long _length;
		private int  _storedIndex;
		private int  _remaining;
		private String _name;

		FileWrapper(long length, String name, int storedIndex, int remaining) {
			this._length = length;
			this._name = name;
			this._storedIndex = storedIndex;
			this._remaining = remaining;
		}

		public long getLength() {
			return this._length;
		}
		public String getName() {
			return this._name;
		}
		public int getStoredIndex() {
			return this._storedIndex;
		}
		public int getRemaining() {
			return this._remaining;
		}
		public void increaseStoredIndex(int increase) {
			this._remaining += increase;
		}
	}
	class ArrayWrapper {
		private byte[] _data;
		
		ArrayWrapper(byte[] data) {
			this._data = data;
		}
		
		public byte[] getData() {
			return this._data;
		}
	}
	
	
	
	
	
	
}
