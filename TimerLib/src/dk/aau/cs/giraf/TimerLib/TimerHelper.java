package dk.aau.cs.giraf.TimerLib;

import java.util.ArrayList;

public class TimerHelper {
	
	Guardian guard = Guardian.getInstance();
	
	public TimerHelper(){
		init();
	}
	
	public void init()
	{
		//Admin call
		populateList();
	}
	
	public void populateList(){
		
	}
	
	public ArrayList<Child> getFullList(){
		return guard.Children();
	}
	
	public Child getProfileList(int id){
		return guard.Children().get(id);
	}
	
	public void loadPredef(){
		guard.predefined().clear();
		Hourglass hourglass30sec = new Hourglass("Timeglas - 30 sek", "Timeglas - (0:30)", 100, 0xff3D3D3D, 0xffffffff, 0xffB8B8B8, 0xff000000, 30, false);
		Hourglass hourglass1min = new Hourglass("Timeglas - 1 min", "Timeglas - (1:0)", 100, 0xff3D3D3D, 0xff000066, 0xffB8B8B8, 0xff000000, 60, false);
		Hourglass hourglass3min = new Hourglass("Timeglas - 3 min", "Timeglas - (3:0)", 100, 0xff3D3D3D, 0xffFFFF00, 0xffB8B8B8, 0xff000000, 180, false);
		Hourglass hourglass5min = new Hourglass("Timeglas - 5 min", "Timeglas - (5:0)", 100, 0xff3D3D3D, 0xff000066, 0xffB8B8B8, 0xff000000, 300, false);
		Hourglass hourglass10min = new Hourglass("Timeglas - 10 min", "Timeglas - (10:0)", 100, 0xff3D3D3D, 0xffFF0000, 0xffB8B8B8, 0xff000000, 600, false);
		Hourglass hourglass30min = new Hourglass("Timeglas - 30 min", "Timeglas - (30:0)", 100, 0xff3D3D3D, 0xff000000, 0xffB8B8B8, 0xff000000, 1800, false);
		
		ProgressBar ProgressBar30sec = new ProgressBar("ProgressBar - 30 sek", "ProgressBar - (0:30)", 100, 0xff3D3D3D, 0xffffffff, 0xffB8B8B8, 0xff000000, 30, false);
		ProgressBar ProgressBar1min = new ProgressBar("ProgressBar - 1 min", "ProgressBar - (1:0)", 100, 0xff3D3D3D, 0xff000066, 0xffB8B8B8, 0xff000000, 60, false);
		ProgressBar ProgressBar3min = new ProgressBar("ProgressBar - 3 min", "ProgressBar - (3:0)", 100, 0xff3D3D3D, 0xffFFFF00, 0xffB8B8B8, 0xff000000, 180, false);
		ProgressBar ProgressBar5min = new ProgressBar("ProgressBar - 5 min", "ProgressBar - (5:0)", 100, 0xff3D3D3D, 0xff000066, 0xffB8B8B8, 0xff000000, 300, false);
		ProgressBar ProgressBar10min = new ProgressBar("ProgressBar - 10 min", "ProgressBar - (10:0)", 100, 0xff3D3D3D, 0xffFF0000, 0xffB8B8B8, 0xff000000, 600, false);
		ProgressBar ProgressBar30min = new ProgressBar("ProgressBar - 30 min", "ProgressBar - (30:0)", 100, 0xff3D3D3D, 0xff000000, 0xffB8B8B8, 0xff000000, 1800, false);
		
		DigitalClock DigitalClock30sec = new DigitalClock("DigitalClock - 30 sek", "DigitalClock - (0:30)", 100, 0xff3D3D3D, 0xffffffff, 0xffB8B8B8, 0xff000000, 30, false);
		DigitalClock DigitalClock1min = new DigitalClock("DigitalClock - 1 min", "DigitalClock - (1:0)", 100, 0xff3D3D3D, 0xff000066, 0xffB8B8B8, 0xff000000, 60, false);
		DigitalClock DigitalClock3min = new DigitalClock("DigitalClock - 3 min", "DigitalClock - (3:0)", 100, 0xff3D3D3D, 0xffFFFF00, 0xffB8B8B8, 0xff000000, 180, false);
		DigitalClock DigitalClock5min = new DigitalClock("DigitalClock - 5 min", "DigitalClock - (5:0)", 100, 0xff3D3D3D, 0xff000066, 0xffB8B8B8, 0xff000000, 300, false);
		DigitalClock DigitalClock10min = new DigitalClock("DigitalClock - 10 min", "DigitalClock - (10:0)", 100, 0xff3D3D3D, 0xffFF0000, 0xffB8B8B8, 0xff000000, 600, false);
		DigitalClock DigitalClock30min = new DigitalClock("DigitalClock - 30 min", "DigitalClock - (30:0)", 100, 0xff3D3D3D, 0xff000000, 0xffB8B8B8, 0xff000000, 1800, false);
			
		TimeTimer TimeTimer30sec = new TimeTimer("Ur - 30 sek", "Ur - (0:30)", 100, 0xff3D3D3D, 0xffffffff, 0xffB8B8B8, 0xff000000, 30, false);
		TimeTimer TimeTimer1min = new TimeTimer("Ur - 1 min", "Ur - (1:0)", 100, 0xff3D3D3D, 0xff000066, 0xffB8B8B8, 0xff000000, 60, false);
		TimeTimer TimeTimer3min = new TimeTimer("Ur - 3 min", "Ur - (3:0)", 100, 0xff3D3D3D, 0xffFFFF00, 0xffB8B8B8, 0xff000000, 180, false);
		TimeTimer TimeTimer5min = new TimeTimer("Ur - 5 min", "Ur - (5:0)", 100, 0xff3D3D3D, 0xff000066, 0xffB8B8B8, 0xff000000, 300, false);
		TimeTimer TimeTimer10min = new TimeTimer("Ur - 10 min", "Ur - (10:0)", 100, 0xff3D3D3D, 0xffFF0000, 0xffB8B8B8, 0xff000000, 600, false);
		TimeTimer TimeTimer30min = new TimeTimer("Ur - 30 min", "Ur - (30:0)", 100, 0xff3D3D3D, 0xff000000, 0xffB8B8B8, 0xff000000, 1800, false);
				
		guard.predefined().add(hourglass30sec);
		guard.predefined().add(hourglass1min);
		guard.predefined().add(hourglass3min);
		guard.predefined().add(hourglass5min);
		guard.predefined().add(hourglass10min);
		guard.predefined().add(hourglass30min);
		
		guard.predefined().add(ProgressBar30sec);
		guard.predefined().add(ProgressBar1min);
		guard.predefined().add(ProgressBar3min);
		guard.predefined().add(ProgressBar5min);
		guard.predefined().add(ProgressBar10min);
		guard.predefined().add(ProgressBar30min);
		
		guard.predefined().add(DigitalClock30sec);
		guard.predefined().add(DigitalClock1min);
		guard.predefined().add(DigitalClock3min);
		guard.predefined().add(DigitalClock5min);
		guard.predefined().add(DigitalClock10min);
		guard.predefined().add(DigitalClock30min);
		
		guard.predefined().add(TimeTimer30sec);
		guard.predefined().add(TimeTimer1min);
		guard.predefined().add(TimeTimer3min);
		guard.predefined().add(TimeTimer5min);
		guard.predefined().add(TimeTimer10min);
		guard.predefined().add(TimeTimer30min);
	}
	
