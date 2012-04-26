package dk.aau.cs.giraf.launcher;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;

public class GAppDragger implements OnDragListener {

	@Override
	public boolean onDrag(View v, DragEvent e) {
		switch(e.getAction()){
		case DragEvent.ACTION_DRAG_STARTED:
			
			break;
		case DragEvent.ACTION_DRAG_ENTERED:
			//HIGHLIGHT APP HERE
			break;
		case DragEvent.ACTION_DRAG_EXITED:
			//dehighlight
			break;
		case DragEvent.ACTION_DROP:
			AppAdapter.setAppBackground(v, (int) Integer.parseInt(e.getClipData().getItemAt(0).getText().toString()));
			break;
		case DragEvent.ACTION_DRAG_ENDED:
			//dehighligth
			break;
		
		}
		return true;
	}

}
