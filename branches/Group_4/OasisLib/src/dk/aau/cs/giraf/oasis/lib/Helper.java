package dk.aau.cs.giraf.oasis.lib;

import android.content.Context;
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
	
	public void CreateDummyData()
	{
		/*Departments*/
		Department dep1 = new Department("Dep1", "Hjoernet", 88888888, "dep1@dep.com");
		Department subdep1 = new Department("subDep1", "Hjoernet", 88888888, "subdep1@dep.com");
		Department dep3 = new Department("Dep3", "Hjoernet", 88888888, "dep3@dep.com");
		/*Add departments*/
		departmentsHelper.insertDepartment(dep1);
		departmentsHelper.insertDepartment(subdep1);
		departmentsHelper.insertDepartment(dep3);
		
		/*Department id's*/
		long dep1Id = 0, subDep1Id = 0, dep3Id = 0;
		
		/*Get departments*/
		for(Department d : departmentsHelper.getDepartments())
		{
			if(d.getName() == "Dep1")
				dep1Id = d.getId();
			else if(d.getName() == "subDep1")
				subDep1Id = d.getId();
			else if(d.getName() == "Dep3")
				dep3Id = d.getId();
		}
		/*Load Departments*/
		Department dep1Loaded = departmentsHelper.getDepartmentById(dep1Id);
		Department subDep1Loaded = departmentsHelper.getDepartmentById(subDep1Id);
		Department dep3Loaded = departmentsHelper.getDepartmentById(dep3Id);
		/*Add subdepartment*/
		departmentsHelper.attachSubDepartmentToDepartment(dep1Loaded, dep3Loaded);
		
		
		
		/*Guardians*/
		Profile Guardian1 = new Profile("Guardian1", "First", "LoL1", 1, 88888888, null, null);
		Profile Guardian2 = new Profile("Guardian2", "Second", "LoL2", 1, 88888888, null, null);
		Profile Guardian3 = new Profile("Guardian3", "Third", "LoL3", 1, 88888888, null, null);
		/*Add guardians*/
		profilesHelper.insertProfile(Guardian1);
		profilesHelper.insertProfile(Guardian2);
		profilesHelper.insertProfile(Guardian3);
		
		
		/*Guardian id's*/
		long guard1Id = 0, guard2Id = 0, guard3Id = 0;
		
		/*Get Guardians*/
		for(Profile g : profilesHelper.getProfiles())
		{
			if(g.getFirstname() == "Guardian1")
				guard1Id = g.getId();
			else if(g.getFirstname() == "Guardian2")
				guard2Id = g.getId();
			else if(g.getFirstname() == "Guardian3")
				guard3Id = g.getId();
		}
		
		/*Load guardians*/
		Profile Guardian1Loaded = profilesHelper.getProfileById(guard1Id);
		Profile Guardian2Loaded = profilesHelper.getProfileById(guard2Id);
		Profile Guardian3Loaded = profilesHelper.getProfileById(guard3Id);
		
		/*Attach guardians to departments*/
		departmentsHelper.attachProfileToDepartment(Guardian1Loaded, dep1Loaded);
		departmentsHelper.attachProfileToDepartment(Guardian2Loaded, subDep1Loaded);
		departmentsHelper.attachProfileToDepartment(Guardian3Loaded, dep3Loaded);
		
		
		
		
		/*Children*/
		Profile Child1 = new Profile("Child1", "G1", "LoLA", 3, 88888888, null, null);
		Profile Child2 = new Profile("Child2", "G1", "LoLA", 3, 88888888, null, null);
		Profile Child3 = new Profile("Child3", "G2", "LoLB", 3, 88888888, null, null);
		Profile Child4 = new Profile("Child4", "G2", "LoLB", 3, 88888888, null, null);
		Profile Child5 = new Profile("Child5", "G3", "LoLC", 3, 88888888, null, null);
		/*Add children*/
		profilesHelper.insertProfile(Child1);
		profilesHelper.insertProfile(Child2);
		profilesHelper.insertProfile(Child3);
		profilesHelper.insertProfile(Child4);
		profilesHelper.insertProfile(Child5);
		
		/*Children id's*/
		long child1Id = 0, child2Id = 0, child3Id = 0, child4Id = 0, child5Id = 0;
		
		/*Get children*/
		for(Profile g : profilesHelper.getProfiles())
		{
			if(g.getFirstname() == "Child1")
				guard1Id = g.getId();
			else if(g.getFirstname() == "Child2")
				guard2Id = g.getId();
			else if(g.getFirstname() == "Child3")
				guard3Id = g.getId();
			else if(g.getFirstname() == "Child4")
				guard3Id = g.getId();
			else if(g.getFirstname() == "Child5")
				guard3Id = g.getId();
		}
		
		/*Load children*/
		Profile Child1Loaded = profilesHelper.getProfileById(child1Id);
		Profile Child2Loaded = profilesHelper.getProfileById(child2Id);
		Profile Child3Loaded = profilesHelper.getProfileById(child3Id);
		Profile Child4Loaded = profilesHelper.getProfileById(child4Id);
		Profile Child5Loaded = profilesHelper.getProfileById(child5Id);

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
	}
	
}
