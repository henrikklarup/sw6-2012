package dk.aau.cs.giraf.parrot;


import parrot.Package.R;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class SpeechBoardFragment extends Fragment
{

	private Activity parrent;


	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);

		this.parrent = activity;

		/*
		PARROTProfile user=PARROTActivity.getUser();
		if(user.getCategoryAt(0)!=null)
		{
			Category cat = user.getCategoryAt(0); //Dummy TODO FIXME



			GridView gridview = (GridView) activity.findViewById(R.id.pictogramgrid);
			gridview.setAdapter(new PictogramAdapter(cat, activity));

			activity.findViewById(R.id.pictogramgrid).setOnDragListener(new BoxDragListener());


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

	}*/
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		parrent.setContentView(R.layout.speechboard_layout);
		
		//FIXME These two lines are temporary for testing (kim)
		GridView gridview = (GridView) parrent.findViewById(R.id.pictogramgrid);
		gridview.setAdapter(new ImageAdapter(parrent));
		
		parrent.findViewById(R.id.pictogramgrid).setOnDragListener(new BoxDragListener());
		parrent.findViewById(R.id.SpeechBoard).setOnDragListener(new BoxDragListener());
		parrent.findViewById(R.id.supercategory).setOnDragListener(new BoxDragListener());
		

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{ 
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.speechboard_layout, container, false);
	}
}
