package dk.aau.cs.giraf.TimerLib;
/**
 * 
 * This enum contains all the different constants which are used in WOMBAT
 *
 */
public enum formFactor {undefined, SubProfile, Hourglass, TimeTimer, ProgressBar, DigitalClock, Timer, SingleImg, SplitImg;

/**
 * Used to convert a string into a fitting enum. Mostly, if not only, used in the CRUD class to retrieve data from the OasisLocalDatabase
 * @param input String from the OasisLocalDatabase
 * @return formFactor enum according to the input
 */
static formFactor convert(Object input){
	
	formFactor form = SubProfile;
	
	String value = String.valueOf(input);
	
	for(formFactor val : formFactor.values()){
		if(value.equals(val.toString())){
			form = val;
		}
	}
	
	
	return form;
}

}