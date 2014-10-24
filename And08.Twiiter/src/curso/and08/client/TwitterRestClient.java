package curso.and08.client;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;



public class TwitterRestClient {
	  private static final String BASE_URL = "https://api.twitter.com/1.1"; 
	  private static final String URL = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=pabloformoso";
	  
	  private static AsyncHttpClient client = new AsyncHttpClient();
	  private static String token = "";
	  private static String type = "";
	  
	  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		  
		  RequestParams requestParams = new RequestParams();
		  requestParams.put("grant_type", "client_credentials");

		  AsyncHttpClient httpClient = new AsyncHttpClient();
		  httpClient.addHeader("Authorization", "Basic " + Base64.encodeToString(("G5OVyTNL6AeueuC4Dj7Cw" + ":" + "gO2j5KdXq6yGsSCRc1oiBcZtLRjk1POc47AzgHofWE").getBytes(), Base64.NO_WRAP));
		  httpClient.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		  httpClient.post("https://api.twitter.com/oauth2/token", requestParams, new AsyncHttpResponseHandler() {
	          public void onSuccess(String response) {
	              try {
	                  JSONObject jsonObject = new JSONObject(response);
	                  
	                  TwitterRestClient.type = jsonObject.getString("token_type");
	                  TwitterRestClient.token = jsonObject.getString("access_token");	                  
	              } catch (JSONException e) {
	                  e.printStackTrace();
	              }
	          };

	          public void onFailure(Throwable error, String response) {
	              Log.e("", "error " + error.toString() + " response " + response);
	          };
	      });			  		 
		  client.addHeader("Authorization", TwitterRestClient.type + ":" + TwitterRestClient.token);
		  client.get(URL, params,responseHandler);
	  }

	  public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.post(getAbsoluteUrl(url), params, responseHandler);
	  }

	  private static String getAbsoluteUrl(String relativeUrl) {
	      return BASE_URL + relativeUrl;
	  }
}
