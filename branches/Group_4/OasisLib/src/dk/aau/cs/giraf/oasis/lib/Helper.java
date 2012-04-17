package dk.aau.cs.giraf.oasis.lib;

import android.content.Context;

import android.util.Log;
import dk.aau.cs.giraf.oasis.lib.controllers.AppsHelper;
import dk.aau.cs.giraf.oasis.lib.controllers.DepartmentsHelper;
import dk.aau.cs.giraf.oasis.lib.controllers.MediaHelper;
import dk.aau.cs.giraf.oasis.lib.controllers.ProfilesHelper;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

/**
 * Helper class, instantiating all the helper classes into one
 * @author Admin
 *
 */
public class Helper {
	
	private static Context _context;
	public ProfilesHelper profilesHelper;
	public MediaHelper mediaHelper;
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
		departmentsHelper = new DepartmentsHelper(_context);
		appsHelper = new AppsHelper(_context);
	}
	
	public void ClearAll() {
		profilesHelper.clearProfilesTable();
		mediaHelper.clearMediaTable();
		departmentsHelper.clearDepartmentsTable();
		appsHelper.clearAppsTable();
	}
	
	public void CreateDummyData() {
				
		/*Guardians*/
		Profile Guardian1 = new Profile("Guardian1", "First", "LoL1", 1, 88888888, null, null);
		Profile Guardian2 = new Profile("Guardian2", "Second", "LoL2", 1, 88888888, null, null);
		Profile Guardian3 = new Profile("Guardian3", "Third", "LoL3", 1, 88888888, null, null);
		/*Add guardians*/
		long guard1Id = profilesHelper.insertProfile(Guardian1);
		long guard2Id = profilesHelper.insertProfile(Guardian2);
		long guard3Id = profilesHelper.insertProfile(Guardian3);
		
		/*Load guardians*/
		Profile Guardian1Loaded = profilesHelper.getProfileById(guard1Id);
		Profile Guardian2Loaded = profilesHelper.getProfileById(guard2Id);
		Profile Guardian3Loaded = profilesHelper.getProfileById(guard3Id);
		
		/*Departments*/
		Department dep1 = new Department("Dep1", "Hjoernet", 88888888, "dep1[at]dep.com");
		Department subdep1 = new Department("subDep1", "Hjoernet", 88888888, "subdep1[at]dep.com");
		Department dep3 = new Department("Dep3", "Hjoernet", 88888888, "dep3[at]dep.com");
		
		/*Add departments*/
		long dep1Id = departmentsHelper.insertDepartment(dep1);
		long dep2Id = departmentsHelper.insertDepartment(subdep1);
		long dep3Id = departmentsHelper.insertDepartment(dep3);

		/*Load Departments*/
		Department dep1Loaded = departmentsHelper.getDepartmentById(dep1Id);
		Department subDep1Loaded = departmentsHelper.getDepartmentById(dep2Id);
		Department dep3Loaded = departmentsHelper.getDepartmentById(dep3Id);
		
		/*Add subdepartment*/
		departmentsHelper.attachSubDepartmentToDepartment(dep1Loaded, subDep1Loaded);
				
		/*Attach guardians to departments*/
		departmentsHelper.attachProfileToDepartment(Guardian1Loaded, dep1Loaded);
		departmentsHelper.attachProfileToDepartment(Guardian2Loaded, subDep1Loaded);
		departmentsHelper.attachProfileToDepartment(Guardian3Loaded, dep3Loaded);
		
		/*Set Hardcoded certificates*/
		profilesHelper.setCertificate("abcde", Guardian1Loaded);
		profilesHelper.setCertificate("fghij", Guardian2Loaded);
		profilesHelper.setCertificate("klmno", Guardian3Loaded);
		
		
		
		
		/*Children*/
		Profile Child1 = new Profile("Child1", "G1", "LoLA", 3, 88888888, null, null);
		Profile Child2 = new Profile("Child2", "G1", "LoLA", 3, 88888888, null, null);
		Profile Child3 = new Profile("Child3", "G2", "LoLB", 3, 88888888, null, null);
		Profile Child4 = new Profile("Child4", "G2", "LoLB", 3, 88888888, null, null);
		Profile Child5 = new Profile("Child5", "G3", "LoLC", 3, 88888888, null, null);
		/*Add children*/
		long child1Id = profilesHelper.insertProfile(Child1);
		long child2Id = profilesHelper.insertProfile(Child2);
		long child3Id = profilesHelper.insertProfile(Child3);
		long child4Id = profilesHelper.insertProfile(Child4);
		long child5Id = profilesHelper.insertProfile(Child5);
		
		/*Load children*/
		Profile Child1Loaded = profilesHelper.getProfileById(child1Id);
		Profile Child2Loaded = profilesHelper.getProfileById(child2Id);
		Profile Child3Loaded = profilesHelper.getProfileById(child3Id);
		Profile Child4Loaded = profilesHelper.getProfileById(child4Id);
		Profile Child5Loaded = profilesHelper.getProfileById(child5Id);
		
		/*Set Hardcoded certificates*/
		profilesHelper.setCertificate("pqrst", Child1Loaded);
		profilesHelper.setCertificate("uvxyz", Child2Loaded);
		profilesHelper.setCertificate("12345", Child3Loaded);
		profilesHelper.setCertificate("67890", Child4Loaded);
		profilesHelper.setCertificate("qwert", Child5Loaded);

		/*Attach children to department*/
		departmentsHelper.attachProfileToDepartment(Child1Loaded, dep1Loaded);
		departmentsHelper.attachProfileToDepartment(Child2Loaded, dep1Loaded);
		departmentsHelper.attachProfileToDepartment(Child3Loaded, subDep1Loaded);
		departmentsHelper.attachProfileToDepartment(Child4Loaded, subDep1Loaded);
		departmentsHelper.attachProfileToDepartment(Child5Loaded, dep3Loaded);
		
		
		/*Attach children and guardians*/
		profilesHelper.attachChildToGuardian(Child1Loaded, Guardian1Loaded);
		profilesHelper.attachChildToGuardian(Child2Loaded, Guardian1Loaded);
		profilesHelper.attachChildToGuardian(Child3Loaded, Guardian2Loaded);
		profilesHelper.attachChildToGuardian(Child4Loaded, Guardian2Loaded);
		profilesHelper.attachChildToGuardian(Child5Loaded, Guardian3Loaded);
		
		
		/*Media*/
		Media media1 = new Media("Media1", "/mnt/sdcard/Pictures/giraf/media1.jpg", false, "Picture", Child1Loaded.getId());
		Media media2 = new Media("Media2", "/mnt/sdcard/Pictures/giraf/media2.jpg", true, "Picture", Child1Loaded.getId());
		Media media3 = new Media("Media3", "/mnt/sdcard/Pictures/giraf/media3.jpg", false, "Picture", Child2Loaded.getId());
		
		mediaHelper.insertMedia(media1);
		mediaHelper.insertMedia(media2);
		mediaHelper.insertMedia(media3);
		
	}
	
}