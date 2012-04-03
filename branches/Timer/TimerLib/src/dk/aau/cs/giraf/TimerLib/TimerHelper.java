package dk.aau.cs.giraf.TimerLib;

import java.util.ArrayList;

public class TimerHelper {
	
	public TimerHelper(){
		init();
	}
	
	public void init()
	{
		//Admin call
		Guardian guard = Guardian.getInstance("something");
		populateList();
	}
	
	public void populateList(){
		
	}
	
	public ArrayList<Child> getFullList(){
		Guardian guard = Guardian.getInstance();
		return guard.Children();
	}
	
	public Child getProfileList(int id){
		Guardian guard = Guardian.getInstance();
		return guard.Children().get(id);
	}
	
	public void LoadTestData(){
		//Data
		Child child1 = new Child("Kristian");
		Child child2 = new Child("Simon");
		Child child3 = new Child("Rasmus");
		
		DigitalClock digital1 = new DigitalClock("red Digital clock", "red digital 100% size", 100, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 60, false);
		DigitalClock digital2 = new DigitalClock("green Digital clock 1min", "green digital 1min 50% size", 50, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 60, false);
		DigitalClock digital3 = new DigitalClock("blue Digital clock", "blue digital 25% size 1min", 25, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 60, true);
		DigitalClock digital4 = new DigitalClock("blue Digital clock", "blue digital 25% size 1min", 25, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 60, true);
		DigitalClock digital5 = new DigitalClock("blue Digital clock", "blue digital 25% size 1min", 25, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 60, true);
		
		Hourglass hourglass1 = new Hourglass("Hourglass 10min", "white background hourglass with 10min 100% size digital clock", 100, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 600, false, 25);
		Hourglass hourglass2 = new Hourglass("Hourglass 15min", "black background hourglass with 15min 60% size digital clock", 60, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 900, true, 50);
		Hourglass hourglass3 = new Hourglass("Hourglass 20min", "white background hourglass with 20min 30% size no digital clock", 30, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 1200, false, 100);
		Hourglass hourglass4 = new Hourglass("Hourglass 20min", "white background hourglass with 20min 30% size no digital clock", 30, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 1200, false, 100);
		
		
		TimeTimer circle1 = new TimeTimer("TimeTimer 10min", "white background hourglass with 10min 100% size digital clock", 100, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 600, false);
		TimeTimer circle2 = new TimeTimer("TimeTimer 15min", "black background hourglass with 15min 60% size digital clock", 60, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 900, true);
		TimeTimer circle3 = new TimeTimer("TimeTimer 20min", "white background hourglass with 20min 30% size no digital clock", 30, 0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff, 1200, false);
		
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
