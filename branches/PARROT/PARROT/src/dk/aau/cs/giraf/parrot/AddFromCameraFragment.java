/*
 * Copyright (C) 2011 Mette Bank, Rikke Jensen, Kenneth Brodersen, Thomas Pedersen
 * 
 * This file is part of digiPECS.
 * 
 * digiPECS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * digiPECS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with digiPECS.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Note: Edits Made by Jakob Jørgensen, SW601f12 in continuation of DigiPecs under the name Parrot.
 */

package dk.aau.cs.giraf.parrot;

import dk.aau.cs.giraf.parrot.R;
import java.io.IOException;
import dk.aau.cs.giraf.parrot.digipecs.ImageModificationListener;
import dk.aau.cs.giraf.parrot.digipecs.ImageModifyView;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This activity allows the user to capture images using the hardware device's integrated camera. 
 * This class offers the functionality to capture and save images using the integrated camera. It is possible to modify the captured image, by adding sound and text, before saving it
 * to the pecs book. 
 */
public class AddFromCameraFragment extends Fragment implements OnClickListener 
{

	//The all fragments 
	private Activity parrent;
	
	public static final int REQUEST_CODE = 333;
	//android Camera control class
	private Camera mCamera;
	//This object is used for defining camera parameters
	private Parameters mParameters; 
	
	//The surfaceview is a layoutview that shows a camera preview image
	private SurfaceView mCameraPreview;
	
	//Surfaceholde is a holder that manages this preview (pff, better description?) 
	private SurfaceHolder mSurfaceHolder; 
	
	//The ImageModifierView (also used in ManageImagesActivity), handles all modifications to a captured image, including adding sound/text and the actual addition to the pecs book
	private ImageModifyView mImageView; 
	private Button mTakePictureButton; 
	
	//This private object is used to incapslate 
	private CameraHandler mHandler;
	
	/*
	 * 
	 */
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		this.parrent = activity;
	}
	
	/**
	 * Standard android onCreate method
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parrent.setContentView(R.layout.addimagefromcameramain); 
		initialise(); 
	}
	/**
	 * This method initializes all private members
	 */
	private void initialise()
	{
		mImageView=(ImageModifyView)findViewById(R.id.addImageCompoundView); 
		mImageView.setImageMode(ImageModifyView.IMAGE_MODE_ADD); 
		mImageView.setActivity(parrent); 
		mImageView.setImageModificationListener(new ImageModificationHandler()); 
		mTakePictureButton=(Button)findViewById(R.id.addImageTakePictureButton); 
		mTakePictureButton.setOnClickListener(this); 
		mHandler=new CameraHandler(); 
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.backgroundwhite); 
		mImageView.setPreviewImage(bitmap);  
	}

	/**
	 * This class encapsulates functionality related to configuring the camera and capturing images
	 */
	private class CameraHandler implements Callback {
		/**
		 * This constructor initializes the camera and sets all parameters needed. 
		 */
		public CameraHandler()
		{
			mCamera = Camera.open(); 
			mParameters = mCamera.getParameters(); 
			//Sets focus and blitz mode to auto
			mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO); 
			mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO); 
			//Sets picture quality, should this be better
			mParameters.setJpegQuality(50); 
			//And the thumbnail quality
			mParameters.setJpegThumbnailQuality(75); 
			mParameters.setPictureSize(1024, 1024);
			mParameters.setJpegThumbnailSize(256, 256); 
			mParameters.setPreviewSize(500, 500); 
			mCamera.setParameters(mParameters);
			mCameraPreview = (SurfaceView)findViewById(R.id.cameraSurfaceView); 
			mSurfaceHolder = mCameraPreview.getHolder(); 
			
			//The surfaceholder requires somw callback methods. These are explained later
			mSurfaceHolder.addCallback(this); 
			mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); 
			mSurfaceHolder.setFixedSize(500, 500); 
			
		}

		/**
		 * One of these callbacks. This is not used in the current implementation
		 */
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
		}

		/**
		 * This method is invoked when the surfaceHolder has been initialized. This happens when the holder is ready for obtaining a preview from the camera
		 */
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				mCamera.setPreviewDisplay(mSurfaceHolder);
				mCamera.startPreview(); 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * This method is invoked when a surface holder is destroyed. 
		 * It just stops the preview and releases the camera resource. 
		 */
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			mCamera.stopPreview(); 
			mCamera.release(); 
		}

		/**
		 * This mehtod can be invoked to capture an image. This method is called when the "take picture" button is pressed
		 */
		public void takePicture() {
			//The camera is asked to take a picture
			//The .takePicture() takes three listeners as argumetns. Only one of these is implemente here. 
			mCamera.takePicture(null,null, pictureCallBackJpeg);
			mTakePictureButton.setEnabled(false); 
		}
		/**
		 * This private class is created to handle callbacks 
		*/
		PictureCallback pictureCallBackJpeg = new PictureCallback() {
			/**
			 * This method is invoked every time a picture has been captured and processed by the camera resource. The method is called with the compressed jpeg image as argument.  
			 */
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				// Convert byte array to bitmap
				Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length); 
				
				// Sets the preview image on the mImageView. This preview image is shown the the right
				mImageView.setPreviewImage(image); 
			}
		};
	}

	/**
	 * This is a simple container class handling callbacks from the mImageView
	 */
	private class ImageModificationHandler implements ImageModificationListener {
		/**
		 * This callback is invoked every time an image has been saved. 
		 */
		@Override
		public void ImageSaved() {
			//Set the result to ok and close the activity
			setResult(RESULT_OK); 
			finish(); 
		}

		/**
		 * Callback method which is invoked every time 
		 */
		@Override
		public void ImageDeleted() {
			//Not handled in this class
		}
		
	}
	/**
	 * This method handles all onClick events issued by buttons on the user interface. 
	 */
	@Override
	public void onClick(View v) {
		//The 
		mHandler.takePicture(); 
	}
}
