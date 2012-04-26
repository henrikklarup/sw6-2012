package dk.aau.cs.giraf.oasis.lib;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import dk.aau.cs.giraf.oasis.lib.controllers.AppsHelper;
import dk.aau.cs.giraf.oasis.lib.controllers.DepartmentsHelper;
import dk.aau.cs.giraf.oasis.lib.controllers.MediaHelper;
import dk.aau.cs.giraf.oasis.lib.controllers.ProfilesHelper;
import dk.aau.cs.giraf.oasis.lib.controllers.ServerHelper;
import dk.aau.cs.giraf.oasis.lib.controllers.TagsHelper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Tag;

// Imports for private controllers
/*
import dk.aau.cs.giraf.oasis.lib.controllers.AuthUsersController;
import dk.aau.cs.giraf.oasis.lib.controllers.HasDepartmentController;
import dk.aau.cs.giraf.oasis.lib.controllers.HasGuardianController;
import dk.aau.cs.giraf.oasis.lib.controllers.HasLinkController;
import dk.aau.cs.giraf.oasis.lib.controllers.HasSubDepartmentController;
import dk.aau.cs.giraf.oasis.lib.controllers.HasTagController;
import dk.aau.cs.giraf.oasis.lib.controllers.ListOfAppsController;
import dk.aau.cs.giraf.oasis.lib.controllers.MediaDepartmentAccessController;
import dk.aau.cs.giraf.oasis.lib.controllers.MediaProfileAccessController;
*/

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
	public TagsHelper tagsHelper;
	public ServerHelper serverHelper;
	
	//Controllers only for viewDB
	/*
	public AuthUsersController au;
	public HasDepartmentController hd;
	public HasGuardianController hg;
	public HasLinkController hl;
	public HasSubDepartmentController hsd;
	public HasTagController ht;
	public ListOfAppsController loa;
	public MediaDepartmentAccessController mda;
	public MediaProfileAccessController mpa;
	*/
	
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
		tagsHelper = new TagsHelper(_context);
		serverHelper = new ServerHelper();
		
		//Controller only for viewDB
		/*
		au = new AuthUsersController(_context);
		hd = new HasDepartmentController(_context);
		hg = new HasGuardianController(_context);
		hl = new HasLinkController(_context);
		hsd = new HasSubDepartmentController(_context);
		ht = new HasTagController(_context);
		loa = new ListOfAppsController(_context);
		mda = new MediaDepartmentAccessController(_context);
		mpa = new MediaProfileAccessController(_context);
		*/
	}
	
	/**
	 * Clear all method
	 */
	public void ClearAll() {
		profilesHelper.clearProfilesTable();
		mediaHelper.clearMediaTable();
		departmentsHelper.clearDepartmentsTable();
		appsHelper.clearAppsTable();
		tagsHelper.clearTagsTable();
		
		//Controller only for viewDB
		/*
		au.clearAuthUsersTable();
		hd.clearHasDepartmentTable();
		hg.clearHasGuardianTable();
		hl.clearHasLinkTable();
		hsd.clearHasSubDepartmentTable();
		ht.clearHasTagTable();
		loa.clearListOfAppsTable();
		mda.clearMediaDepartmentAccessTable();
		mpa.clearMediaProfileAccessTable();
		*/
	}
	
	/**
	 * Dummy data method
	 */
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
		Media media1 = new Media("Media1", "/mnt/sdcard/Pictures/giraf/private/media1.jpg", false, "Picture", Child1Loaded.getId());
		Media media2 = new Media("Media2", "/mnt/sdcard/Pictures/giraf/public/media2.jpg", true, "Picture", Child1Loaded.getId());
		Media media3 = new Media("Media3", "/mnt/sdcard/Pictures/giraf/private/media3.jpg", false, "Picture", Child2Loaded.getId());
		
		long media1Id = mediaHelper.insertMedia(media1);
		long media2Id = mediaHelper.insertMedia(media2);
		long media3Id = mediaHelper.insertMedia(media3);
		
		Media media1Loaded = mediaHelper.getMediaById(media1Id);
		Media media2Loaded = mediaHelper.getMediaById(media2Id);
		Media media3Loaded = mediaHelper.getMediaById(media3Id);
		
		/*Attach submedia to media*/
		mediaHelper.attachSubMediaToMedia(media2Loaded, media2Loaded);
		
		/*Attach media to department*/
		mediaHelper.attachMediaToDepartment(media1Loaded, dep1Loaded, Child1Loaded);
		mediaHelper.attachMediaToDepartment(media2Loaded, dep1Loaded, Child1Loaded);
		mediaHelper.attachMediaToDepartment(media3Loaded, dep3Loaded, Child2Loaded);
		
		/*Tags*/
		Tag tag1 = new Tag("Dog1");
		Tag tag2 = new Tag("Dog2");
		Tag tag3 = new Tag("Dog3");
		
		long tag1Id = tagsHelper.insertTag(tag1);
		long tag2Id = tagsHelper.insertTag(tag2);
		long tag3Id = tagsHelper.insertTag(tag3);
		
		Tag tag1Loaded = tagsHelper.getTagById(tag1Id);
		Tag tag2Loaded = tagsHelper.getTagById(tag2Id);
		Tag tag3Loaded = tagsHelper.getTagById(tag3Id);
		
		List<Tag> tags1 = new ArrayList<Tag>();
		tags1.add(tag1Loaded);
		tags1.add(tag2Loaded);
		List<Tag> tags2 = new ArrayList<Tag>();
		tags2.add(tag3Loaded);
		tags2.add(tag2Loaded);
		
		
		/*Attach tag to media*/
		mediaHelper.addTagsToMedia(tags1, media1Loaded);
		mediaHelper.addTagsToMedia(tags2, media2Loaded);
		mediaHelper.addTagsToMedia(tags1, media3Loaded);
		
		/*Apps*/
		long appId;
		List<App> apps = appsHelper.getApps();
		
		if (!apps.isEmpty()) {
			appId = apps.get(apps.size() - 1).getId();
		} else {
			appId = 0;
		}
		
		App app1 = new App("App1", "1", "/mnt/sdcard/?", Long.toString(appId + 1), "FakeActivity");
		App app2 = new App("App2", "1", "/mnt/sdcard/?", Long.toString(appId + 2), "FakeActivity");
		App app3 = new App("App3", "1", "/mnt/sdcard/?", Long.toString(appId + 3), "FakeActivity");
		App app4 = new App("App4", "1", "/mnt/sdcard/?", Long.toString(appId + 4), "FakeActivity");
		
		long app1Id = appsHelper.insertApp(app1);
		long app2Id = appsHelper.insertApp(app2);
		long app3Id = appsHelper.insertApp(app3);
		long app4Id = appsHelper.insertApp(app4);
		
		/*Get apps*/
		App app1Loaded = appsHelper.getAppById(app1Id);
		App app2Loaded = appsHelper.getAppById(app2Id);	
		App app3Loaded = appsHelper.getAppById(app3Id);
		App app4Loaded = appsHelper.getAppById(app4Id);
		
		/*Attach app to profile*/
		appsHelper.attachAppToProfile(app1Loaded, Child1Loaded);
		appsHelper.attachAppToProfile(app2Loaded, Child1Loaded);
		appsHelper.attachAppToProfile(app3Loaded, Child2Loaded);
		appsHelper.attachAppToProfile(app4Loaded, Child3Loaded);
		
	}
	
}
