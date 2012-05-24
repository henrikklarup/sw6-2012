package dk.aau.cs.giraf.savannah.test.JUnit;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import dk.aau.cs.giraf.savannah.io.IOHandler;
import dk.aau.cs.giraf.savannah.io.CRUD;

public class IOHandlerJUnit extends TestCase {
	//Field Variable(s)
	private IOHandler instance;

	public IOHandlerJUnit(String name) {
		super(name);
	}

	protected void setUp() {
		this.instance = IOHandler.getInstance();
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	// ------------------------------------------------- \\
	// 	Tests for calculations and conversions        \\
	// ------------------------------------------------- \\
	public void test1() throws IllegalArgumentException, InvocationTargetException {
		char[] testData = new char[]{'T','e','s','t',' ','d','a','t','a'};
		String testDataAsString = new String(testData);

		byte[] testDataAsByte = (byte[]) PrivateAccessor.getPrivateMethod(this.instance, "messageToBytes",testData);
		String result = "";
		for(int i = 0; i < testDataAsByte.length; i++) {
			result += (char)testDataAsByte[i];
		}

		assertEquals("Should be a clean conversion", testDataAsString, result);
	}
	public void test2() throws IllegalArgumentException, InvocationTargetException {
		//Equal to "Test data"
		byte[] testData = new byte[]{84, 101, 115, 116, 32, 100, 97, 116, 97};
		String result = (String) PrivateAccessor.getPrivateMethod(this.instance, "messageToString", testData);

		assertEquals("Should be a clean conversion", "Test data", result);
	}
	public void test3() throws IllegalArgumentException, InvocationTargetException {
		StringBuilder testData = new StringBuilder("Test data");
		byte[] testDataAsBytes = (byte[]) PrivateAccessor.getPrivateMethod(this.instance, "stringBuilderToBytes", testData);

		String result = "";
		for (int i = 0; i < testDataAsBytes.length; i++) {
			result += (char)testDataAsBytes[i];
		}

		assertEquals("Should be a clean conversion", testData.toString(), result);
	}
	public void test4() throws IllegalArgumentException, InvocationTargetException {
		int testData = 1337;
		int result = (int) PrivateAccessor.getPrivateMethod(this.instance, "sizeOf", testData);

		assertEquals("Number of cells an int uses", 4, result);
	}

	/*
	 * For some reason it tries to pass the arg, long, as an int, this fails.
	 * It is probably because of method overloading, since this is not implemented specified in the
	 * PrivateAccessor.getPrivateMethod() - it just takes the
	 * first method with the supplied name......
	 */
	public void test5() throws IllegalArgumentException, InvocationTargetException {
		long testData = 1234567890L;
		int result = (int) PrivateAccessor.getPrivateMethod(this.instance, "sizeOf", testData);

		assertEquals("Number of cells a long uses", 10, result);
	}
	public void test6() throws IllegalArgumentException, InvocationTargetException {
		int testData = 1337;
		String result = (String) PrivateAccessor.getPrivateMethod(this.instance, "xmlLength", testData);

		assertEquals("How the xmlLength represents its length", "0000001337", result);
	}
	public void test7() throws IllegalArgumentException, InvocationTargetException {
		long testData = 4690624512L;
		String result = (String) PrivateAccessor.getPrivateMethod(this.instance, "fileLength", testData);

		assertEquals("How the fileLength represents its length", "0000000004690624512", result);
	}
	public void test8() throws IllegalArgumentException, InvocationTargetException {
		String testData = "XMLSchema.xsd";
		String result = (String) PrivateAccessor.getPrivateMethod(this.instance, "fileName", testData);

		/* DO NOT TOUCH THIS STRING !!! */
		String expected = "                                                                                                                                                                                                                                                   XMLSchema.xsd";

		assertEquals("How the fileName represents it name", expected, result);
	}
	public void test9() throws IllegalArgumentException, InvocationTargetException {
		int testData = 01;
		String result = (String) PrivateAccessor.getPrivateMethod(this.instance, "pingLength", testData);

		assertEquals("How the pingLength represents its length", "0001", result);
	}

	/*
	 * makePing 
	 */
	public void test10() throws IllegalArgumentException, InvocationTargetException {
		StringBuilder sb = new StringBuilder();
		int testData = 32;
		PrivateAccessor.getPrivateMethod(this.instance, "makePing", sb, testData);
		
		Pattern expr = Pattern.compile("PING\\[[0-9]*\\]");
		Matcher man = expr.matcher(sb.toString());
		int pingLength = -1;
		if (man.find() == true) {
			expr = Pattern.compile("\\[[0-9]*\\]");
			man = expr.matcher(sb.toString());
			if (man.find() == true) {
				pingLength = Integer.parseInt(sb.toString().substring(man.start()+1, man.end()-1));
			}
		}
		String randomData = sb.toString().substring(man.end()+2, man.end()+34);
		
		assertEquals("The length of a ping", 32, pingLength);
		assertEquals("The amount of random data in a ping", testData, randomData.length());
		
	}
	
	/*
	 * makeCRUD
	 */
	public void test11() throws IllegalArgumentException, InvocationTargetException {
		StringBuilder sb = new StringBuilder();
		PrivateAccessor.getPrivateMethod(this.instance, "makeCRUD", CRUD.COMMIT, sb);
		
		assertEquals("How a CRUD is represented", "TYPE[1]", sb.toString());
	}
	
	/*
	 * makeXML
	 */
	public void test12() throws IllegalArgumentException, InvocationTargetException {
		StringBuilder sb = new StringBuilder();
		String testData = "<random>stuff</random>";
		boolean noFiles = false;
		PrivateAccessor.getPrivateMethod(this.instance, "makeXML", testData, noFiles, sb);
		
		assertEquals("How a makeXML represents its data", "MXML[0000000022,0]=\"<random>stuff</random>\"", sb.toString());
	}
	
	/*
	 * makeACCEPT
	 */
	public void test13() throws IllegalArgumentException, InvocationTargetException {
		StringBuilder sb = new StringBuilder();
		PrivateAccessor.getPrivateMethod(this.instance, "makeACCEPT", sb);
		
		assertEquals("How makeACCEPT represents its data", "[ACCEPT]", sb.toString());
	}
	
	/*
	 * sendFILE
	 */
	public void test14() throws IllegalArgumentException, InvocationTargetException, IOException {
		TestOutputStream os = new TestOutputStream(new StringBuilder());
		
		//The file contains: "This file is almost empty!"
		File f = new File(IOHandlerJUnit.class.getResource("./IOHandlerJUnitTestData01").getFile());
		boolean noFiles = false;
		
		//Cheating the method and using an OutputStream "suited" for debugging
		PrivateAccessor.getPrivateMethod(this.instance, "sendFILE", os, f, noFiles);
		
		String fileLength = (String) PrivateAccessor.getPrivateMethod(this.instance, "fileLength", f.length());
		String fileName = (String) PrivateAccessor.getPrivateMethod(this.instance, "fileName", f.getName());
		String header = "FILE[";
		int anyFiles = (noFiles == false) ? 0 : 1;
		String end = "," + anyFiles + "]=\"";
		String expected = header + fileLength + "," + fileName + end + this.readFile(f) + "\"";
		
		assertEquals("How a file is sent", expected, os.toString());
	}

	/*
	 * sendPackage1
	 * Has the same problem as test5() - PrivateAccessor.getPrivateMethod()
	 * does not "support" method overloading - of any type...
	 * However the execution sequence changes from compilation to compilation...
	 * And this in turns makes it work - at some point.
	 */
	public void test15() throws IllegalArgumentException, InvocationTargetException {
		TestOutputStream os = new TestOutputStream(new StringBuilder());
		int pingSize = 32;

		//Cheating the method and using an OutputStream "suited" for debugging
		PrivateAccessor.getPrivateMethod(this.instance, "sendPackage", os, pingSize);
		assertNotNull("Testing to see if a ping is received", os.toString());
	}
	
	/*
	 * sendPackage2
	 * Problem: see test15
	 */
	public void test16() throws IllegalArgumentException, InvocationTargetException {
		TestOutputStream os = new TestOutputStream(new StringBuilder());
		String testData = "<random>stuff</random>";
		
		PrivateAccessor.getPrivateMethod(this.instance, "sendPackage", os, CRUD.REQUEST, testData);
		assertNotNull("Testing to see if a request is received", os.toString());
	}
	
	/*
	 * sendPackage3
	 * Problem: see test15
	 */
	public void test17() throws IllegalArgumentException, InvocationTargetException {
		TestOutputStream os = new TestOutputStream(new StringBuilder());
		String testData = "<random>stuff</random>";
		File f = new File(IOHandlerJUnit.class.getResource("./IOHandlerJUnitTestData01").getFile());
		
		PrivateAccessor.getPrivateMethod(this.instance, "sendPackage", os, CRUD.COMMIT, testData, f);
		assertNotNull("Testing to see if a commit is received", os.toString());
	}
	
	/*
	 * sendPackage4
	 * Problem: see test15
	 */
	public void test18() throws IllegalArgumentException, InvocationTargetException {
		TestOutputStream os = new TestOutputStream(new StringBuilder());
		String testData = "<random>stuff</random>";
		File[] files = new File[]{new File(IOHandlerJUnit.class.getResource("./IOHandlerJUnitTestData01").getFile()),
								  new File(IOHandlerJUnit.class.getResource("./IOHandlerJUnitTestData01").getFile())};
		
		PrivateAccessor.getPrivateMethod(this.instance, "sendPackage", os, CRUD.COMMIT, testData, files);
		assertNotNull("Testing to see if a commit is received", os.toString());
	}

	public String readFile(File f) throws IOException {
		StringBuilder sb = new StringBuilder();
		InputStream is = new FileInputStream(f);
		
		//Just has to work - doesn't need optimal speed....
		byte[] buf = new byte[2];
		int len;
		
		while ((len = is.read(buf)) > 0) {
			sb.append(this.appendBuffer(buf));
		}
		return sb.toString();
	}
	public StringBuilder appendBuffer(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			sb.append((char)b[i]);
		}
		return sb;
	}
	class TestOutputStream extends OutputStream {
		private StringBuilder sb = null;
		
		public TestOutputStream(StringBuilder builder) {
			this.sb = builder;
		}
		
		@Override
		public void write(int arg0) throws IOException {
			this.sb.append((char)arg0);
			
		}
		@Override
		public synchronized void write(byte[]b) throws IOException {
			this.sb.append(this.appendBuffer(b));
		}
		
		@Override
		public String toString() {
			return this.sb.toString();
		}
		
		private StringBuilder appendBuffer(byte[] b) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < b.length; i++) {
				sb.append((char)b[i]);
			}
			return sb;
		}	
	}
	
	
}
