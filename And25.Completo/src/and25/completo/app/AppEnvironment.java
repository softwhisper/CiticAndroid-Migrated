package and25.completo.app;

import and25.completo.shared.VolleySingleton;
import android.app.Application;

public class AppEnvironment extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		VolleySingleton.getInstance(getApplicationContext());
	}
}
