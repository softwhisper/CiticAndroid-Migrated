package es.sw.weather.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import es.sw.weather.ActiveRecord;

@DatabaseTable
public class MainData extends ActiveRecord {
	public final String TAG = "MainData";
	
	@DatabaseField(generatedId = true)
	public int id;
	
	//Esto hace de clave ajena, aunque realmente es un objeto entero de tipo weather
	@DatabaseField(foreign = true, canBeNull = false)
	public Weather city;
	
	@DatabaseField
	public double temp;
	
	@DatabaseField
	public double pressure;
	
	@DatabaseField
	public double tempMin;
	
	@DatabaseField
	public double tempMax;
	
	@DatabaseField
	public double humidity;
	
	public MainData(){
		
	}
	
	public MainData(JSONObject jsonObject) {
//		id = 1;
		try{
		temp = jsonObject.getDouble("temp");
		pressure = jsonObject.getDouble("pressure");
		tempMin = jsonObject.getDouble("temp_min");
		tempMax = jsonObject.getDouble("temp_max");
		humidity = jsonObject.getDouble("humidity");
		}catch(JSONException e){
			Log.e(TAG, "Parsing failed");
		}
	}
	/*"temp": 282.85,
        "pressure": 1010,
        "temp_min": 279.26,
        "temp_max": 287.04,
        "humidity": 92*/
	
}