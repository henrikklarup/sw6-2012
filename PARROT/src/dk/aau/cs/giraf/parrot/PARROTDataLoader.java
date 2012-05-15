package dk.aau.cs.giraf.parrot;

import java.util.ArrayList;
import java.util.List;

import android.R.bool;
import android.app.Activity;
import android.os.Bundle;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;

/**
 * 
 * @Rasmus
 * The PARROT Data Loader is used for interacting with the admin functionality of the GIRAF Project.
 *
 */
public class PARROTDataLoader {

	private Activity parrent;
	private Helper help;
	private App app;

	public PARROTDataLoader(Activity activity)
	{
		this.parrent = activity;
		help = new Helper(parrent);
		app = help.appsHelper.getAppByPackageName();
	}

	/**
	 * 
	 * @return
	 * The PARROTProfile corresponding to the current child user of the PARROT system.
	 */
	public PARROTProfile loadPARROT()
	{
		//This part of the code is supposed to get a profile from the launcher, and read it from the admin.
		Long childId = parrent.getIntent().getExtras().getLong("currentChildID");
		Long appId = app.getId();

		return loadProfile(childId, appId);
	}

	/**
	 * @param childId
	 * The ID of the child using the app.
	 * @param appId
	 * The ID of the app.
	 * @return
	 * This methods loads a specific PARROTProfile.
	 */
	public PARROTProfile loadProfile(Long childId,Long appId)	
	{
		Profile prof;


		if(childId !=null && appId !=null)
		{
			prof = help.profilesHelper.getProfileById(childId);	//It used to be "currentProfileId"
			//app = help.appsHelper.getAppByIds(appId, childId);

			Pictogram pic = new Pictogram(prof.getFirstname(), prof.getPicture(), null, null);	//TODO discuss whether this image might be changed
			PARROTProfile parrotUser = new PARROTProfile(prof.getFirstname(), pic);
			parrotUser.setProfileID(prof.getId());
			Setting<String, String, String> specialSettings = app.getSettings();//This object might be null

			//Load the settings
			parrotUser = loadSettings(parrotUser, specialSettings);

			//Add all of the categories to the profile
			int number = 0;
			String categoryString=null;
			while (true)
			{
				//Here we read the pictograms of the categories
				//The settings reader uses this format : category +number | cat_property | value
				try
				{
					categoryString = specialSettings.get("category"+number).get("pictograms");	//FIXME if this value is null, we will crash
				}
				catch (NullPointerException e)
				{
					//the value does not exist, so we will not load anymore categories
					break;
				}


				//				if(categoryString !=null)		//If the category of that number exists
				//				{
				String colourString = specialSettings.get("category"+number).get("colour");
				int col=Integer.valueOf(colourString);
				String iconString = specialSettings.get("category"+number).get("icon");
				parrotUser.addCategory(loadCategory(categoryString,col,iconString));
				number++;
				//				}
				//				else
				//				{
				//					break;
				//				}

			}

			return parrotUser;
		}
		else
		{
			//If no profile is found, return null.
			//TODO find out if this means that a Guardian is using the PARROT app.
			//It doesn't, it means the launcher has not provided a profile, either due to an error, or because PARROT has been launched outside of GIRAF.
			return null;
		}

	}

	private PARROTProfile loadSettings(PARROTProfile parrotUser, Setting<String, String, String> profileSettings) {

		//First we load the colour settings
		int catColour = Integer.valueOf(profileSettings.get("ColourSettings").get("SuperCategory"));
		int sentenceColour = Integer.valueOf(profileSettings.get("ColourSettings").get("SentenceBoard"));
		parrotUser.setCategoryColor(catColour);
		parrotUser.setSentenceBoardColor(sentenceColour);

		//Then we load the tab settings
		boolean tab0 = Boolean.valueOf(profileSettings.get("Rights").get("tab0"));
		boolean tab1 = Boolean.valueOf(profileSettings.get("Rights").get("tab1"));
		boolean tab2 = Boolean.valueOf(profileSettings.get("Rights").get("tab2"));
		parrotUser.setRights(0, tab0);
		parrotUser.setRights(1, tab1);
		parrotUser.setRights(2, tab2);

		return parrotUser;
	}

