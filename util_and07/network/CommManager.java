package curso.and07.network;

import curso.and07.listeners.HeartQuakesListener;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

/**
 * 
 * @author pablo
 *
 */
public class CommManager {
	
	private final static String TAG = "CommManager";
	
	public static void doReadHeartQuakes(Context context, Handler handler) {
		Log.d(TAG, "doReadHeartQuakes");
		
		StringBuilder urlString = new StringBuilder("http://earthquake.usgs.gov/eqcenter/catalogs/1day-M2.5.xml");
		new CommRequest(urlString.toString(), 
				null, 
				new HeartQuakesListener(context, handler), 
				context, 
				"Cargando Terremotos");
	}
}
