package dk.aau.cs.giraf.launcher;

import java.util.HashMap;
import java.util.Random;

import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

class AppInfo extends App {

	/**
	 * The intent used to start the application.
	 */
	Intent intent;

	/**
	 * The application icon.
	 */
	private Drawable mIcon;
	
	/**
	 * 
	 * @return The icon for the app.
	 */
	public Drawable getIcon() {
		return this.mIcon;
	}

	/**
	 * The application icon background color.
	 */
	private int mBgColor;
	
	/**
	 * 
	 * @return The background color of the app.
	 */
	public int getBgColor() {
		return this.mBgColor;
	}
	
	/**
	 * ID of the guardian who is using the launcher.
	 */
	private Long mGuardianID;
	
	/**
	 * Set ID for the current guardian using the system.
	 * @param guardian The guardian using the system.
	 */
	public void setGuardian(Profile guardian) {
		if (guardian.getPRole() == 3) {
			mGuardianID = guardian.getId();
		} else {
			mGuardianID = -1L;
		}
	}
	
	/**
	 * 
	 * @return ID of the guardian logged into the system.
	 */
	public Long getGuardianID() {
		return mGuardianID;
	}
	
	/**
	 * Getter for the title of the app. 
	 * Cuts the name off, to make sure it's not too long to show in the launcher.
	 * @return 
	 */
	public String getShortenedName() {
		String title = super.getName();
		
		if(title.length() > 6){
			return title.subSequence(0, 5) + "...";
		} else {
			return title;
		}
	}
	
	/**
	 * Loads information needed by the app.
	 * @param context Context of the current activity.
	 */
	public void load(Context context) {
		loadBgColor(context);
		loadIcon(context);
		
		if (mGuardianID == null) {
			mGuardianID = -1L;
		}
	}
	
	/**
	 * Finds the background color of the app.
	 * @param context Context of the current activity.
	 */
	private void loadBgColor(Context context) {
		int[] c = context.getResources().getIntArray(R.array.appcolors);
		HashMap<String, String> colorSettings = this.getSettings().get(Tools.COLORS);
		
		if(colorSettings != null && colorSettings.containsKey(Tools.COLORS)) {
			mBgColor = Integer.parseInt(colorSettings.get(Tools.COLOR_BG));
		} else {
			Random rand = new Random();
			int position = rand.nextInt(c.length);
			
			this.getSettings().addValue(Tools.COLORS, Tools.COLOR_BG, String.valueOf(c[position]));
			mBgColor = c[position];
		}
	}
	
	/**
	 * Finds the icon of the app.
	 * @param context Context of the current activity.
	 */
	private void loadIcon(Context context) {
		
	}
}
