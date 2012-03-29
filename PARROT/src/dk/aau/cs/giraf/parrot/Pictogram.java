package dk.aau.cs.giraf.parrot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore.Images;
import android.text.Html.ImageGetter;


/**
 * 
 * @author Rasmus
 * The Pictogram class contains the pictures used in PARROT, as well as associated sounds and text.
 *
 */
public class Pictogram {
	private String name;              ///< Corresponding text.
	private String imagePath;               ///< Corresponding image.
	private String soundPath;             ///< Corresponding sound effect.
	private String wordPath; 		///< Corresponding pronounciation.
	
	//This are the constructor for the Pictogram class
	public Pictogram(String name, String imagePath, String soundPath, String wordPath)
	{
		//FIXME replace these with safer methods
		this.name=name;
		this.imagePath=imagePath;
		this.soundPath=soundPath;
		this.wordPath=wordPath;
	}
	
	//TODO make methods to ensure that the constructor can not put illegal arguments as the path for images, sounds and words
	
	//TODO make methods to show images and play sounds and words.
	public Bitmap getImage()
	{
		return BitmapFactory.decodeFile(imagePath);
	}
	
}
