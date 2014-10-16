package curso.and02.alumni;

import curso.and02.alumni.R;
import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

/**
 * Esta Activity extiende directamente de ListActivity. Siguiendo la configuraci—n bajo convenci—n 
 * Android espera encontrar una list con un id "@android:id/list" para acceder a sus propiedades
 * de forma autom‡tica.
 * 
 * @author pablo
 *
 */
public class AlumniList extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alumni_list);
		// Show the Up button in the action bar.
		setupActionBar();
		
		// Creamos un array de Strings con alumnos "dummies" para el ejemplo
		String[] alumnis = new String[22];
		for (int i = 0; i < 22; i++) 
			alumnis[i] = "Alumno " + i;
		
		// TODO 2 - crear el ArrayAdapter. Recordar que lo hemos usado antes
		
		// TIP1: El layout de la celda se llama alumni_list_item
		// TIP2: El tercer par‡metro es el id del TextView donde vamos a poner el nombre
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.alumni_list_item, R.id.txtName, alumnis);
				
		// TODO 3 - asociar al adapter, fijaos de que extienda esta Activity
		setListAdapter(adapter);
		
		getListView().setOnItemClickListener(new OnItemClickListener() {
			
			// TODO 4 - completar el Listener con el evento adecuado poniendo en un Toast la posici—n seleccionada
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getApplicationContext(), "Alumno " + position, Toast.LENGTH_SHORT).show();
			}
			
			
		});
	}
	
	
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alumni_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