	public Category loadCategory(String pictureIDs,int colour,String iconString)
	{
		Long iconId = Long.valueOf(iconString);
		Category cat = new Category(colour,loadPictogram(iconId));
		ArrayList<Long> listIDs = getIDsFromString(pictureIDs);
		for(int i = 0; i<listIDs.size();i++)
		{
			cat.addPictogram(loadPictogram(listIDs.get(i)));
		}
		return cat;
	}

	public Pictogram loadPictogram(long id)
	{
		Pictogram pic = null;
		Media media=help.mediaHelper.getSingleMediaById(id); //This is the image media //TODO check type

		List<Media> subMedias =	help.mediaHelper.getSubMediaByMedia(media); //TODO find out if this is ok, or if it needs to be an ArrayList
		Media investigatedMedia;
		String soundPath = null;
		String wordPath = null;
		long soundID = -1; //If this value is still -1 when we save a media, it is because the pictogram has no sound.
		long wordID = -1;

		if(subMedias != null)	//Media files can have a link to a sub-media file, check if this one does.
		{
			for(int i = 0;i<subMedias.size();i++) 		
			{
				investigatedMedia =subMedias.get(i);
				if(investigatedMedia.getMType().equals("SOUND"))
				{
					soundPath = investigatedMedia.getMPath();
					soundID= investigatedMedia.getId();
				}
				else if(investigatedMedia.getMType().equals("WORD"))
				{
					wordPath = investigatedMedia.getMPath();
					wordID = investigatedMedia.getId();
				}
			}
		}
		pic = new Pictogram(media.getName(), media.getMPath(), soundPath, wordPath);
		//set the different ID's
		pic.setImageID(id);
		pic.setSoundID(soundID);
		pic.setWordID(wordID);

		return pic;
	}

	//This function takes a string consisting of IDs, and returns a list of integer IDs instead
	public ArrayList<Long> getIDsFromString(String IDstring)
	{
		ArrayList<Long> listOfID = new ArrayList<Long>();

		if(IDstring !=null && IDstring.charAt(0)!='$' && IDstring.charAt(0)!='#')
		{
			String temp = String.valueOf(IDstring.charAt(0));
			int w = 0;
			while(IDstring.charAt(w)!='$')
			{
				w++;
				if(IDstring.charAt(w)!='#')
				{
					temp = temp+ IDstring.charAt(w);
				}
				else if(IDstring.charAt(w)=='$')
				{
					break;
				}
				else
				{
					listOfID.add(Long.valueOf(temp));
					w++;
					temp = String.valueOf(IDstring.charAt(w));
				}
			}
		}

		return listOfID;
	}

	public void saveProfile(PARROTProfile user)
	{
		Setting<String, String, String> profileSetting = new Setting<String, String, String>();
		//save profile settings
		saveSettings(profileSetting, user);

		for(int i=0;i<user.getCategories().size();i++)
		{
			profileSetting = saveCategory(user.getCategoryAt(i), i, profileSetting);
		}
		//after all the changes are made, we save the settings to the database
		app.setSettings(profileSetting);
	}

	public Setting<String, String, String> saveSettings(Setting<String, String, String> profileSettings, PARROTProfile user)
	{
		//First, we save the colour settings
		profileSettings.addValue("ColourSettings", "SuperCategory", String.valueOf(user.getCategoryColor()));
		profileSettings.get("ColourSettings").put("SentenceBoard", String.valueOf(user.getSentenceBoardColor()));

		//Then we save the rights, which are the available tabs for the user.
		profileSettings.addValue("Rights", "tab0", String.valueOf(user.getRights(0)));
		profileSettings.get("Rights").put("tab1", String.valueOf(user.getRights(1)));
		profileSettings.get("Rights").put("tab2", String.valueOf(user.getRights(2)));

		//Now we return the settings so that they can be saved.
		return profileSettings;	
	}

