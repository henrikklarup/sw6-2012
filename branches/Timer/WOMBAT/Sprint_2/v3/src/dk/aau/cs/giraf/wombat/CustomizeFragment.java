package dk.aau.cs.giraf.wombat;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import sw6.oasis.controllers.Helper;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomizeFragment extends Fragment {
	Helper helper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		helper = new Helper(getActivity().getApplicationContext());
		// Write Tag = Detail and Text = Detail Opened in the LogCat
		Log.e("Customize", "Customize Opened");

	}

	@Override
	// Start the list empty
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Context c = getActivity().getApplicationContext(); // Load Context

		/* Create minute Wheel */
		final WheelView mins = (WheelView) getActivity().findViewById(R.id.minPicker);
		mins.setViewAdapter(new NumericWheelAdapter(c, 0, 60));
		mins.setCurrentItem(30);
		mins.setCyclic(true);

		/* Create Seconds wheel */
		final WheelView secs = (WheelView) getActivity().findViewById(
				R.id.secPicker);
		secs.setViewAdapter(new NumericWheelAdapter(c, 0, 60));
		secs.setCurrentItem(20);
		secs.setCyclic(true);

		/* Create description of time chosen */
		final TextView timeDescription = (TextView) getActivity().findViewById(
				R.id.showTime);
		timeDescription.setText(getTimeDescription(mins.getCurrentItem(),
				secs.getCurrentItem()));

		
		mins.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				timeDescription.setText(getTimeDescription(mins.getCurrentItem(),
						secs.getCurrentItem()));
			}
		});
		
		secs.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				timeDescription.setText(getTimeDescription(mins.getCurrentItem(),
						secs.getCurrentItem()));
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Populate the fragment according to the details layout
		return inflater.inflate(R.layout.customize, container, false);
	}

	/**
	 * Create the correct format of the time description
	 * 
	 * @param mins
	 *            minutes
	 * @param secs
	 *            seconds
	 * @return A string with the time correctly formatted, according to the
	 *         strings resource
	 */
	private String getTimeDescription(int mins, int secs) {
		String timeText = "";
		/* Første del med mellemrum */
		if (mins == 1) {
			timeText = "1 ";
			timeText += this.getString(R.string.minut);

		} else if (mins != 0) {
			timeText = mins + " ";
			timeText += this.getString(R.string.minutes);
		}

		/* Insert the devider if its needed */
		if (mins != 0 && secs != 0) {
			timeText += " " + this.getString(R.string.and_devider) + " ";
		}

		if (secs == 1) {
			timeText += "1 ";
			timeText += this.getString(R.string.second);
		} else if (secs != 0) {
			timeText += secs + " ";
			timeText += this.getString(R.string.seconds);
		}

		return timeText;
	}

	/**
	 * Sets the predefined settings of the chosen template
	 * 
	 * @param id
	 *            identification of the template
	 */
	public void setSettings(Template template) {
		// TODO: Insert logic to load settings and put them into the view
	}
}
