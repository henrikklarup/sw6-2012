package dk.aau.cs.giraf.oasis.lib.models;

import java.util.HashMap;

public class Stat<Key, Type, Value> extends HashMap<Key, HashMap<Type, Value>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Stat() {
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
}
