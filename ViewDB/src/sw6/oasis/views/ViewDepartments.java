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
import dk.aau.cs.giraf.oasis.lib.models.Department;

public class ViewDepartments extends ListFragment {
	
	Helper helper;
	Department department;
	Button bAdd, bDel;
	TextView tvHeader;
	ArrayAdapter<Department> mAdapter;
	List<Department> valueList;
	
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
		
		department = new Department();
		
		tvHeader = (TextView) getView().findViewById(R.id.table_header);
		tvHeader.setText("DepartmentsTable");
		bAdd = (Button) getView().findViewById(R.id.add);
		bAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				department.setName("DepartmentName");
				department.setAddress("DepartmentAddress");
				department.setPhone(123456789);
				
				helper.departmentsHelper.insertDepartment(department);
				mAdapter.notifyDataSetChanged();
			}
		});
		bDel = (Button) getView().findViewById(R.id.delete);
		bDel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				helper.departmentsHelper.clearDepartmentsTable();
				mAdapter.notifyDataSetChanged();
			}
		});
		
		valueList = helper.departmentsHelper.getDepartments();

        mAdapter = new ArrayAdapter<Department>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, valueList);

        setListAdapter(mAdapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		long _id = getListAdapter().getItemId(position);
		department.setId(_id);
		department.setName("DepartmentModified");
		department.setAddress("ModifiedAddress");
		department.setPhone(123456789);
		helper.departmentsHelper.modifyDepartment(department);
		mAdapter.notifyDataSetChanged();
	}
}