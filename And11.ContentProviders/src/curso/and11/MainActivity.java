package curso.and11;

import java.net.URI;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog.Calls;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import curso.and11.R;
import curso.and11.ClientsProvider.Clients;

public class MainActivity extends Activity {
	
	private Button btnLlamadas;
	private Button btnLeer;
	private Button btnInsertar;
	private Button btnEliminar;
	private EditText text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bindUI();
		setListeners();
	}

	private void bindUI() {
		btnLlamadas = (Button)findViewById(R.id.btnLlamadas);
		btnLeer = (Button)findViewById(R.id.btnLeerClientes);
		btnInsertar = (Button)findViewById(R.id.btnInsertarCliente);
		btnEliminar = (Button)findViewById(R.id.btnEliminar);
		text = (EditText)findViewById(R.id.editText1);
	}
	
	private void setListeners() {
		btnLlamadas.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				calls();
			}
		});
		btnLeer.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				readClients();
			}
		});
		btnInsertar.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				insertClient();
			}
		});
		btnEliminar.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				deleteClient();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void readClients() {
		String[] projection = new String[] {
				Clients._ID,
				Clients.C_NAME,
				Clients.C_PHONE,
				Clients.C_EMAIL,
		};
		
		
		Uri uri = ClientsProvider.CONTENT_URI;		
		ContentResolver cr = getContentResolver();
		
		try {
			Cursor cursor = cr.query(uri, projection, null, null, null);
			if (cursor.moveToFirst()) {
				text.setText("");
				
				int cname = cursor.getColumnIndex(Clients.C_NAME);
				int cphone = cursor.getColumnIndex(Clients.C_PHONE);
				int cemail = cursor.getColumnIndex(Clients.C_EMAIL);
								
				do {
					text.append(cursor.getString(cname) + " " + 
								cursor.getString(cphone) + " " + 
								cursor.getString(cemail) + " \n");
					
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
		}		
	}
	
	private void insertClient() {
		ContentValues values = new ContentValues();
		values.put(Clients.C_EMAIL, "pablo@pabloformoso.com");
		values.put(Clients.C_NAME, "Pablo");
		values.put(Clients.C_PHONE, "608711440");
		
		ContentResolver cr = getContentResolver();
		cr.insert(ClientsProvider.CONTENT_URI, values);
	}
	
	private void deleteClient() {
		ContentResolver cr = getContentResolver();
		cr.delete(ClientsProvider.CONTENT_URI, Clients._ID + "= 1", null);
	}

	private void calls() {
		String[] projection = new String[] { Calls.TYPE, Calls.NUMBER };

		Uri llamadasUri = Calls.CONTENT_URI;

		ContentResolver cr = getContentResolver();

		Cursor cur = cr.query(llamadasUri, projection, // Columnas a
														// devolver
				null, // Condici�n de la query
				null, // Argumentos variables de la query
				null); // Orden de los resultados

		if (cur.moveToFirst()) {
			int type;
			String callType = "";
			String phone;

			int ctype = cur.getColumnIndex(Calls.TYPE);
			int cnumber = cur.getColumnIndex(Calls.NUMBER);
			
			
			text.setText("");

			do {
				type = cur.getInt(ctype);
				phone = cur.getString(cnumber);

				if (type == Calls.INCOMING_TYPE)
					callType = "ENTRANTE";
				else if (type == Calls.OUTGOING_TYPE)
					callType = "SALIENTE";
				else if (type == Calls.MISSED_TYPE)
					callType = "PERDIDA";

				text.append(callType + " - " + phone + "\n");

			} while (cur.moveToNext());
		} else {
			text.setText("No hay llamadas");
		}
	}
}
