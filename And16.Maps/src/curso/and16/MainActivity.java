package curso.and16;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import curso.and16.R;

public class MainActivity extends Activity {
  static final LatLng UDC = new LatLng(43.332627, -8.408017);
  static final LatLng UDC_EXT = new LatLng(43.332700, -8.408023);
  private GoogleMap map;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
    if (map != null) {
    	Marker citic = map.addMarker(new MarkerOptions().position(UDC)
    			.title("UDC"));
    	Marker citic_ex = map.addMarker(new MarkerOptions()
        		.position(UDC_EXT)
        		.title("UDC")
        		.snippet("Universidad....")
        		.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));

    	// Move the camera instantly to hamburg with a zoom of 15.
    	map.moveCamera(CameraUpdateFactory.newLatLngZoom(UDC, 15));
    	
    	// Zoom in, animating the camera.
    	map.animateCamera(CameraUpdateFactory.zoomTo(18), 5000, null);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    //getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }

} 