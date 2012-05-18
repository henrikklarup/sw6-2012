package dk.aau.cs.giraf.wombat;

import android.app.Activity;
import android.content.Context;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WCheckbox extends Button{

	private boolean mChecked;
	
	public WCheckbox(Context c) {
		super(c);
	}

	private void changePicture(){
		// TODO: Change picture
	}
	
	public void setOnClickListener(boolean checked,
			OnClickListener onClickListener) {
		mChecked = checked;
		super.setOnClickListener(onClickListener);
		
	}

	public boolean changeCheckedState() {
		mChecked = !mChecked;
		changePicture();
		return mChecked;
	}

	public void setChecked(boolean gradient) {
		mChecked = gradient;
	}

}
