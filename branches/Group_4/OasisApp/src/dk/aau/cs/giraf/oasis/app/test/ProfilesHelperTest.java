package dk.aau.cs.giraf.oasis.app.test;

import java.util.List;
import java.util.Random;

import android.test.ActivityInstrumentationTestCase2;
import dk.aau.cs.giraf.oasis.app.MainActivity;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

public class ProfilesHelperTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mActivity;

	public ProfilesHelperTest() {
		super("dk.aau.cs.giraf.oasis.app", MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);

		mActivity = getActivity();
	}

	public void testRemoveProfile() {
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long id = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(id);
		int result = mActivity.helper.profilesHelper.removeProfile(newProfile);
		Profile p = mActivity.helper.profilesHelper.getProfileById(id);

		assertNull("Should be null, because Profile is removed", p);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testRemoveChildAttachmentToGuardian() {
		Profile newChild = new Profile("Test", "Child", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long idChild = mActivity.helper.profilesHelper.insertProfile(newChild);
		newChild.setId(idChild);
		Profile newGuard = new Profile("Test", "Guard", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idGuard = mActivity.helper.profilesHelper.insertProfile(newGuard);
		newGuard.setId(idGuard);
		mActivity.helper.profilesHelper.attachChildToGuardian(newChild, newGuard);
		int result = mActivity.helper.profilesHelper.removeChildAttachmentToGuardian(newChild, newGuard);

		assertEquals("Should return 1", 1, result);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testInsertProfile() {
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long id = mActivity.helper.profilesHelper.insertProfile(newProfile);

		assertNotSame("Should not be -1", -1, id);
	}

	public void testAttachChildToGuardian() {
		Profile newChild = new Profile("Test", "Child", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long idChild = mActivity.helper.profilesHelper.insertProfile(newChild);
		newChild.setId(idChild);
		Profile newGuard = new Profile("Test", "Guard", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idGuard = mActivity.helper.profilesHelper.insertProfile(newGuard);
		newGuard.setId(idGuard);
		int result = mActivity.helper.profilesHelper.attachChildToGuardian(newChild, newGuard);

		assertEquals("Should return 0", 0, result);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testModifyProfile() {
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long id = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(id);
		int result = mActivity.helper.profilesHelper.modifyProfile(newProfile);

		assertNotSame("Should not be -1", -1, result);
		assertEquals("Should return 1", 1, result);
	}

	public void testAuthenticateProfile() {
		Random rnd = new Random();
		StringBuilder cert = new StringBuilder();
		for (int i = 0; i < 200; i++)
		{
			cert.append((char)(rnd.nextInt(26) + 97));
		}
		String certificate = cert.toString();
		Profile expectedProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);

		long id = mActivity.helper.profilesHelper.insertProfile(expectedProfile);
		if (id != -1) {
			expectedProfile.setId(id);
			mActivity.helper.profilesHelper.setCertificate(certificate, expectedProfile);
		} else {
			// FOO
		}


		Profile actualProfile = mActivity.helper.profilesHelper.authenticateProfile(certificate);

		assertEquals("Should return profile; Test Profile", expectedProfile, actualProfile);
	}

	public void testSetCertificate() {
		Random rnd = new Random();
		StringBuilder cert = new StringBuilder();
		for (int i = 0; i < 200; i++)
		{
			cert.append((char)(rnd.nextInt(26) + 97));
		}
		String certificate = cert.toString();
		Profile expectedProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long id = mActivity.helper.profilesHelper.insertProfile(expectedProfile);
		expectedProfile.setId(id);
		int result = mActivity.helper.profilesHelper.setCertificate(certificate, expectedProfile);

		assertNotSame("Should not be -1", -1, result);
		assertEquals("Should return 1", 1, result);
	}

	public void testGetProfiles() {
		List<Profile> list = mActivity.helper.profilesHelper.getProfiles();

		assertNotNull("Should not be empty", list);
	}

	public void testGetGuardians() {
		List<Profile> list = mActivity.helper.profilesHelper.getGuardians();

		assertNotNull("Should not be empty", list);
	}

	public void testGetChildren() {
		List<Profile> list = mActivity.helper.profilesHelper.getChildren();

		assertNotNull("Should not be empty", list);
	}

	public void testGetCertificatesByProfile() {
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long id = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(id);
		List<String> list = mActivity.helper.profilesHelper.getCertificatesByProfile(newProfile);

		assertNotNull("Should not be empty", list);
	}

	public void testGetChildrenByDepartment() {
		Department newDepartment = new Department("TestDep", "TestAddress", 12345678, "test@test.dk");
		long id = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(id);
		List<Profile> list = mActivity.helper.profilesHelper.getChildrenByDepartment(newDepartment);
		
		assertNotNull("Should not be empty", list);
	}

	public void testGetChildrenByDepartmentAndSubDepartments() {
		Department newDepartment = new Department("TestDep", "TestAddress", 12345678, "test@test.dk");
		long id = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(id);
		List<Profile> list = mActivity.helper.profilesHelper.getChildrenByDepartmentAndSubDepartments(newDepartment);
		
		assertNotNull("Should not be empty", list);
	}

	public void testGetChildrenByGuardian() {
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long id = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(id);
		List<Profile> list = mActivity.helper.profilesHelper.getChildrenByGuardian(newProfile);

		assertNotNull("Should not be empty", list);
	}

	public void testGetChildrenWithNoDepartment() {
		List<Profile> list = mActivity.helper.profilesHelper.getChildrenWithNoDepartment();

		assertNotNull("Should not be empty", list);
	}

	public void testGetProfilesByDepartment() {
		Department newDepartment = new Department("TestDep", "TestAddress", 12345678, "test@test.dk");
		long id = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(id);
		List<Profile> list = mActivity.helper.profilesHelper.getProfilesByDepartment(newDepartment);
		
		assertNotNull("Should not be empty", list);
	}

	public void testGetGuardiansWithNoDepartment() {
		List<Profile> list = mActivity.helper.profilesHelper.getGuardiansWithNoDepartment();
		
		assertNotNull("Should not be empty", list);
	}

	public void testGetGuardiansByDepartment() {
		Department newDepartment = new Department("TestDep", "TestAddress", 12345678, "test@test.dk");
		long id = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(id);
		List<Profile> list = mActivity.helper.profilesHelper.getGuardiansByDepartment(newDepartment);
		
		assertNotNull("Should not be empty", list);
	}

	public void testGetGuardiansByChild() {
		Profile newChild = new Profile("Test", "Profile", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long id = mActivity.helper.profilesHelper.insertProfile(newChild);
		newChild.setId(id);
		List<Profile> list = mActivity.helper.profilesHelper.getChildrenByGuardian(newChild);

		assertNotNull("Should not be empty", list);
	}

	public void testGetProfileById() {
		Profile expectedProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long id = mActivity.helper.profilesHelper.insertProfile(expectedProfile);
		expectedProfile.setId(id);
		Profile foundProfile = mActivity.helper.profilesHelper.getProfileById(id);

		assertEquals("Should return profile; Test Profile", expectedProfile, foundProfile);
	}

	public void testGetProfilesByName() {
		String name = "Test";
		List<Profile> list = mActivity.helper.profilesHelper.getProfilesByName(name);
		
		assertNotNull("Should not be empty", list);
	}
}
