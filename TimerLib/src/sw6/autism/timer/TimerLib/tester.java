package sw6.autism.timer.TimerLib;

import java.util.ArrayList;

public class tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DigitalClock temp1 = new DigitalClock(0, null, null, null, 0, false, false, null, null);
		Circle temp2 = new Circle(0, null, null, null, 0, false, false, null, null);
		ArrayList<Profile> sup = new ArrayList<Profile>();
		sup.add(temp1);
		sup.add(temp2);
		System.out.print(sup.get(0)._changeColor);

	}

}
