package dk.aau.cs.giraf.parrot;

import java.util.List;

import android.graphics.Color;

/**
 * 
 * @author Rasmus
 *The category class is used to store a number of Pictograms
 */
public class Category {
	private List<Pictogram> pictograms;
	private Color categoryColour;
	
	//This is the constructor method.
	public Category(Color colour)
	{
		//TODO we might have to add an icon and a name of the category as well.
		categoryColour = colour;
	}

	public List<Pictogram> getPictograms() {
		return pictograms;
	}
	
	public void addPictogram(Pictogram pic)
	{
		pictograms.add(pic);
	}

}
