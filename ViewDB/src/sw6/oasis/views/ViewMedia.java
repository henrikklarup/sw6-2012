package sw6.oasis.views;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Media;

public class ViewMedia extends ListFragment {

	Helper helper;
	Media media;
	Button bAdd, bDel;
	TextView tvHeader;
	ArrayAdapter<Media> mAdapter;
	List<Media> valueList;

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
				
				helper.mediaHelper.insertMedia(media);
				mAdapter.notifyDataSetChanged();
			}
		});
		bDel = (Button) getView().findViewById(R.id.delete);
		bDel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				helper.mediaHelper.clearMediaTable();
				mAdapter.notifyDataSetChanged();
			}
		});

		valueList = helper.mediaHelper.getMedia();

		mAdapter = new ArrayAdapter<Media>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, valueList);

		setListAdapter(mAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		long _id = getListAdapter().getItemId(position);
		media.setId(_id);
		media.setName("MediaModified");
		media.setPath("ModifiedPath");
		media.setTags("ModifiedTag");
		media.setType("ModifiedType");
		media.setOwnerId(654321);
		media.set_public(false);
		helper.mediaHelper.modifyMedia(media);
		mAdapter.notifyDataSetChanged();
	}
}