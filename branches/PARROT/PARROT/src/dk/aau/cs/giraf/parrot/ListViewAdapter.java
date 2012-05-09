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

public class ListViewAdapter extends ArrayAdapter<Category> //FIXME In this class Profiles might not be right. Check if it works with Profile and change it if not.
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
			view = layoutInflater.inflate(R.layout.profile_list, null); //FIXME change profile_list to apropriate xml document
		}

		Category category = items.get(position);
		if (category != null) {
			ImageView imageView = (ImageView) view.findViewById(R.id.catpic); //FIXME add this to xml
			TextView textView = (TextView) view.findViewById(R.id.catname); //FIXME add this to xml

			if (imageView != null) {
				imageView.setImageResource(R.drawable.default_profile);
			}
			if (textView != null) {

				textView.setText(category.name);//FIXME reffer to the categorys name. (add a name dalhoff!!!)

			}

		}

		return view;
	}

}
