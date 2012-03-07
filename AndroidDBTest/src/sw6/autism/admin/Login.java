package sw6.autism.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity implements OnClickListener {

	public static final String Preferences = "AdminCookie";
	private SharedPreferences settings;
	Button bLogin, bRegister;
	EditText etUsername, etPassword;
	Intent direct;
	private DataSource datasource;

	public void initialize() {
		bLogin = (Button) findViewById(R.id.bLoginLogin);
		bLogin.setOnClickListener(this);
		bRegister = (Button) findViewById(R.id.bLoginRegister);
		bRegister.setOnClickListener(this);
		etUsername = (EditText) findViewById(R.id.etLoginUsername);
		etUsername.setText(settings.getString("Username", ""));
		etPassword = (EditText) findViewById(R.id.etLoginPassword);
		etPassword.setText(settings.getString("Password", ""));
		datasource = new DataSource(this);
		datasource.open();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		settings = getSharedPreferences("Preferences", 0);
		setContentView(R.layout.login);
		initialize();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bLoginLogin:
			String sUsername = etUsername.getText().toString();
			String sPassword = etPassword.getText().toString();

			User user;
			user = datasource.findUserByUsername(sUsername);
			
			if (user != null && user.getPassword().equals(sPassword)) {
				
				//Evt smæk dette ind i en metode i en helper class
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("Username", sUsername);
				editor.putString("Password", sPassword);
				editor.commit();
				
				direct = new Intent("sw6.autism.admin.APPMENULIST");
				startActivity(direct);
			} else {
				AlertDialog ad = new AlertDialog.Builder(this).create();
				ad.setTitle("Login FAIL!");
				ad.setMessage("Bad Username or Password!");
				ad.setButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				ad.show();
			}
			break;

		case R.id.bLoginRegister:
			direct = new Intent("sw6.autism.admin.REGISTER");
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