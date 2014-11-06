package curso.and21;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScheduleReceiver extends BroadcastReceiver {

	// Restart service every 30 seconds
	private static final long REPEAT_TIME = 1000 * 5;

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("SCHEDULE", "onReceive()");
		AlarmManager service = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		
		Intent i = new Intent(context, StartServiceReceiver.class);
		PendingIntent pending = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
		
		Calendar cal = Calendar.getInstance();
		
		// Start 30 seconds after boot completed
		cal.add(Calendar.SECOND, 5);

		// Fetch every 30 seconds
		// InexactRepeating allows Android to optimize the energy consumption
		//service.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), REPEAT_TIME, pending);

		service.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), REPEAT_TIME, pending);

	}
}