	public void TESTsaveTestProfile()
	{
		help = new Helper(parrent);
		//help.CreateDummyData();
		Profile tempProf = new Profile("Jens","Jensen",null,Profile.pRoles.CHILD.ordinal(),12345678,null,null);
		Long profileId = help.profilesHelper.insertProfile(tempProf);


		Setting<String, String, String> profileSetting = new Setting<String, String, String>();

		//START TEMP LINES
		Pictogram tempPic= new Pictogram("Koala","/sdcard/Pictures/005.jpg", null, null);
		//		Category tempCat = new Category(0,tempPic);
		//		tempCat.addPictogram(tempPic);
		//		tempCat.addPictogram(tempPic);
		Pictogram tempPic2 = new Pictogram("Meg", "/sdcard/Pictures/meg.png", null, null);
		//		tempCat.addPictogram(tempPic2);

		PARROTProfile testProfile = new PARROTProfile("Niels", tempPic);
		testProfile.setProfileID(profileId);

		//		for (int i=0;i<6;i++)
		//		{
		//			tempCat.addPictogram(tempPic);
		//			tempCat.addPictogram(tempPic2);
		//		}

		//		Category tempCat2 = new Category(2, tempPic2);
		tempPic = new Pictogram("Bob", "/sdcard/Pictures/007.jpg", null, null);
		tempPic2= new Pictogram("Madeline", "/sdcard/Pictures/003.jpg", null, null);

		//		for (int i=0;i<6;i++)
		//		{
		//			tempCat2.addPictogram(tempPic);
		//			tempCat2.addPictogram(tempPic2);
		//		}
		//		

		Pictogram badePic = new Pictogram("Bade", "/sdcard/Pictogram/Bade.png", null, "/sdcard/Pictogram/bade.wma");
		badePic.setNewPictogram(true);
		Pictogram børsteTænderPic = new Pictogram("Børste Tænder", "/sdcard/Pictogram/Børste_Tænder.png", null, "/sdcard/Pictogram/børste_tænder.wma");
		børsteTænderPic.setNewPictogram(true);
		Pictogram drikkePic = new Pictogram("Drikke", "/sdcard/Pictogram/Drikke.png", null, "/sdcard/Pictogram/drikke.wma");
		Pictogram duPic = new Pictogram("Du", "/sdcard/Pictogram/Du.png", null, "/sdcard/Pictogram/du.wma");
		Pictogram filmPic = new Pictogram("Film", "/sdcard/Pictogram/Film.png", null, "/sdcard/Pictogram/film.wma");
		Pictogram forHøjtPic = new Pictogram("For Højt", "/sdcard/Pictogram/For_Højt.png", null, "/sdcard/Pictogram/for_højt.wma");
		Pictogram gåPic = new Pictogram("Gå", "/sdcard/Pictogram/Gå.png", null, "/sdcard/Pictogram/gå.wma");
		Pictogram jaPic = new Pictogram("Ja", "/sdcard/Pictogram/Ja.png", null, "/sdcard/Pictogram/ja.wma");
		Pictogram kørePic = new Pictogram("Køre", "/sdcard/Pictogram/Køre.png", null, "/sdcard/Pictogram/køre.wma");
		Pictogram laveMadPic = new Pictogram("Lave Mad", "/sdcard/Pictogram/Lave_Mad.png", null, "/sdcard/Pictogram/lave_mad.wma");
		Pictogram legePic = new Pictogram("Lege", "/sdcard/Pictogram/Lege.png", null, "/sdcard/Pictogram/lege.wma");
		Pictogram migPic = new Pictogram("Mig", "/sdcard/Pictogram/Mig.png", null, "/sdcard/Pictogram/mig.wma");
		migPic.setNewPictogram(true);
		Pictogram morgenRoutinePic = new Pictogram("Morgen Routine", "/sdcard/Pictogram/Morgen_Routine.png", null, "/sdcard/Pictogram/morgen_routine.wma");
		Pictogram nejPic = new Pictogram("Nej", "/sdcard/Pictogram/Nej.png", null, "/sdcard/Pictogram/nej.wma");
		Pictogram sePic = new Pictogram("Se", "/sdcard/Pictogram/Se.png", null, "/sdcard/Pictogram/se.wma");
		Pictogram sideNedPic = new Pictogram("Side Ned", "/sdcard/Pictogram/Side_Ned.png", null, "/sdcard/Pictogram/side_ned.wma");
		Pictogram SpilleComputerPic = new Pictogram("Spille Computer", "/sdcard/Pictogram/Spille_Computer.png", null, "/sdcard/Pictogram/spille_computer.wma");
		Pictogram stopPic = new Pictogram("Stop", "/sdcard/Pictogram/Stop.png", null, "/sdcard/Pictogram/stop.wma");
		Pictogram sultenPic = new Pictogram("Sulten", "/sdcard/Pictogram/Sulten.png", null, "/sdcard/Pictogram/sulten.wma");
		Pictogram søvnigPic = new Pictogram("Søvnig", "/sdcard/Pictogram/Søvnig.png", null, "/sdcard/Pictogram/søvnig.wma");
		Pictogram taleSammenPic = new Pictogram("Tale Sammen", "/sdcard/Pictogram/Tale_Sammen.png", null, "/sdcard/Pictogram/tale_sammen.wma");
		Pictogram tørstigPic = new Pictogram("Tørstig", "/sdcard/Pictogram/Tørstig.png", null, "/sdcard/Pictogram/tørstig.wma");
		Pictogram væreStillePic = new Pictogram("Være Stille", "/sdcard/Pictogram/Være_Stille.png", null, "/sdcard/Pictogram/være_stille.wma");

		Category tempCat3 = new Category(2, migPic);

		tempCat3.addPictogram(badePic);
		tempCat3.addPictogram(børsteTænderPic);
		tempCat3.addPictogram(drikkePic);
		tempCat3.addPictogram(duPic);
		tempCat3.addPictogram(filmPic);
		tempCat3.addPictogram(forHøjtPic);
		tempCat3.addPictogram(gåPic);
		tempCat3.addPictogram(jaPic);
		tempCat3.addPictogram(kørePic);
		tempCat3.addPictogram(laveMadPic);
		tempCat3.addPictogram(legePic);
		tempCat3.addPictogram(migPic);
		tempCat3.addPictogram(morgenRoutinePic);
		tempCat3.addPictogram(nejPic);
		tempCat3.addPictogram(sePic);
		tempCat3.addPictogram(sideNedPic);
		tempCat3.addPictogram(SpilleComputerPic);
		tempCat3.addPictogram(stopPic);
		tempCat3.addPictogram(sultenPic);
		tempCat3.addPictogram(søvnigPic);
		tempCat3.addPictogram(taleSammenPic);
		tempCat3.addPictogram(tørstigPic);
		tempCat3.addPictogram(væreStillePic);
		//		
		//		testProfile.addCategory(tempCat);
		//		testProfile.addCategory(tempCat2);
		testProfile.addCategory(tempCat3);
		PARROTActivity.setUser(testProfile);

		for(int i=0;i<testProfile.getCategories().size();i++)
		{
			profileSetting = saveCategory(testProfile.getCategoryAt(i), i, profileSetting);
		}
		profileSetting = saveSettings(profileSetting, testProfile);
		app.setSettings(profileSetting);	//FIXME figure out if settings works as intended, or if addValue replaces ALL previous values
		long appID=app.getId();
		PARROTProfile parrot =loadProfile(profileId, appID);
		PARROTActivity.setUser(parrot);
		//END TEMP LINES
	}

