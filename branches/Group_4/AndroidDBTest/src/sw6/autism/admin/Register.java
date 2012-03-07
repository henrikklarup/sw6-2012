package sw6.autism.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends Activity implements OnClickListener{

	EditText etUsername, etPassword, etRepassword;
	Button bSubmit, bCancel;
	Intent direct;
	private DataSource datasource;

	public void initialize() {
		etUsername = (EditText) findViewById(R.id.etRegisterUsername);
		etPassword = (EditText) findViewById(R.id.etRegisterPassword);
		etRepassword = (EditText) findViewById(R.id.etRegisterRePassword);
		bSubmit = (Button) findViewById(R.id.bRegisterSubmit);
		bSubmit.setOnClickListener(this);
		bCancel = (Button) findViewById(R.id.bRegisterCancel);
		bCancel.setOnClickListener(this);
		datasource = new DataSource(this);
		datasource.open();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		initialize();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bRegisterSubmit:
			String sUsername = etUsername.getText().toString();
			String sPassword = etPassword.getText().toString();
			String sRePassword = etRepassword.getText().toString();
			AlertDialog ad = new AlertDialog.Builder(this).create();

			if (!sUsername.equals("") && !sPassword.equals("") && sPassword.equals(sRePassword)) {
				boolean nameNotTaken;
				nameNotTaken = datasource.createUser(sUsername, sPassword);

				if (nameNotTaken) {
				ad.setTitle("Register");
				ad.setMessage("User has been registered");
				ad.setButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						direct = new Intent("sw6.autism.admin.LOGIN");
						startActivity(direct);
					}
				});
				ad.show();
				} else {
					ad.setTitle("Username");
					ad.setMessage("User with that name already exists");
					ad.setButton("OK", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					ad.show();
				}
			} else {
				ad.setTitle("Register FAIL");
				ad.setMessage("Some fields are insufficient");
				ad.setButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				ad.show();
			}
			break;
			
		case R.id.bRegisterCancel:
			direct = new Intent("sw6.autism.admin.LOGIN");
			finish();
			startActivity(direct);
			break;
		}
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}
}
