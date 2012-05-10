package dk.aau.cs.giraf.wombat;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.Adapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import dk.aau.cs.giraf.TimerLib.Child;
import dk.aau.cs.giraf.TimerLib.Guardian;

public class WDialog extends Dialog{
	
	private int layoutId = 1337;
	private int editTextLayoutId = 1338;
	
	public WDialog(Context context) {
		super(context, R.style.WombatStyle_Dialog);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.profile_list_dialog);

		Drawable d = context.getResources().getDrawable(R.drawable.wdialog_background);
		d.setColorFilter(Guardian.backgroundColor, PorterDuff.Mode.OVERLAY);
		this.getWindow().setBackgroundDrawable(d);
	}
	
	public WDialog(Context context, String title){
		this(context);
		this.setTitle(title);
	}
	
	public WDialog(Context context, int title){
		this(context, context.getResources().getString(title));
	}
	
	public void setTitle(String title){
		TextView tv = (TextView)this.findViewById(R.id.profile_list_dialog_title);
		tv.setText(title);
		tv.setPadding(30, 20, 30, 0);
	}
	
	protected void setAdapter(ArrayAdapter<?> adapter) {
		ListView lv = (ListView) this.findViewById(R.id.profile_list_dialog_listview);
		lv.setAdapter(adapter);
	}
	
	public void setOnItemClickListener(OnItemClickListener onItemClickListener){
		ListView lv = (ListView) this.findViewById(R.id.profile_list_dialog_listview);
		lv.setOnItemClickListener(onItemClickListener);
	}

	public void addButton(int textResource, int buttonId, View.OnClickListener listener){
		this.addButton(this.getContext().getResources().getString(textResource), buttonId, listener);
	}
	
	public void addButton(int textResource, int buttonId){
		this.addButton(this.getContext().getResources().getString(textResource), buttonId);
	}
	
	public void addButton(String text, int buttonId, View.OnClickListener listener){
		this.addButton(text, buttonId);
		this.setButtonOnClickListener(buttonId, listener);
	}
	
	public void addButton(String text, int buttonId){
		LinearLayout buttonLayout = (LinearLayout) this.findViewById(layoutId);
		if(buttonLayout == null){
			LinearLayout ll = (LinearLayout) this.findViewById(R.id.profile_list_dialog);
			buttonLayout = new LinearLayout(this.getContext());
			buttonLayout.setId(layoutId);
			buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
			buttonLayout.setGravity(Gravity.CENTER);
			buttonLayout.setPadding(0, 0, 0, 20);
			ll.addView(buttonLayout);
		}
		this.mAddButton(text, buttonId, buttonLayout);
	}
	
	private void mAddButton(String text, int buttonId, LinearLayout buttonLayout){
		Button b = new Button(this.getContext());
		b.setId(buttonId);
		b.setBackgroundResource(R.drawable.list);
		b.setPadding(30, 20, 30, 20);
		b.setText(text);
		buttonLayout.addView(b);
	}
	
	public void setButtonOnClickListener(int buttonId, View.OnClickListener listener){
		Button b = (Button)this.findViewById(buttonId);
		b.setOnClickListener(listener);
	}

	public void addEditText(String text, int editTextId) {
		LinearLayout editTextLayout = (LinearLayout) this.findViewById(editTextLayoutId);
		if(editTextLayout == null){
			LinearLayout ll = (LinearLayout) this.findViewById(R.id.profile_list_dialog);
			ListView lv = (ListView) this.findViewById(R.id.profile_list_dialog_listview);
			lv.setLayoutParams(new LinearLayout.LayoutParams(0,0));
			
			editTextLayout = new LinearLayout(this.getContext());
			editTextLayout.setId(editTextLayoutId);
			editTextLayout.setOrientation(LinearLayout.VERTICAL);
			editTextLayout.setGravity(Gravity.CENTER);
			editTextLayout.setPadding(0, 0, 0, 20);
			
			ll.addView(editTextLayout);
		}
		
		EditText et = new EditText(this.getContext());
		et.setId(editTextId);
		et.setText(text);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(20, 20, 20, 20);
		et.setLayoutParams(params);
		editTextLayout.addView(et);
		editTextLayout.setLayoutParams(new LinearLayout.LayoutParams(300, LayoutParams.WRAP_CONTENT));
		
	}
	
	public String getEditTextText(int editTextId){
		EditText et = (EditText) this.findViewById(editTextId);
		return et.getText().toString();
	}
}
