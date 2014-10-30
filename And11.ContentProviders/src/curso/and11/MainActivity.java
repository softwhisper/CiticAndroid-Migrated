package curso.and11;

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
	
	private EditText text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// TODO
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void readClients() {
		// TODO
	}
	
	private void insertClient() {
		// TODO
	}
	
	private void deleteClient() {
		// TODO
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
