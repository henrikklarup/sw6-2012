package dk.aau.cs.giraf.parrot;

import java.util.ArrayList;

/**
 * 
 * @Rasmus
 *The Category class is used to store a number of pictograms
 */
public class Category {
	private ArrayList<Pictogram> pictograms; 
	private int categoryColour;
	private Pictogram icon;
	
	//This is the constructor method.
	public Category(int colour, Pictogram icon)
	{
		//TODO we might have to add an icon and a name of the category as well.
		pictograms = new ArrayList<Pictogram>();
		setCategoryColour(colour);
		setIcon(icon);
	}

	public ArrayList<Pictogram> getPictograms() {
		return pictograms;
	}
	
	public Pictogram getPictogramAtIndex(int i)
	{
		return pictograms.get(i);
	}
	
	public void addPictogram(Pictogram pic)
	{
		pictograms.add(pic);
	}
	
	public void addPictogramAtIndex(Pictogram pic, int index)
	{
		pictograms.add(index, pic);
	}
	
	public void removePictogram(int i)
	{
		pictograms.remove(i);
	}

	public int getCategoryColour() {
		return categoryColour;
	}

	public void setCategoryColour(int categoryColour) {
		this.categoryColour = categoryColour;
	}

	public Pictogram getIcon() {
		return icon;
	}

	public void setIcon(Pictogram icon) {
		this.icon = icon;
	}

}
