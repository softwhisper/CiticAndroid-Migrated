package curso.and12;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import curso.and12.R;

public class MainActivity extends Activity implements SensorEventListener {
	
	private SensorManager sensorManager;
	private boolean color = false;
	private TextView view;
	private long lastUpdate;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		view = (TextView)findViewById(R.id.textView1);
		view.setBackgroundColor(Color.GREEN);
		
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		lastUpdate = System.currentTimeMillis();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			getAccelerometer(event);
		}
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	


	private void getAccelerometer(SensorEvent event) {
		float[] values = event.values;

	    float x = values[0];
	    float y = values[1];
	    float z = values[2];

	    float accelationSquareRoot = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
	    long actualTime = System.currentTimeMillis();
	    
	    if (accelationSquareRoot >= 2) {
	      if (actualTime - lastUpdate < 200) {
	        return;
	      }
	      
	      lastUpdate = actualTime;
	      
	      Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT).show();
	      
	      if (color) {
	        view.setBackgroundColor(Color.GREEN);
	      } else {
	        view.setBackgroundColor(Color.RED);
	      }
	      
	      color = !color;
	    }
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this, 
									sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
									SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
	}
	
}
