package es.sw.weather.model;

import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import es.sw.weather.ActiveRecord;

@DatabaseTable
public class Weather extends ActiveRecord{
	public final String TAG = "Weather";
	
	@DatabaseField(id = true)
	public int id;
	
	@DatabaseField
	public int cityCod;
	
	@DatabaseField
	public String city;
	
	/*@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	public Collection<MainData> data;*/
	/*@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)*/
	/*@SerializedName ("main")
	public MainData data;*/
	
	public Weather(){
		
	}
	
	public Weather(JSONObject jsonObject){
		try {
			id = jsonObject.getInt("id");
			cityCod = jsonObject.getInt("cod");
			city = jsonObject.getString("name");
		} catch (JSONException e) {
			Log.e(TAG, "el parseo de json ha fallado");
			e.printStackTrace();
		}
	}
	
/*	public MainData getMainData(){
		return data;
		
	}*/
	
	/*public MainData getLastMainData() {
		return data;
	}*/
	
}

