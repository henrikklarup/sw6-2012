package sw6.oasis.views;

import sw6.oasis.controllers.Helper;
import sw6.oasis.viewmodels.Media;
import sw6.oasis.views.R;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ViewMedia extends ListFragment {

	Helper helper;
	Media media;
	ArrayAdapter<String> adapter;
	Button bAdd, bDel;
	TextView tvHeader;
	int _position;
	SimpleCursorAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tableview, container, false);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		helper = new Helper(getActivity().getApplicationContext());
		
		media = new Media();

		tvHeader = (TextView) getView().findViewById(R.id.table_header);
		tvHeader.setText("MediaTable");
		bAdd = (Button) getView().findViewById(R.id.add);
		bAdd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				media.setName("Media");
				media.setPath("Path");
				media.setTags("Tag");
				media.setType("Type");
				media.setOwnerId(123456);
				media.set_public(true);
				
				helper.insertMedia(media);
				mAdapter.notifyDataSetChanged();
			}
		});
		bDel = (Button) getView().findViewById(R.id.delete);
		bDel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				helper.clearMediaTable();
				mAdapter.notifyDataSetChanged();
			}
		});

		Cursor cursor = helper.getMedia();
		int[] to = new int[] { R.id.media_column_one, R.id.media_column_two, R.id.media_column_three, R.id.media_column_four, R.id.media_column_five, R.id.media_column_six, R.id.media_column_seven};

		mAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.media_list, cursor, cursor.getColumnNames(), to);

		setListAdapter(mAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		long _id = getListAdapter().getItemId(position);
		media.setId(_id);
		media.setName("MediaModified");
		helper.modifyMedia(media);
		mAdapter.notifyDataSetChanged();
	}
}