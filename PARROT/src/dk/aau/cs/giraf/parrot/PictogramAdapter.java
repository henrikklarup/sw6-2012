package dk.aau.cs.giraf.parrot;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Rasmus
 * This is the Pictogram Adapter class. It is used to import the images from pictograms into a gridview.
 */
public class PictogramAdapter extends BaseAdapter {

	private Category cat;
	private Context context;
	
	public PictogramAdapter(Category cat, Context c)
	{
		this.cat=cat;
		context = c;
	}
	
	public int getCount() {
		//return the number of pictograms
		return cat.getPictograms().size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	//create an image view for each pictogram in the list.
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ImageView imageView;
		View view = convertView;
		TextView textView;
		Pictogram pct=cat.getPictogramAtIndex(position);
		if (convertView == null) 
		{  // if it's not recycled, initialize some attributes
			LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.pictogramview, null);
			
			imageView = (ImageView) view.findViewById(R.id.pictogrambitmap); 
            imageView.setLayoutParams(new GridView.LayoutParams(145, 145));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            imageView.setImageBitmap(pct.getBitmap());
            
            textView = (TextView) view.findViewById(R.id.pictogramtext);
            textView.setText(pct.getName());
        } 
        return view;
	}

}
