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
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import dk.aau.cs.giraf.TimerLib.Child;
import dk.aau.cs.giraf.TimerLib.Guardian;
import dk.aau.cs.giraf.TimerLib.SubProfile;
import dk.aau.cs.giraf.TimerLib.formFactor;

public class CustomizeFragment extends Fragment {
	private SubProfile preSubP;
	private SubProfile currSubP;
	private Guardian guard = Guardian.getInstance();

	private Button hourglassButton;
	private Button timetimerButton;
	private Button progressbarButton;
	private Button digitalButton;
	private Button startButton;
	private Button saveButton;
	private Button saveAsButton;
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Populate the fragment according to the details layout
		return inflater.inflate(R.layout.customize, container, false);
	}

	@Override
	// Start the list empty
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		currSubP = new SubProfile("", "", 10, 0xFF000000, 0xFFFF0000,
				0xFFFFFFFF, 0xFFFF0000, 600, false);

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
		initSaveAsButton();
		initStartButton();
	}

	public void setDefaultProfile() {
		currSubP = new SubProfile("", "", 10, 0xFF000000, 0xFFFF0000,
				0xFFFFFFFF, 0xFFFF0000, 600, false);
		
		loadSettings(currSubP);
	}

	/**
	 * Initialize the style chooser buttons
	 */
	private void initStyleChoser() {
		hourglassButton = (Button) getActivity().findViewById(
				R.id.houglassButton);
		hourglassButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				selectStyle(formFactor.Hourglass);

			}
		});

		timetimerButton = (Button) getActivity().findViewById(
				R.id.timetimerButton);
		timetimerButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				selectStyle(formFactor.TimeTimer);

			}
		});

		progressbarButton = (Button) getActivity().findViewById(
				R.id.progressbarButton);
		progressbarButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				selectStyle(formFactor.ProgressBar);

			}
		});

		digitalButton = (Button) getActivity().findViewById(R.id.digitalButton);
		digitalButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				selectStyle(formFactor.DigitalClock);

			}
		});
	}

	/**
	 * Change style on currSubP according to formFactor type
	 * 
	 * @param formType
	 *            Style to change to
	 */
	private void selectStyle(formFactor formType) {
		if (formType == formFactor.Hourglass) {
			currSubP = currSubP.toHourglass();
			hourglassButton.setSelected(true);
		} else {
			hourglassButton.setSelected(false);
		}

		if (formType == formFactor.TimeTimer) {
			currSubP = currSubP.toTimeTimer();
			timetimerButton.setSelected(true);
		} else {
			timetimerButton.setSelected(false);
		}
		if (formType == formFactor.ProgressBar) {
			currSubP = currSubP.toProgressBar();
			progressbarButton.setSelected(true);
		} else {
			progressbarButton.setSelected(false);
		}
		if (formType == formFactor.DigitalClock) {
			currSubP = currSubP.toDigitalClock();
			digitalButton.setSelected(true);
		} else {
			digitalButton.setSelected(false);
		}
		genName_Description();
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
		mins.setCyclic(true);

		/* Create Seconds wheel */
		secs = (WheelView) getActivity().findViewById(R.id.secPicker);
		secs.setViewAdapter(new NumericWheelAdapter(getActivity()
				.getApplicationContext(), 0, 59));
		secs.setCyclic(true);

		/* Create description of time chosen */
		timeDescription = (TextView) getActivity().findViewById(R.id.showTime);
		setTime(currSubP._totalTime);

		/* Add on change listeners for both wheels */
		mins.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateTime(mins.getCurrentItem(), secs.getCurrentItem());

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
					previousMins = 0;
				}
			}
		});

		secs.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateTime(mins.getCurrentItem(), secs.getCurrentItem());
			}
		});
	}

	/**
	 * Sets the time on the time picker wheels
	 * 
	 * @param totalTime
	 *            Total time in seconds
	 */
	private void setTime(int totalTime) {
		int minutes, seconds;
		Log.e("Time", "" + totalTime);
		if(totalTime == 60){
			minutes = 1;
			seconds = 0;
		} else if (totalTime >= 60 * 60) {
			minutes = 60;
			seconds = 0;
		} else {
			minutes = totalTime / 60;
			seconds = totalTime % 60;
		}

		mins.setCurrentItem(minutes);
		secs.setCurrentItem(seconds);

		if (minutes == 60) {
			previousMins = 60;
			previousSecs = seconds;

			secs.setCurrentItem(0);
			secs.setViewAdapter(new NumericWheelAdapter(getActivity()
					.getApplicationContext(), 0, 0));
			secs.setCyclic(false);
		} else if (previousMins == 60) {
			secs.setViewAdapter(new NumericWheelAdapter(getActivity()
					.getApplicationContext(), 0, 60));

			secs.setCurrentItem(previousSecs);
			secs.setCyclic(true);
			previousMins = 0;
		}

		updateTime(minutes, seconds);
	}

	/**
	 * Update time on currSubP and updates the time text
	 * 
	 * @param time
	 *            Total time in seconds
	 */
	private void updateTime(int m_minutes, int m_seconds) {
		currSubP._totalTime = (m_minutes * 60) + m_seconds;

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
		genName_Description();
	}

	/**
	 * Initialize the color picker buttons, change colors here etc.
	 */
	private void initColorButtons() {
		checkboxGradient = (CheckBox) getActivity().findViewById(
				R.id.checkbox_gradient);

		checkboxGradient.setChecked(currSubP._gradient);
		checkboxGradient.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				currSubP._gradient = isChecked;				
			}
		});
		
		colorGradientButton1 = (Button) getActivity().findViewById(
				R.id.gradientButton_1);

		setColor(colorGradientButton1.getBackground(), currSubP._timeLeftColor);

		// colorGradientButton1.setBackgroundColor(currSubP._timeLeftColor);
		colorGradientButton1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(),
						currSubP._timeLeftColor, new OnAmbilWarnaListener() {
							public void onCancel(AmbilWarnaDialog dialog) {
							}

							public void onOk(AmbilWarnaDialog dialog, int color) {
								currSubP._timeLeftColor = color;
								setColor(colorGradientButton1.getBackground(),
										currSubP._timeLeftColor);
							}
						});
				dialog.show();
			}
		});

		colorGradientButton2 = (Button) getActivity().findViewById(
				R.id.gradientButton_2);
		setColor(colorGradientButton2.getBackground(), currSubP._timeSpentColor);
		colorGradientButton2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(),
						currSubP._timeSpentColor, new OnAmbilWarnaListener() {
							public void onCancel(AmbilWarnaDialog dialog) {
							}

							public void onOk(AmbilWarnaDialog dialog, int color) {
								currSubP._timeSpentColor = color;
								setColor(colorGradientButton2.getBackground(),
										currSubP._timeSpentColor);
							}
						});
				dialog.show();
			}
		});

		colorFrameButton = (Button) getActivity().findViewById(
				R.id.frameColorButton);
		setColor(colorFrameButton.getBackground(), currSubP._frameColor);
		colorFrameButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(),
						currSubP._frameColor, new OnAmbilWarnaListener() {
							public void onCancel(AmbilWarnaDialog dialog) {
							}

							public void onOk(AmbilWarnaDialog dialog, int color) {
								currSubP._frameColor = color;
								setColor(colorFrameButton.getBackground(),
										currSubP._frameColor);
							}
						});
				dialog.show();
			}
		});

		colorBackgroundButton = (Button) getActivity().findViewById(
				R.id.backgroundColorButton);
		setColor(colorBackgroundButton.getBackground(), currSubP._bgcolor);
		colorBackgroundButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(),
						currSubP._bgcolor, new OnAmbilWarnaListener() {
							public void onCancel(AmbilWarnaDialog dialog) {
							}

							public void onOk(AmbilWarnaDialog dialog, int color) {
								currSubP._bgcolor = color;
								setColor(colorBackgroundButton.getBackground(),
										currSubP._bgcolor);
							}
						});
				dialog.show();
			}

		});
	}

	private void setColor(Drawable d, int color) {
		PorterDuffColorFilter filter = new PorterDuffColorFilter(color,
				PorterDuff.Mode.SRC_ATOP);
		d.setColorFilter(filter);

	}

	/**
	 * Initialize the attachment button
	 */
	private void initAttachmentButton() {
		final List<String> values = new ArrayList<String>();
		final List<SubProfile> subProfiles;

		if (guard.getChild() == null) {
			subProfiles = new ArrayList<SubProfile>();
			for (Child c : guard.Children()) {
				for (SubProfile p : c.SubProfiles()) {
					subProfiles.add(p);
				}
			}

		} else {
			subProfiles = guard.getChild().SubProfiles();
		}

		for (SubProfile subProfile : subProfiles) {
			values.add(subProfile._name);
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
						setAttachment(subProfiles.get(item));
					}
				});
				AlertDialog alert = builder.create();
				alert.show();

			}
		});
	}

	/**
	 * Sets the attachment to subProfile, resets if subProfile == null
	 * 
	 * @param subProfile
	 *            SubProfile to be attached
	 */
	private void setAttachment(SubProfile subProfile) {
		int pictureRes;
		int textRes;
		String attachText = getActivity().getApplicationContext().getString(
				R.string.attached);

		TextView attView = (TextView) getActivity().findViewById(
				R.id.customize_attachment_text);

		if (subProfile != null) {

			currSubP.setAttachment(subProfile);

			switch (subProfile.formType()) {
			case Hourglass:
				pictureRes = R.drawable.thumbnail_hourglass;
				textRes = R.string.customize_hourglass_description;
				break;
			case DigitalClock:
				pictureRes = R.drawable.thumbnail_digital;
				textRes = R.string.customize_digital_description;
				break;
			case ProgressBar:
				pictureRes = R.drawable.thumbnail_progressbar;
				textRes = R.string.customize_progressbar_description;
				break;
			case TimeTimer:
				pictureRes = R.drawable.thumbnail_timetimer;
				textRes = R.string.customize_timetimer_description;
				break;
			default:
				pictureRes = R.drawable.thumbnail_attachment;
				textRes = R.string.attachment_button_description;
				attachText = "";
				break;
			}
		} else {
			pictureRes = R.drawable.thumbnail_attachment;
			textRes = R.string.attachment_button_description;

			attachText = "";
		}

		attachmentButton.setCompoundDrawablesWithIntrinsicBounds(0, pictureRes,
				0, 0);
		attachmentButton.setText(textRes);
		attView.setText(attachText);
	}

	/**
	 * Initialize the save button
	 */
	private void initSaveButton() {
		saveButton = (Button) getActivity().findViewById(R.id.customize_save);
		saveButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TimerLoader.subProfileID = currSubP.save(preSubP);
				guard.publishList().get(TimerLoader.profilePosition).select();
				
				SubProfileFragment spf = (SubProfileFragment) getFragmentManager().findFragmentById(R.id.subprofileFragment);
				spf.reloadSubProfiles();
			}
		});
	}
	

	private void genName_Description() {
		String name = "";
		String desc = "";

		switch (currSubP.formType()) {
		case Hourglass:
			name += getString(R.string.customize_hourglass_description);
			break;

		case DigitalClock:
			name += getString(R.string.customize_digital_description);
			break;

		case ProgressBar:
			name += getString(R.string.customize_progressbar_description);
			break;

		case TimeTimer:
			name += getString(R.string.customize_timetimer_description);
			break;

		default:
			name += "";
			break;
		}
		
		int seconds = currSubP._totalTime % 60;
		int minutes = (currSubP._totalTime - seconds) / 60;
		
		desc += name + " - ";
		desc += "(" + minutes + ":" + seconds + ")";
		
		currSubP._name = name;
		currSubP._desc = desc;
	}

	/**
	 * Initialize the Save As button
	 */
	private void initSaveAsButton() {
		final ArrayList<String> values = new ArrayList<String>();
		for (Child c : guard.Children()) {
			values.add(c._name);
		}

		// Cast values to CharSequence
		final CharSequence[] items = values.toArray(new CharSequence[values
				.size()]);

		saveAsButton = (Button) getActivity().findViewById(
				R.id.customize_save_as);
		saveAsButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle(getActivity().getString(
						R.string.choose_profile));
				builder.setItems(items, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int item) {
						Child c = guard.Children().get(item);
						c.save(currSubP);
						SubProfileFragment df = (SubProfileFragment) getFragmentManager()
								.findFragmentById(R.id.subprofileFragment);
						df.loadSubProfiles();

						String toastText = currSubP._name;
						toastText += " "
								+ getActivity().getApplicationContext()
										.getText(R.string.toast_text);
						toastText += " " + c._name;

						Toast toast = Toast.makeText(getActivity(), toastText,
								3000);
						toast.show();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
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
				currSubP.addLastUsed(preSubP);
				currSubP.select();
				Intent i = new Intent(getActivity().getApplicationContext(),
						OpenGLActivity.class);
				startActivity(i);
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
		currSubP = subProfile.copy();
		preSubP = subProfile;

		/* Set Style */
		selectStyle(currSubP.formType());

		/* Set Time */
		setTime(currSubP._totalTime);

		/* Set Colors */
		checkboxGradient.setChecked(currSubP._gradient);
		setColor(colorGradientButton1.getBackground(), currSubP._timeLeftColor);
		setColor(colorGradientButton2.getBackground(), currSubP._timeSpentColor);
		setColor(colorFrameButton.getBackground(), currSubP._frameColor);
		setColor(colorBackgroundButton.getBackground(), currSubP._bgcolor);

		/* Set Attachment */
		setAttachment(currSubP.getAttachment());

	}

	public void reloadCustomize() {
		// currSubP = new
	}
}
