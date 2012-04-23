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
				
				
				if( self.getId() == R.id.sentenceboard && SpeechBoardFragment.dragOwnerID != R.id.sentenceboard)	//We are about to drop a view into the speechboard
				{
					GridView speech = (GridView) parrent.findViewById(R.id.sentenceboard);
					int index = speech.getChildCount();	//TODO replace this with the actual position
					int categoryIndex=0;//TODO make sure that this refers to the current category
					Pictogram pic = PARROTActivity.getUser().getCategoryAt(categoryIndex).getPictogramAtIndex(SpeechBoardFragment.draggedPictogramIndex);	

					SpeechBoardFragment.speechBoardCategory.addPictogram(pic);//Add the references pictogram to the back-end list
					speech.setAdapter(new PictogramAdapter(SpeechBoardFragment.speechBoardCategory, parrent));
					speech.invalidate();
				}
				if(self.getId() == R.id.sentenceboard && SpeechBoardFragment.dragOwnerID == R.id.sentenceboard)
				{
					
				}
				if(self.getId() != R.id.sentenceboard && SpeechBoardFragment.dragOwnerID == R.id.sentenceboard) //If we drag something from the sentenceboard to somewhere else
				{
					GridView speech = (GridView) parrent.findViewById(R.id.sentenceboard);
					int index = SpeechBoardFragment.draggedPictogramIndex;
					SpeechBoardFragment.speechBoardCategory.removePictogram(index);
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


