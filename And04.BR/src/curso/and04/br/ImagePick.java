package curso.and04.br;

import java.io.InputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class ImagePick extends Activity {
	
	private static final int REQUEST_IMAGE = 1;
	private Bitmap bitmap;
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_pick);
		
		// imageView = (ImageView)findViewById(R.id.imgPicked);
	}

	
	public void pickImage(View view) {
		/*
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, REQUEST_IMAGE);
		*/
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		InputStream stream = null;
		if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK) {
			try {
				if (bitmap != null) 
					bitmap.recycle();
				
				stream = getContentResolver().openInputStream(data.getData());
				bitmap = BitmapFactory.decodeStream(stream);
				
				imageView.setImageBitmap(bitmap);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		}
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_pick, menu);
		return true;
	}

}
