package dk.aau.cs.giraf.oasis.lib;

import android.content.Context;
import dk.aau.cs.giraf.oasis.lib.controllers.AppsHelper;
import dk.aau.cs.giraf.oasis.lib.controllers.DepartmentsHelper;
import dk.aau.cs.giraf.oasis.lib.controllers.ListOfAppsHelper;
import dk.aau.cs.giraf.oasis.lib.controllers.MediaHelper;
import dk.aau.cs.giraf.oasis.lib.controllers.ProfilesHelper;

/**
 * Helper class, instantiating all the helper classes into one
 * @author Admin
 *
 */
public class Helper {
	
	private static Context _context;
	public ProfilesHelper profilesHelper;
	public MediaHelper mediaHelper;
	public ListOfAppsHelper listOfAppsHelper;
	public DepartmentsHelper departmentsHelper;
	public AppsHelper appsHelper;
	
	/**
	 * Constructor
	 * @param context Current context
	 */
	public Helper(Context context){
		_context = context;
		profilesHelper = new ProfilesHelper(_context);
		mediaHelper = new MediaHelper(_context);
		listOfAppsHelper = new ListOfAppsHelper(_context);
		departmentsHelper = new DepartmentsHelper(_context);
		appsHelper = new AppsHelper(_context);
	}
	
}
