package dk.aau.cs.giraf.oasis.lib.models;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Setting<Key, Type, Value> extends HashMap<Key, HashMap<Type, Value>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Setting() {
		super();
	}
	
	public void addValue(Key key, Type type, Value value)
	{
		HashMap<Type, Value> typeMap = get(type);
		if(typeMap == null) {
			typeMap = new HashMap<Type, Value>();
			put(key, typeMap);
		}
		typeMap.put(type, value);
	}
	
	public static byte[] toByteArray (Setting<String, String, String> setting)
	{
	  Object obj = (Object)setting;
	  byte[] bytes = null;
	  ByteArrayOutputStream bos = new ByteArrayOutputStream();
	  try {
	    ObjectOutputStream oos = new ObjectOutputStream(bos); 
	    oos.writeObject(obj);
	    oos.flush(); 
	    oos.close(); 
	    bos.close();
	    bytes = bos.toByteArray ();
	  }
	  catch (IOException ex) {
	    //TODO: Handle the exception
	  }
	  return bytes;
	}
	    
	public static Setting<String, String, String> toObject (byte[] bytes)
	{
	  Object obj = null;
	  try {
	    ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
	    ObjectInputStream ois = new ObjectInputStream (bis);
	    obj = ois.readObject();
	  }
	  catch (IOException ex) {
	    //TODO: Handle the exception
	  }
	  catch (ClassNotFoundException ex) {
	    //TODO: Handle the exception
	  }
	  Setting<String, String, String> setting = (Setting<String, String, String>)obj;
	  return setting;
	}
}
