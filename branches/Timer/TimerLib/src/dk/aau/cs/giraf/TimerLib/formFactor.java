package dk.aau.cs.giraf.TimerLib;

public enum formFactor {SubProfile, Hourglass, TimeTimer, ProgressBar, DigitalClock;

static formFactor convert(Object input){
	
	formFactor form = SubProfile;
	
	String value = String.valueOf(input);
	
	if(value.equals(SubProfile.toString())){
		form = SubProfile;
	} else if(value.equals(Hourglass.toString())){
		form = Hourglass;
	} else if(value.equals(TimeTimer.toString())){
		form = TimeTimer;
	} else if(value.equals(ProgressBar.toString())){
		form = ProgressBar;
	} else if(value.equals(DigitalClock.toString())){
		form = DigitalClock;
	}
	
	return form;
}

}