package dk.aau.cs.giraf.oasis.app;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

public class ProfilesFrag extends ExpandableListFragment {

	private Helper helper;
	private List<Profile> list;
	private ChildListAdapter adapter;
	private Button bAdd;
	private String tmpPath;
	private EditText tName, tPhone;
	private ImageView iProfileImg;
	private Spinner spinDep, spinRole;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.children_view, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		helper = new Helper(getActivity().getApplicationContext());

		updateList();

		bAdd = (Button) getView().findViewById(R.id.bAddProfile);
		bAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setCancelable(true);
				builder.setTitle(R.string.addProfile);
				LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = mInflater.inflate(R.layout.createprofile_view, null, false);
				tName = (EditText) view.findViewById(R.id.etCreateProfileName);
				tPhone = (EditText) view.findViewById(R.id.etCreateProfilePhone);
				iProfileImg = (ImageView) view.findViewById(R.id.iCreateProfileImg);
				iProfileImg.setImageResource(R.drawable.no_profile_pic);
//				spinDep = (Spinner) getView().findViewById(R.id.spinnerDep);
//				List<Department> dList = helper.departmentsHelper.getDepartments();
//				List<String> dString = new ArrayList<String>();
//				for (Department d : dList) {
//					dString.add(d.getName());
//				}
//				ArrayAdapter<String> depAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dString);
//				depAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//				spinDep.setAdapter(depAdapter);
//				spinRole = (Spinner) getView().findViewById(R.id.spinnerRole);
//				List<String> rString = new ArrayList<String>();
//				rString.add("Pædagog");
//				rString.add("Barn");
//				ArrayAdapter<String> roleAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, rString);
//				roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//				spinRole.setAdapter(roleAdapter);
				Button bImg = (Button) view.findViewById(R.id.bCreateProfileImg);
				bImg.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						List<Media> valueList = helper.mediaHelper.getMedia();
						List<String> items = new ArrayList<String>();
						for (Media m : valueList) {
							items.add(m.getMPath());
						}

						final tmpListAdapter adapter = new tmpListAdapter(getActivity(), R.layout.children_list_childitem, items);

						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
						builder.setTitle("Vælg et Profil Billede");
						builder.setAdapter(adapter,
								new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int item) {
								tmpPath = adapter.getItem(item);
								if (tmpPath != null) {
									Bitmap bm = BitmapFactory.decodeFile(tmpPath);
									iProfileImg.setImageBitmap(bm);
								} else {
									iProfileImg.setImageResource(R.drawable.no_profile_pic);
								}

								dialog.dismiss();
							}
						});
						builder.setNegativeButton("Cancel", null);
						AlertDialog alert = builder.create();
						alert.show();
					}
				});

				builder.setView(view);
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String newName = tName.getText().toString();
						String tmp[] = newName.split(" ");
						String first = tmp[0];
						String middle = "";
						for (int i = 1; i < tmp.length-1; i++) {
							middle += tmp[i] + " ";
						}
						if (middle.equals("")) {
							middle = null;
						}
						String last = tmp[tmp.length-1];
						String newPhone = tPhone.getText().toString();
						Profile newProfile = new Profile();
						newProfile.setFirstname(first);
						newProfile.setSurname(last);
						newProfile.setMiddlename(middle);
						newProfile.setPhone(Long.parseLong(newPhone));
						if (spinRole.getSelectedItem().equals("Barn")) {
							newProfile.setPRole(Profile.pRoles.CHILD.ordinal());
						} else {
							newProfile.setPRole(Profile.pRoles.GUARDIAN.ordinal());
						}
						newProfile.setId(helper.profilesHelper.insertProfile(newProfile));
						dialog.dismiss();
					}
				});
				builder.setNegativeButton("Afbryd", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}

	private void updateList() {
		adapter = new ChildListAdapter(getActivity().getApplicationContext());

		list = helper.profilesHelper.getProfiles();

		List<Department> depList = helper.departmentsHelper.getDepartments();

		for(Department d : depList) {
			ArrayList<Profile> result = new ArrayList<Profile>();
			List<Profile> pList = helper.profilesHelper.getProfilesByDepartment(d);		

			for(Profile p : pList) {
				if (!list.contains(p)) {
					result.add(p);
				}
			}
			if (!result.isEmpty()) {
				adapter.AddGroup(d, result);
			}
		}

		setListAdapter(adapter);
	}
}
