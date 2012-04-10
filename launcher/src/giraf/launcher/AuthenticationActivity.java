package giraf.launcher;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.android.CaptureActivity;

public class AuthenticationActivity extends CaptureActivity {

	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.authentication);
	    }
	    
	    @Override
	    public void handleDecode(Result rawResult, Bitmap barcode)
	    {
	       Toast.makeText(this.getApplicationContext(), "Scanned code "+ rawResult.getText(), Toast.LENGTH_LONG).show();
	    }

}
