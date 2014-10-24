package curso.and07.listeners;

import java.io.IOException;
import java.io.InputStream;

import curso.and07.shared.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Handler;
import android.util.Log;

public abstract class CustomListener {

	protected Context context = null;
	protected Handler handler = null;
	private String errorMessage = null;
	
	public CustomListener(Context _context, Handler _handler) {
		context = _context;
		handler = _handler;
	}

	protected byte[] getMockedResponse(String fileName) throws IOException {
		AssetManager assetManager = context.getAssets();
		InputStream istr = assetManager.open(fileName);
		return Utils.converInputStreamToByteArray(istr);
	}
	
	public void setErrorMessage(String _error) {
		errorMessage = _error;
		if (_error != null) {
			Log.e("", errorMessage);
		}
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public boolean isError() {
		return errorMessage != null;
	}
	
	public SharedPreferences getPrefs() {
		return Utils.getPrefs(context);
	}

	public abstract void processIncomingData(byte[] data, int status);
	
}
