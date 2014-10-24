package curso.and07.shared;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;


public class Utils {

	public static int getScreenWidth(Activity activity) {
		WindowManager w = activity.getWindowManager(); 
		Display display = w.getDefaultDisplay(); 
		int width = display.getWidth();
		return width;
	}

	public static int getScreenHeight(Activity activity) {
		WindowManager w = activity.getWindowManager(); 
		Display display = w.getDefaultDisplay(); 
		int height = display.getHeight();
		return height;
	}
	
	public static void showToastLong(String message, Context context) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
	
	public static void showToastShort(String message, Context context) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	public static byte[] converInputStreamToByteArray(InputStream is) throws IOException {

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();

		return buffer.toByteArray();

	}
	
	public static SharedPreferences getPrefs(Context context) {
		return context.getSharedPreferences("curso", Context.MODE_PRIVATE);
	}
	
	public static String convertUrlToFileName(String url) {
		String fileName = url.replace("://", ".");
		fileName = fileName.replace("/", ".");
		
		return fileName;
	}

}

