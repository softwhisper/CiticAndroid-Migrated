package curso.and09;

import curso.and09.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MostrarOpciones extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_opciones);
        
        Button btnAceptar = (Button) findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener( new OnClickListener() {
        	public void onClick(View v) {
				try {
					Log.i("", "Finalizar!");
					finish();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
        
        SharedPreferences pref = getSharedPreferences("APP", 0);
			
		Log.i("", "Opcion 1: " + pref.getBoolean("opcion1", false));
		Log.i("", "Opcion 2: " + pref.getString("opcion2", ""));
		Log.i("", "Opcion 3: " + pref.getString("opcion3", ""));
		Log.i("", "Opcion 4: " + pref.getString("opcion4", ""));
		
		TextView tv = (TextView) findViewById(R.id.valor1);
		if (pref.getBoolean("opcion1", false)){
			tv.setText("Verdadero!");
		}else{
			tv.setText("Falso!");
		}
		tv = (TextView) findViewById(R.id.valor2);
		tv.setText(pref.getString("opcion2", ""));
		
		tv = (TextView) findViewById(R.id.valor3);
		tv.setText(pref.getString("opcion3", ""));
			
    }    
}
