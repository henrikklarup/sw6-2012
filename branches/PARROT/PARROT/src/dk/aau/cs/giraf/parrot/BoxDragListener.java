package dk.aau.cs.giraf.parrot;

import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class BoxDragListener implements OnDragListener
{
	boolean insideOfMe = false;
	public boolean onDrag(View self, DragEvent event) {
		if (event.getAction() == DragEvent.ACTION_DRAG_STARTED){
			//Dummy
		} else if (event.getAction() == DragEvent.ACTION_DRAG_ENTERED){ 
			insideOfMe = true;
		} else if (event.getAction() == DragEvent.ACTION_DRAG_EXITED){
			insideOfMe = false;
		} else if (event.getAction() == DragEvent.ACTION_DROP){
			if (insideOfMe){
				View view = (View) event.getLocalState();
				ViewGroup owner = (ViewGroup) view.getParent();
				owner.removeView(view);
				LinearLayout container = (LinearLayout) self;
				if (container.getChildCount() > 0){
					container.addView(view, container.getChildCount());
				} else {
					container.addView(view);
				}
				
				
				
			}
		} else if (event.getAction() == DragEvent.ACTION_DRAG_ENDED){
			//Dummy				
		}
		return true;
	}
}


