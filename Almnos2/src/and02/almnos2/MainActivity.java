package and02.almnos2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


/**
 * Main 
 * 
 * @author pablo
 *
 */
public class MainActivity extends Activity {
	
	private static Context context;	
	
	private Button btnSaludar;
	private Button btnWeb;
	private Button btnLista;
	private Button btnForm;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        context = this;
        
        bindUI();
        setActions();
    }
    
    
    private void bindUI() {
    	btnSaludar = (Button)findViewById(R.id.btn_saludar);
    	btnWeb = (Button)findViewById(R.id.btn_web);
    	btnLista = (Button)findViewById(R.id.btn_list);
    	btnForm = (Button)findViewById(R.id.btn_form);
    }
    
    private void setActions() {
    	btnSaludar.setOnClickListener( new OnClickListener() {		
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "Hola", Toast.LENGTH_LONG).show();
			}
		});
    	
    	btnWeb.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				// Intent Implicito
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://marca.com"));
				startActivity(i);
				
				// Intent Explícito
				/* Intent intent = new Intent(context, WebActivity.class);
				intent.putExtra("url", "http:://www.google.es");
				startActivity(intent);
				*/
			}
		});
    	
    	btnForm.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, CreateForm.class);
				startActivity(intent);
			}
		});
    }
    
    
}
