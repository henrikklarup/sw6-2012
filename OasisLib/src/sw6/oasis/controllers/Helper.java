package sw6.oasis.controllers;

import android.content.Context;

/**
 * Helper class, containing all functions you are ever gonna need!! xD
 * @author Admin
 *
 */
public class Helper {
	
	private static Context _context;
	public ProfilesHelper profilesHelper;
	public MediaHelper mediaHelper;
	public ListOfAppsHelper listOfAppsHelper;
	public DepartmentsHelper departmentsHelper;
	public CertificateHelper certificateHelper;
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
		certificateHelper = new CertificateHelper(_context);
		appsHelper = new AppsHelper(_context);
	}
	
}
