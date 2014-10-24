package curso.and07.network;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import curso.and07.listeners.CustomListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

public class CommRequest extends AsyncTask<Object, Boolean, byte[]> {

	private static final int TIMEOUT_READ = 0; // infinite
	private static final int TIMEOUT_CONNECT = 20 * 1000; //in (mills) seconds
	private static final int NUMBER_OF_MAX_RETRIES_IN_CASE_OF_TIMEOUT = 3;

	public static final String PREFIX_HTTPS =  "https://";
	public static final String PREFIX_HTTP =  "http://";

	private CustomListener listener;
	private Context context;
	private ProgressDialog progressDialog;

	private String soapContent;
	private String url;
	private boolean showLoading;
	private String waitingMessage;
	private Handler downloadNotifier;
	
	private HttpURLConnection con;
	
	private int status;
	private long startTime;

	@SuppressWarnings("unused")
	private CommRequest() {}

	/**
	 * the constructor start its thread itself.
	 * @param type
	 * @param dataParams
	 * @param _context
	 * @param showLoading
	 */
	public CommRequest(String _url, String _soapContent, CustomListener _listener, Context _context) {

		context = _context;

		listener = _listener;
		soapContent = _soapContent;
		url = _url;
		showLoading = false;

		execute();
	}
	
	/**
	 * the constructor start its thread itself.
	 * @param type
	 * @param dataParams
	 * @param _context
	 * @param showLoading
	 * @param handler
	 */
	public CommRequest(String _url, String _soapContent, CustomListener _listener, Context _context, Handler handler) {

		context = _context;

		listener = _listener;
		soapContent = _soapContent;
		url = _url;
		showLoading = false;
		downloadNotifier = handler;
		
		execute();
	}
	
	/**
	 * the constructor start its thread itself.
	 * @param type
	 * @param dataParams
	 * @param _context
	 * @param showLoading
	 */
	public CommRequest(String _url, String _soapContent, CustomListener _listener, Context _context, String _waitingMessage) {

		context = _context;

		listener = _listener;
		soapContent = _soapContent;
		url = _url;
		showLoading = true;
		waitingMessage = _waitingMessage;

		execute();
	}

	@Override
	protected void onPreExecute () {
		if (showLoading) {
			progressDialog = ProgressDialog.show(context
					, ""
					, waitingMessage
					, true
					, true);
		}
	}

	@Override
	protected void onProgressUpdate (Boolean... values) {
		//Has finished?
	}

	@Override
	protected byte[] doInBackground(Object... params) {
		Log.v("CommRequest", "CommManagerRequest.doInBackground()");
		
		byte[] result = null;

		try {
			boolean retry = true;
			boolean allright = false;
			SocketTimeoutException timeout = null;

			int retried = 0;
			
			while (retry) {
				try {
					result = doRequest(url, soapContent);
					allright = true;

				} catch (SocketTimeoutException e) {
					timeout = e;
				} 

				retried++;

				if (allright || (!allright && retried >= NUMBER_OF_MAX_RETRIES_IN_CASE_OF_TIMEOUT)) {
					retry = false;

					if (timeout != null) {
						throw timeout;
					}
				}
			}

		} catch (Exception e) {
			result = null;
			listener.setErrorMessage(e.getMessage());
		}

		if (result == null || result.length == 0) {
			listener.setErrorMessage("result is null");

			Log.e("", "ERROR: Empty response from server");
		}

		return result;
	}

	protected void onPostExecute (byte[] result) {
		if (showLoading && progressDialog != null) {
			progressDialog.dismiss();
		}
		
		if (downloadNotifier != null) {
			downloadNotifier.sendEmptyMessage(0);
		}
		
		listener.processIncomingData(result, status);		
	}	
	
	final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};
	
	/**
	 * Trust every server - dont check for any certificate
	 */
	private static void trustAllHosts() {
	        // Create a trust manager that does not validate certificate chains
	        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
	                
	        	public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	        		return new java.security.cert.X509Certificate[] {};
	            }

				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {
					// TODO Auto-generated method stub
					
				}

	        }};

	        // Install the all-trusting trust manager
	        try {
	                SSLContext sc = SSLContext.getInstance("TLS");
	                sc.init(null, trustAllCerts, new java.security.SecureRandom());
	                HttpsURLConnection
	                                .setDefaultSSLSocketFactory(sc.getSocketFactory());
	        } catch (Exception e) {
	                e.printStackTrace();
	        }
	}
	
	public byte[] doRequest(String urlLink, String content) throws SocketTimeoutException {
		Log.v("CommRequest", ".doRequest()");
		
		startTime = System.currentTimeMillis();
		
		byte[] result = null;
		
		
		URL url;
		InputStream is=null;
		
		try {
			url = new URL(urlLink);

			if (urlLink.startsWith(PREFIX_HTTP)) {
				con = (HttpURLConnection) url.openConnection();

			} else if (urlLink.startsWith(PREFIX_HTTPS)) {
				trustAllHosts();
				HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
				https.setHostnameVerifier(DO_NOT_VERIFY);
				con = https;
			} else {
				throw new MalformedURLException("CommRequest.doRequest(): Malformed url: " + urlLink);
			}

			con.setReadTimeout(TIMEOUT_READ);
			con.setConnectTimeout(TIMEOUT_CONNECT);

			if (content != null) {
				con.setRequestMethod("POST");	
			} else {
				con.setRequestMethod("GET");
			}
			
			con.setDoInput(true);                  
			
			if (content != null) {
				con.setDoOutput(true);				
				OutputStream  oStrm = null;

				if (urlLink.startsWith(PREFIX_HTTP)) {
					oStrm = con.getOutputStream();

				} else { //if (urlLink.startsWith(PREFIX_HTTPS)) {
					oStrm = ((HttpsURLConnection)con).getOutputStream();
				}

				oStrm.write(content.getBytes());
				oStrm.flush();//optional
			}
			
			// Start the query
			con.connect();
			
			status = con.getResponseCode();
			
			Log.v("CommRequest", "response code: " + status);
			Log.v("CommRequest", "Cookie: " + con.getRequestProperty("Cookie"));
			int i = 1;
			
			String header;
			
		    while ((header = con.getHeaderField(i)) != null) {
		    	String key = con.getHeaderFieldKey(i);
		        Log.v("CommRequest",  ((key==null) ? "" : key + ": ") + header);
		        
		        i++;
		    }
		    

			switch (con.getResponseCode()) {
			case 200:
				is = con.getInputStream();
				result = convertInputStreamToByteArray(is);
				break;

			default:
				is = con.getErrorStream();	
				result = convertInputStreamToByteArray(is);
				break;
			}

		} catch (SocketTimeoutException e) {
			Log.d("CommRequest", "SocketTimeoutException");
			throw e;
		} catch (IOException e) {
			//handle the exception !
			Log.d("CommRequest", "CommRequest.doRequest(): " + e.getMessage());
			e.printStackTrace();
			
		}

		long duration = System.currentTimeMillis() - startTime;
		
		Log.i("CommRequest", "Request took " + (duration / 1000) + " secs for url: " + urlLink);

		return result;
	}

	private String convertStreamToString(InputStream is) throws IOException {
		Log.v("CommRequest", ".convertStreamToString()");
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {        
			return "";
		}
	}
	
	private byte[] convertInputStreamToByteArray(InputStream is) throws IOException {
		Log.v("CommRequest", ".convertInputStreamToByteArray()");
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();

		return buffer.toByteArray();

	}


}
