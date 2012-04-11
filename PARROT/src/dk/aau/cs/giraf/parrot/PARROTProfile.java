package dk.aau.cs.giraf.parrot;

import java.util.ArrayList;

/**
 * 
 * @Rasmus
 * This is the PARROT Profile class. It handles the information associated with the current user, such as available pictograms
 * organized in categories and settings, as well as personal information whenever it is needed.
 */
public class PARROTProfile {
	private String name;
	private Pictogram icon;
	private ArrayList<Category> categories;
	//TODO add settings such as colour specification and others...
	
	public PARROTProfile(String newName, Pictogram pic)
	{
		this.setName(newName);
		this.setIcon(pic);
	}

	public ArrayList<Category> getCategories() {
		return categories;
	}
	
	
	public Category getCategoryAt(int i)
	{
		return categories.get(i);
	}
	
	public void addCategory(Category cat)
	{
		categories.add(cat);
	}
	
	public void removeCaregory(int i)
	{
		categories.remove(i);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Pictogram getIcon() {
		return icon;
	}

	public void setIcon(Pictogram icon) {
		this.icon = icon;
	}
}
