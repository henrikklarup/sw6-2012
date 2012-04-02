package dk.aau.cs.giraf.parrot;

import java.util.ArrayList;

import android.graphics.Color;

/**
 * 
 * @author Rasmus
 *The category class is used to store a number of Pictograms
 */
public class Category {
	private ArrayList<Pictogram> pictograms; 
	private int categoryColour;
	
	//This is the constructor method.
	public Category(int colour)
	{
		//TODO we might have to add an icon and a name of the category as well.
		pictograms = new ArrayList<Pictogram>();
		categoryColour = colour;
	}

	public ArrayList<Pictogram> getPictograms() {
		return pictograms;
	}
	
	public void addPictogram(Pictogram pic)
	{
		pictograms.add(pic);
	}

}
