package com.androidaz.scanner;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.android.CaptureActivity;

public class ScannerActivity extends CaptureActivity 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    @Override 
    public void handleDecode(Result rawResult, Bitmap barcode) 
    {
    	//Toast.makeText(this.getApplicationContext(), "Scanned code " + rawResult.getText(), Toast.LENGTH_LONG);
    	ProgressDialog dialog = ProgressDialog.show(ScannerActivity.this, "", rawResult.getText(), true, true);
    	//handler.sendEmptyMessageDelayed(R.id.restart_preview, CaptureActivity.BULK_MODE_SCAN_DELAY_MS);
    }
}