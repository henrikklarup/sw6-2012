package dk.aau.cs.giraf.parrot;

import parrot.Package.R;
import android.app.Activity;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class BoxDragListener implements OnDragListener
{
	private Activity parrent;
	
	public BoxDragListener(Activity active) {
		parrent = active;
	}
	
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
				//View view = (View) event.getLocalState();
				
				
				if(R.id.sentenceboard == self.getId() && SpeechBoardFragment.dragOwnerID != R.id.sentenceboard)	//We are about to drop a view into the speechboard
				{
					GridView speech = (GridView) parrent.findViewById(R.id.sentenceboard);
					int index = speech.getChildCount();	//TODO replace this with the actual position
					//speech.addView(view, index);
					int categoryIndex=0;//TODO make sure that this refers to the current category
					Pictogram pic = PARROTActivity.getUser().getCategoryAt(categoryIndex).getPictogramAtIndex(SpeechBoardFragment.draggedPictogramIndex);	
					//ImageView img = new ImageView(parrent);
					//img.setImageBitmap(pic.getBitmap());
					//speech.addView(img);
					//SpeechBoardFragment.speechboardPictograms.add(index, pic);	//Add the references pictogram to the back-end list
					SpeechBoardFragment.speechBoardCategory.addPictogram(pic);
					speech.setAdapter(new PictogramAdapter(SpeechBoardFragment.speechBoardCategory, parrent));
					speech.invalidate();
				}


			}
			//To ensure that no wrong references will be made, the index is reset to -1
			SpeechBoardFragment.draggedPictogramIndex = -1;
		} else if (event.getAction() == DragEvent.ACTION_DRAG_ENDED){
			//Dummy				
		}
		return true;
	}
}


