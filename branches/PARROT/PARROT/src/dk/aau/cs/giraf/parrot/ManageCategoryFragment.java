package dk.aau.cs.giraf.parrot;

import java.util.ArrayList;

import parrot.Package.R;
import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData;
import android.os.Bundle;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

public class ManageCategoryFragment extends Fragment {

	private Activity parrent;
	
	//Remembers the index of the item that is currently being dragged.
	public static int draggedItemIndex = -1;
	public static int catDragOwnerID =-1;
	public static int currentCategoryId = 0; //This is the currrent category that is chosen
	public static PARROTProfile profileBeingModified = PARROTActivity.getUser();
	public static ArrayList<Pictogram> categories =  new ArrayList<Pictogram>();
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	    parrent.setContentView(R.layout.managecategory_layout);//FIXME Create xml, and reffer to it here.
		
		Spinner profiles = (Spinner) parrent.findViewById(R.id.profiles); //FIXME Make xml for this to work.
		ListView categories = (ListView) parrent.findViewById(R.id.categories); //FIXME Make xml for this to work.
		GridView pictograms = (GridView) parrent.findViewById(R.id.pictograms);//FIXME Make xml for this to work.
		ImageView trash = (ImageView) parrent.findViewById(R.id.trash);//FIXME Make xml for this to work.
		ImageView categoryPic = (ImageView) parrent.findViewById(R.id.categorypic);//FIXME Make xml for this to work.
		
		ArrayAdapter<String> categoriesArray = new ArrayAdap; //FIXME not sure which constructor to use...
		
		setListAdapter();
				
		
		
		categories.setAdapter(new ListViewAdapter(this, R.id.categoriesitem, profileBeingModified.getCategoryAt(currentCategoryId))); //FIXME Create xml for this
		
		pictograms.setAdapter(new PictogramAdapter(profileBeingModified.getCategoryAt(currentCategoryId), parrent));
		
		
		categories.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id)
			{
				draggedItemIndex = position;
				catDragOwnerID = R.id.pictogramgrid;
				ClipData data = ClipData.newPlainText("label", "text"); //TODO Dummy. Pictogram information can be placed here instead.
				DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
				view.startDrag(data, shadowBuilder, view, 0);
				return true;
			}

		});
		
		pictograms.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id)
			{
				draggedItemIndex = position;
				catDragOwnerID = R.id.pictogramgrid;
				ClipData data = ClipData.newPlainText("label", "text"); //TODO Dummy. Pictogram information can be placed here instead.
				DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
				view.startDrag(data, shadowBuilder, view, 0);
				return true;
			}

		});
		
		
		
		
	}
	
	
	
}
