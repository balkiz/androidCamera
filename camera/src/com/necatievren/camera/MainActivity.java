package com.necatievren.camera;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.SurfaceView;
import android.widget.Button;
public class MainActivity extends Activity implements SurfaceHolder.Callback {

	private Camera mCamera;
	private Button fCekBtn;
	private SurfaceView srfView;
	private PackageManager pm;
	private Boolean hasCamera = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		fCekBtn = (Button) findViewById(com.necatievren.camera.R.id.fCekBtn);
		srfView = (SurfaceView) findViewById(com.necatievren.camera.R.id.surfaceView);
		
		pm = this.getPackageManager();
		
		if(pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
			hasCamera = true;
			SurfaceHolder holder = srfView.getHolder();
			holder.addCallback(this);
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            holder.setFixedSize(600, 400);
            
		}else{
			Log.v("uyarý", " *** kamera mevcut deðil ! ");
		}
		
		fCekBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(hasCamera){
					takePicture();
				}else{
					Log.v("uyarý", " *** kamera mevcut deðil fotoðraf çekilemez");
				}
	
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	 /***
     * 
     * SurfaceHolder.Callback
     * 
     */
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		 mCamera= Camera.open();
	     mCamera.setDisplayOrientation(90);
	     try {
	            mCamera.setPreviewDisplay(holder);
	            mCamera.startPreview();
	     } catch (IOException e) {
	            e.printStackTrace();
	     }
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		 mCamera.stopPreview();
	     mCamera.release();
	}
	 /***
	  * 
     * 
     * SurfaceHolder.Callback
     * 
     */
	
	
	
	
	/**
	 * 
	 *Take Picture 
	 * 
	 */
	private void takePicture(){
		mCamera.takePicture(_shutterCallBack, _rawCallBack, _jpgCallBack);
	}
	
	
	ShutterCallback _shutterCallBack = new ShutterCallback(){
		@Override
		public void onShutter() {
			//deklansör kapandýðýnda yapýlacak iþlem
		}
	};
	
	
	PictureCallback _rawCallBack=new PictureCallback(){
		@Override
		public void onPictureTaken(byte[] arg0, Camera arg1) {
			//görüntünün raw verisiyle yapýlacak iþlem
		}
	};
	
	
	PictureCallback _jpgCallBack = new PictureCallback(){
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			FileOutputStream foutStream = null;
			try {
				foutStream = new FileOutputStream("/sdcard/TestGorsel.jpg");
				foutStream.write(data);
				foutStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	};
	
	/**
	 * 
	 *Take Picture 
	 * 
	 */
}
