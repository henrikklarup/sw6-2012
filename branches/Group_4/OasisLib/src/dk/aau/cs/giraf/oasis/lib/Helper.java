package dk.aau.cs.giraf.oasis.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	
//	/**
//	 * Clear all method
//	 */
//	public void ClearAll() {
//		profilesHelper.clearProfilesTable();
//		mediaHelper.clearMediaTable();
//		departmentsHelper.clearDepartmentsTable();
//		appsHelper.clearAppsTable();
//		tagsHelper.clearTagsTable();
//		
//		//Controller only for viewDB
//		/*
//		au.clearAuthUsersTable();
//		hd.clearHasDepartmentTable();
//		hg.clearHasGuardianTable();
//		hl.clearHasLinkTable();
//		hsd.clearHasSubDepartmentTable();
//		ht.clearHasTagTable();
//		loa.clearListOfAppsTable();
//		mda.clearMediaDepartmentAccessTable();
//		mpa.clearMediaProfileAccessTable();
//		*/
//	}
	
	/**
	 * Dummy data method
	 */
	public void CreateDummyData() {
		
		/*Guardians*/
		Profile guardian1 = new Profile("User01", "User01", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		Profile guardian2 = new Profile("User02", "User02", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		Profile guardian3 = new Profile("User03", "User03", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		Profile guardian4 = new Profile("User04", "User04", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		Profile guardian5 = new Profile("User05", "User05", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		Profile guardian6 = new Profile("User06", "User06", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		Profile guardian7 = new Profile("User07", "User07", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		Profile guardian8 = new Profile("User08", "User08", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		Profile guardian9 = new Profile("User09", "User09", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		Profile guardian0 = new Profile("User10", "User10", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		
		/*Add guardians*/
		long guardian1Id = profilesHelper.insertProfile(guardian1);
		long guardian2Id = profilesHelper.insertProfile(guardian2);
		long guardian3Id = profilesHelper.insertProfile(guardian3);
		long guardian4Id = profilesHelper.insertProfile(guardian4);
		long guardian5Id = profilesHelper.insertProfile(guardian5);
		long guardian6Id = profilesHelper.insertProfile(guardian6);
		long guardian7Id = profilesHelper.insertProfile(guardian7);
		long guardian8Id = profilesHelper.insertProfile(guardian8);
		long guardian9Id = profilesHelper.insertProfile(guardian9);
		long guardian0Id = profilesHelper.insertProfile(guardian0);
		
		/*Load guardians*/
		Profile guardian1Loaded = profilesHelper.getProfileById(guardian1Id);
		Profile guardian2Loaded = profilesHelper.getProfileById(guardian2Id);
		Profile guardian3Loaded = profilesHelper.getProfileById(guardian3Id);
		Profile guardian4Loaded = profilesHelper.getProfileById(guardian4Id);
		Profile guardian5Loaded = profilesHelper.getProfileById(guardian5Id);
		Profile guardian6Loaded = profilesHelper.getProfileById(guardian6Id);
		Profile guardian7Loaded = profilesHelper.getProfileById(guardian7Id);
		Profile guardian8Loaded = profilesHelper.getProfileById(guardian8Id);
		Profile guardian9Loaded = profilesHelper.getProfileById(guardian9Id);
		Profile guardian0Loaded = profilesHelper.getProfileById(guardian0Id);
	
		/*Set Hardcoded certificates*/
		profilesHelper.setCertificate("jkkxlagqyrztlrexhzofekyzrnppajeobqxcmunkqhsbrgpxdtqgygnmbhrgnpphaxsjshlpupgakmirhpyfaivvtpynqarxsghhilhkqvpelpreevykxurtppcggkzfaepihlodgznrmbrzgqucstflhmndibuymmvwauvdlyqnnlxkurinuypmqypspmkqavuhfwsh", guardian1);
		profilesHelper.setCertificate("ldsgjrtvuiwclpjtuxysmyjgpzsqnrtbwbdmgpalnlwtxzdubhbbtkioukaiwgbebhwovfuevykgbbnktnbzhxwugnkkllgjyovisfzzghyuqvxaoscblwqtvujqzgctslihoqetymxfupblcegpfjrfzyrfnjwevgeimxkrdixocyqmaxmyelptofyrsrtrggffmgak", guardian2);
		profilesHelper.setCertificate("atntrqkxlnftvxquwrwcdloaumdhzkgyglezzsebpvnethrlstvmlorrolymdynjcyonkrtvcuagwigdqqkftsxxhklcnbhznthcqjxnjzzdoqvmfdlxrudcyakvrnfcbohdumawlwmfndjascmvrsoxfjgwzhdvcvqcroxoyjeazmxtrjtlkldoevgdrqvgfbklhtgm", guardian3);
		profilesHelper.setCertificate("juuaygujymvacvldvhgirtvtumbdtfhmthtumpgqjvlhzvpzmwezifupvfhpjermlckxdvmjfpmqfadepwdvgdtwvoqkruyeuklsrurgirioqiqdzdxnbuemdmezycyncjqkvcjhgfusfggckxaispgbrzcxmtrgztnbshucxpaoodjvqujhwyeccnsxfjgkrjfoszvu", guardian4);
		profilesHelper.setCertificate("qyrmohbmcnsljhknvggvcdifarowqvpckzxfvlkwnglztjormumiroifttxbqzmybyvbulrvnoxrdidieoxeeayxkohqrwapdnszdnnegsgdnwdoenjlwcgurjtvmufwhjfnkcpyalzkrvzmspdliaodnlkookaszjyurwjclxufomktgucbsaknxztrpkhxutbelrrc", guardian5);
		profilesHelper.setCertificate("bphiomxvbsricewxcpuzpdtqjdcywlaplsmzjqzayhdyxeawyaeeofkpvfhwaudzwaafihtfuddsbrjhuztepopztbdgcokafnrgqrbaydsryfianltscyitukssklazgubhkdvvjqolmwiyyhuidhyqxoxwabmvdnnxatvzhvkawyiktbswjdcqlustzermuytgqvae", guardian6);
		profilesHelper.setCertificate("qldstjxxvbdacxfqjfwbjysjzmuobkajrdnofbtewuwpfkrobhqeblvpolnwtrhxiovuepqgssemakkjvpqoworokauseymbhafvmyhcnpdfxvpevsnjvbcwzlbordoaifgjixztsadmhldzbnvbaaxvmhssijnhvrqfretxqxhxvxsjuwcknxbktfigctbwppndwxpj", guardian7);
		profilesHelper.setCertificate("duzogdegzhtazsqmjwmxfktmnqcbpuxuvgxmbhpkzcnomoxrtrqlfisqdvfmnhmrmssocxifquqtfnzczzznunywesepobaiikgzlaecairmrlcqzdtfrxmispgamrwwcgzvlfnaysrexwdtmhytgpnncelikvrfozixdtsixwipnfactxywyeqvjhosqwekdnbcbcac", guardian8);
		profilesHelper.setCertificate("wzkbaaogonrkgckgfrjrwdvklpcwpmloamhlfqmytotpqkrixkwqnqamazcbybhjfdalsvqpdpiwlyctcuvtyclgreonxkqqevokjdbjwdcrgkhozleidpnoiwkdmcaylmosmwbfsrcnmlwlstgfljfwdgodinjrjrygeurrxyjpudsqukqkdgwwerlotgafhqhlxszv", guardian9);
		profilesHelper.setCertificate("osrvixzwyklicmkcwymiccawlbgctvigycafvftciuznhqrrztnyoafqrfuskqdbddrrppnadthngsfsdvooybjfwdfcdzxfdpzyvaxibxcbqnebgifdusgldvdkeonkvdmcmwffghreolhfxhrwgcogpsfayzxsoeyqwddposjdqwwiovnwabefudybzapihunhluaj", guardian0);
		
		/*Children*/
		Profile child1 = new Profile("Child1", "Child1", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);
		Profile child2 = new Profile("Child2", "Child2", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);
		Profile child3 = new Profile("Child3", "Child3", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);
		Profile child4 = new Profile("Child4", "Child4", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);
		Profile child5 = new Profile("Child5", "Child5", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);
		Profile child6 = new Profile("Child6", "Child6", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);
		Profile child7 = new Profile("Child7", "Child7", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);
		Profile child8 = new Profile("Child8", "Child8", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);
		Profile child9 = new Profile("Child9", "Child9", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);
		Profile child0 = new Profile("Child10", "Child10", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);

		/*Add children*/
		long child1Id = profilesHelper.insertProfile(child1);
		long child2Id = profilesHelper.insertProfile(child2);
		long child3Id = profilesHelper.insertProfile(child3);
		long child4Id = profilesHelper.insertProfile(child4);
		long child5Id = profilesHelper.insertProfile(child5);
		long child6Id = profilesHelper.insertProfile(child6);
		long child7Id = profilesHelper.insertProfile(child7);
		long child8Id = profilesHelper.insertProfile(child8);
		long child9Id = profilesHelper.insertProfile(child9);
		long child0Id = profilesHelper.insertProfile(child0);
		
		/*Load children*/
		Profile child1Loaded = profilesHelper.getProfileById(child1Id);
		Profile child2Loaded = profilesHelper.getProfileById(child2Id);
		Profile child3Loaded = profilesHelper.getProfileById(child3Id);
		Profile child4Loaded = profilesHelper.getProfileById(child4Id);
		Profile child5Loaded = profilesHelper.getProfileById(child5Id);
		Profile child6Loaded = profilesHelper.getProfileById(child6Id);
		Profile child7Loaded = profilesHelper.getProfileById(child7Id);
		Profile child8Loaded = profilesHelper.getProfileById(child8Id);
		Profile child9Loaded = profilesHelper.getProfileById(child9Id);
		Profile child0Loaded = profilesHelper.getProfileById(child0Id);

		/*Attach children and guardians*/
		profilesHelper.attachChildToGuardian(child1Loaded, guardian1Loaded);
		profilesHelper.attachChildToGuardian(child2Loaded, guardian1Loaded);
		profilesHelper.attachChildToGuardian(child3Loaded, guardian1Loaded);
		profilesHelper.attachChildToGuardian(child4Loaded, guardian1Loaded);
		profilesHelper.attachChildToGuardian(child5Loaded, guardian1Loaded);
		profilesHelper.attachChildToGuardian(child6Loaded, guardian1Loaded);
		profilesHelper.attachChildToGuardian(child7Loaded, guardian1Loaded);
		profilesHelper.attachChildToGuardian(child8Loaded, guardian1Loaded);
		profilesHelper.attachChildToGuardian(child9Loaded, guardian1Loaded);
		profilesHelper.attachChildToGuardian(child0Loaded, guardian1Loaded);
		
		profilesHelper.attachChildToGuardian(child2Loaded, guardian2Loaded);
		profilesHelper.attachChildToGuardian(child3Loaded, guardian3Loaded);
		profilesHelper.attachChildToGuardian(child4Loaded, guardian4Loaded);
		profilesHelper.attachChildToGuardian(child5Loaded, guardian5Loaded);
		profilesHelper.attachChildToGuardian(child6Loaded, guardian6Loaded);
		profilesHelper.attachChildToGuardian(child7Loaded, guardian7Loaded);
		profilesHelper.attachChildToGuardian(child8Loaded, guardian8Loaded);
		profilesHelper.attachChildToGuardian(child9Loaded, guardian9Loaded);
		profilesHelper.attachChildToGuardian(child0Loaded, guardian0Loaded);
		
		/*Departments*/
		Department department1 = new Department("Egebakken", "Hjoernet", 88888888, "dep1@dep.com");
		Department department2 = new Department("Birken", "Hjoernet", 88888888, "dep2@dep.com");
		Department subdepartment11 = new Department("SubDep11", "Hjoernet", 88888888, "subdep11@dep.com");
		Department subdepartment12 = new Department("SubDep12", "Hjoernet", 88888888, "subdep12@dep.com");
		Department subsubdepartment111= new Department("SubSubDep111", "Hjoernet", 88888888, "subsubdep111@dep.com");
		Department subsubdepartment112 = new Department("SubSubDep112", "Hjoernet", 88888888, "subsubdep112@dep.com");
		Department subsubdepartment121 = new Department("SubSubDep123", "Hjoernet", 88888888, "subsubdep123@dep.com");
		
		
		/*Add departments*/
		long department1Id = departmentsHelper.insertDepartment(department1);
		long department2Id = departmentsHelper.insertDepartment(department2);
		long subdepartment11Id = departmentsHelper.insertDepartment(subdepartment11);
		long subdepartment12Id = departmentsHelper.insertDepartment(subdepartment12);
		long subsubdepartment111Id = departmentsHelper.insertDepartment(subsubdepartment111);
		long subsubdepartment112Id = departmentsHelper.insertDepartment(subsubdepartment112);
		long subsubdepartment121Id = departmentsHelper.insertDepartment(subsubdepartment121);

		/*Load Departments*/
		Department department1Loaded = departmentsHelper.getDepartmentById(department1Id);
		Department department2Loaded = departmentsHelper.getDepartmentById(department2Id);
		Department subdepartment11Loaded = departmentsHelper.getDepartmentById(subdepartment11Id);
		Department subdepartment12Loaded = departmentsHelper.getDepartmentById(subdepartment12Id);
		Department subsubdepartment111Loaded = departmentsHelper.getDepartmentById(subsubdepartment111Id);
		Department subsubdepartment112Loaded = departmentsHelper.getDepartmentById(subsubdepartment112Id);
		Department subsubdepartment121Loaded = departmentsHelper.getDepartmentById(subsubdepartment121Id);
		
		/*Add subdepartments*/
		departmentsHelper.attachSubDepartmentToDepartment(department1Loaded, subdepartment11Loaded);
		departmentsHelper.attachSubDepartmentToDepartment(department1Loaded, subdepartment12Loaded);
		departmentsHelper.attachSubDepartmentToDepartment(subdepartment11Loaded, subsubdepartment111Loaded);
		departmentsHelper.attachSubDepartmentToDepartment(subdepartment11Loaded, subsubdepartment112Loaded);
		departmentsHelper.attachSubDepartmentToDepartment(subdepartment12Loaded, subsubdepartment121Loaded);
				
		/*Attach guardians to departments*/
		departmentsHelper.attachProfileToDepartment(guardian1Loaded, department1Loaded);
		departmentsHelper.attachProfileToDepartment(guardian2Loaded, department2Loaded);
		departmentsHelper.attachProfileToDepartment(guardian3Loaded, subdepartment11Loaded);
		departmentsHelper.attachProfileToDepartment(guardian4Loaded, subdepartment12Loaded);
		departmentsHelper.attachProfileToDepartment(guardian5Loaded, subsubdepartment111Loaded);
		departmentsHelper.attachProfileToDepartment(guardian6Loaded, subsubdepartment112Loaded);
		departmentsHelper.attachProfileToDepartment(guardian7Loaded, subsubdepartment121Loaded);
		departmentsHelper.attachProfileToDepartment(guardian8Loaded, subsubdepartment111Loaded);
		departmentsHelper.attachProfileToDepartment(guardian9Loaded, subsubdepartment112Loaded);
		departmentsHelper.attachProfileToDepartment(guardian0Loaded, subsubdepartment121Loaded);
		
		/*Attach children to department*/
		departmentsHelper.attachProfileToDepartment(child1Loaded, department1Loaded);
		departmentsHelper.attachProfileToDepartment(child2Loaded, department2Loaded);
		departmentsHelper.attachProfileToDepartment(child3Loaded, subdepartment11Loaded);
		departmentsHelper.attachProfileToDepartment(child4Loaded, subdepartment12Loaded);
		departmentsHelper.attachProfileToDepartment(child5Loaded, subsubdepartment111Loaded);
		departmentsHelper.attachProfileToDepartment(child6Loaded, subsubdepartment112Loaded);
		departmentsHelper.attachProfileToDepartment(child7Loaded, subsubdepartment121Loaded);
		departmentsHelper.attachProfileToDepartment(child8Loaded, subsubdepartment111Loaded);
		departmentsHelper.attachProfileToDepartment(child9Loaded, subsubdepartment112Loaded);
		departmentsHelper.attachProfileToDepartment(child0Loaded, subsubdepartment121Loaded);
		
		/*Media*/
		Media media1 = new Media("Media1", "/mnt/sdcard/Pictures/giraf/private/media1.jpg", false, "Picture", child1Loaded.getId());
		Media media2 = new Media("Media2", "/mnt/sdcard/Pictures/giraf/public/media2.jpg", true, "Picture", child1Loaded.getId());
		Media media3 = new Media("Media3", "/mnt/sdcard/Pictures/giraf/private/media3.jpg", false, "Picture", child2Loaded.getId());
		
		long media1Id = mediaHelper.insertMedia(media1);
		long media2Id = mediaHelper.insertMedia(media2);
		long media3Id = mediaHelper.insertMedia(media3);
		
		Media media1Loaded = mediaHelper.getMediaById(media1Id);
		Media media2Loaded = mediaHelper.getMediaById(media2Id);
		Media media3Loaded = mediaHelper.getMediaById(media3Id);
		
		/*Attach submedia to media*/
		mediaHelper.attachSubMediaToMedia(media2Loaded, media2Loaded);
		
		/*Attach media to department*/
		mediaHelper.attachMediaToDepartment(media1Loaded, department1Loaded, child1Loaded);
		mediaHelper.attachMediaToDepartment(media2Loaded, department1Loaded, child1Loaded);
		mediaHelper.attachMediaToDepartment(media3Loaded, subdepartment11Loaded, child2Loaded);
		
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
		String basePackageName = "dk.aau.cs.giraf.";
		
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 10 + 1; i++)
		{
			sb.append((char)(rnd.nextInt(26) + 97));
		}
		String randomString = sb.toString();
		
		App app1 = new App("App1", "1", "/mnt/sdcard/?", basePackageName + randomString, "FakeActivity");
		App app2 = new App("App2", "1", "/mnt/sdcard/?", basePackageName + randomString, "FakeActivity");
		App app3 = new App("App3", "1", "/mnt/sdcard/?", basePackageName + randomString, "FakeActivity");
		App app4 = new App("App4", "1", "/mnt/sdcard/?", basePackageName + randomString, "FakeActivity");
		
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
		appsHelper.attachAppToProfile(app1Loaded, child1Loaded);
		appsHelper.attachAppToProfile(app2Loaded, child1Loaded);
		appsHelper.attachAppToProfile(app3Loaded, child2Loaded);
		appsHelper.attachAppToProfile(app4Loaded, child3Loaded);
		
	}
	
}
