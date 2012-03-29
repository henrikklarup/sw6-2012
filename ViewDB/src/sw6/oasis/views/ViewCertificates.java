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
import dk.aau.cs.giraf.oasis.lib.models.Certificate;

public class ViewCertificates extends ListFragment {

	Helper helper;
	Certificate certificate;
	Button bAdd, bDel;
	TextView tvHeader;
	ArrayAdapter<Certificate> mAdapter;
	List<Certificate> valueList;
	
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
				helper.certificateHelper.insertCertificate(certificate);
				mAdapter.notifyDataSetChanged();
			}
		});
		bDel = (Button) getView().findViewById(R.id.delete);
		bDel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				helper.certificateHelper.clearCertificateTable();
				mAdapter.notifyDataSetChanged();
			}
		});
		
		valueList = helper.certificateHelper.getCertificates();

        mAdapter = new ArrayAdapter<Certificate>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, valueList);

        setListAdapter(mAdapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		long _id = getListAdapter().getItemId(position);
		certificate.setId(_id);
		certificate.setAuthkey("ModifiedCertificate");
		helper.certificateHelper.modifyCertificate(certificate);
		mAdapter.notifyDataSetChanged();
	}
}