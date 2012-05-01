package dk.aau.cs.giraf.parrot;


import java.io.InputStream;
import java.util.ArrayList;

import parrot.Package.R;
import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

public class SpeechBoardFragment extends Fragment
{

	private Activity parrent;
	
	//Remembers the index of the pictogram that is currently being dragged.
	public static int draggedPictogramIndex = -1;
	public static int dragOwnerID =-1;
	//Serves as the back-end storage for the visual speechboard
	public static ArrayList<Pictogram> speechboardPictograms = new ArrayList<Pictogram>();
	public static Category speechBoardCategory = new Category(0x00ff00,null);	//This category contains the pictograms on the sentenceboard
	public static Category displayedCat = new Category(0x00ff00,null);			//This category contains the pictograms displayed on the big board
	private PARROTProfile user = null;
	
	/*//Not in use anymore
	//Use asset manager to convert resource path into string path.
	AssetManager asset = parrent.getResources().getAssets();
	InputStream pictureBuffer =asset.open("drawable/usynlige.png");
	String picturePath = pictureBuffer.toString();
	*/
	
	private Pictogram empty;  //FIXME find the right path to the picture "usynlig", or find a way to use the picture directly
	
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.parrent = activity;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		parrent.setContentView(R.layout.speechboard_layout);
		empty = new Pictogram("#usynlig#", null, null, null, parrent);

		user=PARROTActivity.getUser();
		if(user.getCategoryAt(0)!=null)
		{
			displayedCat = user.getCategoryAt(0); //TODO we might have to replace this.

			//Fills the sentenceboard with emty pictograms
			speechBoardCategory.addPictogram(empty);
			speechBoardCategory.addPictogram(empty);
			speechBoardCategory.addPictogram(empty);
			speechBoardCategory.addPictogram(empty);

			GridView pictogramGrid = (GridView) parrent.findViewById(R.id.pictogramgrid);
			pictogramGrid.setAdapter(new PictogramAdapter(displayedCat, parrent));
			
			GridView sentenceBoardGrid = (GridView) parrent.findViewById(R.id.sentenceboard);
			sentenceBoardGrid.setAdapter(new PictogramAdapter(speechBoardCategory, parrent));
			
			GridView superCategoryGrid = (GridView) parrent.findViewById(R.id.supercategory);
			superCategoryGrid.setAdapter(new CategoryAdapter(user.getCategories(), parrent));
			
			parrent.findViewById(R.id.pictogramgrid).setOnDragListener(new BoxDragListener(parrent));
			parrent.findViewById(R.id.sentenceboard).setOnDragListener(new BoxDragListener(parrent));
			parrent.findViewById(R.id.supercategory).setOnDragListener(new BoxDragListener(parrent));


			pictogramGrid.setOnItemLongClickListener(new OnItemLongClickListener()
			{

				public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id)
				{
					draggedPictogramIndex = position; //TODO make sure that position is the index of the pictogram
					dragOwnerID = R.id.pictogramgrid;
					ClipData data = ClipData.newPlainText("label", "text"); //TODO Dummy. Pictogram information can be placed here instead.
					DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
					view.startDrag(data, shadowBuilder, view, 0);
					return true;
				}

			});
			
			sentenceBoardGrid.setOnItemLongClickListener(new OnItemLongClickListener()
			{

				public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id)
				{
					draggedPictogramIndex = position; //TODO make sure that position is the index of the pictogram
					dragOwnerID = R.id.sentenceboard;
//					speechBoardCategory.removePictogram(draggedPictogramIndex);					
					ClipData data = ClipData.newPlainText("label", "text"); //TODO Dummy. Pictogram information can be placed here instead.
					DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
					view.startDrag(data, shadowBuilder, view, 0);
					return true;
				}

			});
			
			superCategoryGrid.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View view, int position, long id)
				{
					displayedCat = user.getCategoryAt(position);
					GridView pictogramGrid = (GridView) parrent.findViewById(R.id.pictogramgrid);
					pictogramGrid.setAdapter(new PictogramAdapter(displayedCat, parrent));
					//pictogramGrid.invalidate();
					
				}
			});
			
			

		}



	}
}

