package dk.aau.cs.giraf.parrot;




import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;



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
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		parrent.setContentView(R.layout.options_layout);
		initColorButtons();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		parrent.setContentView(R.layout.options_layout);
	}

	private void initColorButtons() {

		Button ccc = (Button) parrent.findViewById(R.id.changecategorycolor);// these are two different buttons

		Button csc = (Button) parrent.findViewById(R.id.changesentencecolor);

		//FIXME after clicking OK on any of the colour picker dialogs, it is nolonger possible to click the buttons.
		//This is fixed by removing the  lines that interact the views of another fragment.

		ccc.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(),
						PARROTActivity.getUser().getCategoryColor(), new OnAmbilWarnaListener() {
					public void onCancel(AmbilWarnaDialog dialog) {
					}

					public void onOk(AmbilWarnaDialog dialog, int color) {
						PARROTProfile user = PARROTActivity.getUser();
						user.setCategoryColor(color);
						PARROTActivity.setUser(user);
					}
				});
				dialog.show();
			}
		});

		csc.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(),
						PARROTActivity.getUser().getSentenceBoardColor(), new OnAmbilWarnaListener() {
					public void onCancel(AmbilWarnaDialog dialog) {
					}

					public void onOk(AmbilWarnaDialog dialog, int color) {
						PARROTProfile user = PARROTActivity.getUser();
						user.setSentenceBoardColor(color);
						PARROTActivity.setUser(user);
					}
				});
				dialog.show();

			}
		});
	}
}
