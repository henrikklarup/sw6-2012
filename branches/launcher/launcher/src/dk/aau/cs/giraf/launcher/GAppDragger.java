package dk.aau.cs.giraf.launcher;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;

public class GAppDragger implements OnDragListener {

	@Override
	public boolean onDrag(View view, DragEvent drawEvent) {
		switch(drawEvent.getAction()){
		case DragEvent.ACTION_DRAG_STARTED:
			
			break;
		case DragEvent.ACTION_DRAG_ENTERED:
			/*
			 * Future todo: implement highlighting
			 */
			break;
		case DragEvent.ACTION_DRAG_EXITED:
			/*
			 * Future todo: implement dehighlighting
			 */
			break;
		case DragEvent.ACTION_DROP:
			AppAdapter.setAppBackground(view, (int) Integer.parseInt(drawEvent.getClipData().getItemAt(0).getText().toString()));
			break;
		case DragEvent.ACTION_DRAG_ENDED:
			/*
			 * Future todo: implement dehighlighting
			 */
			break;
		
		}
		return true;
	}

}
