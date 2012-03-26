package giraffe.launcher;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

public class GiraffeOnClickListener implements OnClickListener {
	private final int position;

	public GiraffeOnClickListener(int position)
	{
		this.position = position;
	}

	public void onClick(View v)
	{
		// Preform a function based on the position
	}

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
}

