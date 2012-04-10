package savannah.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.lang.StringBuilder;

/**
 * This class is used to handle a TransmissionPackage, in the form of an InputStream.
 * Upon instantiating this class, the class will begin deconstructing the InputStream data,
 * and turning it into an XML String and Files.
 * The Files are created in the specified folder path. These Files will be created, even though
 * the entire package may not be received. 
 * @author Sir.Thundar
 *
 */
public class TransmissionHandler {
	//Field Variables
	private String xml				= "";
	private File folder				= null;
	private int bufferSize			= -1;
	private int cr					= -1;
	private int start 				= 0;
	private int XMLlength 			= 0;
	private int FILElength 			= 0;
	private static final int STDBUFFER = 4096;

	
	/**
	 * 
	 * @param is
	 * @param bufferSize
	 * @param folderPath
	 * @throws FileNotFoundException
	 */
	public TransmissionHandler(InputStream is, int bufferSize, String folderPath) throws FileNotFoundException {
		if (folderPath.equals("") == true || this.makeFolder(folderPath) == false) {
			throw new IllegalArgumentException("folderPath: Cannot be null !");
		}
		if (this.bufferSize <= 0) {
			this.bufferSize = STDBUFFER;
		}
		else {
			this.bufferSize = bufferSize;
		}
		this.deconstruct(is);
	}

	/**
	 * 
	 * @param folderPath
	 * @return
	 */
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

	/**
	 * 
	 * @param s
	 * @param vf
	 * @return
	 */
	private final String readUntil(String s, VirtualFile vf) {
		String temp = "";
		while (true) {
			if (temp.contains(s) == true) {
				break;
			}
			temp += (char)vf.getData(this.start);
			this.start++;
		}
		return temp;
	}
	
	/**
	 * 
	 * @param len
	 * @param vf
	 * @return
	 */
	private final StringBuilder readUntil(int len, VirtualFile vf) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < len; i++) {
			sb.append((char)vf.getData(this.start));
			this.start++;
		}
		return sb;
	}
	
	/**
	 * 
	 * @param len
	 * @param vf
	 * @return
	 */
	private final byte[] readUntil2(int len, VirtualFile vf) {
		byte[] buf = new byte[len];
		for (int i = 0; i < len; i++) {
			buf[i] = vf.getData(this.start);
			this.start++;
		}
		return buf;
	}

	/**
	 * 
	 * @param vf
	 * @return
	 */
	private final int getCR(VirtualFile vf) {
		this.start += 5;
		String temp = readUntil("]", vf);
		temp = temp.substring(0, temp.length() -1);

		return Integer.parseInt(temp); 
	}
	
	/**
	 * 
	 * @param vf
	 * @return
	 */
	private final String getXML(VirtualFile vf) {
		this.start += 5;

		String temp = this.readUntil("]", vf);
		temp = temp.substring(0, temp.length() -1);
		this.XMLlength = Integer.parseInt(temp);
		this.start += 2;

		StringBuilder xmlData = this.readUntil(this.XMLlength, vf);
		return xmlData.toString();
	}

	/**
	 * 
	 * @param vf
	 */
	private final void getFile(VirtualFile vf) {
		//We use this check to prevent looping when creating the files
		if (this.start + 1 != vf.size()) {
			this.start += 6;

			//Setting File length
			String temp = this.readUntil(",", vf);
			temp = temp.substring(0, temp.length() -1);
			this.FILElength = Integer.parseInt(temp);

			//Setting File name and File path
			temp = this.readUntil("]", vf);
			temp = temp.substring(0, temp.length() -1);
			String path = this.folder.getAbsolutePath() + "\\" + temp;
			System.out.println("Read: path -- " + path);

			this.start += 2;
			byte[] buf = this.readUntil2(this.FILElength, vf);

			try {
				File dst = new File(path);
				if (dst.exists() == false) {
					boolean success = dst.createNewFile();
					if (success == false) {
						throw new IllegalArgumentException("Could not create File !");
					}
				}

				OutputStream os = new FileOutputStream(path);
				os.write(buf);
				os.flush();
				os.close();
				System.out.println("Done - File: " + dst.getName());
			}	catch (FileNotFoundException e) {
				System.err.println("Could not find the File on Server HDD !");
			}	catch (IOException e) {
				System.err.println("Could not write data to File on Server HDD !");
			}	catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
			}
		}
		else {
			//We increment start  so we don't loop !
			this.start++;
			System.out.println("All Files have been read !");
		}
	}
	/*
	 * Make a readUntil(int upToSomeNumber, String upToSomeChar)
	 * This method should be able to find the number of bytes
	 * more precisely.
	 * 
	 * Also re-make the format of the TransmissionPackage
	 * Format: TYPE[int]MXML[length]="---data---"FILE[length,name.extension]="---data---"FILE...
	 * 		   off: 5  off 6     off 3          off 6                     off 3         off 6 ...
	 * This format should make it more easy to keep track
	 * of the offset of the different things in the String(InputStream)
	 * 
	 *  Maybe the entire Virtual File class should
	 *  somehow be replace by a StringBuilder ?
	 *  Since this would reduce the amount of memory required
	 *  by the Server.
	 *  
	 *  Also fix the buffer problem, make it more dynamic
	 *  like done in the Virtual File class's read method !
	 *  This is to optimize transfer-rates, since the current
	 *  method too often gives a "too low" buffer - aka
	 *  buffer size 4096 bytes - transfer-rate 386 bytes.
	 *  Example of buffer to implement:
	 *  Data: 4096 bytes
	 *  Buffer: 1500 bytes
	 *  Read 1500 bytes (1500 bytes read)
	 *  Read 1500 bytes	(3000 bytes read)
	 *  Read 1096 bytes	(4096 bytes read)
	 *  
	 */

	/**
	 * 
	 * @param is
	 * @throws FileNotFoundException
	 */
	private final void deconstruct(InputStream is) throws FileNotFoundException {
		System.out.println("Starting to deconstruct !");
		VirtualFile vf = new VirtualFile(this.bufferSize);
		vf.addData(is);
		this.cr = this.getCR(vf);
		this.xml = this.getXML(vf);
		while (this.start != vf.size()) {
			this.getFile(vf);	
		}
		System.out.println("Deconstrcution complete !");
	}

	/**
	 * 
	 */
	public final void cleanup() {
		this.xml = null;
		this.folder = null;

	}

	/**
	 * 
	 * @return
	 */
	public String XML() {
		return this.xml;
	}
}
