package dk.aau.cs.giraf.parrot;




import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;



public class OptionsFragment extends Fragment
{
	private CheckBox checkboxGradient;
	
	private Activity parrent;
	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		this.parrent = activity;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{        
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.options_layout, container, false);
	}
	private void initColorButtons() {
		
		Button ccc = (Button) parrent.findViewById(R.id);
	
		
		colorGradientButton1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(),
						currSubP.timeLeftColor, new OnAmbilWarnaListener() {
							public void onCancel(AmbilWarnaDialog dialog) {
							}

							public void onOk(AmbilWarnaDialog dialog, int color) {
								currSubP.timeLeftColor = color;
								setColor(colorGradientButton1.getBackground(),
										currSubP.timeLeftColor);
							}
						});
				dialog.show();
			}
		});
}
