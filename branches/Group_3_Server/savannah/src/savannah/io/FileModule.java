package savannah.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

import java.io.OutputStream;
import java.io.FileOutputStream;

public class FileModule {
	private static final int STDBUFFER = 4096;
	private int bufferSize = -1;

	public FileModule(int bufferSize) {
		if (this.bufferSize <= 0) {
			this.bufferSize = STDBUFFER;
		}
		else {
			this.bufferSize = bufferSize;
		}
	}

	private void makeFile(String URI) {
		try {
			if (URI.equals("") == true) {
				throw new IllegalArgumentException("URI: cannot be null");
			}
			File dst = new File(URI);
			boolean success = dst.createNewFile();
			String result = (success) ? "File did not exist and was created -_-'" : "File already exists ^_^"; 
			System.out.println(result);
		}	catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}	catch (IOException e) {
			System.err.println("Could not create File !");
		}

	}
	private void makeFile(File in) {
		try {
			boolean success = in.createNewFile();
			String result = (success) ? "File did not exist and was created -_-'" : "File already exists ^_^";
			System.out.println(result);
		}	catch (IOException e) {
			System.err.println("Could not create File !");
		}
	}

	public void createFile(InputStream src, String URI) {
		try {
			this.makeFile(URI);
			
			OutputStream dst = new FileOutputStream(new File(URI));
			byte[] buf = new byte[this.bufferSize];
			int len;

			while ((len = src.read(buf)) > 0 ) {
				dst.write(buf, 0, len);
			}
			dst.flush();
			dst.close();
		}	catch (IOException e) {
			System.err.println("Could not perform read !");
		}
	}
	public void createFile(InputStream src, File dst) {
		try {
			this.makeFile(dst);
			
			OutputStream stream = new FileOutputStream(dst);
			byte[] buf = new byte[this.bufferSize];
			int len;

			while ((len = src.read(buf)) > 0) {
				stream.write(buf, 0, len);
			}
			stream.flush();
			stream.close();

		}	catch (IOException e) {
			System.err.println("Could not create newFile !");
		}
	}

	public InputStream getFileStream(String URI) {
		InputStream stream = null;
		try {
			if (URI.equals("") == true) {
				throw new IllegalArgumentException("URI cannot be null");
			}
			File src = new File(URI);
			if (src.exists() == false) {
				throw new FileNotFoundException("Could not find File !");
			}
			stream = new FileInputStream(src);
		}	catch (FileNotFoundException e) {
			System.err.println("Could not find File !");
		}	catch (IllegalArgumentException e) {
			System.err.println("URI cannot be null");
		}
		return stream;
	}
	public InputStream getFileStream(File in) {
		InputStream stream = null;
		try {
			if (in.exists() == false) {
				throw new FileNotFoundException("Could not find File !");
			}
			stream = new FileInputStream(in);
		}	catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
		return stream;
	}

	public void writeToFile(File dst, InputStream stream) {
		try {
			if (dst.exists() == false) {
				throw new FileNotFoundException("Could not find File");
			}
			FileOutputStream fos = new FileOutputStream(dst);
			byte[] data = new byte[this.bufferSize];
			int len;

			while ((len = stream.read(data)) > 0) {
				fos.write(data, 0, len);
			}
			fos.flush();
			fos.close();
		}	catch (FileNotFoundException e) {
			System.err.println("Could not find File !");
		}	catch (IOException e) {
			System.err.println("Could not perform write !");
		}

	}
	public void writeToFile(String URI, InputStream stream) {
		try {
			if (URI.equals("") == true) {
				throw new IllegalArgumentException("URI cannot be null !");
			}
			File dst = new File(URI);
			if (dst.exists() == false) {
				throw new FileNotFoundException("Could not find File !");
			}
			FileOutputStream fos = new FileOutputStream(dst);
			byte[] data = new byte[this.bufferSize];
			int len;

			while ((len = stream.read(data)) > 0) {
				fos.write(data, 0, len);
			}
			fos.flush();
			fos.close();
		}	catch (FileNotFoundException e) {
			System.err.println("Could not find File !");
		}	catch (IOException e) {
			System.err.println("Could not write to File !");
		}	catch (IllegalArgumentException e) {
			System.err.println("URI cannot be null !");
		}
	}

	public void copyData(String src, String dst) {
		File ssrc = null;
		File ddst = null;
		try {
			if (src.equals("") == true || dst.equals("") == true) {
				if (src.equals("") == true) {
					throw new IllegalArgumentException("src: Connot be null !");
				}
				else if (dst.equals("") == true) {
					throw new IllegalArgumentException("dst: Cannot be null !");
				}
			}
			ssrc = new File(src);
			ddst = new File(dst);
			if (ssrc.exists() == false || ddst.exists() == false) {
				if (ssrc.exists() == false) {
					throw new FileNotFoundException("src: Could not find File !");
				}
				else if (ddst.exists() == false) {
					throw new FileNotFoundException("dst: Could not find File !");
				}
			}
			InputStream is = new FileInputStream(ssrc);
			OutputStream os = new FileOutputStream(ddst);

			byte[] buf = new byte[this.bufferSize];
			int len;

			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
			os.flush();
			os.close();
			is.close();
		}	catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}	catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}	catch (IOException e) {
			System.err.println("Could not perform read !");
		}
	}
	public void copyData(String src, File dst) {
		File ssrc = null;
		try {
			if (src.equals("") == true) {
				throw new IllegalArgumentException("src: Cannot be null !");
			}
			ssrc = new File(src);
			if (ssrc.exists() == false || dst.exists() == false) {
				if (ssrc.exists() == false) {
					throw new FileNotFoundException("src: Could not find File !");
				}
				else if (dst.exists() == false) {
					throw new FileNotFoundException("dst: Could not find File !");
				}
			}
			InputStream is = new FileInputStream(ssrc);
			OutputStream os = new FileOutputStream(dst);

			byte[] buf = new byte[this.bufferSize];
			int len;

			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
			os.flush();
			os.close();
			is.close();
		}	catch (FileNotFoundException e) {

		}	catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}	catch (IOException e) {
			System.err.println("Could not perform read !");
		}
	}
	public void copyData(File src, File dst) {
		try {
			if (src.exists() == false || dst.exists() == false) {
				if (src.exists() == false) {
					throw new FileNotFoundException("src: Could not find File !");
				}
				else if (dst.exists() == false) {
					throw new FileNotFoundException("dst: Could not find File !");
				}
			}
			InputStream is = new FileInputStream(src);
			OutputStream os = new FileOutputStream(dst);

			byte[] buf = new byte[this.bufferSize];
			int len;

			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
			os.flush();
			os.close();
			is.close();
		}	catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}	catch (IOException e) {
			System.err.println("Could not perform read !");
		}
	}
	public void copyData(File src, String dst) {
		File ddst = null;
		try {
			if (dst.equals("") == true) {
				throw new IllegalArgumentException("dst: Cannot be null !");
			}
			ddst = new File(dst);
			if (src.exists() == false || ddst.exists() == false) {
				if (src.exists() == false) {
					throw new FileNotFoundException("src: Could not find File !");
				}
				else if (ddst.exists() == false) {
					throw new FileNotFoundException("dst: Could not find File !");
				}
			}
			InputStream is = new FileInputStream(src);
			OutputStream os = new FileOutputStream(ddst);

			byte[] buf = new byte[this.bufferSize];
			int len;

			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
			os.flush();
			os.close();
			is.close();			
		}	catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}	catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}	catch (IOException e) {
			System.err.println("Could not perform read !");
		}
	}

	private int calculateBuffer(File f) throws IOException, NullPointerException {
		if (f == null) {
			throw new NullPointerException("f: Cannot be null !");
		}
		if (f.exists() == false) {
			throw new IOException("Could not find File !");
		}
		int calc = 1;
		for (int i = 1; i < this.bufferSize; i++) {
			long result = f.length() % i;
			if (result == 0) {
				System.out.println("Result: " + result + "     i: " + i);
				calc = i;
			}
		}
		return calc;
	}
	private int calculateBuffer(VirtualFile vf) throws NullPointerException {
		if (vf == null) {
			throw new NullPointerException("vf: Cannot be null !");
		}
		int calc = 1;
		for (int i = 1; i < this.bufferSize; i++) {
			long result = vf.size() % i;
			if (result == 0) {
				calc = i;
			}
		}
		return calc;
	}
	
	public void readByteToChar(InputStream is, StringBuilder sb) throws IOException {
		VirtualFile vf = new VirtualFile(this.bufferSize);
		vf.addData(is);
		
		byte[] buf = new byte[this.calculateBuffer(vf)];
		int len;
		while ((len = vf.read(buf)) > 0) {
			sb.append(this.byteToChar(buf));
		}
		//Cleaning up after the VirtualFile
		vf.clearAllData();
		vf = null;
	}
	public char[] byteToChar(byte[] b) {
		char[] temp = new char[b.length]; 
		for (int i = 0; i < b.length; i++) {
			temp[i] = (char)b[i];
		}
		return temp;
	}
	
	public void writeCharToByte(StringBuilder sb, File f) throws IOException {
		OutputStream os = new FileOutputStream(f);
		char[] buf = new char[sb.length()];
		sb.getChars(0, sb.length(), buf, 0);
		os.write(charToByte(buf));
		os.flush();
		os.close();
	}
	public byte[] charToByte(char[] c) {
		byte[] temp = new byte[c.length];
		for (int i = 0; i < c.length; i++) {
			temp[i] = (byte)c[i];
		}
		return temp;
	}
}
