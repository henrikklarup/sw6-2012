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
	private SubProfile currentSubProfile;
	private Guardian guard = Guardian.getInstance();
	private Child currentChild;

	private int totalTime;

	private int gradientColor1;
	private int gradientColor2;
	private int frameColor;
	private int backgroundColor;

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
				// TODO Functionality which highlights button and stores the
				// choice

			}
		});

		timetimerButton = (Button) getActivity().findViewById(
				R.id.timetimerButton);
		timetimerButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Functionality which highlights button and stores the
				// choice

			}
		});

		progressbarButton = (Button) getActivity().findViewById(
				R.id.progressbarButton);
		progressbarButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Functionality which highlights button and stores the
				// choice

			}
		});

		digitalButton = (Button) getActivity().findViewById(R.id.digitalButton);
		digitalButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Functionality which highlights button and stores the
				// choice

			}
		});

	}

	/**
	 * Initialize the start button
	 */
	private void initStartButton() {
		startButton = (Button) getActivity().findViewById(
				R.id.customize_start_button);
		startButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO: Start "Vis" Activity
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
						c.Profiles().add(currentSubProfile);
						// TODO: Insert profile store functionality
					}
				});
				AlertDialog alert = builder.create();
				alert.show();

			}
		});

	}

	private void initAttachmentButton() {
		final List<String> values = new ArrayList<String>();

		if (currentChild == null) {
			for (Child c : guard.Children()) {
				for (SubProfile p : c.Profiles()) {
					values.add(p._name);
				}
			}

		} else {
			for (SubProfile p : currentChild.Profiles()) {
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
						// TODO: Insert logic which inserts correct picture and
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
		timeDescription.setText(getTimeDescription(mins.getCurrentItem(),
				secs.getCurrentItem()));

		/* Add on change listeners for both wheels */
		mins.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				timeDescription.setText(getTimeDescription(
						mins.getCurrentItem(), secs.getCurrentItem()));
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
				timeDescription.setText(getTimeDescription(
						mins.getCurrentItem(), secs.getCurrentItem()));
			}
		});
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
								gradientColor1 = color;
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
								gradientColor1 = color;
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
								frameColor = color;
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
								backgroundColor = color;
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
	public void loadSettings(SubProfile subp) {
		// TODO: Insert logic to load settings and put them into the view
		setTime(subp._totalTime);
		setColors(subp._gradient, subp._timeLeftColor, subp._timeSpentColor, subp._frameColor, subp._bgcolor);

	}

	/**
	 * Sets the current time in the fragment wheels.
	 * 
	 * @param _totalTime
	 *            The total time in seconds
	 */
	private void setTime(int _totalTime) {
		totalTime = _totalTime;

		int seconds = _totalTime % 60;
		int minutes = (_totalTime - seconds) / 60;
		mins.setCurrentItem(minutes);
		secs.setCurrentItem(seconds);

	}

	/**
	 * Sets the colors in the fragment in android color ints (fx. 0xFFFFFFFF).
	 * 
	 * @param gradient
	 *            Is gradient checked or not
	 * @param timeLeft
	 *            Which color is the time left
	 * @param timeSpent
	 *            Which color is the time spent or the gradient to time left
	 * @param frame
	 *            Which color is the frame of the timer
	 * @param background
	 *            Which color is the background of the timer
	 */
	private void setColors(boolean gradient, int timeLeft, int timeSpent,
			int frame, int background) {
		gradientColor1 = timeLeft;
		gradientColor2 = timeLeft;
		frameColor = frame;
		backgroundColor = background;

		checkboxGradient.setChecked(gradient);
		colorGradientButton1.setBackgroundColor(gradientColor1);
		colorGradientButton2.setBackgroundColor(gradientColor2);
		colorFrameButton.setBackgroundColor(frameColor);
		colorBackgroundButton.setBackgroundColor(backgroundColor);
	}
}
