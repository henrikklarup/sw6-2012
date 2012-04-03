package dk.aau.cs.giraf.wombat;

import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import dk.aau.cs.giraf.TimerLib.Child;
import dk.aau.cs.giraf.TimerLib.Guardian;
import dk.aau.cs.giraf.TimerLib.SubProfile;

public class CustomizeFragment extends Fragment {
	private SubProfile currSubP;
	private Guardian guard = Guardian.getInstance();

	private Button hourglassButton;
	private Button timetimerButton;
	private Button progressbarButton;
	private Button digitalButton;
	private Button startButton;
	private Button saveButton;
	private Button attachmentButton;
	private Button colorGradientButton1;
	private Button colorGradientButton2;
	private Button colorFrameButton;
	private Button colorBackgroundButton;

	private CheckBox checkboxGradient;

	private WheelView mins;
	private WheelView secs;

	private TextView timeDescription;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Write Tag = Detail and Text = Detail Opened in the LogCat
		Log.e("Customize", "Customize Opened");

	}

	@Override
	// Start the list empty
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		currSubP = new SubProfile(101, "Test", "", 10, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 6000, true);

		/********* TIME CHOSER *********/
		initStyleChoser();

		/********* TIMEPICKER *********/
		initTimePicker();

		/********* COLORPICKER *********/
		initColorButtons();

		/********* ATTACHMENT PICKER *********/
		initAttachmentButton();

		/******** SAVE BUTTON ***********/
		initSaveButton();
		initStartButton();
	}

	private void initStyleChoser() {
		hourglassButton = (Button) getActivity().findViewById(
				R.id.houglassButton);
		hourglassButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Functionality which stores the choice
				deSelectStyles();
				hourglassButton.setSelected(true);

			}
		});

		timetimerButton = (Button) getActivity().findViewById(
				R.id.timetimerButton);
		timetimerButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Functionality which stores the choice
				deSelectStyles();
				timetimerButton.setSelected(true);

			}
		});

		progressbarButton = (Button) getActivity().findViewById(
				R.id.progressbarButton);
		progressbarButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Functionality which stores the choice
				deSelectStyles();
				progressbarButton.setSelected(true);

			}
		});

		digitalButton = (Button) getActivity().findViewById(R.id.digitalButton);
		digitalButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Functionality which stores the choice
				deSelectStyles();
				digitalButton.setSelected(true);

			}
		});

	}

	protected void deSelectStyles() {
		hourglassButton.setSelected(false);
		timetimerButton.setSelected(false);
		progressbarButton.setSelected(false);
		digitalButton.setSelected(false);
		
	}

	/**
	 * Initialize the start button
	 */
	private void initStartButton() {
		startButton = (Button) getActivity().findViewById(
				R.id.customize_start_button);
		startButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				guard.addLastUsed(currSubP);
			}
		});
	}

	/**
	 * Initialize the Save button
	 */
	private void initSaveButton() {
		final ArrayList<String> values = new ArrayList<String>();
		for (Child c : guard.Children()) {
			values.add(c._name);
		}

		// Cast values to CharSequence
		final CharSequence[] items = values.toArray(new CharSequence[values
				.size()]);

		saveButton = (Button) getActivity().findViewById(R.id.customize_save);
		saveButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle(getActivity().getString(
						R.string.choose_profile));
				builder.setItems(items, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int item) {
						Child c = guard.Children().get(item);
						c.save(currSubP);
					}
				});
				AlertDialog alert = builder.create();
				alert.show();

			}
		});

	}

	private void initAttachmentButton() {
		final List<String> values = new ArrayList<String>();

		if (guard.selected() == null) {
			for (Child c : guard.Children()) {
				for (SubProfile p : c.SubProfiles()) {
					values.add(p._name);
				}
			}

		} else {
			for (SubProfile p : guard.selected().SubProfiles()) {
				values.add(p._name);
			}
		}

		// Cast values to CharSequence
		final CharSequence[] items = values.toArray(new CharSequence[values
				.size()]);

		attachmentButton = (Button) getActivity().findViewById(
				R.id.customize_attachment);
		attachmentButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle(getActivity().getString(
						R.string.attachment_button_description));
				builder.setItems(items, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int item) {
						// TODO: Insert logic which inserts correct picture etc.
						attachmentButton
								.setCompoundDrawablesWithIntrinsicBounds(0,
										R.drawable.thumbnail_hourglass, 0, 0);
						attachmentButton
								.setText(R.string.customize_hourglass_description);
						TextView v = (TextView) getActivity().findViewById(
								R.id.customize_attachment_text);
						v.setText(R.string.attached);
					}
				});
				AlertDialog alert = builder.create();
				alert.show();

			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Populate the fragment according to the details layout
		return inflater.inflate(R.layout.customize, container, false);
	}

	private int previousMins;
	private int previousSecs;

	/**
	 * Initialize the time picker wheel
	 */
	private void initTimePicker() {
		/* Create minute Wheel */
		mins = (WheelView) getActivity().findViewById(R.id.minPicker);
		mins.setViewAdapter(new NumericWheelAdapter(getActivity()
				.getApplicationContext(), 0, 60));
		mins.setCurrentItem(30);
		mins.setCyclic(true);

		/* Create Seconds wheel */
		secs = (WheelView) getActivity().findViewById(R.id.secPicker);
		secs.setViewAdapter(new NumericWheelAdapter(getActivity()
				.getApplicationContext(), 0, 60));
		secs.setCurrentItem(0);
		secs.setCyclic(true);

		/* Create description of time chosen */
		timeDescription = (TextView) getActivity().findViewById(R.id.showTime);
		updateTimeDescription();

		/* Add on change listeners for both wheels */
		mins.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				currSubP._totalTime = (mins.getCurrentItem() * 60) + secs.getCurrentItem();
				updateTimeDescription();
				
				if (mins.getCurrentItem() == 60) {
					previousMins = 60;
					previousSecs = secs.getCurrentItem();
					
					secs.setCurrentItem(0);
					secs.setViewAdapter(new NumericWheelAdapter(getActivity()
							.getApplicationContext(), 0, 0));
					secs.setCyclic(false);
				} else if (previousMins == 60) {
					
					secs.setViewAdapter(new NumericWheelAdapter(getActivity()
							.getApplicationContext(), 0, 60));
				
					secs.setCurrentItem(previousSecs);
					secs.setCyclic(true);
				}
			}
		});

		secs.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				currSubP._totalTime = (mins.getCurrentItem() * 60) + secs.getCurrentItem();
				updateTimeDescription();
			}
		});
	}

	/**
	 * Update the correct format of the time description
	 * 
	 */
	private void updateTimeDescription() {
		int m_minutes = mins.getCurrentItem();
		int m_seconds = secs.getCurrentItem();
		String timeText = "";
		/* Første del med mellemrum */
		if (m_minutes == 1) {
			timeText = "1 ";
			timeText += this.getString(R.string.minut);

		} else if (m_minutes != 0) {
			timeText = m_minutes + " ";
			timeText += this.getString(R.string.minutes);
		}

		/* Insert the devider if its needed */
		if (m_minutes != 0 && m_seconds != 0) {
			timeText += " " + this.getString(R.string.and_devider) + " ";
		}

		if (m_seconds == 1) {
			timeText += "1 ";
			timeText += this.getString(R.string.second);
		} else if (m_seconds != 0) {
			timeText += m_seconds + " ";
			timeText += this.getString(R.string.seconds);
		}


		timeDescription.setText(timeText);
	}

	/**
	 * Initialize the color picker buttons, change colors here etc.
	 */
	private void initColorButtons() {
		colorGradientButton1 = (Button) getActivity().findViewById(
				R.id.gradientButton_1);
		checkboxGradient = (CheckBox) getActivity().findViewById(
				R.id.checkbox_gradient);
		colorGradientButton1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(),
						0xffffffff, new OnAmbilWarnaListener() {
							public void onCancel(AmbilWarnaDialog dialog) {
							}

							public void onOk(AmbilWarnaDialog dialog, int color) {
								colorGradientButton1.setBackgroundColor(color);
								currSubP._timeLeftColor = color;
							}
						});
				dialog.show();
			}
		});

		colorGradientButton2 = (Button) getActivity().findViewById(
				R.id.gradientButton_2);
		colorGradientButton2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(),
						0xffffffff, new OnAmbilWarnaListener() {
							public void onCancel(AmbilWarnaDialog dialog) {
							}

							public void onOk(AmbilWarnaDialog dialog, int color) {
								colorGradientButton2.setBackgroundColor(color);
								currSubP._timeSpentColor = color;
							}
						});
				dialog.show();
			}
		});

		colorFrameButton = (Button) getActivity().findViewById(
				R.id.frameColorButton);
		colorFrameButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(),
						0xffffffff, new OnAmbilWarnaListener() {
							public void onCancel(AmbilWarnaDialog dialog) {
							}

							public void onOk(AmbilWarnaDialog dialog, int color) {
								colorFrameButton.setBackgroundColor(color);
								currSubP._frameColor = color;
							}
						});
				dialog.show();
			}
		});

		colorBackgroundButton = (Button) getActivity().findViewById(
				R.id.backgroundColorButton);
		colorBackgroundButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(),
						0xffffffff, new OnAmbilWarnaListener() {
							public void onCancel(AmbilWarnaDialog dialog) {
							}

							public void onOk(AmbilWarnaDialog dialog, int color) {
								colorBackgroundButton.setBackgroundColor(color);
								currSubP._bgcolor = color;
							}
						});
				dialog.show();
			}

		});
	}

	/**
	 * Sets the predefined settings of the chosen subprofile
	 * 
	 * @param subp
	 *            The Subprofile chosen
	 */
	public void loadSettings(SubProfile subProfile) {
		// TODO: Insert logic to load settings and put them into the view
		currSubP = subProfile;
		
		/* Set Time */
		int seconds = currSubP._totalTime % 60;
		int minutes = (currSubP._totalTime - seconds) / 60;
		mins.setCurrentItem(minutes);
		secs.setCurrentItem(seconds);

		/* Set Colors */
		checkboxGradient.setChecked(currSubP._gradient);
		colorGradientButton1.setBackgroundColor(currSubP._timeLeftColor);
		colorGradientButton2.setBackgroundColor(currSubP._timeSpentColor);
		colorFrameButton.setBackgroundColor(currSubP._frameColor);
		colorBackgroundButton.setBackgroundColor(currSubP._bgcolor);

	}
}
