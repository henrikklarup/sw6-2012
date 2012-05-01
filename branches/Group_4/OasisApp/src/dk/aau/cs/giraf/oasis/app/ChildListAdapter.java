package dk.aau.cs.giraf.oasis.app;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

public class ChildListAdapter extends BaseAdapter {

	private static List<Profile> values;
	private LayoutInflater mInflater;

	public ChildListAdapter(Context context, List<Profile> results) {
		values = results;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return values.size();
	}

	@Override
	public Object getItem(int arg0) {
		return values.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.children_list_item, null);

			holder = new ViewHolder();
			holder.tvFirstName = (TextView) convertView.findViewById(R.id.tvItemFirstName);
			holder.tvMiddleName = (TextView) convertView.findViewById(R.id.tvItemMiddleName);
			holder.tvSurName = (TextView) convertView.findViewById(R.id.tvItemSurName);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvFirstName.setText(values.get(position).getFirstname());
		holder.tvMiddleName.setText(values.get(position).getMiddlename());
		holder.tvSurName.setText(values.get(position).getSurname());

		return convertView;
	}

	static class ViewHolder {
		TextView tvFirstName, tvMiddleName, tvSurName;
	}
}
