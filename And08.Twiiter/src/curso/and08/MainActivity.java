package curso.and08;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.*;

import com.loopj.android.http.JsonHttpResponseHandler;

import curso.and08.R;
import curso.and08.client.TwitterRestClient;

import android.os.Bundle;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {
	private final static String TAG = "MAIN";
	private ProgressDialog dialog;
	
	private ArrayList<String> tweets = new ArrayList<String>();
	Context context = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		
		try {
			getPublicTimeline();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
    public void getPublicTimeline() throws JSONException {
    	Log.d(TAG, "Send Request");
        TwitterRestClient.get("statuses/user_timeline/pabloformoso.json", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray timeline) {
            	Log.d(TAG, "Success");
                try {
	                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, 
																			android.R.layout.simple_list_item_1, 
																			android.R.id.text1);
	                
	                for (int i = 0; i < timeline.length(); i++) {
	                	JSONObject tw = timeline.getJSONObject(i);
	                	tweets.add(tw.getString("text"));
	                	adapter.add(tw.getString("text"));
	                }					
	               
	                setListAdapter(adapter);
	                
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            
            @Override
            public void onStart() {
            	super.onStart();
            	dialog = ProgressDialog.show(context
    					, ""
    					, "Cargando Timeline"
    					, true
    					, true);
            }
            
            @Override
            public void onFinish() {
            	super.onFinish();
            	dialog.dismiss();
            }
            
            @Override
            public void onFailure(Throwable arg0) {
            	dialog.dismiss();
            	Log.d(TAG, "FAIL!!!");
            }
        });
    }	

}
