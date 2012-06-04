package dk.aau.cs.giraf.wombat;

import android.content.Context;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

public class WCheckbox extends Button{

	private boolean mChecked;
	
	public WCheckbox(Context c) {
		super(c);
		
		this.setBackgroundResource(R.drawable.btn);

		this.setLayoutParams(
				new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 
						ViewGroup.LayoutParams.MATCH_PARENT));
	}

	private void changePicture(){
		if(mChecked){
			this.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.checkmark, 0, 0);
		} else {
			this.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		}
		
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
		changePicture();
	}

}
