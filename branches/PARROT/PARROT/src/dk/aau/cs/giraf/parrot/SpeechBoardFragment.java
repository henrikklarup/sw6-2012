package dk.aau.cs.giraf.parrot;


import parrot.Package.R;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.DragShadowBuilder;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

public class SpeechBoardFragment extends Fragment
{


	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);


		Category cat = null; //Dummy TODO FIXME
		GridView gridview = (GridView) activity.findViewById(R.id.pictogramgrid);
		gridview.setAdapter(new PictogramAdapter(cat, activity));

		gridview.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int arg2, long arg3)
			{
				//Look At DndActivity for inspiration
				return false;
			}


		});
	}



	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{        
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.speechboard_layout, container, false);
	}
}