	public void LoadTestData(){
		//Data
		Child child1 = new Child("Kristian");
		Child child2 = new Child("Simon");
		Child child3 = new Child("Rasmus");
		
		DigitalClock digital1 = new DigitalClock("Ered Digital clock", "red digital 100% size", 100, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 60, false);
		DigitalClock digital2 = new DigitalClock("Kgreen Digital clock 1min", "green digital 1min 50% size", 50, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 60, false);
		DigitalClock digital3 = new DigitalClock("Cblue Digital clock", "blue digital 25% size 1min", 25, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 60, true);
		DigitalClock digital4 = new DigitalClock("Bblue Digital clock", "blue digital 25% size 1min", 25, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 60, true);
		DigitalClock digital5 = new DigitalClock("Ablue Digital clock", "blue digital 25% size 1min", 25, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 60, true);
		
		Hourglass hourglass1 = new Hourglass("DHourglass 10min", "white background hourglass with 10min 100% size digital clock", 100, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 600, false);
		Hourglass hourglass2 = new Hourglass("CHourglass 15min", "black background hourglass with 15min 60% size digital clock", 60, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 900, true);
		Hourglass hourglass3 = new Hourglass("BHourglass 20min", "white background hourglass with 20min 30% size no digital clock", 30, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 1200, false);
		Hourglass hourglass4 = new Hourglass("AHourglass 20min", "white background hourglass with 20min 30% size no digital clock", 30, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 1200, false);
		
		
		TimeTimer circle1 = new TimeTimer("ATimeTimer 10min", "white background hourglass with 10min 100% size digital clock", 100, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 600, false);
		TimeTimer circle2 = new TimeTimer("CTimeTimer 15min", "black background hourglass with 15min 60% size digital clock", 60, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 900, true);
		TimeTimer circle3 = new TimeTimer("BTimeTimer 20min", "white background hourglass with 20min 30% size no digital clock", 30, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 1200, false);
		
		Guardian guard = Guardian.getInstance();
		guard.Children().clear();
		child1.SubProfiles().add(hourglass1);child1.SubProfiles().add(circle1);child1.SubProfiles().add(digital2);
		child2.SubProfiles().add(hourglass2);child2.SubProfiles().add(circle2);
		child3.SubProfiles().add(hourglass3);child3.SubProfiles().add(circle3);
		
		guard.Children().add(child1);
		guard.Children().add(child2);
		guard.Children().add(child3);
		
	}

}
