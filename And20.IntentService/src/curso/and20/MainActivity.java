package curso.and20;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import curso.and20.DownloadService;
import curso.and20.R;

public class MainActivity extends Activity {
	
	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			Object path = message.obj;
			
			if (message.arg1 == RESULT_OK && path != null) {
				Toast.makeText(MainActivity.this, "Downloaded" + path.toString(), Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(MainActivity.this, "Download failed.", Toast.LENGTH_LONG).show();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btnDowload = (Button)findViewById(R.id.button1);
		btnDowload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    Intent intent = new Intent(MainActivity.this, DownloadService.class);
			    
			    // Create a new Messenger for the communication back
			    Messenger messenger = new Messenger(handler);
			    intent.putExtra("MESSENGER", messenger);
			    intent.setData(Uri.parse("http://www.vogella.com/articles/AndroidServices/article.html"));
			    intent.putExtra("urlpath", "http://www.vogella.com/articles/AndroidServices/article.html");
			    startService(intent);				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
