package es.sw.weather;

import org.apache.http.protocol.ResponseConnControl;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;

import es.sw.weather.model.MainData;
import es.sw.weather.model.Weather;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	public final String TAG = "MainActivity";
	private final String REQUESTTAG = "getWeatherLondon";
	PlaceholderFragment fragment;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment = new PlaceholderFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment).commit();
        }
        
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	fragment.setPbVisible(true);
    	JsonObjectRequest jobj = new JsonObjectRequest("http://api.openweathermap.org/data/2.5/weather?q=London,uk", null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObj) {
				//Esto haría un parseo directo pero como no coinciden mis clases con los datos recibidos lo hago en los constructores a mano
				/*Gson gson = new Gson();
				final Weather weather = gson.fromJson(jsonObj.toString(), Weather.class); //Declarada final para poder acceder a ella desde la segunda asyncTask al estar
*/				//declarado el método anónimo.
				
				fragment.setPbVisible(false);
				
				Weather weather = new Weather(jsonObj);
				MainData data = new MainData(jsonObj);
				data.city = weather;
				//Log.d(TAG, weather.city + weather.cityCod + weather.data.temp);
				
				if (fragment != null){
					fragment.paintWeather(weather, data);
				}
				
				//guardado en BBDD de forma síncrona
				Weather.save(getApplicationContext(), Weather.class, weather);
				Weather.save(getApplicationContext(), MainData.class, data);
				
				//guardado en BBDD de forma asíncrona usando SaveQuery que usa AsyncTask
				/*SaveQuery<Weather> saveWeather = new SaveQuery<Weather>(Weather.class, weather){
					@Override
					public void onSave(boolean success) {//Es como un listener que se ejecuta al terminar la acción, en este caso el guardado
						Log.d(TAG, "Terminada la primera query");
						SaveQuery<MainData> saveData = new SaveQuery<MainData>(MainData.class, weather.getMainData()){
							@Override
							public void onSave(boolean success) {
								Log.d(TAG, "Segunda query terminada");
							}
						};
						saveData.execute(getApplicationContext());
						Log.d(TAG, "Lanzada segunda query");
					}
				};
				saveWeather.execute(getApplicationContext());
				Log.d(TAG, "Lanzada la primera query");*/
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				fragment.setPbVisible(false);
				Log.e(TAG, "Error " + error.networkResponse);
			}
		});
    	
    	jobj.setTag(REQUESTTAG);
    	jobj.setShouldCache(false);
    	VolleySingleton.getInstance().getRequestQueue().add(jobj);
    	
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	
    	VolleySingleton.getInstance().getRequestQueue().cancelAll(REQUESTTAG);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
    	private TextView city;
    	private TextView codCity;
    	private TextView temp;
    	private TextView pressure;
    	private TextView tempMax;
    	private TextView tempMin;
    	private TextView humidity;
    	private ProgressBar progressBar;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            
            city = (TextView)rootView.findViewById(R.id.city_tv);
            codCity = (TextView)rootView.findViewById(R.id.cod_city);
            temp = (TextView)rootView.findViewById(R.id.temp_txt);
            pressure = (TextView)rootView.findViewById(R.id.pressure_txt);
            tempMax = (TextView)rootView.findViewById(R.id.temp_max_txt);
            tempMin = (TextView)rootView.findViewById(R.id.temp_min_txt);
            humidity = (TextView)rootView.findViewById(R.id.humidity_txt);
            progressBar = (ProgressBar)rootView.findViewById(R.id.load_data_pb);
            
            return rootView;
        }
        
        public void paintWeather(Weather weather, MainData data){
        	
        	
        	if (weather != null) {
        		city.setText(weather.city);
            	codCity.setText(String.valueOf(weather.cityCod));
			}
        	if (data != null) {
        		temp.setText(Double.toString(data.temp));
				pressure.setText(Double.toString(data.pressure));
				tempMax.setText(Double.toString(data.tempMax));
				tempMin.setText(Double.toString(data.tempMin));
				humidity.setText(Double.toString(data.humidity));
        	}
        }
        
        public void setPbVisible(boolean visible){
        	if (visible){
        		progressBar.setVisibility(ProgressBar.VISIBLE);
        	}
        	else if (visible == false){
        		progressBar.setVisibility(ProgressBar.GONE);
        	}
        }
    }

}
