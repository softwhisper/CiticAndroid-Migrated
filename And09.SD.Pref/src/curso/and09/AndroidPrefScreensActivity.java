package curso.and09;

import curso.and09.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AndroidPrefScreensActivity extends Activity {
   
	private Button btnPreferencias;
	private Button btnObtenerPreferencias;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnPreferencias = (Button)findViewById(R.id.BtnPreferencias);
        btnObtenerPreferencias = (Button)findViewById(R.id.BtnObtenerPreferencias);
        
        //asignamos evento: abrir nueva pantalla
        btnPreferencias.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(AndroidPrefScreensActivity.this, 
						                 PantallaOpciones.class));
			}
		});
        
        btnObtenerPreferencias.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(AndroidPrefScreensActivity.this, MostrarOpciones.class);
				startActivity(intent);
			}
		});
    }
	
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    
    
    /*
    private static final int OPCION_1 = 1;
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	menu.add(Menu.NONE, OPCION_1, Menu.NONE,
	"Leer ficheros").setIcon(R.drawable.tag);
	return true;
	}
     */
    
    
    //gestionamos clicks en elementos de menu
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {    	
		
    	Log.i("", "Seleccionado menu: " + featureId);
	
    	//en que elemento de menu se ha hecho click?
    	if (item.getItemId() == R.id.menu_ficheros){
    		Log.i("", "Abriendo ficheros");
    		Intent intent = new Intent(AndroidPrefScreensActivity.this, AndroidFicheros.class);
			startActivity(intent);
			return false;
    	}else if(item.getItemId() == R.id.menu_multimedia){
    		Log.i("", "Abriendo multimedia");
       		Intent intent = new Intent(AndroidPrefScreensActivity.this, FicherosMultimedia.class);
    			startActivity(intent);
    			return false;
    	}else if(item.getItemId() == R.id.menu_camara){
    		Log.i("", "Abriendo camara");
       		Intent intent = new Intent(AndroidPrefScreensActivity.this, Camara.class);
    			startActivity(intent);
    			return false;
    	} else if(item.getItemId() == R.id.menu_compartir){
    		Log.i("", "Compartir...");
    		Intent sendIntent = new Intent();
    		sendIntent.setAction(Intent.ACTION_SEND);
    		sendIntent.putExtra(Intent.EXTRA_TEXT, "Compartiendo una prueba.");
    		sendIntent.setType("text/plain");
    		startActivity(Intent.createChooser(sendIntent, "Enviar a..."));
    	}
    	
    	return super.onMenuItemSelected(featureId, item);
    }
}