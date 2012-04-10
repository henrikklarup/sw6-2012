package savannah.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;

public class TestModule {

	public static void main(String[] args) throws IOException {
		File src = new File("C:\\temp\\2292.jpg");
		File dst = new File("C:\\temp\\30767.jpg");
		System.out.println(dst.toURI());
		File tre = new File("C:\\temp\\rand.txt");
		File snd = new File("C:\\temp\\temp.mp3");

		TransmissionPackage tp = new TransmissionPackage(CRUD.COMMIT, "lol - XML - xD @!?0", src, dst,tre,snd);
		long t1 = System.currentTimeMillis();
		StringBuilder sb = tp.getPackage();
		long t2 = System.currentTimeMillis();
//		System.out.println(sb.toString());
		System.out.println("Time to calc Packet: " + (t2 - t1));
		System.out.println("Packet Size: " + (tp.approximateTransmissionSize()/1024) + "KB");
		
		FileModule fm = new FileModule(4096);
		fm.writeCharToByte(sb, tre);
		
		TransmissionHandler th = new TransmissionHandler(new FileInputStream(tre), 4096, "C:\\newFolder");
//		th.write();
		
//		StringBuilder sb = new StringBuilder();
//		InputStream is = new FileInputStream(src);
//		System.out.println("Result: " + calcBuff(tre));
		
//		Test1(src, tre, 4096);
	}
	public static void Test1(File src, File tre, int bufferSize) throws IOException {
		FileModule fm = new FileModule(bufferSize);
		StringBuilder sb = new StringBuilder();
		InputStream is = new FileInputStream(src);
		
		System.out.println("File: " + src.getName() + " - Size: " + src.length());
		
		System.out.println("Starting to read !");
		long start1 = System.currentTimeMillis();
		fm.readByteToChar(is, sb);
		long end1 = System.currentTimeMillis();
		System.out.println("Read complete !");
		System.out.println("Read took: " + (end1 - start1) + "\n");
		
		System.out.println("Starting to write !");
		long start2 = System.currentTimeMillis();
		fm.writeCharToByte(sb, tre);
		long end2 = System.currentTimeMillis();
		System.out.println("Write complete !");
		System.out.println("Write took: " + (end2 - start2) + "\n");
		
		System.out.println("File: " + tre.getName() + " - Size: " + tre.length());
		System.out.println("Size diff: " + (src.length() - tre.length()));
	}

	public static int calcBuff(File f) throws IOException, NullPointerException {
		if (f == null) {
			throw new NullPointerException("f: Cannot be null !");
		}
		if (f.exists() == false) {
			throw new IOException("Could not find File !");
		}
		int calc = 1;
		for (int i = 1; i < 5000; i++) {
			long result = f.length() % i;
			if (result == 0) {
				System.out.println("Result: " + result + "     i: " + i);
				calc = i;
			}
		}
		return calc;
	}
	
	//If the buffer is, atm, more than 128 - then it does not correctly copy the file....!
	//Tests have shown that at buffer = 150 it fails !
	//Tests have shown that there might be a direct link between the number of iterations and bufferSize !
	public static void readAndConvert(int bufferSize, InputStream is, StringBuilder sb) throws IOException {
		byte[] buf = new byte[bufferSize];
		int len;
		
		while ((len = is.read(buf)) > 0) {
			sb.append(convert(buf));
		}
	}
	public static void writeTheConverted(StringBuilder sb, File f) throws IOException {
		OutputStream os = new FileOutputStream(f);
		
		char[] buf = new char[sb.length()];
		sb.getChars(0, sb.length(), buf, 0);
		os.write(convertB(buf));
		os.flush();
		os.close();
		
	}
	
	private static byte[] convertB(char[] c) {
		byte[] temp = new byte[c.length];
		for (int i = 0; i < c.length; i++) {
			temp[i] = (byte)c[i];
		}
		return temp;
	}
	
	private static char[] convert(byte[] b) {
		char[] temp = new char[b.length]; 
		for (int i = 0; i < b.length; i++) {
			temp[i] = (char)b[i];
		}
		return temp;
	}
	
	
	
	
	
	

}
