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
	
	//This is the constructor method.
	public Category(int colour)
	{
		//TODO we might have to add an icon and a name of the category as well.
		pictograms = new ArrayList<Pictogram>();
		setCategoryColour(colour);
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

}
