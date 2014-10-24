package curso.and09;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import curso.and09.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AndroidFicheros extends Activity {

	private Button btnLeerRaw = null;
	private Button btnEscribirFichero = null;
	private Button btnLeerFichero = null;
	private Button btnEscribirSD = null;
	private Button btnLeerSD = null;
	private EditText txtResultado = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main_ficheros);

		btnLeerRaw = (Button) findViewById(R.id.BtnLeerRaw);
		btnEscribirFichero = (Button) findViewById(R.id.BtnEscribirFichero);
		btnLeerFichero = (Button) findViewById(R.id.BtnLeerFichero);
		btnEscribirSD = (Button) findViewById(R.id.BtnEscribirSD);
		btnLeerSD = (Button) findViewById(R.id.BtnLeerSD);
		txtResultado = (EditText) findViewById(R.id.TxtResultado);

		btnLeerRaw.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				String linea = "";

				try {
					InputStream fraw = getResources().openRawResource(R.raw.prueba_raw);

					BufferedReader brin = new BufferedReader(
							new InputStreamReader(fraw));

					linea = brin.readLine();

					fraw.close();

					txtResultado.setText(linea);
				} catch (Exception ex) {
					Log.e("Ficheros", "Error al leer fichero desde recurso raw");
					;
				}
			}
		});

		btnEscribirFichero.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				try {
					OutputStreamWriter fout = new OutputStreamWriter(
							openFileOutput("prueba_int.txt",
									Context.MODE_PRIVATE));

					fout.write("Texto de prueba.");
					fout.close();

					txtResultado.setText("Fichero creado!");
				} catch (Exception ex) {
					Log.e("Ficheros",
							"Error al escribir fichero a memoria interna");
				}
			}
		});

		btnLeerFichero.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				try {
					BufferedReader fin = new BufferedReader(
							new InputStreamReader(
									openFileInput("prueba_int.txt")));

					String texto = fin.readLine();
					fin.close();

					txtResultado.setText(texto);
				} catch (Exception ex) {
					Log.e("Ficheros",
							"Error al leer fichero desde memoria interna");
				}
			}
		});

		btnEscribirSD.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				boolean sdDisponible = false;
				boolean sdAccesoEscritura = false;

				// Comprobamos el estado de la memoria externa (tarjeta SD)
				String estado = Environment.getExternalStorageState();

				if (estado.equals(Environment.MEDIA_MOUNTED)) {
					sdDisponible = true;
					sdAccesoEscritura = true;
				} else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
					sdDisponible = true;
					sdAccesoEscritura = false;
				} else {
					sdDisponible = false;
					sdAccesoEscritura = false;
				}

				// Si la memoria externa está disponible y se puede escribir
				if (sdDisponible && sdAccesoEscritura) {
					try {
						File ruta_sd = Environment
								.getExternalStorageDirectory();

						File f = new File(ruta_sd.getAbsolutePath(),
								"prueba_sd.txt");

						OutputStreamWriter fout = new OutputStreamWriter(
								new FileOutputStream(f));

						fout.write("Texto de prueba.");
						fout.close();

						txtResultado.setText("Fichero SD creado!");
					} catch (Exception ex) {
						Log.e("Ficheros",
								"Error al escribir fichero a tarjeta SD");
					}
				}
			}
		});

		btnLeerSD.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				try {
					File ruta_sd = Environment.getExternalStorageDirectory();

					File f = new File(ruta_sd.getAbsolutePath(),
							"prueba_sd.txt");

					BufferedReader fin = new BufferedReader(
							new InputStreamReader(new FileInputStream(f)));

					String texto = fin.readLine();
					fin.close();

					txtResultado.setText(texto);
				} catch (Exception ex) {
					Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");
				}
			}
		});
	}
}