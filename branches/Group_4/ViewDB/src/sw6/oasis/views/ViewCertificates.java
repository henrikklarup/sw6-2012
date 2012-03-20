package sw6.oasis.views;

import sw6.oasis.controllers.Helper;
import sw6.oasis.viewmodels.Certificate;
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

public class ViewCertificates extends ListFragment {

	Helper helper;
	Certificate certificate;
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
		
		certificate = new Certificate();
		
		tvHeader = (TextView) getView().findViewById(R.id.table_header);
		tvHeader.setText("CertificatesTable");
		bAdd = (Button) getView().findViewById(R.id.add);
		bAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				certificate.setAuthkey("CertificateString");
				helper.insertCertificate(certificate);
				mAdapter.notifyDataSetChanged();
			}
		});
		bDel = (Button) getView().findViewById(R.id.delete);
		bDel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				helper.clearCertificateTable();
				mAdapter.notifyDataSetChanged();
			}
		});
		
		Cursor cursor = helper.getCertificates();
        int[] to = new int[] { R.id.certificate_column_one, R.id.certificate_column_two};

        mAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.certificate_list, cursor, cursor.getColumnNames(), to);

        setListAdapter(mAdapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		long _id = getListAdapter().getItemId(position);
		certificate.setId(_id);
		certificate.setAuthkey("ModifiedCertificate");
		helper.modifyCertificate(certificate);
		mAdapter.notifyDataSetChanged();
	}
}