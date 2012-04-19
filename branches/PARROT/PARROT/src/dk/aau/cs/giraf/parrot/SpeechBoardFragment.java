package dk.aau.cs.giraf.parrot;


import parrot.Package.R;
import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData;
import android.drm.DrmManagerClient.OnEventListener;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

public class SpeechBoardFragment extends Fragment
{

	private Activity parrent;


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
			
//			 GridView gridview = (GridView) parrent.findViewById(R.id.pictogramgrid);
//		        gridview.setAdapter(new ImageAdapter(parrent));
//	
		
		PARROTProfile user=PARROTActivity.getUser();
		if(user.getCategoryAt(0)!=null)
		{
			Category cat = user.getCategoryAt(0); //TODO we might have to replace this.

			
			
			
			GridView gridview = (GridView) parrent.findViewById(R.id.pictogramgrid);
			gridview.setAdapter(new PictogramAdapter(cat, parrent));

			parrent.findViewById(R.id.pictogramgrid).setOnDragListener(new BoxDragListener());
			parrent.findViewById(R.id.SpeechBoard).setOnDragListener(new BoxDragListener());
			parrent.findViewById(R.id.supercategory).setOnDragListener(new BoxDragListener());


			gridview.setOnItemLongClickListener(new OnItemLongClickListener()
			{

				public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id)
				{
					//Look At DndActivity for inspiration
					ClipData data = ClipData.newPlainText("label", "text"); //TODO Dummy. Pictogram information can be placed here instead.
					DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
					view.startDrag(data, shadowBuilder, view, 0);
					return true;
				}

			});
			
			
		}
		


	}
}

