package sw6.oasis.views;

import sw6.oasis.controllers.Helper;
import sw6.oasis.viewmodels.Department;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ViewDepartments extends ListFragment {
	Helper helper;
	Department department;
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
		
		Cursor cursor = helper.departmentsHelper.getDepartments();
        int[] to = new int[] { R.id.department_column_one, R.id.department_column_two, R.id.department_column_three, R.id.department_column_four};

        mAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.department_list, cursor, cursor.getColumnNames(), to);

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