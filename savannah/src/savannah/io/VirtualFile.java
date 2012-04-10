package savannah.io;

import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class represents a File, however, instances are stored in the computer memory.
 * Because an instance of this class is stored in the memory, you should not use
 * it for very large files. This class is not recommended for files that are >= 
 * the maximum size of an Integer.
 * @author Sir.Thundar
 *
 */
public class VirtualFile {
	private List<Byte> data = new ArrayList<Byte>();
	private static final int STDBUFFER = 4096;
	private int bufferSize = -1;
	private volatile int index; 
	private String dest;

	/**
	 * 
	 * @param URI
	 * @param bufferSize
	 */
	public VirtualFile(int bufferSize) {
		this.resetIndex();
		if (this.bufferSize <= 0) {
			this.bufferSize = STDBUFFER;
		}
		else {
			this.bufferSize = bufferSize;
		}
	}
	public VirtualFile(int bufferSize, String dst) {
		this.resetIndex();
		if (this.bufferSize <= 0) {
			this.bufferSize = STDBUFFER;
		}
		else {
			this.bufferSize = bufferSize;
		}
		this.dest = dst;
	}
	
	public synchronized String getDst() {
		return this.dest;
	}

	public synchronized void addData(byte b) {
		this.data.add(b);
	}
	
	/**
	 * This method will add all the data from the specified
	 * InputStream into the instance of the VirtualFile.
	 * @param is - the specified InputStream
	 */
	public synchronized void addData(InputStream is) {
		byte[] buf = new byte[this.bufferSize];
		int len;
		try {
			while ((len = is.read(buf)) > 0) {
				for (int i = 0; i < len; i++) {
					this.data.add(buf[i]);
				}
			}
		}	catch (IOException e) {
			System.err.println("Could not perform read !");
		}
	}
	
	/**
	 * This method will add all the data from the specified
	 * byte[] into the instance of the VirtualFile.
	 * @param b - the specified byte[]
	 */
	public synchronized void addData(byte[] b) {
		try {
			if (b == null) {
				throw new NullPointerException("b: Cannot be null !");
			}
			for (int i = 0; i < b.length; i++) {
				this.data.add(b[i]);
			}
		}	catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * This method will write all the data from the 
	 * VirtualFile into the specified File object.
	 * @param dst - the specified File object
	 */
	public synchronized void writeDataToFile(File dst) {
		if (this.data.size() > 0) {
			try {
				if (dst.exists() == false) {
					throw new FileNotFoundException("Could not find File");
				}
				OutputStream os = new FileOutputStream(dst);
				for (int i = 0; i < this.data.size(); i++) {
					os.write(this.data.get(i));
				}
				os.flush();
				os.close();
			}	catch (FileNotFoundException e) {
				System.err.println(e.getMessage());
			}	catch (IOException e) {
				System.err.println("Could not perform write !");
			}
		}
		else {
			//Else don't write anything - there's no need
		}
	}
	
	/**
	 * This method will write all the data from the 
	 * VirtualFile into a File using the specified URI.
	 * @param URI - the specified URI
	 */
	public synchronized void writeDataToFile(String URI) {
		if (this.data.size() > 0) {
			try {
				if (URI.equals("") == true) {
					throw new IllegalArgumentException("URI: cannot be null !");
				}
				File dst = new File(URI);
				if (dst.exists() == false) {
					throw new FileNotFoundException("Could not find File !");
				}
				OutputStream os = new FileOutputStream(dst);
				for (int i = 0; i < this.data.size(); i++) {
					os.write(this.data.get(i));
				}
				os.flush();
				os.close();
			}	catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
			}	catch(FileNotFoundException e) {
				System.err.println(e.getMessage());
			}	catch (IOException e) {
				System.err.println("Could not perform write !");
			}
		}
		else {
			//Else don't write anything - there's no need
		}
	}
	
	/**
	 * Gets the size of the VirtualFile's buffer.
	 * @return - the size of the VirtualFile's buffer
	 */
	public int getBufferSize() {
		return this.bufferSize;
	}

	/**
	 * Sets the buffer size of the VirtualFile to the 
	 * specified buffer size.
	 * @param bufferSize - the specified buffer size
	 */
	public synchronized void setBufferSize(int bufferSize) {
		try {
			if (bufferSize <= 0) {
				throw new IllegalArgumentException("bufferSize: cannot be <= 0 !");
			}
			else {
				this.bufferSize = bufferSize;
			}
		}	catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
	}
	private synchronized int getIndex() {
		return this.index;
	}
	private synchronized void resetIndex() {
		this.index = 0;
	}
	private synchronized void incrementIndex() {
		this.index++;
	}

	/**
	 * This method removes all the data in the
	 * instance of the VirtualFile.
	 */
	public void clearAllData() {
		this.data.clear();
	}
	
	/**
	 * Tests if the VirtualFile has any data.
	 * @return - true if there is no data; false otherwise
	 */
	public boolean isEmpty() {
		return this.data.isEmpty();
	}
	
	/**
	 * Returns the amount of bytes in the VirtualFile instance.
	 * @return - amount of bytes in the VirtualFile
	 */
	public int size() {
		return this.data.size();
	}
	
	/**
	 * Returns the number of available bytes of the VirtualFile
	 * that have not yet been read.
	 * When all the bytes have been read, this method will return
	 * the amount of bytes in the VirtualFile.
	 * @return - the number of available bytes
	 */
	public synchronized int available() {
		if (this.size() - this.getIndex() == 0) {
			return this.size();
		}
		else {
			return this.size() - this.getIndex();
		}
	}
	
	/**
	 * This method will try to read b.length bytes into the specifed byte[]
	 * and return the number of bytes read.
	 * If b.length is zero this method returns 0.
	 * If all the bytes from the VirtualFile have been read, then this
	 * method will return -1.
	 * @param b - the specified byte[]
	 * @return - the number of bytes read
	 * @throws NullPointerException - if b is null
	 */
	public synchronized int read(byte[] b) throws NullPointerException {
		if (b == null) {
			throw new NullPointerException("b: Cannot be null !");
		}
		int len = -1;
		if (b.length == 0) {
			len = 0;
		}
		if (this.getIndex() == this.size()) {
			len = -1;
		}
		else {
			int temp = 0;
			synchronized (this.data) {
				int max = 0;
				int iter = this.getIndex();
				if (this.size() - this.getIndex() < b.length) {
					max = this.size();
				}
				else {
					max = this.getIndex() + b.length;
				}
				for (int i = iter; i < max; i++) {
					b[temp] = this.data.get(i);
					iter++;	temp++;
					this.incrementIndex();
							
				}
				len = temp;
			}
		}
		if (len == -1) {
			this.resetIndex();
		}
		return len;
	}

	public synchronized byte getData(int idx) {
		if (idx < 0) {
			throw new IndexOutOfBoundsException("idx: Cannot be less than zero !");
		}
		else if (idx > this.size()) {
			throw new IndexOutOfBoundsException("idx: Cannot be greater than the size of the VirtualFile !");
		}
		else if (this.isEmpty() == true) {
			throw new NullPointerException("idx: Cannot return because the VirtualFile is empty !");
		}
		else {
			return this.data.get(idx);
		}
	}

}
