package curso.and21;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartServiceReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("START_SERVICE", "onReceive()");
	    Intent service = new Intent(context, LocalWordService.class);
	    context.startService(service);
	}

}
