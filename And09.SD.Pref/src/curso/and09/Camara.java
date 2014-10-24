package curso.and09;

import java.util.List;

import curso.and09.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Camara extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camara);

		//sacamos foto
		Button btn = (Button) findViewById(R.id.btnSacarFoto);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				sacarFoto(1);
			}
		});

		//click en boton de compartir
		btn = (Button) findViewById(R.id.btnCompartir);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// si hay imagen, la compartimos
				if (mImageBitmap != null && saved.length() > 0) {
					Intent sharingIntent = new Intent(Intent.ACTION_SEND);
					Uri screenshotUri = Uri.parse(saved);
					sharingIntent.setType("image/png");
					sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
					startActivity(Intent.createChooser(sharingIntent,
							"Compartir usando..."));
				} else {
					Toast.makeText(Camara.this, "No hay foto...",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	// queremos sacar una foto
	private void sacarFoto(int actionCode) {
		if (isIntentAvailable(this, MediaStore.ACTION_IMAGE_CAPTURE)) {
			Intent takePictureIntent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(takePictureIntent, actionCode);
		} else {
			Toast.makeText(this, "No hay c‡mara disponible...",
					Toast.LENGTH_LONG).show();
		}
	}

	// comprobamos si tenemos gestion de camara
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	// imagen sacada: la gestionamos.
	Bitmap mImageBitmap = null;
	String saved = "";

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				// use imageUri here to access the image
				Bundle extras = data.getExtras();
				mImageBitmap = (Bitmap) extras.get("data");
				ImageView img = (ImageView) findViewById(R.id.imgCamara);
				img.setImageBitmap(mImageBitmap);

				saved = Images.Media.insertImage(this.getContentResolver(),
						mImageBitmap, "Prueba", "Descripci—n prueba");
				// Uri sdCardUri = Uri.parse("file://" +
				// Environment.getExternalStorageDirectory());

			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "No se ha sacado la foto",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "No se ha sacado la foto",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

}
