package curso.and15;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import curso.and15.R;

public class MainActivity extends Activity implements SurfaceHolder.Callback, Camera.ShutterCallback, Camera.PictureCallback {
	
	SurfaceView preview;
	Camera camera;
	Bitmap photo;
	ImageView imgPreview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		preview = (SurfaceView)findViewById(R.id.surfCamara);
		preview.getHolder().addCallback(this);
		
		imgPreview = (ImageView)findViewById(R.id.imgPreview);
		
		Button shutter = (Button)findViewById(R.id.button1);
		shutter.setOnClickListener( new OnClickListener() {			
			@Override
			public void onClick(View v) {
				camera.takePicture(MainActivity.this, null, null, MainActivity.this);
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		camera.release();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		camera.stopPreview();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onShutter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		if (camera != null) {
			Camera.Parameters params = camera.getParameters();
			List<Camera.Size> sizes = params.getSupportedPreviewSizes();
			Camera.Size selected = sizes.get(0);
			params.setPreviewSize(selected.width, selected.height);
			camera.setParameters(params);
			camera.setDisplayOrientation(0);
			
			
			try {
				camera.setPreviewDisplay(holder);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			camera.startPreview();
		} else {
			Toast.makeText(this, "No hay camara o hay algœn error.", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		camera = Camera.open();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d("", "Destroyed");
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 6;
		
		photo = BitmapFactory.decodeByteArray(data, 0, data.length, options);
		imgPreview.setImageBitmap(photo);
		camera.startPreview();
	}

}
