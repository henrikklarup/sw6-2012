package dk.aau.cs.giraf.parrot;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

public class ListViewAdapter extends ArrayAdapter<Category> 
{
	private ArrayList<Category> items;

	public ListViewAdapter(Context context, int textViewResourceId, ArrayList<Category> items)
	{
		super(context, textViewResourceId, items);
		this.items = items;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.categoriesitem, null); 
		}

		Category category = items.get(position);
		if (category != null) {
			ImageView imageView = (ImageView) view.findViewById(R.id.catpic); 
			TextView textView = (TextView) view.findViewById(R.id.catname);

			if (imageView != null) {
				//imageView.setImageResource(R.drawable.default_profile);
				imageView.setImageBitmap(items.get(position).getIcon().getBitmap());
			}
			/*if (textView != null) {

				textView.setText(category.name);//FIXME reffer to the categorys name. (add a name dalhoff!!!)

			}*/

		}

		return view;
	}

}
