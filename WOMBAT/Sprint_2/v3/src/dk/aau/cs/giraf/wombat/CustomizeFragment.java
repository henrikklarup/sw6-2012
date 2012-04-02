package dk.aau.cs.giraf.wombat;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import sw6.oasis.controllers.Helper;
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
import dk.aau.cs.giraf.TimerLib.SubProfile;

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
		final Button hourglassButton = (Button) getActivity().findViewById(
				R.id.houglassButton);
		hourglassButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		final Button timetimerButton = (Button) getActivity().findViewById(
				R.id.timetimerButton);
		timetimerButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		final Button progressbarButton = (Button) getActivity().findViewById(
				R.id.progressbarButton);
		progressbarButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		final Button digitalButton = (Button) getActivity().findViewById(
				R.id.digitalButton);
		digitalButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * Initialize the start button
	 */
	private void initStartButton() {
		final Button startButton = (Button) getActivity().findViewById(
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
		final CharSequence[] items = { "Foo", "Bar", "Baz" }; // TODO: Insert
																// correct
																// profile list
		final Button saveButton = (Button) getActivity().findViewById(
				R.id.customize_save);
		saveButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle(getActivity().getString(
						R.string.choose_profile));
				builder.setItems(items, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int item) {
						// TODO: Insert profile store functionality
					}
				});
				AlertDialog alert = builder.create();
				alert.show();

			}
		});

	}

	private void initAttachmentButton() {
		final CharSequence[] items = { "Foo", "Bar", "Baz" }; // Generate
																// correct
																// attachment
																// list
		final Button attachmentButton = (Button) getActivity().findViewById(
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
						// text
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
		final WheelView mins = (WheelView) getActivity().findViewById(
				R.id.minPicker);
		mins.setViewAdapter(new NumericWheelAdapter(getActivity()
				.getApplicationContext(), 0, 60));
		mins.setCurrentItem(30);
		mins.setCyclic(true);

		/* Create Seconds wheel */
		final WheelView secs = (WheelView) getActivity().findViewById(
				R.id.secPicker);
		secs.setViewAdapter(new NumericWheelAdapter(getActivity()
				.getApplicationContext(), 0, 60));
		secs.setCurrentItem(0);
		secs.setCyclic(true);

		/* Create description of time chosen */
		final TextView timeDescription = (TextView) getActivity().findViewById(
				R.id.showTime);
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
		final Button colorGradientButton1 = (Button) getActivity()
				.findViewById(R.id.gradientButton_1);
		colorGradientButton1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(),
						0xffffffff, new OnAmbilWarnaListener() {
							public void onCancel(AmbilWarnaDialog dialog) {
							}

							public void onOk(AmbilWarnaDialog dialog, int color) {
								colorGradientButton1.setBackgroundColor(color);
							}
						});
				dialog.show();
			}
		});

		final Button colorGradientButton2 = (Button) getActivity()
				.findViewById(R.id.gradientButton_2);
		colorGradientButton2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CheckBox checkbox = (CheckBox) getActivity().findViewById(
						R.id.checkbox_gradient);
				if (!checkbox.isChecked()) {
					checkbox.setChecked(true);
				}
				AmbilWarnaDialog dialog = new AmbilWarnaDialog(
						getActivity(), 0xffffffff,
						new OnAmbilWarnaListener() {
							public void onCancel(AmbilWarnaDialog dialog) {
							}

							public void onOk(AmbilWarnaDialog dialog,
									int color) {
								colorGradientButton2
										.setBackgroundColor(color);
							}
						});
				dialog.show();
			}
		});

		final Button colorFrameButton = (Button) getActivity().findViewById(
				R.id.frameColorButton);
		colorFrameButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(),
						0xffffffff, new OnAmbilWarnaListener() {
							public void onCancel(AmbilWarnaDialog dialog) {
							}

							public void onOk(AmbilWarnaDialog dialog, int color) {
								colorFrameButton.setBackgroundColor(color);
							}
						});
				dialog.show();
			}
		});

		final Button colorBackgroundButton = (Button) getActivity()
				.findViewById(R.id.backgroundColorButton);
		colorBackgroundButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(),
						0xffffffff, new OnAmbilWarnaListener() {
							public void onCancel(AmbilWarnaDialog dialog) {
							}

							public void onOk(AmbilWarnaDialog dialog, int color) {
								colorBackgroundButton.setBackgroundColor(color);
							}
						});
				dialog.show();
			}

		});
	}

	/**
	 * Sets the predefined settings of the chosen template
	 * 
	 * @param id
	 *            identification of the template
	 */
	public void setSettings(SubProfile template) {
		// TODO: Insert logic to load settings and put them into the view
	}
}