	private Setting<String, String, String> saveCategory(Category category, int categoryNumber, Setting<String, String, String> profileSetting ) {
		//first, we save the pictograms
		String pictogramString = "";
		for(int i=0;i<category.getPictograms().size();i++)
		{
			Pictogram pic = category.getPictogramAtIndex(i);

			pic = savePictogram(pic);

			//In any case, save the references
			pictogramString = pictogramString + pic.getImageID()+'#';

		}
		pictogramString += "$";
		pictogramString = pictogramString.replace("#$", "$");	//Here we make sure that the end is $, and not #$
		profileSetting.addValue("category"+categoryNumber, "pictograms", pictogramString);
		//then we save the colour
		profileSetting.get("category"+categoryNumber).put("colour", String.valueOf(category.getCategoryColour()));
		//and then we save the icon
		Pictogram icon = category.getIcon();
		savePictogram(icon);
		profileSetting.get("category"+categoryNumber).put("icon", String.valueOf(icon.getImageID()));

		return profileSetting;
	}

	/**
	 * 
	 * @Rasmus This method is used to save completely new pictograms to the database, as well as modify existing ones.
	 */
	private Pictogram savePictogram(Pictogram pic)
	{
		Media imageMedia = null;
		Media soundMedia = null;
		Media wordMedia = null;
		if(pic.getImageID() == -1) //if the picture is new in the database
		{
			imageMedia = new Media(pic.getName(), pic.getImagePath(), true, "IMAGE", PARROTActivity.getUser().getProfileID());
			pic.setImageID(help.mediaHelper.insertMedia(imageMedia));
			imageMedia.setId(pic.getImageID());
		}
		else
		{
			imageMedia = new Media(pic.getName(),pic.getImagePath(),true,"IMAGE",PARROTActivity.getUser().getProfileID());
			imageMedia.setId(pic.getImageID());
			help.mediaHelper.modifyMedia(imageMedia);
		}

		


		if(pic.getWordID() == -1 && pic.getWordPath() != null) //if the word is not in the database
		{
			wordMedia = new Media(pic.getName(), pic.getWordPath(), true, "WORD", PARROTActivity.getUser().getProfileID());	//TODO we might want to set the booleans to false
			pic.setWordID(help.mediaHelper.insertMedia(wordMedia));
			wordMedia.setId(pic.getWordID());
		}
		else if(pic.getWordPath() != null)
		{
			wordMedia = new Media(pic.getName(), pic.getWordPath(), true, "WORD", PARROTActivity.getUser().getProfileID());	//TODO we might want to set the booleans to false
			wordMedia.setId(pic.getWordID());
			help.mediaHelper.modifyMedia(wordMedia);
		}
		
		if(pic.getSoundID() == -1 && pic.getSoundPath() != null) //if the sound is new in the database
		{
			soundMedia = new Media(pic.getName(), pic.getSoundPath(), true, "SOUND", PARROTActivity.getUser().getProfileID());	//TODO we might want to set the booleans to false
			pic.setSoundID(help.mediaHelper.insertMedia(soundMedia));
			soundMedia.setId(pic.getSoundID());
		}
		else
		{
			if(pic.getSoundPath() != null)
			{
				soundMedia = new Media(pic.getName(), pic.getSoundPath(), true, "SOUND", PARROTActivity.getUser().getProfileID());	//TODO we might want to set the booleans to false
				soundMedia.setId(pic.getSoundID());
				help.mediaHelper.modifyMedia(soundMedia);
			}
		}

		// save the submedia references for NEW pictograms
		if(pic.isNewPictogram() == true)
		{
			if(soundMedia != null)
			{
				help.mediaHelper.attachSubMediaToMedia(soundMedia, imageMedia);
			}
			if(wordMedia !=null)
			{
				help.mediaHelper.attachSubMediaToMedia(wordMedia, imageMedia);
			}
		}
		//save the submedia references for a MODIFIED pictogram
		else if(pic.isChanged() == true)
		{
			List<Media> mediasToRemove = help.mediaHelper.getSubMediaByMedia(imageMedia);
			for(int i = 0;i<mediasToRemove.size();i++)	//find all previous references, and remove them
			{
				help.mediaHelper.removeSubMediaAttachmentToMedia(mediasToRemove.get(i), imageMedia);
			}
			if(soundMedia != null)
			{
				help.mediaHelper.attachSubMediaToMedia(soundMedia, imageMedia);
			}
			if(wordMedia !=null)
			{
				help.mediaHelper.attachSubMediaToMedia(wordMedia, imageMedia);
			}

		}
		//Update the information about the pictogram so that it is nolonger new or changed from the version in the database.
		pic.setChanged(false);
		pic.setNewPictogram(false);
		return pic;

	}

}
