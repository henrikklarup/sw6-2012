package dk.aau.cs.giraf.parrot;

import parrot.Package.R;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
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
	public static int currentCategoryId = 0;
	public static PARROTProfile profileBeingModified = PARROTActivity.getUser();
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.);//FIXME Create xml, and reffer to it here.
		
		Spinner profiles = (Spinner) parrent.findViewById(R.id.profiles); //FIXME Make xml for this to work.
		ListView categories = (ListView) parrent.findViewById(R.id.categories); //FIXME Make xml for this to work.
		GridView pictograms = (GridView) parrent.findViewById(R.id.pictograms);//FIXME Make xml for this to work.
		ImageView trash = (ImageView) parrent.findViewById(R.id.trash);//FIXME Make xml for this to work.
		ImageView categoryPic = (ImageView) parrent.findViewById(R.id.categorypic);//FIXME Make xml for this to work.
		
		ArrayAdapter<String> categoriesArray = new ArrayAdap; //FIXME not sure which constructor to use...
		
		setListAdapter();
				
		categoriesArray.setAdapter(/*adapter*/);
		
		
		
	}
	
	
	
}
