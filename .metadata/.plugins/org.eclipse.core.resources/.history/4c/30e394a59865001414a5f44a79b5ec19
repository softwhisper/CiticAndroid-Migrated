package curso.and14;

import java.util.Timer;
import java.util.TimerTask;

import curso.and14.R;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private SensorManager sensorManager;
	private TextView temperatureTextView;
	private TextView pressureTextView;
	private TextView lightTextView;
	
	private float currentTemp = Float.NaN;
	private float currentPres = Float.NaN;
	private float currentLigh = Float.NaN;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		temperatureTextView = (TextView)findViewById(R.id.txtTemperature);
		pressureTextView = (TextView)findViewById(R.id.txtPressure);
		lightTextView = (TextView)findViewById(R.id.txtLight);
		
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		
		Timer updateTimer = new Timer("weatherUpdate");
		updateTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				updateUI();
			}
		}, 0, 1000);
	}
	
	private final SensorEventListener tempSensorListener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			currentTemp = event.values[0];
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	};
	
	private final SensorEventListener pressureSensorListener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			currentPres = event.values[0];
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) { }
	};
	
	private final SensorEventListener lightSensorListener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			currentLigh = event.values[0];
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) { }
	};	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		if (lightSensor != null)
			sensorManager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
		else
			lightTextView.setText("No est‡ disponible el sensor de temperatura");
		
		Sensor tmpSensor = sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
		if (tmpSensor != null)
			sensorManager.registerListener(tempSensorListener, tmpSensor, SensorManager.SENSOR_DELAY_NORMAL);
		else
			temperatureTextView.setText("No est‡ disponible el sensor de temperatura");
		

		Sensor pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
		if (pressureSensor != null)
			sensorManager.registerListener(pressureSensorListener, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
		else
			pressureTextView.setText("No est‡ disponible el sensor de presiones");		
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(lightSensorListener);
		sensorManager.unregisterListener(tempSensorListener);
		sensorManager.unregisterListener(pressureSensorListener);
	}
	
	private void updateUI() {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				if (!Float.isNaN(currentPres)) {
					pressureTextView.setText(currentPres + " atm");
					pressureTextView.invalidate();
				}
				
				if (!Float.isNaN(currentLigh)) {
					String weather = "Soleado";
					if (currentLigh <= SensorManager.LIGHT_CLOUDY)
						weather = "Noche";
					else if (currentLigh <= SensorManager.LIGHT_OVERCAST) 
						weather = "Nuboso";
					else if (currentLigh <= SensorManager.LIGHT_SUNLIGHT)
						weather = "Cubierto";
					
					lightTextView.setText(weather);
					lightTextView.invalidate();
				}
				
				if (!Float.isNaN(currentTemp)) {
					temperatureTextView.setText(currentTemp + " C");
					temperatureTextView.invalidate();
				}
			}
		});
	}

}
