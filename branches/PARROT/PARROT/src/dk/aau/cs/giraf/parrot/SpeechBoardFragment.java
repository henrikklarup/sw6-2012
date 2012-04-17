package dk.aau.cs.giraf.parrot;


import parrot.Package.R;
import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

public class SpeechBoardFragment extends Fragment
{


	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		
		PARROTProfile user=PARROTActivity.getUser();
		if(user.getCategoryAt(0)!=null)
		{
			Category cat = user.getCategoryAt(0); //Dummy TODO FIXME



			GridView gridview = (GridView) activity.findViewById(R.id.pictogramgrid);
			gridview.setAdapter(new PictogramAdapter(cat, activity));

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



	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{        
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.speechboard_layout, container, false);
	}
}
