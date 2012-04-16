package dk.aau.cs.giraf.parrot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;


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
		this.setName(name);
		this.setImagePath(imagePath);
		this.setSoundPath(soundPath);
		this.setWordPath(wordPath);
	}

	//TODO make methods to ensure that the constructor can not put illegal arguments as the path for images, sounds and words

	//TODO make methods to show images and play sounds and words.
	public ImageView getImage()
	{
		//FIXME this method will throw an exception if the path is invalid
		ImageView image = null;
		Bitmap bm = BitmapFactory.decodeFile(imagePath);
		image.setImageBitmap(bm);
		return image;
	}

	public Bitmap getBitmap()
	{
		Bitmap bm = BitmapFactory.decodeFile(imagePath);
		return bm;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSoundPath() {
		return soundPath;
	}

	public void setSoundPath(String soundPath) {
		this.soundPath = soundPath;
	}

	public String getWordPath() {
		return wordPath;
	}

	public void setWordPath(String wordPath) {
		this.wordPath = wordPath;
	}

	private boolean validPath(String path)
	{
		//this method checks if a given path is valid
		//TODO write me...
		return true; //change me
	}

	private void playItem(final String path) {
		// Running this in the background to keep UI reponsive and smooth
		new Thread(new Runnable() {
			public void run() {
				try {
					AudioPlayer.play(path, null);
				} catch (Exception e) {
				}
			}
		}).start();
		//TODO check that the thread is stopped again at some point.
	}

	public void playSound()
	{
		if(soundPath!=null && validPath(soundPath))
		{
			playItem(soundPath);
		}
	}

	public void playWord()
	{
		if(wordPath!=null &&validPath(wordPath))
		{
			playItem(wordPath);
		}
	}

}
