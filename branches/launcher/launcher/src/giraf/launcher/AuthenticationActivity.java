package giraf.launcher;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.Result;
import com.google.zxing.client.android.CaptureActivity;

public class AuthenticationActivity extends CaptureActivity {

	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.authentication);
	        
	        final ImageView instruct = (ImageView) findViewById(R.id.animation);
	        instruct.setBackgroundResource(R.animator.instruct_ani);
	        instruct.post(new Runnable(){
	        	@Override
	        	public void run(){
	        		AnimationDrawable anim = (AnimationDrawable) instruct.getBackground();
	        		anim.start();
	        	}
	        });
	    }
	    
	    @Override
	    public void handleDecode(Result rawResult, Bitmap barcode)
	    {
	    ProgressDialog dialog = ProgressDialog.show(AuthenticationActivity.this, "", rawResult.getText(), true, true);
	       //Toast.makeText(this.getApplicationContext(), "Scanned code "+ rawResult.getText(), Toast.LENGTH_LONG).show();
	    this.getHandler().sendEmptyMessageDelayed(R.id.restart_preview, 500);
	    }

}
