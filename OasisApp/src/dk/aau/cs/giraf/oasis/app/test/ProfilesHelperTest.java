package dk.aau.cs.giraf.oasis.app.test;

import android.test.ActivityInstrumentationTestCase2;
import dk.aau.cs.giraf.oasis.app.MainActivity;
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
		mActivity.helper.profilesHelper.removeProfile(newProfile);
		Profile p = mActivity.helper.profilesHelper.getProfileById(id);
		
		assertNull("Should be null, because Profile is removed", p);
	}
	
	public void testRemoveChildAttachment() {
		Profile newChild = new Profile("Test", "Child", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long idChild = mActivity.helper.profilesHelper.insertProfile(newChild);
		newChild.setId(idChild);
		Profile newGuard = new Profile("Test", "Guard", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idGuard = mActivity.helper.profilesHelper.insertProfile(newGuard);
		newGuard.setId(idGuard);
		mActivity.helper.profilesHelper.attachChildToGuardian(newChild, newGuard);
		int result = mActivity.helper.profilesHelper.removeChildAttachmentToGuardian(newChild, newGuard);
		
		assertEquals("Should return 1", 1, result);
	}
	
	public void testInsertProfile() {
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long id = mActivity.helper.profilesHelper.insertProfile(newProfile);
		
		assertNotSame("Should not be -1", -1, id);
	}
	
	public void testAttachChildToGuardian() {
		
	}
	
	public void testModifyProfile() {
		
	}
	
	public void testAuthenticateProfile() {
		String certificate = "testprofilejrkpvdfzdxcvjxsuqcbktvncatholiepgczcbjlpbcgqrhmvgzzbbumjradmrwiscvkwnwtzdeopxaxzifovultejikcphyhgzsodxhgmvnggsecqmkfxowulfiuspqjxhrmdpxbrvqxjpwlbtalezrpxqrvjduvtxstaibrptjbztftlwufinbsaeloo";
		Profile expectedProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		
		long id = mActivity.helper.profilesHelper.insertProfile(expectedProfile);
		if (id != -1) {
			expectedProfile.setId(id);
			mActivity.helper.profilesHelper.setCertificate(certificate, expectedProfile);
		} else {
//			hej jens
		}
		
		
		Profile actualProfile = mActivity.helper.profilesHelper.authenticateProfile(certificate);
		
		assertEquals("Should return profile; Test Profile", expectedProfile, actualProfile);
	}
	
	public void testSetCertificate() {
		
	}
	
	public void testGetProfiles() {
		
	}

	public void testGetGuardians() {
		
	}
	
	public void testGetChildren() {
		
	}
	
	public void testGetCertificatesByProfile() {
		
	}
	
	public void testGetChildrenByDepartment() {
		
	}
	
	public void testGetChildrenByDepartmentAndSubDepartments() {
		
	}
	
	public void testGetChildrenByGuardians() {
		
	}
	
	public void testGetChildrenWithNoDepartment() {
		
	}
	
	public void testGetProfilesByDepartment() {
		
	}
	
	public void testGetGuardiansWithNoDepartment() {
		
	}
	
	public void testGetGuardiansByDepartment() {
		
	}
	
	public void testGetGuardiansByChild() {
		
	}
	
	public void testGetProfilesById() {
		
	}
	
	public void testGetProfilesByName() {
		
	}
}
