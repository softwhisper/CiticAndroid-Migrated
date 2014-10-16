package curso.and02.alumni;

import curso.and02.alumni.R;
import curso.and02.alumni.models.Alumno;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;

/**
 * 
 * @author pablo
 *
 */
public class CreateAlumni extends Activity {
	
	EditText name;
	EditText lastname;
	RadioButton rbM2nica;
	RadioButton rbGtec;
	RadioButton rbLidia;
	RadioButton rbMads;
	RadioButton rbGac;
	Spinner spAreas;
	String area = "";
	CheckBox is_present;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_form);
		
		name = (EditText)findViewById(R.id.editText1);
		lastname = (EditText)findViewById(R.id.editText2);
		rbM2nica = (RadioButton)findViewById(R.id.radio0);
		rbGtec = (RadioButton)findViewById(R.id.radio1);
		rbLidia = (RadioButton)findViewById(R.id.radio2);
		rbMads = (RadioButton)findViewById(R.id.radio3);
		rbGac = (RadioButton)findViewById(R.id.radio4);
		spAreas = (Spinner)findViewById(R.id.areas);
		is_present = (CheckBox)findViewById(R.id.checkBox1);
		
		// load content for the Spinner
		final String[] areasList = new String[] {
				"IA", "TIC", "Matem‡ticas", "Otros"
		};
		
		// Adater thar inflates the spinner from a array
		ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, areasList);
		spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spAreas.setAdapter(spinAdapter);
		
		// Trigger an event when item is selected
		spAreas.setOnItemSelectedListener( 
		new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
				area = areasList[position];
			}
			
            public void onNothingSelected(AdapterView<?> parent) {
                area = "";
            }			
		});
		
		
		// Save the alumni info
		Button btnSave = (Button)findViewById(R.id.btnSave);
		btnSave.setOnClickListener( new OnClickListener() {
			
			public void onClick(View v) {
				Toast.makeText(CreateAlumni.this, "OK", Toast.LENGTH_LONG).show();
				
				Alumno alm = new Alumno();
				alm.setName(name.getText().toString());
				alm.setLastname(lastname.getText().toString());
				
				if (rbM2nica.isChecked()) {
					alm.setGroupo("M2NICA");
				} else if (rbGtec.isChecked()) {
					alm.setGroupo("GTEC");
				} else if (rbLidia.isChecked()) {
					alm.setGroupo("Lidia");
				} else if (rbMads.isChecked()) {
					alm.setGroupo("MADS");
				} else {
					alm.setGroupo("GAC");
				}
				
				alm.setIs_present(is_present.isChecked());
				alm.setArea(area);
				
				Log.d("CREATE", alm.toString());
				
				Intent intent = new Intent(CreateAlumni.this, AlimniDetail.class);
				Bundle b = new Bundle();
				b.putSerializable("alumno", alm);
				intent.putExtras(b);
				
				startActivity(intent);
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_alumni, menu);
		return true;
	}

}
