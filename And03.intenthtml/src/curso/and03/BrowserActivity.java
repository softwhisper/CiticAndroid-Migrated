package curso.and03;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

/**
 * 
 * @author pablo
 *
 */
public class BrowserActivity extends Activity {

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browser);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		Intent intent = getIntent();
		TextView txtView = (TextView)findViewById(R.id.txtHtml);
		String action = intent.getAction();
		
		// Asegurar la acci—n
		if (!action.equals(Intent.ACTION_VIEW)) 
			throw new RuntimeException("Acci—n no reconocida");
		
		Uri data = intent.getData();
		URL url;
		
		try {
			
			url = new URL(data.getScheme(), data.getHost(), data.getPath());
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			
			while ((line = rd.readLine()) != null) {
				txtView.append(line);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.browser, menu);
		return true;
	}

}
